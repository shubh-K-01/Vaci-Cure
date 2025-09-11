package com.demeatrix.VaciCure.service;

import com.demeatrix.VaciCure.dto.ChildPatientDTO;

public interface ChildPatientService {
    public Long addChildPatient(ChildPatientDTO childPatientDTO) throws RuntimeException;
    public ChildPatientDTO getChildPatientById(Long childPatient) throws RuntimeException;
}
