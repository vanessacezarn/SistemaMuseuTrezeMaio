package eglv.sistemagerenciamentoacervos.controller;

import eglv.sistemagerenciamentoacervos.dao.RevistaDAO;
import eglv.sistemagerenciamentoacervos.model.Revista;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.sql.SQLException;

public class RevistaExcluirController {

    @FXML private ComboBox<String> cbOpcaoBusca;
    @FXML private TextField txtBusca;

    @FXML private TableView<Revista> tblAno;

    @FXML private TableColumn<Revista, String> colCodigo;
    @FXML private TableColumn<Revista, String> colTitulo;
    @FXML private TableColumn<Revista, String> colVolume;
    @FXML private TableColumn<Revista, Integer> colAno;
    @FXML private TableColumn<Revista, Integer> colMes;
    @FXML private TableColumn<Revista, String> colIdioma;

    private RevistaDAO revistaDAO = new RevistaDAO();

    @FXML
    private void initialize() {
        cbOpcaoBusca.getItems().addAll("Código", "Título");
        cbOpcaoBusca.getSelectionModel().selectFirst();

        colCodigo.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getCodigo_revista())
        );

        colTitulo.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getTitulo())
        );

        colVolume.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getVolume())
        );

        colAno.setCellValueFactory(data ->
                new SimpleObjectProperty<>(data.getValue().getAno())
        );

        colMes.setCellValueFactory(data ->
                new SimpleObjectProperty<>(data.getValue().getMes())
        );

        colIdioma.setCellValueFactory(data ->
                new SimpleStringProperty(data.getValue().getIdioma())
        );

        carregarTabela();
    }

    @FXML
    private void carregarTabela() {
        try {
            tblAno.getItems().setAll(revistaDAO.listarTodos());
        } catch (SQLException e) {
            mostrarAlerta("Erro ao carregar revistas: " + e.getMessage());
        }
    }

    @FXML
    private void buscarLivro() { // mantém esse nome por causa do FXML
        String criterio = cbOpcaoBusca.getValue();
        String valor = txtBusca.getText();

        try {
            Revista r = null;

            switch (criterio) {
                case "Código":
                    r = revistaDAO.buscarPorCodigo(valor);
                    break;

                case "Título":
                    r = revistaDAO.buscarPorTitulo(valor);
                    break;
            }

            if (r != null) {
                tblAno.getItems().setAll(r);
            } else {
                mostrarAlerta("Nenhuma revista encontrada.");
            }

        } catch (SQLException e) {
            mostrarAlerta("Erro ao buscar revista: " + e.getMessage());
        }
    }

    @FXML
    private void excluirLivro() { // mantém esse nome por causa do FXML
        Revista selecionada = tblAno.getSelectionModel().getSelectedItem();

        if (selecionada == null) {
            mostrarAlerta("Selecione uma revista na tabela para excluir.");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirmação de Exclusão");
        confirm.setHeaderText(null);
        confirm.setContentText(
                "Tem certeza que deseja excluir a revista \"" + selecionada.getTitulo() + "\"?"
        );

        if (confirm.showAndWait().orElse(ButtonType.CANCEL) != ButtonType.OK) {
            return;
        }

        try {
            revistaDAO.delete(selecionada.getId_revista());
            mostrarAlerta("Revista excluída com sucesso!");
            carregarTabela();
        } catch (SQLException e) {
            mostrarAlerta("Erro ao excluir revista: " + e.getMessage());
        }
    }

    @FXML
    private void sair() {
        Stage stage = (Stage) tblAno.getScene().getWindow();
        stage.close();
    }

    private void mostrarAlerta(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informação");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
