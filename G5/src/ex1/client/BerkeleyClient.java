package ex1.client;
import java.io.IOException;
import java.net.*;

public class BerkeleyClient {
    private static final String MULTICAST_ADDRESS = "230.0.0.1";
    private static final int PORT = 2048;

    public static void main(String[] args) {
        try {
            InetAddress group = InetAddress.getByName(MULTICAST_ADDRESS);
            MulticastSocket socket = new MulticastSocket(PORT);
            socket.joinGroup(group);

            // Obtém o tempo local do cliente
            long localTime = System.currentTimeMillis();

            // Envia o tempo local para o servidor
            String timeToSend = Long.toString(localTime);
            byte[] timeData = timeToSend.getBytes();
            DatagramPacket timePacket = new DatagramPacket(timeData, timeData.length, group, PORT);
            socket.send(timePacket);

            // Recebe o tempo médio calculado pelo servidor
            byte[] responseData = new byte[256];
            DatagramPacket responsePacket = new DatagramPacket(responseData, responseData.length);
            socket.receive(responsePacket);

            String received = new String(responsePacket.getData(), 0, responsePacket.getLength());
            long averageTime = Long.parseLong(received);

            // Calcula a diferença entre o tempo médio e o tempo local do cliente
            long timeDifference = averageTime - localTime;

            // Ajusta o tempo local do cliente
            long adjustedTime = localTime + timeDifference;

            System.out.println("Local Time Before Adjustment: " + localTime);
            System.out.println("Adjusted Time After Synchronization: " + adjustedTime);

            socket.leaveGroup(group);
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}