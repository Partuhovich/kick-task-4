package org.partapp.kicktask4.command.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.partapp.kicktask4.command.Command;
import org.partapp.kicktask4.controller.router.Router;
import org.partapp.kicktask4.entity.UserEntity;
import org.partapp.kicktask4.exception.CommandException;
import org.partapp.kicktask4.exception.ServiceException;
import org.partapp.kicktask4.service.impl.UserServiceImpl;

import static org.partapp.kicktask4.command.impl.param.RequestParameter.*;

public class DeleteUserCommand implements Command {
    private static final Logger logger = LogManager.getLogger(DeleteUserCommand.class);

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {

        logger.debug("Delete User Command execution");
        UserServiceImpl userService = UserServiceImpl.getInstance();
        String userIdString = request.getParameter(PARAM_USER_ID);
        HttpSession session = request.getSession();
        UserEntity currentUser = (UserEntity) session.getAttribute(PARAM_USER);
        Router router = new Router();

        if (currentUser == null) {
            logger.warn("Unauthorized attempt to delete item");
            router.setPage(LOGIN_PAGE);
            router.setForward();
            return router;
        }

        Long userId = Long.parseLong(userIdString);

        try {
            UserEntity user = userService.getUserById(userId);
            if (userService.deleteUser(user, currentUser)) {
                logger.info("User with id: {} successfully deleted by user: {}", userId, currentUser.getUsername());
                request.setAttribute(PARAM_SUCCESS_MSG, "User successfully deleted");
            } else {
                logger.warn("Failed to delete user with id: {} by user: {}", userId, currentUser.getUsername());
                request.setAttribute(PARAM_ERROR_MSG, "Failed to delete user");
            }
            router.setPage(USERS_PAGE);
            router.setRedirect();
        } catch (ServiceException e) {
            logger.error("Failed to delete user ID: {}", userId, e);
            throw new CommandException(e);
        }

        session.setAttribute(PARAM_CURRENT_PAGE, router.getPage());
        return router;
    }
}
