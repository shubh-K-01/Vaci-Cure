package com.demeatrix.VaciCure.service;

import com.demeatrix.VaciCure.dto.UserDTO;
import com.demeatrix.VaciCure.entity.User;
import com.demeatrix.VaciCure.exception.InvalidPasswordException;
import com.demeatrix.VaciCure.exception.UserAlreadyExistsException;
import com.demeatrix.VaciCure.exception.UserDoesNotExistException;
import com.demeatrix.VaciCure.mapper.UserMapper;
import com.demeatrix.VaciCure.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    public void registerUser(UserDTO userDTO) {
        log.info("Attempting to register user with email: {}",
                userDTO.getEmail() != null ? userDTO.getEmail().replaceAll("(?<=.{2}).(?=.*@)", "*") : "null");

        Optional<User> existingUser= userRepository.findByEmail(userDTO.getEmail());
        if(existingUser.isPresent()){
            throw new UserAlreadyExistsException("User already exists with this email");
        }
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
    }

    @Override
    public UserDTO loginUser(UserDTO userDTO) {
        User user = userRepository.findByEmailAndPassword(userDTO.getEmail(), userDTO.getPassword())
                .orElseThrow(() -> new UserDoesNotExistException("No User exists with this email" + userDTO.getEmail()));

        if(!passwordEncoder.matches(userDTO.getPassword(), user.getPassword())){
            throw new InvalidPasswordException("Password does not match for the registered email");
        }
        user.setPassword(null);
        return userMapper.toDTO(user);
    }

    @Override
    public void updateUser(UserDTO userDTO) {

    }

    @Override
    public UserDTO getUserById(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserDoesNotExistException("No user found with given Id " + id));
        return userMapper.toDTO(user);
    }
}
