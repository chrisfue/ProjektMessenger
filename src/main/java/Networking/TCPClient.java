package Networking;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class TCPClient {

    public static void main(String[] args) {
        try {

            Socket client = new Socket("192.168.1.180", 4712);
            System.out.println("Client: connected to " + client.getInetAddress());
            byte bmessage[] = (args[0]+ "\n").getBytes();
            OutputStream output = client.getOutputStream();
            output.write(bmessage);


            InputStream in = client.getInputStream();
            byte b[] = new byte[100];
            int bytes = in.read(b);
            System.out.println("Client: recieved " + bytes + " Bytes from Server");
            String message = new String(b);
            System.out.println("Client: Message from Server: " + message);
        } catch (IOException e) {
// Generelles Exception-Handling zu Demo-Zwecken
            e.printStackTrace();
        }
    }
}
