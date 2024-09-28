import java.io.*;
import java.net.*;
import java.util.Map;
import java.util.HashMap;
import java.lang.Thread;

public class Server {
    
    public static void main(String[] args) {
        DatagramSocket socket;
        DatagramPacket receivePacket;
        DatagramPacket sendPacket;
		
		InetAddress userAdress;
		int userPort;
        String userFullAdress;
        String userName;;
        
        String message;
		byte[] buffer;
        
        Map<String, String> map = new HashMap<String, String>();

        try{
            System.out.println("Iniciando host...");
            socket = new DatagramSocket(1050);
 
            while(true) {
                buffer = new byte[256];
                receivePacket = new DatagramPacket(buffer, buffer.length);			
                socket.receive(receivePacket);

                userAdress = receivePacket.getAddress();
			    userPort = receivePacket.getPort();
                userFullAdress = userAdress + " " + userPort;

                if(map.get(userFullAdress) == null) {
                    userName = new String(receivePacket.getData()).trim();
                    map.put(userFullAdress, userName);
                    System.out.println(userName + " conectado!");
                }
                else {
                    userName = map.get(userFullAdress);
                    System.out.println("Pacote recebido de " + userName + "!");

                    message = userName + ": " + new String(receivePacket.getData()).trim();
                    buffer = message.getBytes();

                    System.out.println("Enviando mensagem de " + userName + " para todo mundo...");
                    for (String key : map.keySet()) {
                        if(key.equals(userFullAdress))
                            continue;

                        userAdress = InetAddress.getByName(key.split(" ")[0].replace("/",""));
                        userPort = Integer.parseInt(key.split(" ")[1]);

                        sendPacket = new DatagramPacket(buffer, buffer.length, userAdress, userPort);
                        socket.send(sendPacket);
                    }
                }
            }
        } catch(IOException e){
            System.err.println("Erro: " + e);
        }        
    }
}