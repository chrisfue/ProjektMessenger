package de.projekt.gui;

import View.GuiView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/***
 *  JavaFX Gui Application of the Messenger Project in ODE
 *
 * @author ChrisFue, MarioKli,JanWie
 * @version 1.1.0
 *
 */
public class GUIApplication_2 extends Application {

    //private final


    /***
     * Start Function of the JavaFX application (similar to a main function in java)
     * @param stage the primary stage for this application, onto which
     * the application scene can be set.
     * Applications may create other stages, if needed, but they will not be
     * primary stages.
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException{
        ClientController clientController = new ClientController(stage);
        GuiView guiView = new GuiView(clientController);
       //Scenes erstellen
        Scene login = new Scene(guiView.loginWindow(),250,180);
        Scene msg = new Scene(guiView.MessageWindow(),500,350);
        //Scenes an Controller übergeben
        clientController.setLogin(login);
        clientController.setMsg(msg);
        stage.setScene(login);
        stage.show();
        clientController.init();



    }

    /***
     * Main Function of the program
     * @param args main function
     */
    public static void main(String[] args) {
        launch();
    }
}
