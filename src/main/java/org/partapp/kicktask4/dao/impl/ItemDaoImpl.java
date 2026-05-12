package org.partapp.kicktask4.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.partapp.kicktask4.connection.ConnectionPool;
import org.partapp.kicktask4.dao.ItemDao;
import org.partapp.kicktask4.exception.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ItemDaoImpl implements ItemDao {
    private static final Logger logger = LogManager.getLogger(ItemDaoImpl.class);
    private static ItemDaoImpl instance = new ItemDaoImpl();

    private static final String ADD_ITEM = "INSERT INTO items (name, description, owner_id) VALUES (?, ?, ?)";
    private static final String DELETE_ITEM = "DELETE FROM items WHERE id = ?";

    private ItemDaoImpl() {}

    public static ItemDaoImpl getInstance() {
        return instance;
    }

    @Override
    public boolean addItem(String name, String description, Long ownerId) throws DaoException {
        logger.info("Adding new item: {}", name);
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConection();

        try (PreparedStatement statement = connection.prepareStatement(ADD_ITEM)) {
            statement.setString(1, name);
            statement.setString(2, description);
            statement.setLong(3, ownerId);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }

    @Override
    public boolean deleteItem(Long itemId) throws DaoException {
        logger.info("Deleting item with id: {}", itemId);
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        Connection connection = connectionPool.getConection();

        try (PreparedStatement statement = connection.prepareStatement(DELETE_ITEM)) {
            statement.setLong(1, itemId);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            connectionPool.releaseConnection(connection);
        }
    }
}
