package org.academiadecodigo.nanderthals;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private ServerSocket serverSocket;
    private ExecutorService executorService;
    private LinkedList<ServerWorker> connectedClients;

    public static void main(String[] args) {
        Server server = new Server();
        server.start(8080);
    }

    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started on port " + port);

            executorService = Executors.newFixedThreadPool(3);
            connectedClients = new LinkedList<>();

            while (true) {
                System.out.println("Waiting for connections...");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connection established with " + clientSocket);

                ServerWorker serverWorker = new ServerWorker(clientSocket);
                connectedClients.add(serverWorker);
                executorService.execute(serverWorker);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        try {
            executorService.shutdown();
            serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void broadcast(String message, ServerWorker sender) {
        for (ServerWorker serverWorker : connectedClients) {
            if (serverWorker != sender) {
                serverWorker.sendMessage(sender.username + ": " + message);
            }
        }
    }

    public void sendPrivateMessage(String message, ServerWorker sender, String recipientUsername) {
        for(ServerWorker serverWorker : connectedClients) {
            if (serverWorker.getUsername().equals(recipientUsername)) {
                serverWorker.sendMessage(sender.getUsername() + ": " + message);
            }
        }
    }

    public void removeWorker(ServerWorker serverWorker) {
        connectedClients.remove(serverWorker);
    }


    private class ServerWorker implements Runnable {
        private final Socket clientSocket;
        private PrintWriter writer;
        private BufferedReader reader;
        private String username;

        public ServerWorker(Socket clientSocket) {
            this.clientSocket = clientSocket;
            try {
                writer = new PrintWriter(clientSocket.getOutputStream(), true);
                reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public String getUsername() {
            return username;
        }

        public void run() {
            try {
                writer = new PrintWriter(clientSocket.getOutputStream(), true);
                reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                writer.println("Enter username: ");
                username = reader.readLine();
                System.out.println(username + " connected");

                String message;
                while ((message = reader.readLine()) != null) {
                    System.out.println(username + ": " + message);
                    if (message.startsWith("/w ")) {
                        int spaceIndex = message.indexOf(" ", 3);
                        if (spaceIndex != -1) {
                            String recipientUsername = message.substring(3, spaceIndex);
                            String privateMessage = message.substring(spaceIndex + 1);
                            sendPrivateMessage(privateMessage, this, recipientUsername);
                        } else {
                            sendMessage("Invalid private message format. Use: /w username message");
                        }
                    } else {
                        broadcast(message, this);
                        }
                    }

                    System.out.println("Connection closed: " + clientSocket);
                    removeWorker(this);
                    clientSocket.close();
                } catch(IOException e){
                    e.printStackTrace();
                }

        }
        public void sendMessage(String message) {
            writer.println(message);
        }
    }
}
