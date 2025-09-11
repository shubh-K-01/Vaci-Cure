package com.demeatrix.VaciCure.repository;

import com.demeatrix.VaciCure.entity.Doctor;
import com.demeatrix.VaciCure.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.print.Doc;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findByEmail(String email);
    Optional<Doctor> findDoctorByLicenseNumber(String licenseNumber);
}