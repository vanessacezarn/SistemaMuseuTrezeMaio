package eglv.sistemagerenciamentoacervos.controller;

import eglv.sistemagerenciamentoacervos.dao.AssuntoDAO;
import eglv.sistemagerenciamentoacervos.dao.ColaboradorDAO;
import eglv.sistemagerenciamentoacervos.model.Assunto;
import eglv.sistemagerenciamentoacervos.model.Colaborador;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javax.swing.*;
import java.sql.SQLException;

public class ColaboradorController {
    @FXML private Label lblCadastro;
    @FXML private Label lblNome;
    @FXML private Label lblSobrenome;
    @FXML private Label lblNacionalidade;
    @FXML private Label lblTipo;
    @FXML private TextField txtNome;
    @FXML private TextField txtSobrenome;
    @FXML private TextField txtNacionalidade;
    @FXML private ComboBox<String> cmbTipo;
    @FXML private Button btnSalvar;

    private final ColaboradorDAO dao = new ColaboradorDAO();

    @FXML public void initialize(){
        cmbTipo.setItems(FXCollections.observableArrayList("Autor", "Editor", "Organizador","Colaborador","Tradutor","Ilustrador"));

    }
    @FXML public void btnSalvar() {
        try {
            String Nome = txtNome.getText() == null ? "" : txtNome.getText().trim();
            String Sobrenome = txtSobrenome.getText() == null ? "" : txtSobrenome.getText();
            String Nacionalidade = txtNacionalidade.getText() == null ? "" : txtNacionalidade.getText();
            String Tipo = cmbTipo.getValue();
            validar(Nome, Sobrenome, Tipo);

            Colaborador c = new Colaborador(null,Nome, Sobrenome, Nacionalidade, Tipo);
            dao.inserir(c);

            showInfo("Sucesso", "Registro salvo com sucesso.");
            limpar();
        } catch (IllegalArgumentException e) {
            showError("Validação", e.getMessage());
        } catch (SQLException e) {
            showError("Banco de dados", e.getMessage());
        }
    }
    private void limpar(){
        txtNome.clear();
        txtSobrenome.clear();
        txtNacionalidade.clear();
        cmbTipo.getSelectionModel().clearSelection();
    }


    private void validar(String Nome, String Sobrenome, String Tipo) {
        if (Nome.isBlank()) throw new IllegalArgumentException("Nome é obrigatório.");
        if (Sobrenome.isBlank()) throw new IllegalArgumentException("Sobrenome é obrigatório");
        if (Tipo == null || Tipo.isBlank())
            throw new IllegalArgumentException("Tipo é obrigatório.");

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
