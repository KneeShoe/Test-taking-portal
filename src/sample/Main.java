package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Server_Client.Client;


public class Main extends Application {

    public static Client user= new Client("127.0.0.1", 5005);
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Login_Signup/Login.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public void stop() throws Exception {
        Main.user.sendString("Exit");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
