package org.partapp.kicktask4.command.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.partapp.kicktask4.command.Command;
import org.partapp.kicktask4.controller.router.Router;
import org.partapp.kicktask4.entity.UserEntity;

import static org.partapp.kicktask4.command.impl.param.RequestParameter.LOGIN_PAGE;
import static org.partapp.kicktask4.command.impl.param.RequestParameter.PARAM_USER;

public class LogoutCommand implements Command {
    private static final Logger logger = LogManager.getLogger(LogoutCommand.class);

    @Override
    public Router execute(HttpServletRequest request) {
        logger.debug("Logout command execution");
        HttpSession session = request.getSession();
        UserEntity currentUser = (UserEntity) session.getAttribute(PARAM_USER);
        Router router = new Router();
        if (currentUser == null) {
            logger.warn("Unauthorized attempt to logout");
            router.setPage(LOGIN_PAGE);
            router.setForward();
            return router;
        }

        request.getSession().invalidate();
        router.setPage(request.getContextPath() + LOGIN_PAGE);
        router.setRedirect();
        return router;
    }
}
