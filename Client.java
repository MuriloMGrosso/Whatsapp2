import java.io.*;
import java.net.*;

public class Client {

    public static void main(String[] args) {

        ClientReceive clientReceive;
        ClientSend clientSend;

        DatagramSocket socket;

        InetAddress serverAdress;
        
        byte[] buffer;
		String message;
        String userName;
        
        if (args.length < 1){
            System.err.println("Erro: informe o endereço IP do servidor e seu nome de usuario");
            System.exit(0);
        }
        else if (args.length < 2){
            System.err.println("Erro: informe seu nome de usuario");
            System.exit(0);
        }
            
        try{
			socket = new DatagramSocket();
            serverAdress = InetAddress.getByName(args[0]);
            userName = args[1];

            clientSend = new ClientSend(userName, socket, serverAdress, 1050);
            clientReceive = new ClientReceive(socket);
            
            clientSend.start();
            clientReceive.start();

            clientSend.join();
            clientReceive.join();

        } catch(Exception e){
            System.err.println("Erro: " + e);
        }
    }
}
