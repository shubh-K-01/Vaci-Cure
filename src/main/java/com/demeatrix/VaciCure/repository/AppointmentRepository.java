package com.demeatrix.VaciCure.repository;

import com.demeatrix.VaciCure.dto.Appointment.AppointmentDTO;
import com.demeatrix.VaciCure.entity.Appointment;
import com.demeatrix.VaciCure.entity.ChildPatient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<AppointmentDTO> findAllAppointmentsOfPatient(Long childPatientId);
}