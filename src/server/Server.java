package server;

import server.controller.ServerController;

public class Server {
    public static void main(String[] args) {
        ServerController server = new ServerController();
        server.start();
    }
}
