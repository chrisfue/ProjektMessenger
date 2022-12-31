package org.example.Application;

import Networking.TCPServer;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;


import java.io.*;
import java.net.Socket;

public class Clientcontroller {
    private Button buttonSend;

    private TextArea toSend;

    private TextArea textReceived;

    public void init() {
        //TCPServer.main(null);


        this.buttonSend.setOnAction((ActionEvent event) -> {

            try {

                Socket client = new Socket("192.168.1.180", 4712);
                //String input[]= new String[1];
                //  input[0] = toSend.getText();
                //   TCPClient.main(input);
                String textIinput = toSend.getText();
                byte bmessage[] = (textIinput + "\n").getBytes();
                OutputStream output = client.getOutputStream();
                output.write(bmessage);

                InputStream responseText = client.getInputStream();
                InputStreamReader inputRead = new InputStreamReader(responseText);
                BufferedReader br = new BufferedReader(inputRead);
                String bufferResponse = br.readLine();
                textReceived.appendText(bufferResponse);


            } catch (IOException e) {
// Generelles Exception-Handling zu Demo-Zwecken
                e.printStackTrace();
            }
        });

    }
    public void setButtonSend(Button buttonSend) {
        this.buttonSend = buttonSend;
    }

    public void setToSend(TextArea toSend) {
        this.toSend = toSend;
    }

    public void setTextReceived(TextArea textReceived) {
        this.textReceived = textReceived;
    }
}