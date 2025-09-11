package com.demeatrix.VaciCure.dto;

import com.demeatrix.VaciCure.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequestDTO {
    private String username;
    private String fullName;
    private String gender;
    private String email;
    private String password;
    private UserRole role;
    private String mobileNumber;
    private String address;
}