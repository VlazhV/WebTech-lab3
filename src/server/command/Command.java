package server.command;

import server.command.exception.CommandException;

public interface Command {
    String execute(Object caller, String request) throws CommandException;
}
