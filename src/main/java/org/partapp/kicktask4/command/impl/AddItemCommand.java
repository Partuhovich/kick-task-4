package org.partapp.kicktask4.command.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.partapp.kicktask4.command.Command;
import org.partapp.kicktask4.controller.router.Router;
import org.partapp.kicktask4.exception.CommandException;
import org.partapp.kicktask4.exception.ServiceException;
import org.partapp.kicktask4.service.ItemService;
import org.partapp.kicktask4.service.impl.ItemServiceImpl;

public class AddItemCommand implements Command {
    private static final Logger logger = LogManager.getLogger(AddItemCommand.class);
    private static final String ITEMS_PAGE = "pages/user/items.jsp";
    private static final String ADD_ITEM_PAGE = "pages/user/addItem.jsp";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        logger.info("AddItemCommand execution");

        String name = request.getParameter("itemName");
        String description = request.getParameter("itemDescription");
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("user");

        if (username == null) {
            logger.warn("Unauthorized attempt to add item");
            Router router = new Router();
            router.setPage("pages/user/login.jsp");
            router.setForward();
            return router;
        }

        Long ownerId = (Long) session.getAttribute("userId");
        if (ownerId == null) {
            logger.error("UserId not found in session");
            Router router = new Router();
            router.setPage(ADD_ITEM_PAGE);
            router.setForward();
            request.setAttribute("error_msg", "User ID not found");
            return router;
        }

        ItemService itemService = ItemServiceImpl.getInstance();
        Router router = new Router();

        try {
            boolean success = itemService.addItem(name, description, ownerId);

            if (success) {
                logger.info("Item added successfully: {} by owner {}", name, ownerId);
                request.setAttribute("success_msg", "Item added successfully");
                router.setPage(ITEMS_PAGE);
                router.setRedirect();
            } else {
                logger.warn("Failed to add item: {}", name);
                request.setAttribute("error_msg", "Failed to add item. Name cannot be empty.");
                router.setPage(ADD_ITEM_PAGE);
                router.setForward();
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        session.setAttribute("current_page", router.getPage());
        return router;
    }
}