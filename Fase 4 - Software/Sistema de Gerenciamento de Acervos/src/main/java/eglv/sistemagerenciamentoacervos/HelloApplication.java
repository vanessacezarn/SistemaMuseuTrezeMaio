package eglv.sistemagerenciamentoacervos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/eglv/sistemagerenciamentoacervos/view/EditoraView.fxml"));
       // FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/eglv/sistemagerenciamentoacervos/view/AssuntoCadView.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/eglv/sistemagerenciamentoacervos/view/ColaboradorCadView.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Cadastro de editora!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}