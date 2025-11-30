package eglv.sistemagerenciamentoacervos.controller;

import eglv.sistemagerenciamentoacervos.dao.EditoraDAO;
import eglv.sistemagerenciamentoacervos.model.Editora;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;


public class EditoraController {
    @FXML private Label lblCadEditora;
    @FXML private Label lblNome;
    @FXML private Label lblLocalizacao;
    @FXML private Label lblPais;
    @FXML private Label lblEstado;
    @FXML private TextField txtNome;
    @FXML private TextField txtPais;
    @FXML private TextField txtEstado;
    @FXML private Button btnSalvar;

    private final EditoraDAO dao = new EditoraDAO();

    @FXML
    private void btnSalvar() {
        try {
            String nome = txtNome.getText() == null ? "" : txtNome.getText().trim();
            String localizacao;
            String pais = txtPais.getText().trim();
            String estado = txtEstado.getText().trim();


            if (!pais.isEmpty() && !estado.isEmpty()) {
                localizacao = pais + " - " + estado;
            } else {
                localizacao = pais + estado; // um deles pode estar vazio
            }


            validar(nome, localizacao);

            //String idStr = txtId.getText();
            Editora p = new Editora(
                    //(idStr == null || idStr.isBlank()) ? null : Integer.parseInt(idStr),
                    null,nome, localizacao
            );

            dao.inserir(p);

            showInfo("Sucesso", "Registro salvo com sucesso.");
        } catch (IllegalArgumentException e) {
            showError("Validação", e.getMessage());
        } catch (SQLException e) {
            showError("Banco de dados", e.getMessage());
        }
    }
    private void validar(String nome, String localizacao) {
        if (nome.isBlank()) throw new IllegalArgumentException("Nome é obrigatório.");

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
