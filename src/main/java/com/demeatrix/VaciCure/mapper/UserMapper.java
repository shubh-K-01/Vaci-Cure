package com.demeatrix.VaciCure.mapper;

import com.demeatrix.VaciCure.dto.ChildPatientDTO;
import com.demeatrix.VaciCure.dto.DoctorDTO;
import com.demeatrix.VaciCure.dto.SignupRequestDTO;
import com.demeatrix.VaciCure.dto.UserDTO;
import com.demeatrix.VaciCure.entity.ChildPatient;
import com.demeatrix.VaciCure.entity.Doctor;
import com.demeatrix.VaciCure.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toDTO(User user);
    User toEntity(UserDTO userDTO);


    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "role", ignore = true)
    User toEntity(SignupRequestDTO signupRequestDTO);

    DoctorDTO toDTO(Doctor doctor);
    Doctor toEntity(DoctorDTO doctorDTO);

    ChildPatientDTO toDTO(ChildPatient childPatient);
    ChildPatient toEntity(ChildPatientDTO childPatientDTO);


}
