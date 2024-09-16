package ex1.server;
import java.io.IOException;
import java.net.*;

public class BerkeleyServer {
    private static final String MULTICAST_ADDRESS = "230.0.0.1";
    private static final int PORT = 2048;
    private static final int MAX_MEMBERS = 5; // Número conhecido de clientes

    public static void main(String[] args) {
        try {
            InetAddress group = InetAddress.getByName(MULTICAST_ADDRESS);
            MulticastSocket socket = new MulticastSocket(PORT);
            socket.joinGroup(group);

            // Envia pedido para obter tempos locais dos membros do grupo
            String request = "REQUEST_TIME";
            byte[] requestData = request.getBytes();
            DatagramPacket requestPacket = new DatagramPacket(requestData, requestData.length, group, PORT);
            socket.send(requestPacket);

            // Define timeout para evitar bloqueio indefinido
            socket.setSoTimeout(5000); // Timeout de 5 segundos

            int receivedResponses = 0;
            long[] localTimes = new long[MAX_MEMBERS]; // Armazena os tempos recebidos

            while (receivedResponses < MAX_MEMBERS) {
                byte[] responseData = new byte[256];
                DatagramPacket responsePacket = new DatagramPacket(responseData, responseData.length);

                try {
                    // Tenta receber datagramas de resposta dos membros
                    socket.receive(responsePacket);
                    String received = new String(responsePacket.getData(), 0, responsePacket.getLength());

                    // Salva o tempo local recebido
                    localTimes[receivedResponses] = Long.parseLong(received);
                    receivedResponses++;
                } catch (SocketTimeoutException e) {
                    System.out.println("Timeout reached. Missing responses: " + (MAX_MEMBERS - receivedResponses));
                    break;
                }
            }

            // Cálculo do tempo médio
            long totalLocalTime = 0;
            for (int i = 0; i < receivedResponses; i++) {
                totalLocalTime += localTimes[i];
            }
            long averageTime = totalLocalTime / receivedResponses;

            // Envia o tempo médio calculado para todos os membros do grupo
            String averageTimeString = Long.toString(averageTime);
            byte[] averageTimeData = averageTimeString.getBytes();
            DatagramPacket averageTimePacket = new DatagramPacket(averageTimeData, averageTimeData.length, group, PORT);
            socket.send(averageTimePacket);

            socket.leaveGroup(group);
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}