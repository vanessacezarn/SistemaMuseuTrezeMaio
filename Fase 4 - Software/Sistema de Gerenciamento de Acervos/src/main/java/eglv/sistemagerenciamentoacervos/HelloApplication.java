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
       // FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/eglv/sistemagerenciamentoacervos/view/ColaboradorCadView.fxml"));
      //  FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/eglv/sistemagerenciamentoacervos/view/EditoraEditarView.fxml"));
       //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/eglv/sistemagerenciamentoacervos/view/EditoraExcluirView.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/eglv/sistemagerenciamentoacervos/view/EditoraListarView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 580, 650);
        stage.setTitle("ACERVO MUSEU TREZE MAIO ");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}