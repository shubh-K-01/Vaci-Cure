package com.demeatrix.VaciCure.service;

import com.demeatrix.VaciCure.dto.DoctorDTO;
import com.demeatrix.VaciCure.entity.Doctor;

public interface DoctorService {
     Long addDoctor(DoctorDTO doctorDTO);

     void updateDoctor(DoctorDTO doctorDTO);

     DoctorDTO getDoctor(String licenseNumber);

     Boolean isDoctorExist(String licenseNumber);
}
