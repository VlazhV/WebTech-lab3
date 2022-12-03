package server.command.impl;

import server.command.Command;
import server.command.exception.CommandException;
import server.model.AuthType;
import server.service.ServiceFactory;

public class AuthCommand implements Command {
    @Override
    public String execute(Object caller, String request) throws CommandException {
        String[] arguments = request.split(" ");
        if (arguments.length != 2) throw new CommandException("AUTH command should contain 1 argument");
        AuthType authType;
        try {
            authType = AuthType.valueOf(arguments[1]);
        } catch (IllegalArgumentException e) {
            throw new CommandException("No such auth type");
        }

        ServiceFactory.getInstance().getAuthService().setAuthType(caller, authType);
        return "Success.";
    }
}
