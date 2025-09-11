package com.demeatrix.VaciCure.service;

import com.demeatrix.VaciCure.dto.DoctorDTO;
import com.demeatrix.VaciCure.entity.Doctor;
import com.demeatrix.VaciCure.exception.DoctorAlreadyExistsException;
import com.demeatrix.VaciCure.exception.DoctorNotFoundException;
import com.demeatrix.VaciCure.mapper.UserMapper;
import com.demeatrix.VaciCure.repository.DoctorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;


    @Override
    public Long addDoctor(DoctorDTO doctorDTO) {
        if (doctorDTO.getEmail() != null && doctorRepository.findByEmail(doctorDTO.getEmail()).isPresent()) {
            log.warn("Doctor with ID {} already exists", doctorDTO.getDoctorId());
            throw new DoctorAlreadyExistsException("Doctor with email" + doctorDTO.getEmail() + " already exists");
        }

        if (doctorDTO.getLicenseNumber() != null && doctorRepository.findDoctorByLicenseNumber(doctorDTO.getLicenseNumber()).isPresent()) {
            log.warn("Doctor with license number {} already exists", doctorDTO.getLicenseNumber());
            throw new DoctorAlreadyExistsException("Doctor with license number " + doctorDTO.getLicenseNumber() + " already exists");
        }

        String currentLicenseNumber = UUID.randomUUID().toString().toUpperCase();
        doctorDTO.setLicenseNumber(currentLicenseNumber);

        doctorDTO.setPassword(passwordEncoder.encode(doctorDTO.getPassword()));

        log.info("Generated License Number: {}", currentLicenseNumber);
        log.info("DoctorDTO before mapping: {}", doctorDTO);

        Doctor doctor = userMapper.toEntity(doctorDTO);
        Doctor savedDoctor = doctorRepository.save(doctor);
        log.info("Doctor with ID {} saved successfully", savedDoctor.getDoctorId());
        return savedDoctor.getDoctorId();
    }

    @Override
    public void updateDoctor(DoctorDTO doctorDTO) {

    }

    public DoctorDTO getDoctor(String licenseNumber) {
       validateLicenseNumber(licenseNumber);
        log.info("Fetching doctor with license number {}", licenseNumber);

        return doctorRepository.findDoctorByLicenseNumber(licenseNumber)
                .map(userMapper::toDTO)
                .orElseThrow(() -> {
                    log.error("Doctor with license number {} not found", licenseNumber);
                    return new DoctorNotFoundException("Doctor with license number " + licenseNumber + " not found");
                });
    }

    private void validateLicenseNumber(String licenseNumber) {
        if (licenseNumber == null) {
            throw new IllegalArgumentException("Invalid license number provided");
        }
    }

}
