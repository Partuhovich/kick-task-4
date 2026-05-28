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
import org.partapp.kicktask4.service.UserService;
import org.partapp.kicktask4.service.impl.UserServiceImpl;

import static org.partapp.kicktask4.command.impl.param.RequestParameter.*;


public class RegisterCommand implements Command {
    private static final Logger logger = LogManager.getLogger(LoginCommand.class);
    private static final String MAIN_PAGE = "index.jsp";
    private static final String REGISTER_PAGE = "pages/user/register.jsp";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        logger.info("Register");
        String username = request.getParameter(PARAM_USERNAME);
        String pass = request.getParameter(PARAM_PASSWORD);

        UserService userService = UserServiceImpl.getInstance();
        String page;
        HttpSession session = request.getSession();
        Router router = new Router();
        try {
            if(userService.userExists(username)) {
                logger.warn("User with this username already exists: {}", username);
                request.setAttribute(PARAM_ERROR_MSG, "User with this username already exists");
                page = REGISTER_PAGE;
                router.setPage(page);
                router.setForward();
            } else {
                if (userService.registration(username, pass)) {
                    logger.info("Registration successful");
                    UserEntity user = userService.getUserByName(username);
                    session.setAttribute(PARAM_USER, user);
                    page = MAIN_PAGE;
                    router.setPage(page);
                    router.setRedirect();
                } else {
                    logger.warn("Registration failed");
                    request.setAttribute(PARAM_ERROR_MSG, "Registration failed");
                    page = REGISTER_PAGE;
                    router.setPage(page);
                    router.setForward();
                }
            }
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        session.setAttribute(PARAM_CURRENT_PAGE, page);
        return router;
    }
}
