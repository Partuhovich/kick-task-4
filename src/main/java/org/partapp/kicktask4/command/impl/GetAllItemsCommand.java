package org.partapp.kicktask4.command.impl;

import jakarta.servlet.http.HttpServletRequest;
import org.partapp.kicktask4.command.Command;
import org.partapp.kicktask4.controller.router.Router;
import org.partapp.kicktask4.exception.CommandException;

public class GetAllItemsCommand  implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        return new Router();
    }
}
