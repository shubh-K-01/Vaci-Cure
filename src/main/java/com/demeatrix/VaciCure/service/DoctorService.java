package com.demeatrix.VaciCure.service;

import com.demeatrix.VaciCure.dto.DoctorDTO;
import com.demeatrix.VaciCure.entity.Doctor;

public interface DoctorService {
    public Long addDoctor(DoctorDTO doctorDTO);
    public void updateDoctor(DoctorDTO doctorDTO);
    public DoctorDTO getDoctor(String licenseNumber);
}
