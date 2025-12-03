package eglv.sistemagerenciamentoacervos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        //EDITORA
        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/eglv/sistemagerenciamentoacervos/view/EditoraCadView.fxml"));
        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/eglv/sistemagerenciamentoacervos/view/EditoraEditarView.fxml"));
        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/eglv/sistemagerenciamentoacervos/view/EditoraExcluirView.fxml"));
        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/eglv/sistemagerenciamentoacervos/view/EditoraListarView.fxml"));
        //Scene scene = new Scene(fxmlLoader.load(), 575, 720);
        //ASSUNTO
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/eglv/sistemagerenciamentoacervos/view/AssuntoCadView.fxml"));
        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/eglv/sistemagerenciamentoacervos/view/AssuntoEditarView.fxml"));
        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/eglv/sistemagerenciamentoacervos/view/AssuntoExcluirView.fxml"));
        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/eglv/sistemagerenciamentoacervos/view/AssuntoListarView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 480, 600);
        //COLABORADOR
        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/eglv/sistemagerenciamentoacervos/view/ColaboradorCadView.fxml"));
        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/eglv/sistemagerenciamentoacervos/view/ColaboradorEditarView.fxml"));
        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/eglv/sistemagerenciamentoacervos/view/ColaboradorExcluirView.fxml"));
        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/eglv/sistemagerenciamentoacervos/view/ColaboradorListarView.fxml"));
        //Scene scene = new Scene(fxmlLoader.load(), 620,760);

        stage.setTitle("ACERVO MUSEU TREZE MAIO ");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}































