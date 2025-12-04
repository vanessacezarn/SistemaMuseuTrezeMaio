package eglv.sistemagerenciamentoacervos.controller;

import eglv.sistemagerenciamentoacervos.dao.LivroDAO;
import eglv.sistemagerenciamentoacervos.model.Livro;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.SQLException;

public class LivroExcluirController {

    @FXML private ComboBox<String> cbOpcaoBusca;
    @FXML private TextField txtBusca;
    @FXML private TableView<Livro> tblLivros;
    @FXML private TableColumn<Livro, String> colCodigo;
    @FXML private TableColumn<Livro, String> colTitulo;
    @FXML private TableColumn<Livro, String> colISBN;
    @FXML private TableColumn<Livro, Integer> colAno;
    @FXML private TableColumn<Livro, String> colIdioma;
    @FXML private TableColumn<Livro, Integer> colQuantidade;

    private LivroDAO livroDAO = new LivroDAO();

    @FXML
    private void initialize() {
        cbOpcaoBusca.getItems().addAll("Código", "Título", "ISBN");
        cbOpcaoBusca.getSelectionModel().selectFirst();

        colCodigo.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getCodigo_livro()));
        colTitulo.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getTitulo()));
        colISBN.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getIsbn()));
        colAno.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getAno_publicacao()));
        colIdioma.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getIdioma()));
        colQuantidade.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getQuantidade()));

        carregarTabela();
    }

    @FXML
    private void carregarTabela() {
        try {
            tblLivros.getItems().setAll(livroDAO.listarTodos());
        } catch (SQLException e) {
            mostrarAlerta("Erro ao carregar livros: " + e.getMessage());
        }
    }

    @FXML
    private void buscarLivro() {
        String criterio = cbOpcaoBusca.getValue();
        String valor = txtBusca.getText();

        try {
            Livro l = null;
            switch (criterio) {
                case "Código": l = livroDAO.buscarPorCodigo(valor); break;
                case "Título": l = livroDAO.buscarPorTitulo(valor); break;
                case "ISBN":   l = livroDAO.buscarPorISBN(valor); break;
            }
            if (l != null) {
                tblLivros.getItems().setAll(l);
            } else {
                mostrarAlerta("Nenhum livro encontrado.");
            }
        } catch (SQLException e) {
            mostrarAlerta("Erro ao buscar livro: " + e.getMessage());
        }
    }

    @FXML
    private void excluirLivro() {
        Livro selecionado = tblLivros.getSelectionModel().getSelectedItem();
        if (selecionado == null) {
            mostrarAlerta("Selecione um livro na tabela para excluir.");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirmação de Exclusão");
        confirm.setHeaderText(null);
        confirm.setContentText("Tem certeza que deseja excluir o livro \"" + selecionado.getTitulo() + "\"?");
        if (confirm.showAndWait().orElse(ButtonType.CANCEL) != ButtonType.OK) {
            return;
        }

        try {
            livroDAO.delete(selecionado.getId_livro());
            mostrarAlerta("Livro excluído com sucesso!");
            carregarTabela();
        } catch (SQLException e) {
            mostrarAlerta("Erro ao excluir livro: " + e.getMessage());
        }
    }

    @FXML
    private void sair() {
        Stage stage = (Stage) tblLivros.getScene().getWindow();
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
