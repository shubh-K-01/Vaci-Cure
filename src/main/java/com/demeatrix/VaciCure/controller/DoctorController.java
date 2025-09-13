package com.demeatrix.VaciCure.controller;

import com.demeatrix.VaciCure.dto.DoctorDTO;
import com.demeatrix.VaciCure.entity.Doctor;
import com.demeatrix.VaciCure.exception.DoctorNotFoundException;
import com.demeatrix.VaciCure.mapper.UserMapper;
import com.demeatrix.VaciCure.repository.DoctorRepository;
import com.demeatrix.VaciCure.service.DoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profile/doctors")
public class DoctorController {

    private final DoctorRepository doctorRepository;
    private final UserMapper userMapper;
    private final DoctorService doctorService;

    @PostMapping("/add")
    public ResponseEntity<Long> addDoctor(@Valid @RequestBody DoctorDTO doctorDTO) {
        Long savedDoctorId = doctorService.addDoctor(doctorDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDoctorId);
    }

    @GetMapping("/get/{licenseNumber}")
    public ResponseEntity<DoctorDTO> findDoctorByLicenseNumber(@PathVariable String licenseNumber) {
        DoctorDTO doctorDTO = doctorRepository.findDoctorByLicenseNumber(licenseNumber)
                .map(userMapper::toDTO)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor with Id " + licenseNumber + " not found"));
        return new ResponseEntity<>(doctorDTO, HttpStatus.OK);
    }
}
