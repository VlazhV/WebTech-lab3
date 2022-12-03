package server.command.impl;

import server.command.exception.CommandException;
import server.model.AuthType;
import server.service.ServiceFactory;
import server.command.Command;

public class CreateCommand implements Command {
    @Override
    public String execute(Object caller, String request) throws CommandException {
        String[] arguments = request.split(" ");
        if (arguments.length != 3) throw new CommandException("CREATE invalid syntax");

        if (ServiceFactory.getInstance().getAuthService().getAuthType(caller) != AuthType.MANAGER)
            return "Should be MANAGER";

        ServiceFactory.getInstance().getCaseService().addCase(arguments[1], arguments[2]);
        return "Success";
    }
}
