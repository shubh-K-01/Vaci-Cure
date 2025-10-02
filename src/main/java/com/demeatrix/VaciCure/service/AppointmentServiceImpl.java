package com.demeatrix.VaciCure.service;

import com.demeatrix.VaciCure.dto.Appointment.AppointmentDTO;
import com.demeatrix.VaciCure.entity.Appointment;
import com.demeatrix.VaciCure.entity.ChildPatient;
import com.demeatrix.VaciCure.entity.Doctor;
import com.demeatrix.VaciCure.enums.AppointmentStatus;
import com.demeatrix.VaciCure.exception.AppointmentException.AppointmentNotFoundException;
import com.demeatrix.VaciCure.exception.DoctorException.DoctorNotFoundException;
import com.demeatrix.VaciCure.mapper.UserMapper;
import com.demeatrix.VaciCure.repository.AppointmentRepository;
import com.demeatrix.VaciCure.repository.ChildPatientRepository;
import com.demeatrix.VaciCure.repository.DoctorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final ChildPatientRepository childPatientRepository;
    private final UserMapper userMapper;

    @Transactional
    @Override
    public AppointmentDTO createNewAppointment(AppointmentDTO appointmentDTO) {

        Appointment appointment = userMapper.toEntity(appointmentDTO);

        DateTimeFormatter formatterWithSeconds = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        DateTimeFormatter formatterWithoutSeconds = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        String dateTimeStr = appointmentDTO.getAppointmentAt();
        LocalDateTime parseDateAndTime;

        try {
            parseDateAndTime = LocalDateTime.parse(dateTimeStr, formatterWithSeconds);
        } catch (Exception e) {
            parseDateAndTime = LocalDateTime.parse(dateTimeStr, formatterWithoutSeconds);
        }

        Doctor doctor = doctorRepository.findDoctorByLicenseNumber(appointmentDTO.getDoctorLicenseNumber()).orElseThrow(() -> new DoctorNotFoundException("Doctor not found"));

        ChildPatient childPatient = childPatientRepository.findById(appointmentDTO.getChildPatientId()).orElseThrow(() -> new RuntimeException("Child patient not found"));

        if (appointmentDTO.getId() != null) throw new RuntimeException("Appointment already exists");

        appointment.setChildPatient(childPatient);
        appointment.setDoctor(doctor);

        childPatient.getAppointments().add(appointment);

        appointment.setAppointmentAt(parseDateAndTime);
        Appointment savedAppointment = appointmentRepository.save(appointment);
        return userMapper.toDTO(savedAppointment);
    }

    @Transactional
    @Override
    public AppointmentDTO updateAppointment(AppointmentDTO appointmentDTO, Long id) {
        Appointment existingAppointment = appointmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Appointment not found"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime parseDateAndTime = LocalDateTime.parse(appointmentDTO.getAppointmentAt(), formatter);

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
