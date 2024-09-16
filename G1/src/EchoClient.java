import java.io.*;
import java.net.*;


public class EchoClient {
    public static void main(String[] args) {


        System.out.println("Echo Client...");

        try {
            InetAddress localhost = InetAddress.getLocalHost();
            Socket echoSocket = new Socket(localhost, 5000); // Conecta-se ao servidor no localhost e na porta 5000
            PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));

            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            String message;

            while (true) {
                System.out.print("Enter a message (or 'exit' to quit): ");
                message = userInput.readLine();
                out.println(message);

                if (message.equalsIgnoreCase("exit")) {
                    break; // Encerra a comunicação
                }

                String serverResponse = in.readLine();
                System.out.println("Server says: " + serverResponse);
            }

            echoSocket.close(); // Fecha a conexão com o servidor
        } catch (UnknownHostException e) {
            System.err.println("Don´t know about host");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't establish a connection to the server.");
            e.printStackTrace();
        }
    }
}
