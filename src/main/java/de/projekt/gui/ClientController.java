package de.projekt.gui;


import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tools.IPAddressValidator;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;


public class ClientController {

    private   Stage stage;

    private Scene login;
    private Scene msg;


    //Textfeld für Username
    private TextField tfLoginUsr;



    public void setLogin(Scene login) {
        this.login = login;
    }



    public void setMsg(Scene msg) {
        this.msg = msg;
    }

    //Textfeld für IP
    private TextField tfLoginIP;
    //Button für Login
    private Button butLogin;


    private TextField tfMessage;
    private TextArea textAreaReceived;
    private Button butSend;

    public void init() {

        this.butLogin.setOnAction((ActionEvent event) -> {

                String IPentered = tfLoginIP.getText();
                String Username = tfLoginUsr.getText();


                //Check dass beide Fenster befüllt sind
                if(!(IPentered.isEmpty())&&!(Username.isEmpty())){

                    //IP-Adresse auf Gültigkeit prüfen
                    IPAddressValidator validator = new IPAddressValidator();
                    if( IPAddressValidator.isValid(IPentered)){


                    //Verbindung zu Server herstellen
                        try{
                            Socket client = new Socket(IPentered,4712);
                        }catch(UnknownHostException e){

                        }
                        catch (IOException e){
                        }
                        stage.setScene(msg);


                      //Nur zum Testen
                    } else{
                        System.out.printf("invalid");
                    }

                }
        });

        this.butSend.setOnAction((ActionEvent event2)-> {
            String textInput = tfMessage.getText();

        });
    }

    public ClientController(Stage stage){
        this.stage = stage;
    }
    public  void setButLogin(Button butLogin){
        this.butLogin = butLogin;
    }

    public void setTfLoginUsr(TextField tfLoginUsr){
        this.tfLoginUsr = tfLoginUsr;
    }

    public void setTfLoginIP(TextField tfLoginIP){
        this.tfLoginIP =tfLoginIP;
    }

}
