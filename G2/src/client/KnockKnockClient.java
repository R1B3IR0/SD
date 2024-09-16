package client;

import java.io.*;
import java.net.*;

public class KnockKnockClient {
    public static void main(String[] args) throws IOException {


        InetAddress localhost = InetAddress.getLocalHost();
        int portNumber = 1024;

        try (
                Socket kkSocket = new Socket(localhost, portNumber); // Conecta-se ao servidor no localhost e na porta 1024
                PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(kkSocket.getInputStream()));
        ) {
            BufferedReader stdIn =
                    new BufferedReader(new InputStreamReader(System.in));
            String fromServer;
            String fromUser;

            while ((fromServer = in.readLine()) != null) {
                System.out.println("Server: " + fromServer);
                if (fromServer.equals("Bye."))
                    break;

                fromUser = stdIn.readLine();
                if (fromUser != null) {
                    System.out.println("Client: " + fromUser);
                    out.println(fromUser);
                }
            }
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + localhost);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                    localhost);
            System.exit(1);
        }
    }
}
