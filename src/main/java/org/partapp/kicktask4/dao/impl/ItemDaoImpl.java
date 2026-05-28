package org.partapp.kicktask4.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.partapp.kicktask4.connection.ConnectionPool;
import org.partapp.kicktask4.dao.BaseDao;
import org.partapp.kicktask4.dao.ItemDao;
import org.partapp.kicktask4.entity.ItemEntity;
import org.partapp.kicktask4.entity.UserEntity;
import org.partapp.kicktask4.exception.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDaoImpl implements BaseDao<ItemEntity>, ItemDao {
    private static final Logger logger = LogManager.getLogger(ItemDaoImpl.class);
    private static ItemDaoImpl instance = new ItemDaoImpl();

    private static final String ADD_ITEM = "INSERT INTO items (name, description, ownerId) VALUES (?, ?, ?)";
    private static final String DELETE_ITEM = "DELETE FROM items WHERE id = ?";
    private static final String UPDATE_ITEM = "UPDATE items SET name = ?, description = ? WHERE id = ?";
    private static final String FIND_ALL_ITEMS = "SELECT id, name, description, ownerId FROM items";
    private static final String FIND_ITEM_BY_ID = "SELECT name, description, ownerId FROM items WHERE id = ?";
    private ItemDaoImpl() {
    }

    public static ItemDaoImpl getInstance() {
        return instance;
    }

    @Override
    public boolean insert(ItemEntity item) throws DaoException {
        logger.info("Adding new item: {}", item.getName());

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(ADD_ITEM)) {

            statement.setString(1, item.getName());
            statement.setString(2, item.getDescription());
            statement.setString(3, item.getOwnerId().toString());

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public ItemEntity update(ItemEntity item) throws DaoException {
        logger.info("Updating item with id: {}", item.getId());

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_ITEM)) {

            statement.setString(1, item.getName());
            statement.setString(2, item.getDescription());
            statement.setLong(3, item.getId());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                logger.warn("Updating item failed, no rows affected. Item id: {}", item.getId());
            }

            return item;
        } catch (SQLException e) {
            logger.error("Failed to update item: {}", item.getId(), e);
            throw new DaoException("Failed to update item", e);
        }
    }

    @Override
    public boolean delete(ItemEntity item) throws DaoException {
        logger.info("Deleting item with id: {}", item.getId());

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_ITEM)) {
            statement.setLong(1, item.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Failed to delete item: {}", item.getId(), e);
            throw new DaoException("Failed to delete item", e);
        }
    }

    @Override
    public List<ItemEntity> findAll() throws DaoException {
        logger.debug("Finding all items");
        List<ItemEntity> items = new ArrayList<>();

        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_ITEMS)) {

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                ItemEntity item = new ItemEntity();
                item.setId(resultSet.getLong("id"));
                item.setName(resultSet.getString("name"));
                item.setDescription(resultSet.getString("description"));
                item.setOwnerId(resultSet.getLong("ownerId"));
                items.add(item);
            }

            logger.info("Found {} items", items.size());
            return items;

        } catch (SQLException e) {
            logger.error("Failed to find all items", e);
            throw new DaoException("Failed to find all items", e);
        }
    }

    public ItemEntity findById(Long itemId) throws DaoException {
        logger.debug("Finding item by id: {}", itemId);

        try(Connection connection = ConnectionPool.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement(FIND_ITEM_BY_ID)){

            statement.setLong(1, itemId);
            ResultSet resultSet = statement.executeQuery();

            ItemEntity item = new ItemEntity();
            if (resultSet.next()) {
                item.setId(itemId);
                item.setName(resultSet.getString("name"));
                item.setDescription(resultSet.getString("description"));
                item.setOwnerId(resultSet.getLong("ownerId"));
            }

            return item;
        } catch (SQLException e) {
            logger.error("Failed to find item by id: {}", itemId, e);
            throw new DaoException("Failed to find item", e);
        }
    }
}
