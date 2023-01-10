package Networking;

import javafx.application.Platform;
import javafx.collections.ObservableList;
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

       public void receiveMessage(TextArea textMessages, ObservableList<String> userNAmesOnline){
        new Thread(new Runnable() {
            @Override
            public void run(){
                try {
                    InputStreamReader inputRead = new InputStreamReader(socket.getInputStream());
                    BufferedReader br = new BufferedReader(inputRead);
                    while (socket.isConnected()) {

                        String bufferResponse = br.readLine();
                        if (bufferResponse != null) {
                            if (bufferResponse.startsWith("Users:")) {
                                Platform.runLater(() -> {
                                    userNAmesOnline.clear();
                                    String userOnline = bufferResponse.substring(6);
                                    String[] onlineUsers = userOnline.split(",");
                                    for (String nameOnline : onlineUsers) {

                                        userNAmesOnline.add(nameOnline);

                                    }
                                });

                            }else{
                                Platform.runLater(()->{
                                    textMessages.appendText('\n' + bufferResponse);
                                });
                            }

                        }    }

                }catch(IOException e){
                   e.printStackTrace();
                  // break;
               }
            }
           // }
        }).start();
       }


    public Socket getSocket() {
        return socket;
    }

    public String getUsername() {
        return username;
    }
}
