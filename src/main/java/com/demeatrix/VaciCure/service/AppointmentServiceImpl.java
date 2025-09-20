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
    public AppointmentDTO createNewAppointment(AppointmentDTO appointmentDTO, Long doctorId, Long childPatientId) {

        Appointment appointment = userMapper.toEntity(appointmentDTO);

        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(() -> new DoctorNotFoundException("Doctor not found"));

        ChildPatient childPatient = childPatientRepository.findById(childPatientId).orElseThrow(() -> new RuntimeException("Child patient not found"));

        if (appointmentDTO.getId() != null) throw new RuntimeException("Appointment already exists");

        appointment.setChildPatient(childPatient);
        appointment.setDoctor(doctor);

        childPatient.getAppointments().add(appointment);

        Appointment savedAppointment = appointmentRepository.save(appointment);
        return userMapper.toDTO(savedAppointment);
    }

    @Transactional
    @Override
    public AppointmentDTO updateAppointment(AppointmentDTO appointmentDTO, Long id) {
        Appointment existingAppointment = appointmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Appointment not found"));

        existingAppointment.setAppointment_at(appointmentDTO.getAppointment_at());
        existingAppointment.setAppointment_at(appointmentDTO.getAppointment_at());

        return userMapper.toDTO(appointmentRepository.save(existingAppointment));
    }

    @Transactional
    public void cancelAppointment(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment with ID " + id + " not found"));

        if (appointment.getStatus() == AppointmentStatus.SCHEDULED) {
            if (appointment.getAppointment_at().isBefore(LocalDateTime.now())) {
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
        Appointment existingAppointment = appointmentRepository.findById(id).orElseThrow( () -> new AppointmentNotFoundException("No appointment found with this Id {}" + id));

        return userMapper.toDTO(existingAppointment);
    }

    @Override
    @Transactional
    public List<AppointmentDTO> getAllAppointments(Long childPatientId) throws RuntimeException {
        return appointmentRepository.findByChildPatient_ChildPatientId(childPatientId);
    }
}
