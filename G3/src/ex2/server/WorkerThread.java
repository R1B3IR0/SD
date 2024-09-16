package ex2.server;

import java.io.*;
import java.net.*;
import java.util.Date;

public class WorkerThread extends Thread {
    private Socket clientSocket = null;

    public WorkerThread(Socket clientSocket) {
        super("WorkerThread");
        this.clientSocket = clientSocket;
    }

    public void run() {
        try {
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            clientSocket.getInputStream()));
            String userInput;
            Date dateAndHour = new Date();

            while ((userInput = in.readLine()) != null) {
                System.out.println("Received from client " + clientSocket.getInetAddress().getHostAddress() + ": " + userInput);
                // Modificar a mensagem a ser enviada de volta ao cliente
                out.println("Echo: " + userInput + " IP: " + clientSocket.getInetAddress().getHostAddress() +
                        " message sent(by client and changed by server)/received at " + dateAndHour); // Envia a resposta de volta ao cliente
            }
            clientSocket.close();

        } catch (IOException e) {
            System.err.println("Exception caught while handling client: " + e.getMessage());
        }
    }
}
