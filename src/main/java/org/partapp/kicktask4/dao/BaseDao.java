package org.partapp.kicktask4.dao;

import org.partapp.kicktask4.entity.ItemEntity;
import org.partapp.kicktask4.exception.DaoException;

import java.sql.SQLException;
import java.util.List;

public interface BaseDao<T> {
    public abstract boolean insert(T t) throws DaoException;
    public abstract boolean delete(T t) throws DaoException;
    public abstract T update(T t) throws DaoException, SQLException;
    public abstract List<T> findAll() throws DaoException;
}
