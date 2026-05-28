package org.partapp.kicktask4.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.partapp.kicktask4.dao.impl.UserDaoImpl;
import org.partapp.kicktask4.entity.UserEntity;
import org.partapp.kicktask4.exception.DaoException;
import org.partapp.kicktask4.exception.ServiceException;
import org.partapp.kicktask4.service.UserService;
import org.partapp.kicktask4.util.PasswordEncoder;

import java.util.List;
import java.util.regex.Pattern;

import static org.partapp.kicktask4.service.impl.param.ServiceParameter.*;

public class UserServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);
    private static final PasswordEncoder encoder = PasswordEncoder.getInstance();
    private static UserServiceImpl instance = new UserServiceImpl();

    private UserServiceImpl() {
    }


    public static UserServiceImpl getInstance() {
        return instance;
    }

    @Override
    public boolean authenticate(String username, String password) throws ServiceException {
        logger.debug("Login for user: {}", username);

        if (!validateUsername(username)) {
            logger.warn("Login failed - invalid username format: {}", username);
            return false;
        }

        if (!validatePassword(password)) {
            logger.warn("Login failed - invalid password format for user: {}", username);
            return false;
        }

        UserDaoImpl userDao = UserDaoImpl.getInstance();
        boolean match = false;
        try {
            if (!userExists(username)) {
                logger.warn("Login failed - user not found: {}", username);
                return false;
            }
            UserEntity userFromBd = userDao.findByName(username);
            match = encoder.matches(password, userFromBd.getPassword());
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return match;
    }


    @Override
    public boolean registration(String username, String password) throws ServiceException {
        logger.debug("Registration for user: {}", username);
        boolean result = false;

        if (!validateUsername(username)) {
            logger.warn("Registration failed - invalid username format: {}", username);
            return false;
        }

        if (!validatePassword(password)) {
            logger.warn("Registration failed - invalid password format for user: {}", username);
            return false;
        }

        String hashedPassword = encoder.encode(password);
        UserEntity newUser = new UserEntity(null, username, hashedPassword);
        UserDaoImpl userDao = UserDaoImpl.getInstance();

        try {
            result = userDao.insert(newUser);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }


    @Override
    public UserEntity getUserByName(String username) throws ServiceException {
        logger.debug("User service get user: {}", username);
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        try {
            return userDao.findByName(username);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public UserEntity getUserById(Long userId) throws ServiceException {
        logger.debug("User service get user with id: {}", userId);
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        try {
            return userDao.findById(userId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<UserEntity> getAllUsers(UserEntity currentUser) throws ServiceException {
        logger.debug("User service get all users");
        if(!currentUser.isAdmin()) {
            throw new ServiceException("Not admin");
        }
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        try {
            return userDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean deleteUser(UserEntity user, UserEntity currentUser) throws ServiceException {
        logger.debug("User service delete user");
        if(!currentUser.isAdmin()) {
            throw new ServiceException("Not admin");
        }
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        boolean result = false;
        try {
            result = userDao.delete(user);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }


    @Override
    public boolean userExists(String username) throws ServiceException
    {
        logger.debug("User service user exists");
        boolean result = false;

        if (username == null || username.trim().isEmpty()) {
            logger.debug("User can not exists without username");
            return false;
        }

        UserDaoImpl userDao = UserDaoImpl.getInstance();
        try {
            result = userDao.userExists(username);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    private boolean validateUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            logger.warn("Username is null or empty");
            return false;
        }

        String trimmedUsername = username.trim();

        if (trimmedUsername.length() < MIN_USERNAME_LENGTH ||
                trimmedUsername.length() > MAX_USERNAME_LENGTH) {
            logger.warn("Username length invalid: {}. Must be between {} and {}",
                    trimmedUsername.length(), MIN_USERNAME_LENGTH, MAX_USERNAME_LENGTH);
            return false;
        }

        boolean matches = Pattern.matches(USERNAME_PATTERN, trimmedUsername);
        if (!matches) {
            logger.warn("Username contains invalid characters: {}", trimmedUsername);
        }
        return matches;
    }

    private boolean validatePassword(String password) {
        if (password == null || password.isEmpty()) {
            logger.warn("Password is null or empty");
            return false;
        }

        if (password.length() < MIN_PASSWORD_LENGTH ||
                password.length() > MAX_PASSWORD_LENGTH) {
            logger.warn("Password length invalid: {}. Must be between {} and {}",
                    password.length(), MIN_PASSWORD_LENGTH, MAX_PASSWORD_LENGTH);
            return false;
        }

        boolean matches = Pattern.matches(PASSWORD_PATTERN, password);
        if (!matches) {
            logger.warn("Password does not meet requirements");
        }
        return matches;
    }
}
