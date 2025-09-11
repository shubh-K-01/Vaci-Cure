package com.demeatrix.VaciCure.repository;

import com.demeatrix.VaciCure.dto.ChildPatientDTO;
import com.demeatrix.VaciCure.entity.ChildPatient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ChildPatientRepository extends JpaRepository<ChildPatient, Long> {

    List<ChildPatient> findByChildBirthDate(LocalDate childBirthDate);
    List<ChildPatient> findByChildBirthDateBetween(LocalDate startDate, LocalDate endDate);
    List<ChildPatient> findByChildPatientNameContaining(String query);

    @Query("SELECT cp FROM ChildPatient cp WHERE cp.childPatientName LIKE %?1% AND cp.childBirthDate BETWEEN :startDate AND :endDate")
    List<Object[]> findByChildPatientNameContainingAndChildBirthDateBetween(String query, LocalDate startDate, LocalDate endDate);

    @Query("SELECT cp.gender, COUNT(cp.gender) FROM ChildPatient cp GROUP BY cp.gender")
    List<Object[]> countEachGenderType();

    @Query(value = "SELECT * from ChildPatient", nativeQuery = true)
    Page<ChildPatient> findAllChildPatients(Pageable pageable);
}
