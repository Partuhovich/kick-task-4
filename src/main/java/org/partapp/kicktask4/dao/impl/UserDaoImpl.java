package org.partapp.kicktask4.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.partapp.kicktask4.connection.ConnectionPool;
import org.partapp.kicktask4.dao.BaseDao;
import org.partapp.kicktask4.dao.UserDao;
import org.partapp.kicktask4.entity.UserEntity;
import org.partapp.kicktask4.exception.DaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements BaseDao<UserEntity>, UserDao {
    private static final Logger logger = LogManager.getLogger(UserDaoImpl.class);
    private static final String SELECT_USER_BY_NAME = "SELECT * FROM users WHERE username = ?";
    private static final String SELECT_USER_BY_ID = "SELECT * FROM users WHERE id = ?";
    private static final String USER_EXISTS = "SELECT EXISTS (SELECT 1 FROM users WHERE username = ?)";
    private static final String ADD_USER = "INSERT INTO users (username, password) VALUES (?, ?)";
    private static final String DELETE_USER = "DELETE FROM users WHERE id = ?";
    private static final String FIND_ALL_USERS = "SELECT id, username, password, isAdmin FROM users";
    private static final String UPDATE_USER = "UPDATE users SET username = ?, password = ? WHERE id = ?";
    private static UserDaoImpl instance = new UserDaoImpl();

    private UserDaoImpl() {
    }

    public static UserDaoImpl getInstance() {
        return instance;
    }

    @Override
    public boolean insert(UserEntity userEntity) throws DaoException {
        logger.info("User DAO registration with username: {}", userEntity.getUsername());

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(ADD_USER)) {

            statement.setString(1, userEntity.getUsername());
            statement.setString(2, userEntity.getPassword());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            logger.error("Error adding user with id {}: {}", userEntity.getId(), e.getMessage());
            throw new DaoException("Failed to add user: " + userEntity.getUsername(), e);
        }
    }

    @Override
    public UserEntity update(UserEntity userEntity) throws DaoException {
        logger.info("Updating user with id: {}", userEntity.getId());

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_USER)) {

            statement.setString(1, userEntity.getUsername());
            statement.setString(2, userEntity.getPassword());
            statement.setLong(3, userEntity.getId());

            int affectedRows = statement.executeUpdate();
            logger.info(affectedRows > 0 ? "User with id {} updated successfully" : "No user found with id {} for update", userEntity.getId());

            return userEntity;

        } catch (SQLException e) {
            logger.error("Error updating user with id {}: {}", userEntity.getId(), e.getMessage());
            throw new DaoException("Failed to update user: " + userEntity.getUsername(), e);
        }
    }

    @Override
    public boolean delete(UserEntity userEntity) throws DaoException {
        logger.info("Delete user with id: {}", userEntity.getId());

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_USER)) {

            statement.setLong(1, userEntity.getId());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            logger.error("Error deleting user with id {}: {}", userEntity.getId(), e.getMessage());
            throw new DaoException("Failed to delete user: " + userEntity.getUsername(), e);
        }
    }

    @Override
    public List<UserEntity> findAll() throws DaoException {
        logger.info("Finding all users");

        List<UserEntity> users = new ArrayList<>();

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_USERS)) {

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                UserEntity user = new UserEntity(
                        resultSet.getLong("id"),
                        resultSet.getString("username"),
                        resultSet.getBoolean("isAdmin")
                );
                users.add(user);
            }

            logger.info("Found {} users", users.size());
            return users;

        } catch (SQLException e) {
            logger.error("Error finding all users.", e);
            throw new DaoException("Failed to find all users", e);
        }
    }

    @Override
    public UserEntity findByName(String username) throws DaoException {
        logger.info("Find user by name");

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_USER_BY_NAME)) {

            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            UserEntity user = new UserEntity();
            if (resultSet.next()) {
                user.setId(resultSet.getLong("id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setAdmin(resultSet.getBoolean("isAdmin"));
            }
            return user;
        } catch (SQLException e) {
            logger.error("Error finding user: {}. {}", username, e);
            throw new DaoException("Failed to find user: " + username, e);
        }
    }

    public UserEntity findById(Long id) throws DaoException {
        logger.debug("Find user by id");

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_USER_BY_ID)) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            UserEntity user = new UserEntity();
            if (resultSet.next()) {
                user.setId(resultSet.getLong("id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setAdmin(resultSet.getBoolean("isAdmin"));
            }
            return user;
        } catch (SQLException e) {
            logger.error("Error finding user: {}. {}", id, e);
            throw new DaoException("Failed to find user: " + id, e);
        }
    }

    public boolean userExists(String username) throws DaoException {
        logger.info("User DAO user exists");

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(USER_EXISTS)) {

            boolean exists = false;
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                exists = resultSet.getBoolean(1);
            }

            return exists;
        } catch (SQLException e) {
            logger.error("Error determining existence of a user: {}. {}", username, e);
            throw new DaoException("Failed determining existence of a user: " + username, e);
        }
    }
}











