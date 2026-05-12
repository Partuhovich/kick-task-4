package org.partapp.kicktask4.controller.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.partapp.kicktask4.connection.ConnectionPool;

public class ServletContextListenerImpl implements ServletContextListener {
    private static final Logger logger = LogManager.getLogger(ServletContextListenerImpl.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("Context initialized");
        ConnectionPool.getInstance();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("Context destroyed");
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        connectionPool.destroyPool();
        connectionPool.deregisterDriver();
    }
}
