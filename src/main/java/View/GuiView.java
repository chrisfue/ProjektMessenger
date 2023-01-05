package View;

import de.projekt.gui.ClientController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class GuiView {

    private final ClientController clientController;

    public  GuiView(ClientController clientController){this.clientController=clientController;}




    //Login Fenster
    public Parent loginWindow(){
        //Layout erstellen
        GridPane root = new GridPane();
        root.setPadding(new Insets(20));

        root.setHgap(10);
        root.setVgap(10);




        //Textfeld für Scene definieren
        TextField tfLoginIP = new TextField();
        tfLoginIP.setPromptText("Bitte IP angeben...");
        root.add(tfLoginIP, 0,0);

        //Textfeld mit Controller verlinken
        this.clientController.setTfLoginIP(tfLoginIP);

        //Textfeld für Scene definieren
        TextField tfLoginUsr = new TextField();
        tfLoginUsr.setPromptText("Username eingeben");
        root.add(tfLoginUsr,0,1);

        //Textfeld mit Controller verlinken
        this.clientController.setTfLoginUsr(tfLoginUsr);

        //Button für Scene definieren
        Button butLogin = new Button("Connect");
        butLogin.setPrefSize(100,100);
        root.add(butLogin,1,2);

        //Button mit Controller verlinken
        this.clientController.setButLogin(butLogin);

        //Label labelStatus für Scene definieren
        Label labelStatus = new Label();
        root.add(labelStatus,0,2);

        //Label labelStatus mit Controller verbinden
        this.clientController.setLabelStatus(labelStatus);

        return root;

    }

    //Message Fenster
    public Parent MessageWindow(){
        GridPane root = new GridPane();


        //Textfeld tfMessage für Scene definieren
        TextField tfMessage = new TextField();
        tfMessage.setPromptText("Message...");
        tfMessage.setMaxWidth(200);

        //Textfeld tfMessage mit Controller verbinden
        this.clientController.setTfMessage(tfMessage);

        //TextArea textAreaReceoved für Scene definieren
        TextArea textAreaReceived = new TextArea("Letzte meldungen...");
        textAreaReceived.setMaxSize(300,200);

        //TextArea text mit Controller verbinden
        this.clientController.setTextAreaReceived(textAreaReceived);

        //Button butsend für scene definieren
        Button butSend = new Button("SEND");
        butSend.setAlignment(Pos.CENTER);

        //Button butSend mit Controller verbinden
        this.clientController.setButSend(butSend);


        //Layout message window
        VBox msg = new VBox(10);
        msg.setLayoutX(10);
        msg.setLayoutY(10);
        msg.getChildren().addAll(textAreaReceived,tfMessage,butSend);


        return msg;

    }
}
