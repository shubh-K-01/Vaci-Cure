package com.demeatrix.VaciCure.service;

import com.demeatrix.VaciCure.dto.ChildPatientDTO;

public interface ChildPatientService {
    Long addChildPatient(ChildPatientDTO childPatientDTO);

    ChildPatientDTO getChildPatientById(Long childPatient);

    Boolean isChildPatientExist(Long id);
}
