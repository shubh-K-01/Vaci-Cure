package com.demeatrix.VaciCure.controller;

import com.demeatrix.VaciCure.dto.Appointment.AppointmentDTO;
import com.demeatrix.VaciCure.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appointment")
@RequiredArgsConstructor
public class AppointmentController {
    private final AppointmentService appointmentService;

    @GetMapping("/get")
    public ResponseEntity<AppointmentDTO> getAppointmentById(@Valid @RequestBody Long appointmentId) {
        return ResponseEntity.ok(appointmentService.getAppointment(appointmentId));
    }

    @PostMapping("/create")
    public ResponseEntity<AppointmentDTO> createNewAppointment(@Valid @RequestBody AppointmentDTO appointmentDTO) {
        return ResponseEntity.ok(appointmentService.createNewAppointment(appointmentDTO, appointmentDTO.getChildPatient().getChildPatientId(), appointmentDTO.getDoctor().getDoctorId()));
    }
}
