package org.partapp.kicktask4.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.partapp.kicktask4.dao.impl.ItemDaoImpl;
import org.partapp.kicktask4.exception.DaoException;
import org.partapp.kicktask4.exception.ServiceException;
import org.partapp.kicktask4.service.ItemService;

public class ItemServiceImpl implements ItemService {
    private static final Logger logger = LogManager.getLogger(ItemServiceImpl.class);
    private static ItemServiceImpl instance = new ItemServiceImpl();

    private ItemServiceImpl() {}

    public static ItemServiceImpl getInstance() {
        return instance;
    }

    @Override
    public boolean addItem(String name, String description) throws ServiceException {
        logger.info("Item service - add item for owner: {}");

        if (name == null || name.trim().isEmpty()) {
            logger.warn("Item name is empty");
            return false;
        }

        ItemDaoImpl itemDao = ItemDaoImpl.getInstance();
        try {
            return itemDao.addItem(name, description);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
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