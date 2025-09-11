package com.demeatrix.VaciCure.service;

import com.demeatrix.VaciCure.dto.ChildPatientDTO;
import com.demeatrix.VaciCure.entity.ChildPatient;
import com.demeatrix.VaciCure.mapper.UserMapper;
import com.demeatrix.VaciCure.repository.ChildPatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
@RequiredArgsConstructor
public class ChildPatientServiceImpl implements ChildPatientService {

    private final ChildPatientRepository childPatientRepository;
    private final UserMapper userMapper;

    @Transactional
    @Override
    public Long addChildPatient(ChildPatientDTO childPatientDTO) throws RuntimeException {
        Boolean existingChildPatient = childPatientRepository.existsById(childPatientDTO.getChildPatientId());
        if(existingChildPatient) throw new RuntimeException("Child with id " + childPatientDTO.getChildPatientId() + " already exists");

        ChildPatient childPatient = childPatientRepository.save(userMapper.toEntity(childPatientDTO));
        return childPatient.getChildPatientId();
    }

    @Transactional
    @Override
    public ChildPatientDTO getChildPatientById(@PathVariable Long childPatientId) {
        ChildPatient existingChildPatient = childPatientRepository.findById(childPatientId).orElseThrow(() -> new RuntimeException("Child with id " + childPatientId + " not found"));

         return userMapper.toDTO(existingChildPatient);
    }


}
