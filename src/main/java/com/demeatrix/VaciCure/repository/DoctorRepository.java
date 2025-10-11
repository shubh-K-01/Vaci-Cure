package com.demeatrix.VaciCure.repository;

import com.demeatrix.VaciCure.entity.Doctor;
import com.demeatrix.VaciCure.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.print.Doc;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findByEmail(String email);

    // Standard finder (Spring Data auto-implements this)
    Optional<Doctor> findDoctorByLicenseNumber(String licenseNumber);

    // Custom existence check (JPQL version)
    @Query("SELECT COUNT(d) > 0 FROM Doctor d WHERE d.licenseNumber = :licenseNumber")
    boolean existsByLicenseNumber(@Param("licenseNumber") String licenseNumber);
}