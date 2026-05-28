package org.partapp.kicktask4.service;

import org.partapp.kicktask4.entity.ItemEntity;
import org.partapp.kicktask4.entity.UserEntity;
import org.partapp.kicktask4.exception.ServiceException;

import java.util.List;

public interface ItemService {
    boolean addItem(String name, String description, Long ownerId) throws ServiceException;
    ItemEntity getItemById(Long itemId) throws ServiceException;
    boolean deleteItem(Long itemId, UserEntity currentUser) throws ServiceException;
    List<ItemEntity> getAllItems() throws ServiceException;
    boolean updateItem(ItemEntity oldItem, ItemEntity newItem, UserEntity currentUser) throws ServiceException;
}
