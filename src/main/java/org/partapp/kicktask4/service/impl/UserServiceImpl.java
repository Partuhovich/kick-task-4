package org.partapp.kicktask4.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.partapp.kicktask4.dao.impl.UserDaoImpl;
import org.partapp.kicktask4.entity.UserEntity;
import org.partapp.kicktask4.exception.DaoException;
import org.partapp.kicktask4.exception.ServiceException;
import org.partapp.kicktask4.service.UserService;

public class UserServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);
    private static UserServiceImpl instance = new UserServiceImpl();

    private UserServiceImpl() {
    }


    public static UserServiceImpl getInstance() {
        return instance;
    }

    @Override
    public boolean authenticate(String username, String password) throws ServiceException {
        logger.debug("Login for user: {}", username);
        if (username == null || password == null) {
            logger.warn("Login failed: username or password is null");
            return false;
        }
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        boolean match = false;
        try {
            UserEntity userFromBd = userDao.findByName(username);
            match = userFromBd.getPassword().equals(password);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return match;
    }

    @Override
    public boolean registration(String username, String password) throws ServiceException {
        logger.debug("Registration for user: {}", username);
        if (username == null || password == null) {
            logger.warn("Registration failed: username or password is null");
            return false;
        }
        UserEntity newUser = new UserEntity(null, username, password);
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        boolean result = false;
        try {
            result = userDao.insert(newUser);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public UserEntity getUserByName(String username) throws ServiceException {
        logger.info("User service get user: {}", username);
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        try{
            return userDao.findByName(username);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }



    @Override
    public boolean userExists(String username) throws ServiceException {
        logger.info("User service user exists");
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        boolean exists = false;
        try{
            exists = userDao.userExists(username);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return exists;
    }
}
