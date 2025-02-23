import java.io.*;
import java.net.*;

public class ClientReceive extends Thread {

    DatagramSocket socket;

    public ClientReceive(DatagramSocket socket) {
        this.socket = socket;
    }

    public void run() {
        DatagramPacket receivePacket;
        byte[] buffer;
		String message;

        try {
            while(true) {
                buffer = new byte[256];
                receivePacket = new DatagramPacket(buffer, buffer.length);

                socket.receive(receivePacket);

                message = new String(receivePacket.getData()).trim();
                System.out.println(message);
            }
        } catch(IOException e) {
            System.err.println("Erro: " + e);
        } 
    }
}
