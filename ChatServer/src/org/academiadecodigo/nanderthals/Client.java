package org.academiadecodigo.nanderthals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;

    public static void main(String[] args) {

        Client client = new Client();
        client.connect("localhost", 8080);
    }
    public void connect(String serverAddress, int serverPort) {
       try {
            socket = new Socket(serverAddress, serverPort);
            System.out.println("Connected to server: " + serverAddress + ":" + serverPort);

            writer = new PrintWriter(socket.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            BufferedReader userInputReader = new BufferedReader(new InputStreamReader(System.in));
           System.out.println("Enter username: ");
           String username = userInputReader.readLine();

           writer.println(username);

           Thread readerThread = new Thread(new Runnable() {
               public void run() {
                   readServerMessages();
               }
           });
           readerThread.start();

           String userInput;
           while ((userInput = userInputReader.readLine()) != null) {
               sendMessage(userInput);
           }

           readerThread.join();
           socket.close();
           writer.close();
           reader.close();
        } catch (IOException | InterruptedException e) {
           e.printStackTrace();
       }
    }

    public void readServerMessages() {
        try {
            String message;
            while ((message = reader.readLine()) != null) {
                System.out.println("Received from server: " + message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        writer.println(message);
    }

}
