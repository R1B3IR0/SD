package ex3ponto2.client;

import java.io.*;
import java.net.*;
import java.util.Scanner;


public class MulticastChatClient {
    private static final String MULTICAST_ADDRESS = "230.0.0.1";
    private static final int PORT = 2048;

    public static void main(String[] args) {
        try {
            // Define o endereço de grupo multicast
            InetAddress group = InetAddress.getByName(MULTICAST_ADDRESS);

            // Cria um socket Multicast associado ao grupo e porta específicos
            MulticastSocket socket = new MulticastSocket(PORT);
            socket.joinGroup(group); // O cliente se junta ao grupo multicast

            // Thread para receber mensagens enviadas para o grupo multicast
            Thread receiverThread = new Thread(() -> {
                try {
                    while (true) {
                        // Prepara um pacote para receber os dados
                        byte[] buf = new byte[256];
                        DatagramPacket packet = new DatagramPacket(buf, buf.length);

                        // Aguarda e recebe pacotes enviados ao grupo multicast
                        socket.receive(packet);

                        // Converte os dados recebidos em uma String
                        String received = new String(packet.getData(), 0, packet.getLength());

                        // Exibe no console a mensagem recebida no servidor
                        System.out.println("Server: " + received);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            receiverThread.start(); // Inicia a thread para receber mensagens

            Scanner scanner = new Scanner(System.in);
            while (true) {
                // Permite que o usuário digite a mensagem a ser enviada
                System.out.print("Enter message: ");
                String message = scanner.nextLine();

                // Converte a mensagem em bytes
                byte[] buf = message.getBytes();

                // Cria um pacote com a mensagem e o envia ao grupo multicast
                DatagramPacket packet = new DatagramPacket(buf, buf.length, group, PORT);
                socket.send(packet);

                // Exibe a mensagem enviada pelo cliente no console
                System.out.println("You: " + message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
