package com.demeatrix.VaciCure;

import com.demeatrix.VaciCure.dto.ChildPatientDTO;
import com.demeatrix.VaciCure.entity.ChildPatient;
import com.demeatrix.VaciCure.service.ChildPatientServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.demeatrix.VaciCure.repository.ChildPatientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@SpringBootTest
public class TestChildPatient {

    @Autowired
    private ChildPatientRepository childPatientRepository;

    @Autowired
    private ChildPatientServiceImpl childPatientService;

    @Test
    public void patientRepositoryTest() {
        Page<ChildPatient> childPatientList = childPatientRepository.findAll(PageRequest.of(0, 2));

        for(ChildPatient childPatient : childPatientList) {
            System.out.println(childPatient);
        }
    }

    @Test
    public void childPatientServiceTest() {
        ChildPatientDTO childPatientDTO = childPatientService.getChildPatientById(1001L);
        System.out.println(childPatientDTO);

        List<Object[]> genderTypeList = childPatientRepository.countEachGenderType();

        for(Object[] objects : genderTypeList) {
            System.out.println(objects[0] + " : " + objects[1]);
        }
    }

}
