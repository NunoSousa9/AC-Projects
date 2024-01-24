package org.academiadecodigo.nanderthals;

public class Main {

    public static void main(String[] args) {
        Server server = new Server();
        try {
            server.start(8080);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
