package Networking;

import javafx.scene.control.TextField;

import java.io.*;
import java.net.Socket;

public class Client {

    private Socket socket;
    private String username;

    private InputStreamReader incoming;





    public Client(Socket socket,String username) {


           this.socket = socket;
          this.username = username;
         try{
             this.incoming = new InputStreamReader(socket.getInputStream());
         }catch(IOException e){
             System.out.println("could not create stream");

         }

       }
/*
       public void receiveMessage(TextField textfield){
        new Thread(new Runnable() {
            @Override
            public void run(){
            while(socket.isClosed()){

            }
            }
        }
       }
*/

    public Socket getSocket() {
        return socket;
    }

    public String getUsername() {
        return username;
    }
}
