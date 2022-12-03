package client;

import client.controller.ClientController;

public class Client {
    public static void main(String[] args) {
        ClientController client = new ClientController();
        client.start();
    }
}
