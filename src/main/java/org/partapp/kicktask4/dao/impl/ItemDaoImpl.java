package org.partapp.kicktask4.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.partapp.kicktask4.connection.ConnectionPool;
import org.partapp.kicktask4.dao.ItemDao;
import org.partapp.kicktask4.entity.ItemEntity;
import org.partapp.kicktask4.exception.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ItemDaoImpl implements ItemDao {
    private static final Logger logger = LogManager.getLogger(ItemDaoImpl.class);
    private static ItemDaoImpl instance = new ItemDaoImpl();

    private static final String ADD_ITEM = "INSERT INTO items (name, description, ownerId) VALUES (?, ?, ?)";
    private static final String DELETE_ITEM = "DELETE FROM items WHERE id = ?";

    private ItemDaoImpl() {
    }

    public static ItemDaoImpl getInstance() {
        return instance;
    }

    public boolean insert(ItemEntity item) throws DaoException {
        logger.info("Adding new item: {}", item.getName());
        try (Connection connection = ConnectionPool.getInstance().getConection();
             PreparedStatement statement = connection.prepareStatement(ADD_ITEM)) {
            statement.setString(1, item.getName());
            statement.setString(2, item.getDescription());
            statement.setString(3, item.getOwnerId().toString());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public boolean delete(ItemEntity item) throws DaoException {
        logger.info("Deleting item with id: {}", item.getId());

        try (Connection connection = ConnectionPool.getInstance().getConection();
             PreparedStatement statement = connection.prepareStatement(DELETE_ITEM)) {
            statement.setLong(1, item.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }


}
