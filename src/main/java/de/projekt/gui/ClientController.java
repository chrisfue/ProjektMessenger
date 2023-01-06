package de.projekt.gui;


import Networking.Client;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import tools.IPAddressValidator;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;


public class ClientController {

    //Definition für Displayzugriff
    private Stage stage;              //Zugriff auf die Stage
    private Scene login;                //Zugriff auf die Scene für LoginWindow
    private Scene msg;                  //Zugriff auf die Scene für MessageWindow

    // private Socket client= null;        //Socket für Verbindung
    private OutputStream output = null; //Outputstream zum schicken von Daten

    private InputStream input = null;

    private Client client;

    //Loginfenster:
    private TextField tfLoginUsr;       //Zugriff au
    private TextField tfLoginIP;        //Textfeld für IP
    private Button butLogin;            //Button für Login
    private Label labelStatus;


    //MessageFenster
    private TextField tfMessage;
    private TextArea textAreaReceived;
    private Button butSend;

    public void init() {

        this.butLogin.setOnAction((ActionEvent event) -> {

            String IPentered = tfLoginIP.getText();
            String username = tfLoginUsr.getText();


            //Check dass beide Fenster befüllt sind
            if (!(IPentered.isEmpty()) && !(username.isEmpty())) {

                //IP-Adresse auf Gültigkeit prüfen
                IPAddressValidator validator = new IPAddressValidator();
                if (IPAddressValidator.isValid(IPentered)) {


                    //Verbindung zu Server herstellen
                    try {
                        Socket clientsocket = new Socket(IPentered, 4712);
                        this.client = new Client(clientsocket, username);


                    }  catch (IOException e) {
                        labelStatus.setText("could not connect\n to this address");

                    }
                    if(client!=null){
                        if(client.getSocket().isConnected()) {
                            byte loginMessage[] = ("/login " + username +"\n").getBytes();
                           try {
                               client.getSocket().getOutputStream().write(loginMessage);
                           }catch(IOException e){
                               System.out.println("fuck");
                           }
                           client.receiveMessage(textAreaReceived);
                            stage.setScene(msg);
                        }
                    }



                    //Nur zum Testen
                } else { //Wenn keine gültige IP-Adresse eingegeben wurde
                    labelStatus.setText("Invalid IP-Format.. please try again");
                    labelStatus.setTextFill(Color.RED);
                }

            } else { //Wenn mindestens eines der beiden Fenster leer ist
                labelStatus.setText("Please enter both IP \nand Username");
                labelStatus.setTextFill(Color.RED);
            }
        });

        this.butSend.setOnAction((ActionEvent event2) -> {
            //Text übernehmen
            String textInput = tfMessage.getText();
            byte bmessage[] = (textInput + "\n").getBytes();
            //Text ausschicken

            try {
                client.getSocket().getOutputStream().write(bmessage);
            } catch (IOException e) {

            }

        });
    }


    //Constructor mit Weitergabe der Stage
    public ClientController(Stage stage) {
        this.stage = stage;
    }

    //Setter für Elemente aus GUI

    public void setLogin(Scene login) {
        this.login = login;
    }

    public void setLabelStatus(Label labelStatus) {
        this.labelStatus = labelStatus;
    }

    public void setMsg(Scene msg) {
        this.msg = msg;
    }

    public void setButLogin(Button butLogin) {
        this.butLogin = butLogin;
    }

    public void setTfLoginUsr(TextField tfLoginUsr) {
        this.tfLoginUsr = tfLoginUsr;
    }

    public void setTfLoginIP(TextField tfLoginIP) {
        this.tfLoginIP = tfLoginIP;
    }


    public void setButSend(Button butSend) {
        this.butSend = butSend;
    }

    public void setTfMessage(TextField tfMessage) {
        this.tfMessage = tfMessage;
    }

    public void setTextAreaReceived(TextArea textAreaReceived) {
        this.textAreaReceived = textAreaReceived;
    }
}
