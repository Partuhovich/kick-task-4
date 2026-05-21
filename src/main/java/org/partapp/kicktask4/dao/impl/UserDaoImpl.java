package org.partapp.kicktask4.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.partapp.kicktask4.connection.ConnectionPool;
import org.partapp.kicktask4.controller.listener.SessionAttributeListenerImpl;
import org.partapp.kicktask4.dao.BaseDao;
import org.partapp.kicktask4.dao.UserDao;
import org.partapp.kicktask4.entity.UserEntity;
import org.partapp.kicktask4.exception.DaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl extends BaseDao<UserEntity> implements UserDao {
    private static final Logger logger = LogManager.getLogger(UserDaoImpl.class);
    private static final String SELECT_USER_BY_NAME = "SELECT * FROM users WHERE username = ?";
    private static final String USER_EXISTS = "SELECT EXISTS (SELECT 1 FROM users WHERE username = ?)";
    private static final String ADD_USER = "INSERT INTO users (username, password) VALUES (?, ?)";
    private static UserDaoImpl instance = new UserDaoImpl();

    private UserDaoImpl() {
    }

    public static UserDaoImpl getInstance() {
        return instance;
    }

    @Override
    public boolean delete(UserEntity userEntity) {
        return false;
    }

    @Override
    public List<UserEntity> findAll(UserEntity userEntity) {
        return List.of();
    }

    @Override
    public UserEntity update(UserEntity userEntity) {
        return null;
    }

    @Override
    public UserEntity findByName(String username) throws DaoException {
        logger.info("User DAO authenticate");
        try (Connection connection = ConnectionPool.getInstance().getConection();
             PreparedStatement statement = connection.prepareStatement(SELECT_USER_BY_NAME)) {

            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            UserEntity user = new UserEntity();
            if (resultSet.next()) {
                user = new UserEntity(
                        resultSet.getLong("id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getBoolean("isAdmin")
                );
            }
            return user;
        } catch (SQLException e) {
            logger.error("Error finding user: {}. {}", username, e);
            throw new DaoException(e);
        }
    }


    @Override
    public boolean insert(UserEntity userEntity) throws DaoException {
        logger.debug("User DAO registration with username: {}", userEntity.getUsername());

        boolean result = false;
        try (Connection connection = ConnectionPool.getInstance().getConection();
             PreparedStatement statement = connection.prepareStatement(ADD_USER)) {
            statement.setString(1, userEntity.getUsername());
            statement.setString(2, userEntity.getPassword());
            int resultSet = statement.executeUpdate();
            return resultSet > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public boolean userExists(String username) throws DaoException {
        logger.info("User DAO user exists");
        boolean exists = false;
        try (   Connection connection = ConnectionPool.getInstance().getConection();
                PreparedStatement statement = connection.prepareStatement(USER_EXISTS)) {

            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                exists = resultSet.getBoolean(1);
            }
            return exists;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}











