package ex1;

import java.io.IOException;
import java.net.*;

public class MulticastServer {
    public static void main(String[] args) {
        final String multicastGroup = "224.0.0.1";
        final int multicastPort = 5000;
        final int timeout = 5000; // Tempo limite para receber respostas (em milissegundos)
        final int numClients = 5; // Número de clientes conhecidos

        try {
            InetAddress group = InetAddress.getByName(multicastGroup);
            MulticastSocket multicastSocket = new MulticastSocket();

            // Enviar solicitação de tempo para o grupo de multicast
            String requestMessage = "Time request";
            DatagramPacket requestPacket = new DatagramPacket(requestMessage.getBytes(),
                    requestMessage.length(), group, multicastPort);
            multicastSocket.send(requestPacket);

            // Definir um tempo limite para recepção de respostas
            multicastSocket.setSoTimeout(timeout);

            byte[] buffer = new byte[1024];
            DatagramPacket responsePacket;

            // Recolher as respostas dos clientes conhecidos
            for (int i = 0; i < numClients; i++) {
                responsePacket = new DatagramPacket(buffer, buffer.length);
                multicastSocket.receive(responsePacket);

                // Exibir as respostas recebidas
                String receivedMessage = new String(responsePacket.getData(), 0, responsePacket.getLength());
                System.out.println("Resposta recebida de " + responsePacket.getAddress() +
                        ": " + receivedMessage);
            }

            multicastSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
