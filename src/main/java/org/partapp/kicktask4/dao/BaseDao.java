package org.partapp.kicktask4.dao;

import org.partapp.kicktask4.exception.DaoException;

import java.util.List;

public abstract class BaseDao<T> {
    public abstract boolean insert(T t) throws DaoException;
    public abstract boolean delete(T t) throws DaoException;
    public abstract List<T> findAll(T t) throws DaoException;
    public abstract T update(T t) throws DaoException;
}
