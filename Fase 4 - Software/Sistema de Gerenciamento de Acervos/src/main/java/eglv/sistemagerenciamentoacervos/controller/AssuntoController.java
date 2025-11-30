package eglv.sistemagerenciamentoacervos.controller;

import eglv.sistemagerenciamentoacervos.dao.AssuntoDAO;
import eglv.sistemagerenciamentoacervos.model.Assunto;
import eglv.sistemagerenciamentoacervos.model.Editora;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.sql.SQLException;

public class AssuntoController {
    @FXML private Label lblCadastro;
    @FXML private Label lblDescricao;
    @FXML private TextArea txaDescricao;
    @FXML private Button btnSalvar;

    private final AssuntoDAO dao = new AssuntoDAO();

    @FXML public void btnSalvar() {
        try {
            String descricao = txaDescricao.getText() == null ? "" : txaDescricao.getText().trim();

            validar(descricao);

            Assunto a = new Assunto(null,descricao);
            dao.inserir(a);

            showInfo("Sucesso", "Registro salvo com sucesso.");
            limpar();
        } catch (IllegalArgumentException e) {
            showError("Validação", e.getMessage());
        } catch (SQLException e) {
            showError("Banco de dados", e.getMessage());
        }
    }
    private void limpar(){
        txaDescricao.clear();
    }


    private void validar(String descricao) {
        if (descricao.isBlank()) throw new IllegalArgumentException("Descrição é obrigatório.");

    }
    private void showError(String title, String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle(title);
        a.setHeaderText(title);
        a.setContentText(msg);
        a.showAndWait();
    }
    private void showInfo(String title, String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(title);
        a.setHeaderText(title);
        a.setContentText(msg);
        a.showAndWait();
    }
}
