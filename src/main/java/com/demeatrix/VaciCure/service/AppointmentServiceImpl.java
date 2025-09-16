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
    public AppointmentDTO createNewAppointment(AppointmentDTO appointmentDTO, Long doctorId, Long childPatientId) {
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(() -> new DoctorNotFoundException("Doctor not found"));

        ChildPatient childPatient = childPatientRepository.findById(childPatientId).orElseThrow(() -> new RuntimeException("Child patient not found"));

        if (appointmentDTO.getId() != null) throw new RuntimeException("Appointment already exists");

        appointmentDTO.setChildPatient(childPatient);
        appointmentDTO.setDoctor(doctor);

        childPatient.getAppointments().add(appointmentDTO);
        appointmentRepository.save(appointmentDTO);
        return appointmentDTO;
    }

    @Transactional
    public AppointmentDTO updateAppointment(AppointmentDTO appointmentDTO, Long id) {
        Appointment existingAppointment = appointmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Appointment not found"));

        existingAppointment.setAppointment_at(appointmentDTO.getAppointment_at());
        existingAppointment.setAppointment_at(appointmentDTO.getAppointment_at());

        return userMapper.toDTO(appointmentRepository.save(existingAppointment));
    }

    @Transactional
    public void deleteAppointment(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException("Appointment with ID " + id + " not found"));

        if (appointment.getStatus() == AppointmentStatus.SCHEDULED) {
            if (appointment.getAppointment_at().isBefore(LocalDateTime.now())) {
                appointment.setStatus(AppointmentStatus.CANCELLED);
            }
        }
        appointmentRepository.save(appointment);
    }

    @Override
    public AppointmentDTO getAppointment(Long id) throws RuntimeException {
        Appointment existingAppointment = appointmentRepository.findById(id).orElseThrow( () -> new AppointmentNotFoundException("No appointment found with this Id {}" + id));

        return userMapper.toDTO(existingAppointment);
    }

    @Override
    public List<AppointmentDTO> getAllAppointments(Long childPatientId) throws RuntimeException {
        return appointmentRepository.findAllAppointmentsOfPatient(childPatientId);
    }
}
