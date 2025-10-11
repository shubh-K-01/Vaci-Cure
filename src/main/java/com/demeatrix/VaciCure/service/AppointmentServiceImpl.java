package com.demeatrix.VaciCure.service;

import com.demeatrix.VaciCure.dto.Appointment.AppointmentDTO;
import com.demeatrix.VaciCure.entity.Appointment;
import com.demeatrix.VaciCure.entity.ChildPatient;
import com.demeatrix.VaciCure.entity.Doctor;
import com.demeatrix.VaciCure.enums.AppointmentStatus;
import com.demeatrix.VaciCure.exception.AppointmentException.AppointmentNotFoundException;
import com.demeatrix.VaciCure.exception.AppointmentException.InvalidAppointmentException;
import com.demeatrix.VaciCure.exception.DoctorException.DoctorNotFoundException;
import com.demeatrix.VaciCure.exception.GlobalExceptionHandler;
import com.demeatrix.VaciCure.exception.PatientException.PatientNotFoundException;
import com.demeatrix.VaciCure.mapper.UserMapper;
import com.demeatrix.VaciCure.repository.AppointmentRepository;
import com.demeatrix.VaciCure.repository.ChildPatientRepository;
import com.demeatrix.VaciCure.repository.DoctorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final DoctorService doctorService;
    private final ChildPatientRepository childPatientRepository;
    private final UserMapper userMapper;

    @Transactional
    @Override
    public AppointmentDTO createNewAppointment(AppointmentDTO appointmentDTO) {
        validateAppointmentRequest(appointmentDTO);

        Appointment appointment = createAppointmentFromDTO(appointmentDTO);
        Appointment savedAppointment = appointmentRepository.save(appointment);

        return userMapper.toDTO(savedAppointment);
    }

    private void validateAppointmentRequest(AppointmentDTO appointmentDTO) {
        if (appointmentDTO.getId() != null) {
            throw new InvalidAppointmentException("Cannot create an appointment that already has an ID");
        }

        if (appointmentDTO.getAppointmentAt() == null) {
            throw new InvalidAppointmentException("Appointment time cannot be null");
        }

        if (appointmentDTO.getAppointmentAt().isBefore(LocalDateTime.now())) {
            throw new InvalidAppointmentException("Appointment must be in the future");
        }

        if (!doctorRepository.existsByLicenseNumber(appointmentDTO.getDoctorLicenseNumber())) {
            throw new DoctorNotFoundException("Doctor with license number " +
                    appointmentDTO.getDoctorLicenseNumber() + " not found");
        }

        if (!childPatientRepository.existsById(appointmentDTO.getChildPatientId())) {
            throw new RuntimeException("Patient with ID " +
                    appointmentDTO.getChildPatientId() + " not found");
        }
    }

    private Appointment createAppointmentFromDTO(AppointmentDTO appointmentDTO) {
        Appointment appointment = userMapper.toEntity(appointmentDTO);
        ChildPatient childPatient = childPatientRepository.findById(appointmentDTO.getChildPatientId())
                .orElseThrow(() -> new PatientNotFoundException("Patient not found with ID: " + appointmentDTO.getChildPatientId()));

        Doctor doctor = doctorRepository.findDoctorByLicenseNumber(appointmentDTO.getDoctorLicenseNumber())
                .orElseThrow(() -> new DoctorNotFoundException("Doctor not found with license number: " + appointmentDTO.getDoctorLicenseNumber()));

        appointment.setChildPatient(childPatient);
        appointment.setDoctor(doctor);
        appointment.setStatus(appointmentDTO.getStatus() != null ?
                appointmentDTO.getStatus() : AppointmentStatus.SCHEDULED);

        childPatient.getAppointments().add(appointment);

        return appointment;
    }

    @Transactional
    @Override
    public AppointmentDTO updateAppointment(AppointmentDTO appointmentDTO, Long id) {
        Appointment existingAppointment = appointmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Appointment not found"));

        existingAppointment.setAppointmentAt(appointmentDTO.getAppointmentAt());
        existingAppointment.setReason(appointmentDTO.getReason());
        existingAppointment.setStatus(appointmentDTO.getStatus());

        return userMapper.toDTO(appointmentRepository.save(existingAppointment));
    }

    @Transactional
    public void cancelAppointment(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment with ID " + id + " not found"));

        if (appointment.getStatus() == AppointmentStatus.SCHEDULED) {
            if (appointment.getAppointmentAt().isBefore(LocalDateTime.now())) {
                appointment.setStatus(AppointmentStatus.CANCELLED);
            } else {
                throw new RuntimeException("Appointment cannot be cancelled");
            }
        }
        appointmentRepository.save(appointment);
    }

    @Transactional
    @Override
    public AppointmentDTO getAppointment(Long id) throws RuntimeException {
        Appointment existingAppointment = appointmentRepository.findById(id).orElseThrow(() -> new AppointmentNotFoundException("No appointment found with this Id {}" + id));

        return userMapper.toDTO(existingAppointment);
    }

    @Override
    @Transactional
    public List<AppointmentDTO> getAllAppointments(Long childPatientId) throws RuntimeException {
        return appointmentRepository.findByChildPatient_ChildPatientId(childPatientId);
    }
}