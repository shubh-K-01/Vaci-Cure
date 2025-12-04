package com.demeatrix.VaciCure.repository;

import com.demeatrix.VaciCure.entity.VaccinationRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VaccinationRecordRepository extends JpaRepository<VaccinationRecord, Long> {

    Optional<VaccinationRecord> findBySchedule_ScheduleId(Long scheduleId);

    boolean existsBySchedule_ScheduleId(Long scheduleId);
}
