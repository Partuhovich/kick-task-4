package org.partapp.kicktask4.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.partapp.kicktask4.dao.impl.UserDaoImpl;
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
        //todo validate
        logger.info("User service authenticate");
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        boolean match = false;
        try {
            match = userDao.authenticate(username, password);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return match;
    }

    @Override
    public boolean registration(String username, String password) throws ServiceException {
        //todo validate
        logger.info("User service registration");
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        boolean result = false;
        try {
            result = userDao.registerUser(username, password);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
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
