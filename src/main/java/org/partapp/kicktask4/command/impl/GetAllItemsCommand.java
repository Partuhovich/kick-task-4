package org.partapp.kicktask4.command.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.partapp.kicktask4.command.Command;
import org.partapp.kicktask4.controller.router.Router;
import org.partapp.kicktask4.entity.ItemEntity;
import org.partapp.kicktask4.exception.CommandException;
import org.partapp.kicktask4.exception.ServiceException;
import org.partapp.kicktask4.service.impl.ItemServiceImpl;

import java.util.List;

import static org.partapp.kicktask4.command.impl.param.RequestParameter.*;

public class GetAllItemsCommand  implements Command {
    private static final Logger logger = LogManager.getLogger(GetAllItemsCommand.class);

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {

        logger.debug("Get all items Command execution");
        ItemServiceImpl itemService = ItemServiceImpl.getInstance();
        HttpSession session = request.getSession();
        Router router = new Router();

        try{
            List<ItemEntity> items = itemService.getAllItems();
            request.setAttribute(PARAM_ITEM_LIST, items);
            router.setPage(ITEMS_PAGE);
            router.setForward();
        } catch (ServiceException e) {
            logger.error("Error getting all items", e);
            throw new CommandException(e);
        }

        session.setAttribute(PARAM_CURRENT_PAGE, router.getPage());
        return router;
    }
}
