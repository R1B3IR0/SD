package ex3ponto2.server;

import java.io.*;
import java.net.*;


public class MulticastChatServer {
    private static final String MULTICAST_ADDRESS = "230.0.0.1";
    private static final int PORT = 2048;

    public static void main(String[] args) {
        try {
            // Define o endereço de grupo multicast
            InetAddress group = InetAddress.getByName(MULTICAST_ADDRESS);

            // Cria um socket Multicast associado ao grupo e porta específicos
            MulticastSocket socket = new MulticastSocket(PORT);
            socket.joinGroup(group); // O servidor se junta ao grupo multicast

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

                        // Exibe no console o IP do cliente que enviou a mensagem e a própria mensagem
                        System.out.println("Client: " + packet.getAddress() + " sent: " + received);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            receiverThread.start(); // Inicia a thread para receber mensagens

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
