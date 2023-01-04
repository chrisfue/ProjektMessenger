package de.projekt.gui;

import View.GuiView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GUIApplication_2 extends Application {

    //private final


    @Override
    public void start(Stage stage) throws IOException{
        ClientController clientController = new ClientController(stage);
        GuiView guiView = new GuiView(clientController);
       //Scenes erstellen
        Scene login = new Scene(guiView.loginWindow(),270,140);
        Scene msg = new Scene(guiView.MessageWindow(),300,200);
        //Scenes an Controller übergeben
        clientController.setLogin(login);
        clientController.setMsg(msg);
        stage.setScene(login);
        stage.show();
        clientController.init();



    }

    public static void main(String[] args) {
        launch();
    }
}
