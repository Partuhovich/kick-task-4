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

import java.util.List;

import static org.partapp.kicktask4.command.impl.param.RequestParameter.*;

public class GetAllUsersCommand implements Command {
    private static final Logger logger = LogManager.getLogger(GetAllUsersCommand.class);

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        logger.debug("Get All Users Command execution");
        UserServiceImpl userService = UserServiceImpl.getInstance();
        HttpSession session = request.getSession();
        UserEntity currentUser = (UserEntity) session.getAttribute(PARAM_USER);
        Router router = new Router();

        if (currentUser == null) {
            logger.warn("Unauthorized attempt to delete item");
            router.setPage(LOGIN_PAGE);
            router.setForward();
            return router;
        }

        try{
            //todo isAdmin validation?
            List<UserEntity> users = userService.getAllUsers(currentUser);
            request.setAttribute(PARAM_USER_LIST, users);
            router.setPage(USERS_PAGE);
            router.setForward();
        } catch (ServiceException e) {
            logger.error("Error getting all users", e);
            throw new CommandException(e);
        }

        session.setAttribute(PARAM_CURRENT_PAGE, router.getPage());
        return router;
    }
}
