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

public class DeleteItemCommand implements Command {
    private static final Logger logger = LogManager.getLogger(DeleteItemCommand.class);
    private static final String ITEMS_PAGE = "pages/user/items.jsp";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        logger.info("DeleteItemCommand execution");

        String itemIdParam = request.getParameter("itemId");
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("user");

        if (username == null) {
            logger.warn("Unauthorized attempt to delete item");
            Router router = new Router();
            router.setPage("pages/user/login.jsp");
            router.setForward();
            return router;
        }

        if (itemIdParam == null || itemIdParam.trim().isEmpty()) {
            logger.warn("Item ID parameter is missing");
            Router router = new Router();
            router.setPage(ITEMS_PAGE);
            router.setForward();
            request.setAttribute("error_msg", "Item ID is required");
            return router;
        }

        Long itemId;
        try {
            itemId = Long.parseLong(itemIdParam);
        } catch (NumberFormatException e) {
            logger.warn("Invalid item ID format: {}", itemIdParam);
            Router router = new Router();
            router.setPage(ITEMS_PAGE);
            router.setForward();
            request.setAttribute("error_msg", "Invalid item ID");
            return router;
        }

        ItemService itemService = ItemServiceImpl.getInstance();
        Router router = new Router();

        try {
            boolean success = itemService.deleteItem(itemId);

            if (success) {
                logger.info("Item deleted successfully: {} by user {}", itemId, username);
                request.setAttribute("success_msg", "Item deleted successfully");
            } else {
                logger.warn("Failed to delete item: {}", itemId);
                request.setAttribute("error_msg", "Failed to delete item");
            }

            router.setPage(ITEMS_PAGE);
            router.setRedirect();
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        session.setAttribute("current_page", router.getPage());
        return router;
    }
}