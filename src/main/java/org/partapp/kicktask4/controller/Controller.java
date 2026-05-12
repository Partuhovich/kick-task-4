package org.partapp.kicktask4.controller;

import java.io.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.partapp.kicktask4.command.Command;
import org.partapp.kicktask4.command.CommandType;
import org.partapp.kicktask4.controller.router.Router;
import org.partapp.kicktask4.controller.router.RouterType;
import org.partapp.kicktask4.exception.CommandException;

@WebServlet(name = "controller", urlPatterns = {"/controller", "*.do"})
public class Controller extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(Controller.class);

    public void init() {
        logger.info("---------> Servlet INIT");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.info("doGet");
        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("doPost");
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        String commandStr = request.getParameter("command");
        Command command = CommandType.defineCommand(commandStr);

        try {
            Router router = command.execute(request);
            RouterType type = router.getType();
            String page = router.getPage();
            if(type == RouterType.FORWARD) {
                request.getRequestDispatcher(page).forward(request, response);
            } else if (type == RouterType.REDIRECT) {
                response.sendRedirect(page);
            }
        } catch (CommandException e) {
            response.sendError(500);
        }

    }

    public void destroy() {

        logger.info("---------> Servlet DESTROY");
    }
}