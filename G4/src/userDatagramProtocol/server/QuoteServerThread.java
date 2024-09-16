package userDatagramProtocol.server;

import java.io.*;
import java.net.*;
import java.util.*;
public class QuoteServerThread extends Thread {
    protected DatagramSocket socket = null;
    protected BufferedReader in = null;
    protected boolean moreQuotes = true;

    public QuoteServerThread() throws IOException {
        this("QuoteServerThread");
    }

    public QuoteServerThread(String name) throws IOException {
        super(name);
        socket = new DatagramSocket(4445);

        try {
            in = new BufferedReader(new FileReader("C:\\Users\\RIBEIRO\\Documents\\ESTG\\23-24\\SD\\G4\\src\\one-liners"));
        } catch (FileNotFoundException e) {
            System.err.println("Could not quote file.Serving time instead.");
        }
    }

    public void run() {
        while (moreQuotes) {
            try {
                byte[] buf = new byte[256];

                // receive request
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);

                // figure out response
                String dString = null;
                if(in == null) {
                    dString = new Date().toString();
                } else {
                    dString = getNextQuote();
                }
                buf = dString.getBytes();

                // send the response to the client at "address" and "port"
                InetAddress address = packet.getAddress();
                int port = packet.getPort();
                packet = new DatagramPacket(buf, buf.length, address, port);
                socket.send(packet);
            } catch (IOException E ) {
                E.printStackTrace();
                moreQuotes = false;
            }
        }
        socket.close();
    }

    protected String getNextQuote() {
        String returnValue = null;
        try {
            if((returnValue = in.readLine()) == null) {
                in.close();
                moreQuotes = false;
                returnValue = "No more quotes. Goodby.";
            }
        } catch (IOException e) {
            returnValue = "IOException occurred in userDatagramProtocol.ex3ponto2.server.";
        }
        return returnValue;
    }
}
