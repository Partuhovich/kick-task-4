package org.partapp.kicktask4.controller.listener;

import jakarta.servlet.http.HttpSessionAttributeListener;
import jakarta.servlet.http.HttpSessionBindingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SessionAttributeListenerImpl implements HttpSessionAttributeListener {
    private static final Logger logger = LogManager.getLogger(SessionAttributeListenerImpl.class);

    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {
        logger.info("<<<+++ attributeAdded: name = {}, value = {}",
                event.getName(), event.getValue());
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {
        logger.info("<<<--- attributeRemoved: name = {}, value = {}",
                event.getName(), event.getValue());
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent event) {
        logger.info("<<<### attributeReplaced: name = {}, value = {}",
                event.getName(), event.getValue());
    }
}
