package Networking;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(4712);

            while(true){

                System.out.println("Server: waiting for connection");
                Socket client = server.accept();
                System.out.println("Server: connected to Client " +
                        client.getInetAddress());
                InputStream input = client.getInputStream();
                InputStreamReader inputRead = new InputStreamReader(input);
                BufferedReader br = new BufferedReader(inputRead);
                String bufferInput = br.readLine();
                System.out.println(bufferInput);;
                // System.out.printf("Server: received '%s'");
                OutputStream stream = client.getOutputStream();
                // String message = "Hello Client";
                byte bmessage[] = (bufferInput+"\n").getBytes();
                stream.write(bmessage);}
        } catch (IOException e) {
// Generelles Exception-Handling zu Demo-Zwecken
            e.printStackTrace();
        }

    }
}
