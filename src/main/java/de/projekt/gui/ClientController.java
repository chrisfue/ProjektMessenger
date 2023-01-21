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


/***
 * ClientController Class controls and bind all functions provided by the GUI and the Client itself.
 * It also starts the server Connection.
 *
 */
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

    /***
     * Bind the Progressindicator between Controller and View Class
     * @param prgIndicator the progressindicator to be displayed
     */
    public void setPrgIndicator(ProgressIndicator prgIndicator) {
        this.prgIndicator = prgIndicator;
    }

    /***
     * Initialisation of javafx methods before the GUI App starts.
     */
    public void init() {

        /***
         * <code>butCancel.setOnAction</code>closed the GUI Login Window by pressing onb CANCEL Button.
         */
        this.butCancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.close();
            }
        });


        /***
         * <code>butLogin.setOnAction</code> Login Handler by Pressing on Login.
         * That init the workflow for the Connection between Client and Server
         */
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

        //todo Jan: muss noch eventhandler auf maouse klicked anpassen

        /***
         * <code>tfLoginUsr.setOnKeyPressed</code>
         * Additional implementation of the Login Handler. Let the Workflow starting when you press enter,
         * after you have entered your name.
         *
         */
        this.tfLoginUsr.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)) {


                    String IPentered = tfLoginIP.getText();
                    String username = tfLoginUsr.getText();

                    //setzt den Title vom Messenger auf den Username
                    setTitleWindow("Angemeldeter User: " + username);


                    //Check dass beide Fenster befüllt sind
                    if (!(IPentered.isEmpty()) && !(username.isEmpty())) {



                        //IP-Adresse auf Gültigkeit prüfen
                        IPAddressValidator validator = new IPAddressValidator();
                        if (IPAddressValidator.isValid(IPentered)) {

                            //Verbindung zu Server herstellen

                            try {
                                Socket clientsocket = new Socket(IPentered, 4711);
                                client = new Client(clientsocket, username);


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
                }
            }
        });


        /***
         * <code>butSend.setOnAction</code> send the Message of the Textfield to to the Server
         */
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


        //needed to show progress indicator without own thread
        /***
         * <code>tfLoginUsr.setOnMouseReleased</code> shows the Progressidicator to see if the program works or not,
         * when the Mouse Key released.
         */
        this.tfLoginUsr.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                prgIndicator.setVisible(true);
                stage.show();
            }
        });
        /***
         * <code>tfLoginUsr.setOnMouseReleased</code> shows the Progressidicator to see if the program works or not,
         * when released the Tab Key.
         */
        this.tfLoginUsr.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                prgIndicator.setVisible(true);
                stage.show();
            }
        });

        /***
         * <code>listView.setOnMouseClicked</code> if you select a name from the list it is sent to the server.
         */
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


        /***
         * <code>butSmile</code> Displays the Smile Emoji in the textfield.
         */
        this.butSmile.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                String Text = tfMessage.getText();

                //interessante möglichkeit
                //tfMessage.setText(Text + " " + (new String(b_emojiSmile, Charset.forName("UTF-8"))));

                tfMessage.setText(emojiSmile);
            }
        });

        /***
         * <code>butSmile</code> Displays the Poo Emoji in the textfield.
         */
        this.butPoo.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                tfMessage.setText(emojiPoo);

            }
        });


    }

    /***
     * Bind the listView with the view in the ViewClass.
     * @param listView displays a List of Members.
     */
    public void setListView(ListView<String> listView) {
        this.listView = listView;
    }

    /***
     * Bind the memberList with the List in the ViewClass.
     * @param memberList an array of Members
     */
    public void setMemberList(ObservableList<String> memberList) {
        this.memberList = memberList;
    }

    /***
     * Bind the selected Member with the Member in the ViewClass.
     * @param selectedMember is a String of a "Member" in the Memberlist.
     */
    public void setSelectedMember(String selectedMember) {
        this.selectedMember = selectedMember;
    }

    //Constructor mit Weitergabe der Stage

    /***
     * Bind the JavaFX Stage with the Stage in the View class.
     * @param stage
     */
    public ClientController(Stage stage) {
        this.stage = stage;
    }

    /***
     * Bind the Status label in the Messenger with the Label in the View Class.
     * @param msgStatusLabel
     */
    public void setMsgStatusLabel(Label msgStatusLabel) {
        this.msgStatusLabel = msgStatusLabel;
    }

    //Setter für Elemente aus GUI

    /***
     * Bind the Login Scene with the Login Scene in the View Class.
     * @param login
     */
    public void setLogin(Scene login) {
        this.login = login;
    }


    /***
     * Bind the Status Label of the Login window with the Label in the Gui View Class.
     * @param labelStatus
     */
    public void setLabelStatus(Label labelStatus) {
        this.labelStatus = labelStatus;
    }

    /***
     * Bind the Message Scene with the Scene in the View Class.
     * @param msg
     */
    public void setMsg(Scene msg) {
        this.msg = msg;
    }

    /***
     * Bind the Button Login with the Button in the View Class.
     * @param butLogin
     */
    public void setButLogin(Button butLogin) {
        this.butLogin = butLogin;
    }

    /***
     * Bind the Cancel Button with the Button of the View Class.
     * @param butCancel
     */
    public void setButCancel(Button butCancel) {
        this.butCancel = butCancel;
    }

    /***
     * Bind the Textfield in the Login Window with the Textfield in the View Class.
     * @param tfLoginUsr
     */
    public void setTfLoginUsr(TextField tfLoginUsr){

        this.tfLoginUsr = tfLoginUsr;

    }

    /***
     * Bind the Textfield of th eIP adress at the Login Window with the Textfield in the View Class.
     * @param tfLoginIP
     */
    public void setTfLoginIP(TextField tfLoginIP) {
        this.tfLoginIP = tfLoginIP;
    }

    /***
     * Bind the Button send at the Messenger Window with the BVuton in the View Class.
     * @param butSend
     */
    public void setButSend(Button butSend) {
        this.butSend = butSend;
    }

    /***
     * Bind the Textfield of the Message at the messenger Windows with the textfield in the View Class.
     * @param tfMessage
     */
    public void setTfMessage(TextField tfMessage) {
        this.tfMessage = tfMessage;
    }

    /***
     * Bind the TextArea of the Messenger window with the Textarea of the View Class
     * @param textAreaReceived
     */
    public void setTextAreaReceived(TextArea textAreaReceived) {
        this.textAreaReceived = textAreaReceived;
    }

