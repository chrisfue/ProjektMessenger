package View;

import org.example.Application.Clientcontroller;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class ClientView {
    private final Clientcontroller clientcontroller;
    public ClientView(Clientcontroller clientcontroller){this.clientcontroller = clientcontroller;}
    public Parent client(){
//Input to send data
        TextArea toSend = new TextArea();
        this.clientcontroller.setToSend(toSend);
        toSend.setMaxWidth(120);
        toSend.maxHeight(200);
        toSend.setPromptText("Enter text to send!");
        Button buttonSend = new Button("send");
        this.clientcontroller.setButtonSend(buttonSend);
        VBox sent = new VBox(toSend,buttonSend);
        sent.setPadding(new Insets(10));

        //Window to receive response
        TextArea textReceived = new TextArea();
        this.clientcontroller.setTextReceived(textReceived);
        VBox received = new VBox(textReceived);
        received.setPadding(new Insets(10));
        received.setMaxWidth(120);
        received.maxHeight(200);


        BorderPane mainPane = new BorderPane();
        mainPane.setLeft(sent);
        mainPane.setRight(received);
        mainPane.setPadding(new Insets(10));

        return mainPane;

    }
}
