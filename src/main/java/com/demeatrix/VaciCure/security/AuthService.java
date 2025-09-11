package com.demeatrix.VaciCure.security;

import com.demeatrix.VaciCure.dto.LoginRequestDTO;
import com.demeatrix.VaciCure.dto.LoginResponseDTO;
import com.demeatrix.VaciCure.dto.SignupRequestDTO;
import com.demeatrix.VaciCure.dto.SignupResponseDTO;
import com.demeatrix.VaciCure.entity.User;
import com.demeatrix.VaciCure.exception.InvalidCredentialsException;
import com.demeatrix.VaciCure.exception.UserAlreadyExistsException;
import com.demeatrix.VaciCure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final AuthUtil authUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SignupResponseDTO signup(SignupRequestDTO signupRequestDTO) {
        if (userRepository.existsByUsername(signupRequestDTO.getUsername())) {
            throw new UserAlreadyExistsException("User already exists with email: " + signupRequestDTO.getEmail() );
        }

        User newUser = User.builder()
                .username(signupRequestDTO.getUsername())
                .fullName(signupRequestDTO.getFullName())
                .gender(signupRequestDTO.getGender())
                .email(signupRequestDTO.getEmail())
                .password(passwordEncoder.encode(signupRequestDTO.getPassword()))
                .role(signupRequestDTO.getRole())
                .mobileNumber(signupRequestDTO.getMobileNumber())
                .address(signupRequestDTO.getAddress())
                .createdAt(LocalDateTime.now())
                .build();

        User savedUser = userRepository.save(newUser);

        String accessToken = authUtil.generateAccessToken(savedUser);
        String refreshToken = authUtil.generateRefreshToken(savedUser);

        return new SignupResponseDTO(accessToken, refreshToken,
                "User registered successfully");
    }

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequestDTO.getUsername(),
                            loginRequestDTO.getPassword()
                    )
            );

            User user = (User) authentication.getPrincipal();

            String accessToken = authUtil.generateAccessToken(user);
//            String refreshToken = authUtil.generateRefreshToken(user);

            log.info("User '{}' logged in successfully at {}",
                    user.getUsername(), Instant.now());

            return new LoginResponseDTO(accessToken, user.getUserId());

        } catch (BadCredentialsException ex) {
            log.warn("Failed login attempt for username '{}' at {}",
                    loginRequestDTO.getUsername(), Instant.now());
            throw new InvalidCredentialsException("Invalid username or password");
        }
    }
}
