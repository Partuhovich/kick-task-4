package org.partapp.kicktask4.connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.partapp.kicktask4.exception.ConnectionPoolException;

import java.sql.*;
import java.util.Properties;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static org.partapp.kicktask4.connection.param.ConnectionParameter.*;

public class ConnectionPool {
    private static final Logger logger = LogManager.getLogger(ConnectionPool.class);
    private static final int MAX_CONNECTIONS_CAPACITY = 10;
    private static final Lock lock = new ReentrantLock();
    private static final String URL = "jdbc:postgresql://localhost:5432/demodb";
    private static ConnectionPool instance;
    private final BlockingDeque<Connection> freeConnections = new LinkedBlockingDeque<>(MAX_CONNECTIONS_CAPACITY);
    private final BlockingDeque<Connection> usedConnections = new LinkedBlockingDeque<>(MAX_CONNECTIONS_CAPACITY);

    static {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
        } catch (SQLException e) {
            logger.fatal("Failed to register Driver");
            throw new ExceptionInInitializerError(e);
        }
    }

    private ConnectionPool() {
        logger.info("Creating connection");
        Properties prop = new Properties();
        prop.put(PARAM_USER, PARAM_POSTGRES);
        prop.put(PARAM_PASSWORD, PARAM_ROOT);
        for (int i = 0; i < MAX_CONNECTIONS_CAPACITY; i++) {
            Connection connection = null;
            try {
                connection = DriverManager.getConnection(URL, prop);
            } catch (SQLException e) {
                logger.fatal("ConnectionPool failed");
                throw new ExceptionInInitializerError(e);
            }
            logger.info("Creating connection number = {}", i + 1);
            freeConnections.add(connection);
        }
    }

    public static ConnectionPool getInstance() {
        if (instance == null) {
            lock.lock();
            try {
                if (instance == null) {
                    instance = new ConnectionPool();
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = freeConnections.take();
            usedConnections.add(connection);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ConnectionPoolException(e);
        }
        return connection;
    }

    public void releaseConnection(Connection connection) {
        try {
            usedConnections.remove(connection);
            freeConnections.put(connection);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ConnectionPoolException(e);
        }
    }

    public void destroyPool() {
        for(int i = 0; i < MAX_CONNECTIONS_CAPACITY; i++){
            try {
                freeConnections.take().close();
            } catch (SQLException | InterruptedException e) {
                throw new ConnectionPoolException(e);
            }
        }
    }

    public void deregisterDriver() {
        try{
            DriverManager.deregisterDriver(DriverManager.getDriver(URL));
        } catch (SQLException e) {
            logger.error("Failed to deregister Driver");
        }
    }
}
