package org.partapp.kicktask4.command;

import jakarta.servlet.http.HttpServletRequest;
import org.partapp.kicktask4.controller.router.Router;
import org.partapp.kicktask4.exception.CommandException;

public interface Command {
    Router execute(HttpServletRequest request) throws CommandException;
}
