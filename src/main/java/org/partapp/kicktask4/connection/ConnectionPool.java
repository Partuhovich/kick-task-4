package org.partapp.kicktask4.connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.Properties;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
    private static final Logger logger = LogManager.getLogger(ConnectionPool.class);
    private static ConnectionPool instance;
    private static final int MAX_CONNECTIONS_CAPACITY = 10;
    private static final Lock lock = new ReentrantLock();
    private final BlockingDeque<Connection> freeConections = new LinkedBlockingDeque<>(MAX_CONNECTIONS_CAPACITY);
    private final BlockingDeque<Connection> usedConections = new LinkedBlockingDeque<>(MAX_CONNECTIONS_CAPACITY);
    private final String URL = "jdbc:postgresql://localhost:5432/demodb";

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
        prop.put("user", "postgres");
        prop.put("password", "root");
        for (int i = 0; i < MAX_CONNECTIONS_CAPACITY; i++) {
            Connection connection = null;
            try {
                connection = DriverManager.getConnection(URL, prop);
            } catch (SQLException e) {
                throw new ExceptionInInitializerError(e);
            }
            logger.info("Creating connection number = {}", i + 1);
            freeConections.add(connection);
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

    public Connection getConection() {
        Connection connection = null;
        try {
            connection = freeConections.take();
            usedConections.add(connection);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
        return connection;
    }

    public void releaseConnection(Connection connection) {
        try {
            usedConections.remove(connection);
            freeConections.put(connection);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }

    public void destroyPool() {
        for(int i = 0; i < MAX_CONNECTIONS_CAPACITY; i++){
            try {
                freeConections.take().close();
            } catch (SQLException | InterruptedException e) {
                throw new RuntimeException(e);
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
