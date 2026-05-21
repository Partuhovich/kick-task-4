package org.partapp.kicktask4.dao;

import org.partapp.kicktask4.entity.UserEntity;
import org.partapp.kicktask4.exception.DaoException;

public interface UserDao {
    UserEntity findByName(String login) throws DaoException;
}
