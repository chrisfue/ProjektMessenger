package Networking;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.Socket;

public class Client {

    private Socket socket;
    private String username;







    public Client(Socket socket,String username) {


           this.socket = socket;
          this.username = username;


       }

       public void receiveMessage(TextArea textMessages){
        new Thread(new Runnable() {
            @Override
            public void run(){
            while(socket.isConnected()){
               try{ InputStreamReader inputRead = new InputStreamReader(socket.getInputStream());
                BufferedReader br = new BufferedReader(inputRead);
                String bufferResponse = br.readLine();
                textMessages.appendText(bufferResponse);}
               catch(IOException e){
                   e.printStackTrace();
                   break;
               }
            }
            }
        }).start();
       }


    public Socket getSocket() {
        return socket;
    }

    public String getUsername() {
        return username;
    }
}
