package View;

import de.projekt.gui.ClientController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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



      //  Scene scLogin = new Scene(root, 270, 140);

        TextField tfLoginIP = new TextField();
        tfLoginIP.setPromptText("Bitte IP angeben...");
        this.clientController.setTfLoginIP(tfLoginIP);
        root.add(tfLoginIP, 0,0);

        TextField tfLoginUsr = new TextField();
        this.clientController.setTfLoginUsr(tfLoginUsr);
        tfLoginUsr.setPromptText("Username eingeben");

        root.add(tfLoginUsr,0,1);



        Button butLogin = new Button("Connect");
        this.clientController.setButLogin(butLogin);
        butLogin.setPrefSize(100,100);

        root.add(butLogin,1,2);




        return root;

    }

    //Message Fenster
    public Parent MessageWindow(){
        GridPane root = new GridPane();



        TextField tfMessage = new TextField();
        tfMessage.setPromptText("Message...");
        tfMessage.setMaxWidth(200);

        TextArea textAreaReceived = new TextArea("Letzte meldungen...");
        textAreaReceived.setMaxSize(300,200);

        //Button hinzufügen
        Button butSend = new Button("SEND");
        butSend.setAlignment(Pos.CENTER);

        VBox msg = new VBox(10);
        msg.setLayoutX(10);
        msg.setLayoutY(10);
        msg.getChildren().addAll(textAreaReceived,tfMessage,butSend);

       // root.add(msg);
        return msg;

    }
}
