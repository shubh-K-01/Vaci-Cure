package com.demeatrix.VaciCure.repository;

import com.demeatrix.VaciCure.entity.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    Optional<Patient> findByPublicId(String publicId);

    Optional<Patient> findByNationalIdHash(String nationalIdHash);

    Optional<Patient> findByPhoneNumber(String phoneNumber);

    boolean existsByNationalIdHash(String nationalIdHash);

    /** Full-text search by name — case-insensitive contains. */
    @Query("SELECT p FROM Patient p WHERE LOWER(p.fullName) LIKE LOWER(CONCAT('%', :name, '%'))")
    Page<Patient> searchByName(@Param("name") String name, Pageable pageable);

    /** Find all children belonging to a guardian user. */
    Page<Patient> findByGuardianUser_UserId(Long guardianUserId, Pageable pageable);
}
