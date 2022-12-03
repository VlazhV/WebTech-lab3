package server.controller;

import server.command.CommandProvider;
import server.command.exception.CommandException;
import server.command.impl.DisconnectCommand;
import server.command.Command;

import java.io.*;
import java.net.Socket;
import java.util.Objects;

public class ServerClientController extends Thread {
    private final Socket socket;
    private final ServerController serverController;

    private BufferedReader reader;
    private PrintWriter writer;

    public ServerClientController(Socket socket, ServerController serverController) {
        this.socket = socket;
        this.serverController = serverController;
    }

    @Override
    public void run() {
        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        sendMessage("Available commands:\nAUTH USER/MANAGER\nDISCONNECT\nVIEW\nCREATE (firstname) (lastname)\nEDIT (id) (firstname) (lastname)");

        boolean running = true;
        do {
            try {
                String request = readMessage();
                if (request == null) {
                    break;
                }

                Command command = CommandProvider.getInstance().getCommand(request);
                String response = command.execute(this, request);
                sendMessage(response);

                if (command instanceof DisconnectCommand) {
                    running = false;
                }
            } catch (CommandException e) {
                e.printStackTrace();
                sendMessage(e.getMessage());
            }
        } while (running);

        serverController.disconnect(this);
    }

    private String readMessage() {
        try {
            return reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void sendMessage(String message) {
        writer.println(message);
        writer.flush();
    }

    public Socket getSocket() {
        return socket;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServerClientController that = (ServerClientController) o;
        return socket.equals(that.socket) && serverController.equals(that.serverController);
    }

    @Override
    public int hashCode() {
        return Objects.hash(socket, serverController);
    }
}
