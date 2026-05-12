package org.partapp.kicktask4.dao;

import org.partapp.kicktask4.exception.DaoException;

public interface UserDao {
    boolean authenticate(String login, String password) throws DaoException;

}
