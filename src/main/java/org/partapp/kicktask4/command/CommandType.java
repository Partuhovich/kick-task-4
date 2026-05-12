package org.partapp.kicktask4.command;

import org.partapp.kicktask4.command.impl.*;

public enum CommandType {
    REGISTER(new RegisterCommand()),
    LOGIN(new LoginCommand()),
    LOGOUT(new LogoutCommand()),
    ADD_ITEM(new AddItemCommand()),
    DELETE_ITEM(new DeleteItemCommand()),
    GET_ALL_ITEMS(new GetAllItemsCommand());
    Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public static Command defineCommand(String commandStr) {
       CommandType current = CommandType.valueOf(commandStr.toUpperCase());
       return current.command;
    }
}
