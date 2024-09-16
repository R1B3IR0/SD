import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer2 {
    public static void main(String [] args) {

        System.out.println("Echo Server is started!");

        try(ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("Waiting for a client to connect....");

            //Espera por um client
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected");

            //Configura fluxos de entrada e saída
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String clientAddress = clientSocket.getInetAddress().getHostAddress();

            String userInput;

            while ((userInput = in.readLine()) != null) {
                System.out.println("Received from client: " + userInput);

                // Modificar a mensagem a ser enviada de volta ao cliente
                String serverResponse = "IP SERVIDOR:" + clientAddress + ":" + userInput;
                out.println(serverResponse);
            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port 5000 or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}
