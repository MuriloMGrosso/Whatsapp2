import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClientSend extends Thread {

    DatagramSocket socket;
    InetAddress serverAdress;
    String userName;
    int serverPort;

    public ClientSend(String userName, DatagramSocket socket, InetAddress serverAdress, int serverPort) {
        this.userName = userName;
        this.socket = socket;
        this.serverAdress = serverAdress;
        this.serverPort = serverPort;
    }

    public void run() {
        DatagramPacket sendPacket;
        Scanner stdIn;
        byte[] buffer;
		String message;

        try {
            // A primeira mensagem ao servidor sempre eh o nome do usuario
            buffer = userName.getBytes();
            sendPacket = new DatagramPacket(buffer, buffer.length, serverAdress, serverPort);
            socket.send(sendPacket);

            // Loop para recebimento de mensagens do servidor
            while(true) {
                stdIn = new Scanner(System.in);
                message = stdIn.nextLine();

                buffer = message.getBytes();
                sendPacket = new DatagramPacket(buffer, buffer.length, serverAdress, serverPort);

                socket.send(sendPacket);
            }
        } catch(IOException e) {
            System.err.println("Erro: " + e);
        }    
    }
}