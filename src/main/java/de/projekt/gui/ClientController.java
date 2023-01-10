package de.projekt.gui;


import Networking.Client;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import tools.IPAddressValidator;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

//merged css into devJAn
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
    private Button butCancel;           //Button vor cancel Login Application
    private Label labelStatus;

    private ProgressIndicator prgIndicator; //progressindicator while waiting on server


    //MessageFenster
    private TextField tfMessage;
    private TextArea textAreaReceived;

    private ObservableList<String> memberList = FXCollections.observableArrayList(); //liste für die Member
    private Button butSend;

    private Label msgStatusLabel;

    public void setPrgIndicator(ProgressIndicator prgIndicator) {
        this.prgIndicator = prgIndicator;
    }

    public void init() {

        this.butCancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.close();
            }
        });

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
                        Socket clientsocket = new Socket(IPentered, 4711);
                        this.client = new Client(clientsocket, username);


                    }  catch (IOException e) {
                        labelStatus.setText("could not connect\n to this address");


                    //todo Chris: interface for the member provided external from (?) server
                    // memberList.addAll("Chris", "Mario", "Jan", "Bitch", "AmArsch");
                    //todo CHris: interface for messenger label
                    // msgStatusLabel.setText("Dies ist ein test um formatierung ec zu testen");
                    //  msgStatusLabel.setTextFill(Color.RED);



                    }
                    if(client!=null){
                        if(client.getSocket().isConnected()) {
                            byte loginMessage[] = ("/login " + username +"\n").getBytes();
                           try {
                               client.getSocket().getOutputStream().write(loginMessage);
                           }catch(IOException e){
                               System.out.println("fuck");
                           }
                           client.receiveMessage(textAreaReceived, memberList);
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


   /*     this.tfLoginUsr.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)) {

                    prgIndicator.setVisible(true);
                    String IPentered = tfLoginIP.getText();
                    String username = tfLoginUsr.getText();


                    //Check dass beide Fenster befüllt sind
                    if (!(IPentered.isEmpty()) && !(username.isEmpty())) {



                        //IP-Adresse auf Gültigkeit prüfen
                        IPAddressValidator validator = new IPAddressValidator();
                        if (IPAddressValidator.isValid(IPentered)) {

                            //is only need for GUI debugging while server not running
                            //System.out.println("IP VALID!");

                            //todo needed for Testing GUI
/*

                    //Verbindung zu Server herstellen
                        try{
                            Socket clientsocket = new Socket(IPentered,4712);
                           *//* this.output = client.getOutputStream();
                            input = client.getInputStream();

                            *//*
                            this.client = new Client(clientsocket,username);



                        }catch(UnknownHostException e){

                        }
                        catch (IOException e){
                            System.out.println("error");
                        }




                            stage.setScene(msg);

                            //todo Chris: interface for the member provided external from (?) server
                            // memberList.addAll("Chris", "Mario", "Jan", "Bitch", "AmArsch");
                            //todo CHris: interface for messenger label
                            // msgStatusLabel.setText("Dies ist ein test um formatierung ec zu testen");
                            //  msgStatusLabel.setTextFill(Color.RED);


                            //Nur zum Testen
                        } else { //Wenn keine gültige IP-Adresse eingegeben wurde
                            labelStatus.setText("Invalid IP-Format.. please try again");
                            labelStatus.setTextFill(Color.RED);
                        }

                    } else { //Wenn mindestens eines der beiden Fenster leer ist
                        labelStatus.setText("Please enter both IP \nand Username");
                        labelStatus.setTextFill(Color.RED);
                    }
                }
            }
        });*/


        this.tfLoginUsr.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                prgIndicator.setVisible(true);
                stage.show();
            }
        });

    }


    public void setMemberList(ObservableList<String> memberList) {
        this.memberList = memberList;
    }

    //Constructor mit Weitergabe der Stage
    public ClientController(Stage stage) {
        this.stage = stage;
    }

    public void setMsgStatusLabel(Label msgStatusLabel) {
        this.msgStatusLabel = msgStatusLabel;
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

    public void setButCancel(Button butCancel) {
        this.butCancel = butCancel;
    }

    public void setTfLoginUsr(TextField tfLoginUsr){

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
