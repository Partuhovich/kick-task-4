package org.partapp.kicktask4.service;

import org.partapp.kicktask4.exception.ServiceException;

public interface UserService {
    public boolean authenticate(String username, String password) throws ServiceException;
    public boolean registration(String username, String password) throws ServiceException;
    public boolean userExists(String username) throws ServiceException;
}
