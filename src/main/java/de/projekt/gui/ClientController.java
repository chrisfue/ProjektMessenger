package de.projekt.gui;


import Networking.Client;
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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import tools.IPAddressValidator;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.Arrays;

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

    private String titleWindow;



    //selected member from visible member list
    private String selectedMember;

    //Loginfenster:
    private TextField tfLoginUsr;       //Zugriff au
    private TextField tfLoginIP;        //Textfeld für IP
    private Button butLogin;            //Button für Login
    private Button butCancel;           //Button vor cancel Login Application

    private  Button butSmile;

    private Button butPoo;

    private Label labelStatus;

    private ProgressIndicator prgIndicator; //progressindicator while waiting on server


    //MessageFenster
    private TextField tfMessage;
    private TextArea textAreaReceived;

    //listen Element für die Member
    private ObservableList<String> memberList = FXCollections.observableArrayList();

    //Listen Container
    ListView<String> listView = new ListView<String>();
    private Button butSend;

    private Label msgStatusLabel;

    //for emojis
    private String emojiPoo;

    private String emojiSmile;
    byte[] b_emojiSmile;


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


                    //todo Chris:
                    // memberList.addAll("Chris", "Mario", "Jan", "Bitch", "AmArsch");


                        //todo neu: selectedMember ist ein String der den Namen der auswahl des Users aus Member Liste zurückgibt!



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

        //todo Jan: muss noch eventhandler auf maouse klicked anpassen
        this.tfLoginUsr.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)) {

                    prgIndicator.setVisible(true);
                    String IPentered = tfLoginIP.getText();
                    String username = tfLoginUsr.getText();

                    //setzt den Title vom Messenger auf den Username
                    setTitleWindow("Angemeldeter User: " + username);


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

                        */



                            stage.setScene(msg);
                            //todo for debugging
                            //memberList.addAll("Chris", "Mario", "Jan", "Bitch", "AmArsch");

                            //todo Chris:
                            // memberList.addAll("Chris", "Mario", "Jan", "Bitch", "AmArsch");



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
        });


        //needed to show progress indicator without own thread
        this.tfLoginUsr.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                prgIndicator.setVisible(true);
                stage.show();
            }
        });
        this.tfLoginUsr.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                prgIndicator.setVisible(true);
                stage.show();
            }
        });

        this.listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //ermöglicht das ausgewählte item auszugeben aus der Memberlist
                selectedMember = listView.getSelectionModel().getSelectedItem();

                System.out.printf(selectedMember);

            }
        });

        //todo entfernen
        //-----------------------------------additional Functions


        butSmile.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                String Text = tfMessage.getText();

                //interessante möglichkeit
                //tfMessage.setText(Text + " " + (new String(b_emojiSmile, Charset.forName("UTF-8"))));

                tfMessage.setText(emojiSmile);
            }
        });

        butPoo.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                tfMessage.setText(emojiPoo);

            }
        });


    }


    public void setListView(ListView<String> listView) {
        this.listView = listView;
    }

    public void setMemberList(ObservableList<String> memberList) {
        this.memberList = memberList;
    }

    public void setSelectedMember(String selectedMember) {
        this.selectedMember = selectedMember;
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

//-----------------------additional Function prototypes------------------------
    public void setTitleWindow(String titleWindow) {
        this.titleWindow = titleWindow;
        setWindowTitle();
    }
    private void setWindowTitle(){
        stage.setTitle(this.titleWindow);
    }

    public void setButSMILE(Button butEMO) {
        this.butSmile = butEMO;
    }

    public void setButPOO(Button butPoo) {
        this.butPoo = butPoo;
    }

    public void setEmojiPoo(String emojiPoo) {
        this.emojiPoo = emojiPoo;
    }

    public void setEmojiSmile(String emojiSmile) {
        this.emojiSmile = emojiSmile;
    }

    public void setB_emojiSmile(byte[] b_emojiSmile) {
        this.b_emojiSmile = b_emojiSmile;
    }
}

