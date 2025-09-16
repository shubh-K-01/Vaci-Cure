package com.demeatrix.VaciCure.controller;

import com.demeatrix.VaciCure.dto.LoginRequestDTO;
import com.demeatrix.VaciCure.dto.LoginResponseDTO;
import com.demeatrix.VaciCure.dto.SignupRequestDTO;
import com.demeatrix.VaciCure.dto.SignupResponseDTO;
import com.demeatrix.VaciCure.entity.User;
import com.demeatrix.VaciCure.exception.UserException.UserDoesNotExistException;
import com.demeatrix.VaciCure.mapper.UserMapper;
import com.demeatrix.VaciCure.security.AuthService;
import com.demeatrix.VaciCure.security.AuthUtil;
import com.demeatrix.VaciCure.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final AuthService authService;
    private final AuthUtil authUtil;
    private final UserMapper userMapper;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDTO> signup(@Valid @RequestBody SignupRequestDTO signupRequestDTO) {

        SignupResponseDTO savedUser = authService.signup(signupRequestDTO); // returns saved User
        User user = userMapper.toEntity(signupRequestDTO);

        String accessToken = authUtil.generateAccessToken(user); // generate JWT after signup
        String refreshToken = authUtil.generateRefreshToken(user);

        SignupResponseDTO response = new SignupResponseDTO(
                accessToken,
                refreshToken,
                "User registered successfully"
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> loginUser(@RequestBody LoginRequestDTO loginRequestDTO) throws UserDoesNotExistException {
        return new ResponseEntity<>(authService.login(loginRequestDTO), HttpStatus.OK);
    }
}
