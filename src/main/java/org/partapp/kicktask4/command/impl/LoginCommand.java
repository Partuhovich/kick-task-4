package org.partapp.kicktask4.command.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.partapp.kicktask4.command.Command;
import org.partapp.kicktask4.controller.router.Router;
import org.partapp.kicktask4.exception.CommandException;
import org.partapp.kicktask4.exception.ServiceException;
import org.partapp.kicktask4.service.UserService;
import org.partapp.kicktask4.service.impl.UserServiceImpl;

public class LoginCommand implements Command {
    private static final Logger logger = LogManager.getLogger(LoginCommand.class);
    private static final String MAIN_PAGE = "index.jsp";
    private static final String LOGIN_PAGE = "pages/user/login.jsp";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        logger.info("Login");
        String login = request.getParameter("username");
        String pass = request.getParameter("password");
        UserService userService = UserServiceImpl.getInstance();
        String page;
        HttpSession session = request.getSession();
        Router router = new Router();
        try {
            if(userService.authenticate(login, pass)){
                logger.info("Successful authentication. User : {}", login);
                session.setAttribute("user", login);
                page = MAIN_PAGE;
                router.setPage(page);
                router.setRedirect();
            } else {
                logger.warn("Failed authentication attempt for user: {}", login);
                request.setAttribute("error_msg", "incorrect login or pass");
                page = LOGIN_PAGE;
                router.setPage(page);
                router.setForward();
            }
            session.setAttribute("current_page", page);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }
        return router;
    }
}
