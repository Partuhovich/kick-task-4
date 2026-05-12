package org.partapp.kicktask4.command.impl;

import jakarta.servlet.http.HttpServletRequest;
import org.partapp.kicktask4.command.Command;
import org.partapp.kicktask4.controller.router.Router;

public class LogoutCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        request.getSession().invalidate();
        return new Router();
    }
}
