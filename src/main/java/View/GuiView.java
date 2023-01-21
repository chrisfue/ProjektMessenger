package View;

import de.projekt.gui.ClientController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.nio.charset.Charset;

public class GuiView {

    private final ClientController clientController;

    private String userName;
    private String selectedMember;

    private String emojiPoo;

    private String emojiSmile;
    byte[] b_emojiSmile;


    public GuiView(ClientController clientController) {
        this.clientController = clientController;
    }


    //Login Fenster
    public Parent loginWindow() {
        //Layout erstellen
        GridPane root = new GridPane();
        root.setPadding(new Insets(10));

        root.setHgap(10);
        root.setVgap(10);
        root.setAlignment(Pos.CENTER);





        HBox buttonBox = new HBox(10); //neded to pos both button in one grid


        //progress Indicator
       ProgressIndicator prgIndicator = new ProgressIndicator();
        prgIndicator.setVisible(false);



        //Textfeld für Scene definieren
        TextField tfLoginIP = new TextField();
        tfLoginIP.setPromptText("Bitte IP angeben...");
        root.add(tfLoginIP, 0, 0);

        //Textfeld mit Controller verlinken
        this.clientController.setTfLoginIP(tfLoginIP);

        //Textfeld für Scene definieren
        TextField tfLoginUsr = new TextField();
        tfLoginUsr.setPromptText("Username eingeben");
        root.add(tfLoginUsr, 0, 1);

        //Textfeld mit Controller verlinken
        this.clientController.setTfLoginUsr(tfLoginUsr);

        //Window name aendern
        this.clientController.setTitleWindow(tfLoginUsr.getText());

        //Button für Scene definieren
        Button butLogin = new Button("Connect");
        Button butCancel = new Button("CANCEL");

        butLogin.setPrefWidth(80);
        butCancel.setPrefWidth(80);

        buttonBox.getChildren().addAll(butCancel,butLogin);
        root.add(buttonBox,0,3);



        //Button mit Controller verlinken
        this.clientController.setButLogin(butLogin);
        this.clientController.setButCancel(butCancel);

        //Label labelStatus für Scene definieren
        Label labelStatus = new Label();
        root.add(labelStatus, 0, 2);

        //progress indicator hinzufügen
        root.add(prgIndicator,0,2);
        GridPane.setHalignment(prgIndicator, HPos.CENTER); //setzt in dem jeweiligen feld das alignment


        //Label labelStatus mit Controller verbinden
        this.clientController.setLabelStatus(labelStatus);

        //progressindicator mit controller verbinden
        this.clientController.setPrgIndicator(prgIndicator);

        //titel festlegen
        this.clientController.setTitleWindow("LOGIN");


        return root;

    }

    //Message Fenster

    public void setEmojiPoo(String emojiPoo) {
        this.emojiPoo = emojiPoo;
    }

    public void setEmojiSmile(String emojiSmile) {
        this.emojiSmile = emojiSmile;
    }

    public Parent MessageWindow(){


        //todo
        //---------- könnte man in eine eigene Klasse geben-----------


        //poo Emoji initialisierung
        byte[] b_emojiPoo = new byte[]{(byte)0xF0, (byte)0x9F, (byte)0x92, (byte)0xA9};
         emojiPoo = new String(b_emojiPoo, Charset.forName("UTF-8"));

        //smile Emoji initialisierung
        b_emojiSmile = new byte[]{(byte)0xF0, (byte)0x9F, (byte)0x98, (byte)0x81};
         emojiSmile = new String(b_emojiSmile, Charset.forName("UTF-8"));

        //-----------------------------

        //TextArea textAreaReceoved für Scene definieren
        TextArea textAreaReceived = new TextArea();
        textAreaReceived.setMaxSize(300,200);
        textAreaReceived.setPromptText("No incoming message...");
        textAreaReceived.setEditable(false); //not allowed to write text inside the area or change the text inside

        //label erstellen
        Label msgStatusLabel = new Label();



//---------------------ListView für Angemeldete Member---------------------------------
        //liste erstellen
        ObservableList<String> memberList = FXCollections.observableArrayList();

        //Viewer der liste erstellen und befüllen
        ListView<String> listView = new ListView<String>();
        listView.setMaxSize(150,200);

        //schreibt die memberListe in das ListView element >> in der GUI
        listView.setItems(memberList);


//---------------------Definitions der verschiedenen Teile der GUI------------------
        //Textfeld tfMessage für Scene definieren
        TextField tfMessage = new TextField();
        tfMessage.setPromptText("Message...");
        tfMessage.setPrefWidth((textAreaReceived.getMaxWidth()/ 6)*5);


        //Button butsend für scene definieren
        Button butSend = new Button("SEND");
        butSend.setMaxWidth((textAreaReceived.getMaxWidth()/ 6));

         Button butEmojiSmile = new Button(emojiSmile);
         butEmojiSmile.setMaxWidth((textAreaReceived.getMaxWidth()/ 6));

         Button butEmojiPoo = new Button(emojiPoo);
        butEmojiSmile.setMaxWidth((textAreaReceived.getMaxWidth()/ 6));
        butEmojiPoo.setAlignment(Pos.BOTTOM_CENTER);


//---------------Objekte mit Controller verbinden-------------------
        this.clientController.setButSend(butSend);
        this.clientController.setButSMILE(butEmojiSmile);
        this.clientController.setButPOO(butEmojiPoo);

        this.clientController.setEmojiPoo(emojiPoo);
        this.clientController.setEmojiSmile(emojiSmile);

        this.clientController.setB_emojiSmile(b_emojiSmile);
        //Textfeld tfMessage mit Controller verbinden
        this.clientController.setTfMessage(tfMessage);

        //Listviewer und liste mit Controller verbinden //todo notwendig?
        this.clientController.setMemberList(memberList);

        this.clientController.setListView(listView);

        //label mit controller verbinden
        this.clientController.setMsgStatusLabel(msgStatusLabel);


        //TextArea text mit Controller verbinden
        this.clientController.setTextAreaReceived(textAreaReceived);

//-----------------------Layouts anpassen und einstellen---------------------------

        //Layout message window
        VBox msg = new VBox(10);
        HBox areaLayout = new HBox(10);
        HBox labelLayout = new HBox(10);
        HBox hmiLayout = new HBox(10);
        HBox emojiLayout = new HBox(10);

        //layout interaction
        hmiLayout.getChildren().addAll(tfMessage,butSend);
        hmiLayout.setAlignment(Pos.CENTER_LEFT);
        //layout member Area
        areaLayout.getChildren().addAll(textAreaReceived, listView);

        //layout label
        labelLayout.getChildren().add(msgStatusLabel);
        labelLayout.setAlignment(Pos.CENTER);

        //layout for emojis
        emojiLayout.getChildren().addAll(butEmojiSmile,butEmojiPoo);
        emojiLayout.setAlignment(Pos.CENTER_LEFT);
        //main layout of the nodes
        msg.setLayoutX(10);
        msg.setLayoutY(10);
        msg.getChildren().addAll(areaLayout,labelLayout, emojiLayout,hmiLayout);
        //msg.setAlignment(Pos.CENTER);



        return msg;

    }
}
