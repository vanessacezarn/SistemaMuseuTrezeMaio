package eglv.sistemagerenciamentoacervos.controller;

import eglv.sistemagerenciamentoacervos.dao.LivroDAO;
import eglv.sistemagerenciamentoacervos.model.Livro;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;

public class LivroEditarController {

    @FXML private ComboBox<String> cbOpcaoBusca;
    @FXML private TextField txtBusca;

    @FXML private TableView<Livro> tblLivros;
    @FXML private TableColumn<Livro, String> colCodigo;
    @FXML private TableColumn<Livro, String> colTitulo;
    @FXML private TableColumn<Livro, String> colISBN;
    @FXML private TableColumn<Livro, Integer> colAno;
    @FXML private TableColumn<Livro, String> colIdioma;
    @FXML private TableColumn<Livro, Integer> colQuantidade;

    @FXML private TextField txtCodigo;
    @FXML private TextField txtTitulo;
    @FXML private TextField txtSubtitulo;
    @FXML private TextField txtISBN;
    @FXML private TextField txtAno;
    @FXML private TextField txtLocalizacao;
    @FXML private TextField txtPaginas;
    @FXML private TextField txtEdicao;
    @FXML private TextField txtIdioma;
    @FXML private TextField txtQuantidade;
    @FXML private ImageView imgPreview;

    private LivroDAO livroDAO = new LivroDAO();
    private Livro livroAtual;

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

        tblLivros.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                livroAtual = newSel;
                preencherCampos(newSel);
            }
        });
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

        if (criterio == null || valor.isEmpty()) {
            mostrarAlerta("Selecione um critério e digite um valor para buscar.");
            return;
        }

        try {
            Livro l = null;
            switch (criterio) {
                case "Código": l = livroDAO.buscarPorCodigo(valor); break;
                case "Título": l = livroDAO.buscarPorTitulo(valor); break;
                case "ISBN":   l = livroDAO.buscarPorISBN(valor); break;
            }

            if (l != null) {
                livroAtual = l;
                preencherCampos(l);
                tblLivros.getSelectionModel().select(l);
            } else {
                mostrarAlerta("Nenhum livro encontrado.");
            }

        } catch (SQLException e) {
            mostrarAlerta("Erro ao buscar livro: " + e.getMessage());
        }
    }

    @FXML
    private void salvarLivro() {
        if (livroAtual == null) {
            mostrarAlerta("Nenhum livro carregado para salvar.");
            return;
        }

        try {
            livroAtual.setTitulo(txtTitulo.getText());
            livroAtual.setSubtitulo(txtSubtitulo.getText());
            livroAtual.setIsbn(txtISBN.getText());
            livroAtual.setAno_publicacao(parseInteger(txtAno.getText()));
            livroAtual.setLocalizacao_acervo(txtLocalizacao.getText());
            livroAtual.setNumero_paginas(txtPaginas.getText());
            livroAtual.setEdicao(txtEdicao.getText());
            livroAtual.setIdioma(txtIdioma.getText());
            livroAtual.setQuantidade(parseInteger(txtQuantidade.getText()));

            livroDAO.update(livroAtual);
            mostrarAlerta("Livro atualizado com sucesso!");
            carregarTabela();

        } catch (SQLException e) {
            mostrarAlerta("Erro ao salvar livro: " + e.getMessage());
        }
    }

    @FXML
    private void selecionarCapa() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecionar Imagem da Capa");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imagens", "*.png", "*.jpg", "*.jpeg"));

        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            try (FileInputStream fis = new FileInputStream(file)) {
                byte[] bytes = fis.readAllBytes();
                if (livroAtual != null) {
                    livroAtual.setCapa(bytes);
                }
                imgPreview.setImage(new Image(file.toURI().toString()));
            } catch (IOException e) {
                mostrarAlerta("Erro ao carregar imagem: " + e.getMessage());
            }
        }
    }

    @FXML
    private void limparCampos() {
        txtCodigo.clear();
        txtTitulo.clear();
        txtSubtitulo.clear();
        txtISBN.clear();
        txtAno.clear();
        txtLocalizacao.clear();
        txtPaginas.clear();
        txtEdicao.clear();
        txtIdioma.clear();
        txtQuantidade.clear();
        imgPreview.setImage(null);
        livroAtual = null;
    }

    @FXML
    private void sair() {
        Stage stage = (Stage) txtTitulo.getScene().getWindow();
        stage.close();
    }

    private void preencherCampos(Livro l) {
        txtCodigo.setText(l.getCodigo_livro());
        txtTitulo.setText(l.getTitulo());
        txtSubtitulo.setText(l.getSubtitulo());
        txtISBN.setText(l.getIsbn());
        txtAno.setText(l.getAno_publicacao() != null ? l.getAno_publicacao().toString() : "");
        txtLocalizacao.setText(l.getLocalizacao_acervo());
        txtPaginas.setText(l.getNumero_paginas());
        txtEdicao.setText(l.getEdicao());
        txtIdioma.setText(l.getIdioma());
        txtQuantidade.setText(l.getQuantidade() != null ? l.getQuantidade().toString() : "");
        if (l.getCapa() != null) {
            imgPreview.setImage(new Image(new ByteArrayInputStream(l.getCapa())));
        }
    }

    private void mostrarAlerta(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informação");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private Integer parseInteger(String value) {
        try {
            return (value == null || value.isEmpty()) ? null : Integer.valueOf(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
