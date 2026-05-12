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
    private static final String SELECT_PASSWORD_FROM_USERS_WHERE_USERNAME = "SELECT password FROM users WHERE username = ?";
    private static final String USER_EXISTS = "SELECT EXISTS (SELECT 1 FROM users WHERE username = ?)";
    private static final String ADD_USER = "INSERT INTO users (username, password) VALUES (?, ?)";
    private static UserDaoImpl instance = new UserDaoImpl();

    private UserDaoImpl() {
    }

    public static UserDaoImpl getInstance() {
        return instance;
    }

    @Override
    public boolean insert(UserEntity userEntity) {
        return false;
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
    public boolean authenticate(String username, String password) throws DaoException {
        logger.info("User DAO authenticate");
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConection();
        boolean match = false;
        try (PreparedStatement statement = connection.prepareStatement(SELECT_PASSWORD_FROM_USERS_WHERE_USERNAME)) {

            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            List<UserEntity> users = new ArrayList<>();
            String passFromDb;
            if (resultSet.next()) {
                passFromDb = resultSet.getString("password");
                match = password.equals(passFromDb);
            }
            return match;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    public boolean registerUser(String username, String password) throws DaoException {
        logger.info("User DAO registration");
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConection();
        boolean result = false;
        try (PreparedStatement statement = connection.prepareStatement(ADD_USER)){
            statement.setString(1, username);
            statement.setString(2, password);
            int resultSet = statement.executeUpdate();
            return resultSet > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    public boolean userExists(String username) throws DaoException {
        logger.info("User DAO user exists");
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConection();
        boolean exists = false;
        try (PreparedStatement statement = connection.prepareStatement(USER_EXISTS)){

            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                exists = resultSet.getBoolean(1);
            }
            return exists;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }
}











