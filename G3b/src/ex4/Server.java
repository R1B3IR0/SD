package ex4;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private static List<ProcessClient> clients = new ArrayList<>();
    private static List<String> messages = new ArrayList<>();

    public static void main(String[] args) {
        int port = 2048;
        boolean listening = true;

        System.out.println("Server is started!");

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Waiting for clients to connect...");

            // Thread para enviar mensagens periodicamente
            Thread messageSenderThread = new Thread(() -> {
                while (true) {
                    try {
                        Thread.sleep(5000); // Enviar mensagens a cada 5 segundos
                        sendMessagesToAllClients();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            messageSenderThread.start();

            while (listening) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());

                // Cria uma nova instância da classe ProcessClient para lidar com o cliente
                ProcessClient client = new ProcessClient(clientSocket);
                clients.add(client);
                client.start();
            }
        } catch (IOException e) {
            System.err.println("Exception caught when trying to listen on port 2048 or listening for a connection");
            System.err.println(e.getMessage());
        }
    }

    public synchronized static void addMessage(String message) {
        messages.add(message);
    }

    public synchronized static void sendMessagesToAllClients() {
        for (ProcessClient client : clients) {
            client.sendMessages(messages);
        }
    }
}
