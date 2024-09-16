package ex4;
import java.io.*;
import java.net.*;
import java.util.Date;
import java.util.List;

public class ProcessClient extends Thread {
    private Socket clientSocket;
    private PrintWriter out;

    public ProcessClient(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void run() {
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String userInput;

            while ((userInput = in.readLine()) != null) {
                String clientIP = clientSocket.getInetAddress().getHostAddress();
                String message = "Received from client " + clientIP + ": " + userInput;

                // Adiciona a mensagem à lista central
                Server.addMessage(message);

                // Envia uma resposta ao cliente
                out.println("Echo: " + userInput + " IP: " + clientIP + " message sent/received at " + new Date());
            }

            clientSocket.close();
        } catch (IOException e) {
            System.err.println("Exception caught while handling client: " + e.getMessage());
        }
    }

    public synchronized void sendMessages(List<String> messages) {
        for (String message : messages) {
            out.println(message);
        }
        messages.clear();
    }
}
