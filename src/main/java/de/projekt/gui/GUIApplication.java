package de.projekt.gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GUIApplication extends Application {

   private String userName;



    @Override
    public void start(Stage primStage) throws Exception {

        //Layout erstellen
        GridPane root = new GridPane();
        root.setPadding(new Insets(20));
        root.setHgap(10);
        root.setVgap(10);
        VBox msg = new VBox(10);
        msg.setLayoutX(10);
        msg.setLayoutY(10);


        //Login Fenster
        Scene scLogin = new Scene(root, 270, 140);
        //Messenger Fenster
        Scene scMsg = new Scene(msg,400,300);

//Textfeld erstellen
        TextField tfMessage = new TextField("Message...");
        tfMessage.setMaxWidth(200);


        TextField tfLoginUsr = new TextField("User name eingeben");
        root.add(tfLoginUsr,0,1);

        TextField tfLoginIP = new TextField("Bitte IP angeben...");
        root.add(tfLoginIP, 0,0);

        TextArea texAreareceived = new TextArea("Letzte meldungen...");
        texAreareceived.setMaxSize(300,200);
        ;
//Label erstellen
        Label labelStatus = new Label();
        root.add(labelStatus,0,2);

//Button hinzufügen
        Button butSend = new Button("SEND");
        butSend.setAlignment(Pos.CENTER);
        Button butLogin = new Button("Connect");
        butLogin.setPrefSize(100,100);

        root.add(butLogin,1,2);
        //root.setAlignment(Pos.CENTER);



//Data Handling


//Eventhandling
            butLogin.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {

                    //todo IF abfrage ob Werte valid sind
                    if((tfLoginIP.getText() != null) && (!tfLoginUsr.getText().isBlank())){

                        final String ipAdress = tfLoginIP.getText();
                        boolean validIp = true; //todo Chris hier brauche ich den bool ob die IP valid ist und verbindung steht

                        //abfrage ob eine valide IP adresse vorliegt und verbindung aufgebaut werden kann, rückgabewert bool
                        //todo Chris hier kommt die funktion für die abfrage/senden der IP rein
                        if(validIp){

                            //todo for debugging
                            labelStatus.setText("accepted....wait");
                            labelStatus.setTextFill(Color.GREEN);
                            //todo entfernen nur für visualisierung
                            try {
                                Thread.sleep(3000);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            userName = tfLoginUsr.getText();
                            //erlaubt in das nächste fenster zu gehen
                            primStage.setScene(scMsg);




                        }else{
                            labelStatus.setText("Ip is invalid... please try again");
                            labelStatus.setTextFill(Color.RED);

                        }
                    }else{
                        labelStatus.setText("Please fill in all fields!");
                        labelStatus.setTextFill(Color.RED);
                    }

                    //labelStatus.setText("");
                    tfLoginIP.clear();
                    tfLoginUsr.clear();


                }
            });

            butSend.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    //todo Chris sende dir den String
                     final String sendString = tfMessage.getText();



                    System.out.println(sendString);
                    texAreareceived.setText(userName + ": "+ sendString);
                    //todo Chris hier kriege ich von dir auch einen string

                }
            });

//elem (nodes) hinzufügen
        //todo not needed for GridPane
       // root.getChildren().addAll(tfLoginIP,tfLoginUsr,butLogin);

        msg.getChildren().addAll(texAreareceived,tfMessage, butSend);




//Scene erstellen

        primStage.setScene(scLogin);
        primStage.show();




    }
//testversion //todo entfernen
    public static void main(String[] args) {
        launch();
    }
}