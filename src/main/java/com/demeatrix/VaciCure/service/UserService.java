package com.demeatrix.VaciCure.service;

import com.demeatrix.VaciCure.dto.UserDTO;
import com.demeatrix.VaciCure.exception.UserException.UserAlreadyExistsException;
import com.demeatrix.VaciCure.exception.UserException.UserDoesNotExistException;

public interface UserService {
     void registerUser(UserDTO userDTO) throws UserAlreadyExistsException;
     UserDTO loginUser(UserDTO userDTO) throws UserDoesNotExistException;
     void updateUser(UserDTO userDTO) throws UserDoesNotExistException;
     UserDTO getUserById(String id) throws UserDoesNotExistException;
}
