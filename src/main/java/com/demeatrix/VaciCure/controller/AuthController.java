package com.demeatrix.VaciCure.controller;

import com.demeatrix.VaciCure.dto.LoginRequestDTO;
import com.demeatrix.VaciCure.dto.LoginResponseDTO;
import com.demeatrix.VaciCure.dto.SignupRequestDTO;
import com.demeatrix.VaciCure.dto.SignupResponseDTO;
import com.demeatrix.VaciCure.entity.User;
import com.demeatrix.VaciCure.mapper.UserMapper;
import com.demeatrix.VaciCure.security.AuthService;
import com.demeatrix.VaciCure.security.AuthUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final AuthUtil authUtil;
    private final UserMapper userMapper;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDTO> signup(@Valid @RequestBody SignupRequestDTO signupRequestDTO) {
        return ResponseEntity.ok(
                authService.signup(signupRequestDTO)
        );
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        return ResponseEntity.ok(authService.login(loginRequestDTO));
    }
}
