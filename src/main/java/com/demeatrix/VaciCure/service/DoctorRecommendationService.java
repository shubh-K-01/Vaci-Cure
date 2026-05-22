package com.demeatrix.VaciCure.service;

import com.demeatrix.VaciCure.dto.ChildPatientDTO;
import com.demeatrix.VaciCure.entity.Doctor;

import java.util.List;
import java.util.Optional;

public interface DoctorRecommendationService {
    Optional<String> getDepartment(String reason);

    Optional<Doctor> getAvailableDoctor(String reason, List<Doctor> doctorList);
}
