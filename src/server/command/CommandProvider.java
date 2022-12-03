package server.command;

import server.command.exception.CommandException;
import server.command.impl.*;
import server.command.impl.*;

public class CommandProvider {
    private static final CommandProvider INSTANCE = new CommandProvider();

    private CommandProvider() {
    }

    public static CommandProvider getInstance() {
        return INSTANCE;
    }

    public Command getCommand(String request) throws CommandException {
        if (request == null) throw new CommandException("No command");
        String[] arguments = request.split(" ");
        if (arguments.length < 1) throw new CommandException("No command");
        switch (arguments[0]) {
            case "AUTH": return new AuthCommand();
            case "DISCONNECT": return new DisconnectCommand();
            case "EDIT": return new EditCommand();
            case "VIEW": return new ViewCommand();
            case "CREATE": return new CreateCommand();
            default:  throw new CommandException("No such command");
        }
    }
}
