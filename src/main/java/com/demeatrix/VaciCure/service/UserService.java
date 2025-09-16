package com.demeatrix.VaciCure.service;

import com.demeatrix.VaciCure.dto.UserDTO;
import com.demeatrix.VaciCure.exception.UserException.UserAlreadyExistsException;
import com.demeatrix.VaciCure.exception.UserException.UserDoesNotExistException;

public interface UserService {
    public void registerUser(UserDTO userDTO) throws UserAlreadyExistsException;
    public UserDTO loginUser(UserDTO userDTO) throws UserDoesNotExistException;
    public void updateUser(UserDTO userDTO) throws UserDoesNotExistException;
    public UserDTO getUserById(String id) throws UserDoesNotExistException;
}
