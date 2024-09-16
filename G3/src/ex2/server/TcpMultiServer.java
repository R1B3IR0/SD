package ex2.server;

import java.io.*;
import java.net.*;


public class TcpMultiServer {
    public static void main(String[] args) {
        int port=2048;
        boolean listening = true;

        System.out.println("TcpMultiServer is started!");

        try (ServerSocket serverSocket = new ServerSocket(port)){
            System.out.println("Waiting for a clients to connect....");


            while (listening) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());

                // Start a new thread to handle the client
                WorkerThread clientThread = new WorkerThread(clientSocket);
                clientThread.start();
            }
        } catch (IOException e) {
            System.err.println("Exception caught when trying to listen on port 2048 or listening for a connection");
            System.err.println(e.getMessage());
        }
    }
}
