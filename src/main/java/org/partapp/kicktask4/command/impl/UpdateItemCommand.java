package org.partapp.kicktask4.command.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.partapp.kicktask4.command.Command;
import org.partapp.kicktask4.controller.router.Router;
import org.partapp.kicktask4.entity.ItemEntity;
import org.partapp.kicktask4.entity.UserEntity;
import org.partapp.kicktask4.exception.CommandException;
import org.partapp.kicktask4.exception.ServiceException;
import org.partapp.kicktask4.service.impl.ItemServiceImpl;

import static org.partapp.kicktask4.command.impl.param.RequestParameter.*;

public class UpdateItemCommand implements Command {
    private static final Logger logger = LogManager.getLogger(UpdateItemCommand.class);
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        logger.debug("UpdateItem execution");

        String itemID = request.getParameter(PARAM_ITEM_ID);
        String name = request.getParameter(PARAM_ITEM_NAME);
        String description = request.getParameter(PARAM_ITEM_DESCRIPTION);

        HttpSession session = request.getSession(false);
        UserEntity currentUser = (UserEntity) session.getAttribute(PARAM_USER);
        Router router = new Router();

        if (currentUser == null) {
            logger.warn("Unauthorized attempt to update item");
            router.setPage(LOGIN_PAGE);
            router.setForward();
            return router;
        }

        ItemServiceImpl itemService = ItemServiceImpl.getInstance();

        try {
            ItemEntity oldItem = itemService.getItemById(Long.parseLong(itemID));
            ItemEntity newItem = new ItemEntity();
            newItem.setName(name);
            newItem.setDescription(description);

            if (itemService.updateItem(oldItem, newItem, currentUser)) {
                logger.info("Item added successfully: {} by owner {}", name, currentUser.getUsername());
                request.setAttribute(PARAM_SUCCESS_MSG, "Item added successfully");
                router.setPage(ITEMS_PAGE);
                router.setRedirect();
            } else {
                logger.warn("Failed to add item: {}", name);
                request.setAttribute(PARAM_ERROR_MSG, "Failed to add item. Name cannot be empty.");
                router.setPage(ADD_ITEM_PAGE);
                router.setForward();
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        session.setAttribute(PARAM_CURRENT_PAGE, router.getPage());
        return router;
    }
}
