package org.example.Application;

import View.ClientView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;



import java.io.IOException;

public class ClientApplication extends Application {

    private final Clientcontroller clientConroller = new Clientcontroller();

    @Override
    public void start(Stage stage) throws IOException {

        ClientView clientView = new ClientView(clientConroller);
        Scene scene = new Scene(clientView.client(), 320, 240);
        stage.setTitle("Client Access");
        stage.setScene(scene);
        stage.show();
        clientConroller.init();
    }

    public static void main(String[] args) {
        launch();
    }
}