//-----------------------additional Function prototypes------------------------

    /***
     * Change the name of the Title at the actual Window.
     * @param titleWindow
     */
    public void setTitleWindow(String titleWindow) {
        this.titleWindow = titleWindow;
        setWindowTitle();
    }
    private void setWindowTitle(){
        stage.setTitle(this.titleWindow);
    }

    /***
     * Bind the Button for the smile Emoji with the button at the view class.
     * @param butEMO
     */
    public void setButSMILE(Button butEMO) {
        this.butSmile = butEMO;
    }

    /***
     * bind the Button for the poo Emoji with the button at the view class
     * @param butPoo
     */
    public void setButPOO(Button butPoo) {
        this.butPoo = butPoo;
    }

    /**
     * Bind the String for the Emoji with the String in the View class.
     * @param emojiPoo
     */
    public void setEmojiPoo(String emojiPoo) {
        this.emojiPoo = emojiPoo;
    }

    /***
     * Bind the String of the Emoji with th String in the View class.
     * @param emojiSmile
     */
    public void setEmojiSmile(String emojiSmile) {
        this.emojiSmile = emojiSmile;
    }

    /***
     * bind the byte array of the Emoji with the array in th view class.
     * When used instead of the String it shows an interessting effect.
     *  @param b_emojiSmile
     */
    public void setB_emojiSmile(byte[] b_emojiSmile) {
        this.b_emojiSmile = b_emojiSmile;
    }
}

