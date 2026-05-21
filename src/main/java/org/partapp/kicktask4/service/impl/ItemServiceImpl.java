package org.partapp.kicktask4.service.impl;

import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.partapp.kicktask4.dao.impl.ItemDaoImpl;
import org.partapp.kicktask4.entity.ItemEntity;
import org.partapp.kicktask4.entity.UserEntity;
import org.partapp.kicktask4.exception.DaoException;
import org.partapp.kicktask4.exception.ServiceException;
import org.partapp.kicktask4.service.ItemService;
import org.partapp.kicktask4.service.UserService;

import java.util.List;

public class ItemServiceImpl implements ItemService {
    private static final Logger logger = LogManager.getLogger(ItemServiceImpl.class);
    private static ItemServiceImpl instance = new ItemServiceImpl();

    private ItemServiceImpl() {}

    public static ItemServiceImpl getInstance() {
        return instance;
    }

    @Override
    public boolean addItem(String name, String description, String ownerName) throws ServiceException {

        logger.info("Item service - add item for owner: {}", ownerName);

        if (name == null || name.trim().isEmpty()) {
            logger.warn("Item name is empty");
            return false;
        }
        ItemDaoImpl itemDao = ItemDaoImpl.getInstance();

        UserEntity owner = UserServiceImpl.getInstance().getUserByName(ownerName);
        ItemEntity newItem = new ItemEntity(name, description, owner.getId());
        try {
            return itemDao.addItem(newItem);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<ItemEntity> getAllItems() {
        logger.info("Item service - get all items");


    }

    @Override
    public boolean deleteItem(Long itemId) throws ServiceException {
        logger.info("Item service - delete item: {}", itemId);

        if (itemId == null || itemId <= 0) {
            logger.warn("Invalid item id");
            return false;
        }

        ItemDaoImpl itemDao = ItemDaoImpl.getInstance();
        try {
            return itemDao.deleteItem(itemId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}