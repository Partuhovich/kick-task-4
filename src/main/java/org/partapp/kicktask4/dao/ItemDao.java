package org.partapp.kicktask4.dao;

import org.partapp.kicktask4.exception.DaoException;

public interface ItemDao {
    boolean addItem(String name, String description) throws DaoException;
    boolean deleteItem(Long itemId) throws DaoException;
}
