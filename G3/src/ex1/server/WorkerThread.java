package ex1.server;

import java.io.*;
import java.net.*;

public class WorkerThread  extends Thread {
    private Socket clientSocket = null;

    public WorkerThread(Socket clientSocket) {
        //super("ex1.server.WorkerThread");
        this.clientSocket = clientSocket;
    }

    public void run() {
        try {
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            clientSocket.getInputStream()));
            String userInput;

            while ((userInput = in.readLine()) != null) {
                System.out.println("Received from client " + clientSocket.getInetAddress() + ": " + userInput);
                out.println("Echo: " + userInput); // Envia a resposta de volta ao cliente
            }
            clientSocket.close();

        } catch (IOException e) {
            System.err.println("Exception caught while handling client: " + e.getMessage());
        }
    }
}
