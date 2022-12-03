package server.command.impl;

import server.command.Command;
import server.command.exception.CommandException;
import server.model.AuthType;
import server.service.ServiceFactory;

public class DisconnectCommand implements Command {
    @Override
    public String execute(Object caller, String request) throws CommandException {
        ServiceFactory.getInstance().getAuthService().setAuthType(caller, AuthType.UNAUTH);
        return "Bye bye!";
    }
}
