package com.demeatrix.VaciCure.repository;

import com.demeatrix.VaciCure.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

}