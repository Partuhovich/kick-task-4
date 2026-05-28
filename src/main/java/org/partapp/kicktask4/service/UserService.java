package org.partapp.kicktask4.service;

import org.partapp.kicktask4.entity.UserEntity;
import org.partapp.kicktask4.exception.ServiceException;

import java.util.List;

public interface UserService {
    boolean authenticate(String username, String password) throws ServiceException;
    boolean registration(String username, String password) throws ServiceException;
    UserEntity getUserById(Long userId) throws ServiceException;
    List<UserEntity> getAllUsers(UserEntity currentUser) throws ServiceException;
    boolean deleteUser(UserEntity user, UserEntity currentUser) throws ServiceException;
    boolean userExists(String username) throws ServiceException;
    UserEntity getUserByName(String username) throws ServiceException;
}
