package org.partapp.kicktask4.service;

import org.partapp.kicktask4.entity.UserEntity;
import org.partapp.kicktask4.exception.ServiceException;

public interface UserService {
    public boolean authenticate(String username, String password) throws ServiceException;
    public boolean registration(String username, String password) throws ServiceException;
    public boolean userExists(String username) throws ServiceException;
    public UserEntity getUserByName(String username) throws ServiceException;
}
