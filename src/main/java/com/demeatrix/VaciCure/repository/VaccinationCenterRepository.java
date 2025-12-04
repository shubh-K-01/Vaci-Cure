package com.demeatrix.VaciCure.repository;

import com.demeatrix.VaciCure.entity.VaccinationCenter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VaccinationCenterRepository extends JpaRepository<VaccinationCenter, Long> {

    List<VaccinationCenter> findByIsActiveTrue();

    List<VaccinationCenter> findByOrganization_OrganizationId(Long organizationId);

    boolean existsByNameAndOrganization_OrganizationId(String name, Long organizationId);
}
