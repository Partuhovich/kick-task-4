package org.partapp.kicktask4.service;

import org.partapp.kicktask4.exception.ServiceException;

public interface ItemService {
    boolean addItem(String name, String description, String username) throws ServiceException;
    boolean deleteItem(Long itemId) throws ServiceException;
}
