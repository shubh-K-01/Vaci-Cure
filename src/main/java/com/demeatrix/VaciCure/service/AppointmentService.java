package com.demeatrix.VaciCure.service;

import com.demeatrix.VaciCure.entity.Appointment;
import com.demeatrix.VaciCure.entity.ChildPatient;
import com.demeatrix.VaciCure.entity.Doctor;
import com.demeatrix.VaciCure.repository.AppointmentRepository;
import com.demeatrix.VaciCure.repository.ChildPatientRepository;
import com.demeatrix.VaciCure.repository.DoctorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final ChildPatientRepository childPatientRepository;

    @Transactional
    public Appointment createNewAppointment(Appointment appointment, Long doctorId, Long childPatientId) {
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(() -> new RuntimeException("Doctor not found"));
        ChildPatient childPatient = childPatientRepository.findById(childPatientId).orElseThrow(() -> new RuntimeException("Child patient not found"));

        if(appointment.getId() != null) throw new RuntimeException("Appointment already exists");

        appointment.setChildPatient(childPatient);
        appointment.setDoctor(doctor);

        childPatient.getAppointments().add(appointment);
        appointmentRepository.save(appointment);
        return appointment;
    }

    @Transactional
    public Appointment updateAppointment(Appointment appointment, Long id) {
        Appointment existingAppointment = appointmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Appointment not found"));

        existingAppointment.setAppointment_at(appointment.getAppointment_at());
        existingAppointment.setAppointment_at(appointment.getAppointment_at());

        return appointmentRepository.save(existingAppointment);
    }

}
