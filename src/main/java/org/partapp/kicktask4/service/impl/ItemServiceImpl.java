package org.partapp.kicktask4.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.partapp.kicktask4.dao.impl.ItemDaoImpl;
import org.partapp.kicktask4.entity.ItemEntity;
import org.partapp.kicktask4.entity.UserEntity;
import org.partapp.kicktask4.exception.DaoException;
import org.partapp.kicktask4.exception.ServiceException;
import org.partapp.kicktask4.service.ItemService;

import java.util.List;

public class ItemServiceImpl implements ItemService {
    private static final Logger logger = LogManager.getLogger(ItemServiceImpl.class);
    private static ItemServiceImpl instance = new ItemServiceImpl();

    private ItemServiceImpl() {
    }

    public static ItemServiceImpl getInstance() {
        return instance;
    }

    @Override
    public boolean addItem(String name, String description, Long ownerId) throws ServiceException {
        logger.debug("Item service - add item for owner: {}", ownerId);

        ItemDaoImpl itemDao = ItemDaoImpl.getInstance();
        ItemEntity newItem = new ItemEntity(name, description, ownerId);
        try {
            return itemDao.insert(newItem);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<ItemEntity> getAllItems() throws ServiceException {
        logger.debug("Item service - get all items");
        ItemDaoImpl itemDao = ItemDaoImpl.getInstance();
        try {
            return itemDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

    }

    @Override
    public ItemEntity getItemById(Long itemId) throws ServiceException {
        logger.debug("Item service - get item: {}", itemId);

        ItemDaoImpl itemDao = ItemDaoImpl.getInstance();
        try {
            return itemDao.findById(itemId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

    }

    @Override
    public boolean deleteItem(Long itemId, UserEntity currentUser) throws ServiceException {
        logger.debug("Item service - delete item: {}", itemId);
        ItemDaoImpl itemDao = ItemDaoImpl.getInstance();
        try {
            ItemEntity item = itemDao.findById(itemId);
            if (currentUser.getId() != item.getOwnerId() && !currentUser.isAdmin()) {
                logger.warn("Delete item failed - user {} has no permission to delete item {}",
                        currentUser.getId(), itemId);
                return false;
            }

            return itemDao.delete(item);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean updateItem(ItemEntity oldItem, ItemEntity newItem, UserEntity currentUser) throws ServiceException {
        logger.debug("Item service - update item: {}", newItem.getId());

        ItemDaoImpl itemDao = ItemDaoImpl.getInstance();
        try {
            if (currentUser.getId() != oldItem.getOwnerId() && !currentUser.isAdmin()) {
                logger.warn("Update item failed - user {} has no permission to update item {}",
                        currentUser.getId(), oldItem.getId());
                return false;
            }
            ItemEntity item = itemDao.update(newItem);
            return !item.equals(oldItem); // ???
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}