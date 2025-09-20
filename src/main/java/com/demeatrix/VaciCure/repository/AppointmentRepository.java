package com.demeatrix.VaciCure.repository;

import com.demeatrix.VaciCure.dto.Appointment.AppointmentDTO;
import com.demeatrix.VaciCure.entity.Appointment;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<AppointmentDTO> findByChildPatient_ChildPatientId(Long childPatientId);
}