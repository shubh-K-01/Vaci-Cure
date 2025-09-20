package com.demeatrix.VaciCure.service;

import com.demeatrix.VaciCure.dto.Appointment.AppointmentDTO;

import java.util.List;

public interface AppointmentService {
     AppointmentDTO createNewAppointment(AppointmentDTO appointmentDTO, Long doctorId, Long childPatientId) throws RuntimeException;
     AppointmentDTO updateAppointment(AppointmentDTO appointmentDTO, Long id) throws RuntimeException;
     void cancelAppointment(Long id) throws RuntimeException;
     AppointmentDTO getAppointment(Long id) throws RuntimeException;

    List<AppointmentDTO> getAllAppointments(Long childPatientId) throws RuntimeException;
}
