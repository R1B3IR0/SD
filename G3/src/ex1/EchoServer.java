package ex1;

import java.io.*;
import java.net.*;

//Apenas Referencia para o exercicío 1 - MultiClients
//Class para um single client
public class EchoServer {
    public static void main(String[] args) {

        System.out.println("Echo Server is started!");

        try(ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("Waiting for a client to connect....");

            //Espera por um client
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected");

            //Configura fluxos de entrada e saída
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String userInput;

            while ((userInput = in.readLine()) != null) {
                System.out.println("Received from client: " + userInput);
                out.println("Echo: " + userInput); // Envia a resposta de volta ao cliente
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port 5000 or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}
