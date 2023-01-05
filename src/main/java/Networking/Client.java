package Networking;

import java.io.*;
import java.net.Socket;

public class Client {

    private Socket socket;
    private String username;

    private InputStreamReader incoming;




    public Client(Socket socket,String username) {


           this.socket = socket;
          this.username = username;

       }


    public Socket getSocket() {
        return socket;
    }

    public String getUsername() {
        return username;
    }
}
