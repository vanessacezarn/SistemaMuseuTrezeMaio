package eglv.sistemagerenciamentoacervos.controller;

import eglv.sistemagerenciamentoacervos.dao.RevistaDAO;
import eglv.sistemagerenciamentoacervos.model.Revista;
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

public class RevistaEditarController {

    @FXML private ComboBox<String> cbOpcaoBusca;
    @FXML private TextField txtBusca;

    @FXML private TableView<Revista> tblRevistas;
    @FXML private TableColumn<Revista, String> colCodigo;
    @FXML private TableColumn<Revista, String> colTitulo;
    @FXML private TableColumn<Revista, String> colVolume;
    @FXML private TableColumn<Revista, Integer> colAno;
    @FXML private TableColumn<Revista, Integer> colMes;
    @FXML private TableColumn<Revista, String> colIdioma;

    @FXML private TextField txtCodigo;
    @FXML private TextField txtTitulo;
    @FXML private TextField txtSubtitulo;
    @FXML private TextField txtVolume;
    @FXML private TextField txtAno;
    @FXML private TextField txtMes;
    @FXML private TextField txtPeriodicidade;
    @FXML private TextField txtPaginas;
    @FXML private TextField txtEdicao;
    @FXML private TextField txtIdioma;
    @FXML private TextField txtLocalizacao;
    @FXML private ImageView imgPreview;

    private RevistaDAO revistaDAO = new RevistaDAO();
    private Revista revistaAtual;

    @FXML
    private void initialize() {
        cbOpcaoBusca.getItems().addAll("Código", "Título");
        cbOpcaoBusca.getSelectionModel().selectFirst();

        colCodigo.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getCodigo_revista()));
        colTitulo.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getTitulo()));
        colVolume.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getVolume()));
        colAno.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<Integer>(data.getValue().getAno()));
        colMes.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<Integer>(data.getValue().getMes()));
        colIdioma.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getIdioma()));

        carregarTabela();

        tblRevistas.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                revistaAtual = newSel;
                preencherCampos(newSel);
            }
        });
    }

    @FXML
    private void carregarTabela() {
        try {
            tblRevistas.getItems().setAll(revistaDAO.listarTodos());
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
            Revista r = null;
            switch (criterio) {
                case "Código": r = revistaDAO.buscarPorCodigo(valor); break;
                case "Título": r = revistaDAO.buscarPorTitulo(valor); break;
            }

            if (r != null) {
                revistaAtual = r;
                preencherCampos(r);
                tblRevistas.getSelectionModel().select(r);
            } else {
                mostrarAlerta("Nenhum livro encontrado.");
            }

        } catch (SQLException e) {
            mostrarAlerta("Erro ao buscar livro: " + e.getMessage());
        }
    }

    @FXML
    private void salvarLivro() {
        if (revistaAtual == null) {
            mostrarAlerta("Nenhum livro carregado para salvar.");
            return;
        }

        try {
            revistaAtual.setTitulo(txtTitulo.getText());
            revistaAtual.setSubtitulo(txtSubtitulo.getText());
            revistaAtual.setAno(parseInteger(txtAno.getText()));
            revistaAtual.setMes(parseInteger(txtMes.getText()));
            revistaAtual.setVolume(txtVolume.getText());
            revistaAtual.setPeriodicidade(txtPeriodicidade.getText());
            revistaAtual.setLocalizacao_acervo(txtLocalizacao.getText());
            revistaAtual.setNumero_paginas(txtPaginas.getText());
            revistaAtual.setEdicao(txtEdicao.getText());
            revistaAtual.setIdioma(txtIdioma.getText());

            revistaDAO.atualizar(revistaAtual);
            mostrarAlerta("Revista atualizado com sucesso!");
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
                if (revistaAtual != null) {
                    revistaAtual.setCapa(bytes);
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
        txtMes.clear();
        txtAno.clear();
        txtVolume.clear();
        txtLocalizacao.clear();
        txtPaginas.clear();
        txtEdicao.clear();
        txtIdioma.clear();
        txtPeriodicidade.clear();
        imgPreview.setImage(null);
        revistaAtual = null;
    }

    @FXML
    private void sair() {
        Stage stage = (Stage) txtTitulo.getScene().getWindow();
        stage.close();
    }

    private void preencherCampos(Revista r) {
        if (r == null) return;

        txtCodigo.setText(r.getCodigo_revista());
        txtTitulo.setText(r.getTitulo());
        txtSubtitulo.setText(r.getSubtitulo());
        txtVolume.setText(r.getVolume());

        txtAno.setText(
                r.getAno() != null ? r.getAno().toString() : ""
        );

        txtMes.setText(
                r.getMes() != null ? r.getMes().toString() : ""
        );

        txtPeriodicidade.setText(r.getPeriodicidade());
        txtPaginas.setText(r.getNumero_paginas());
        txtEdicao.setText(r.getEdicao());
        txtIdioma.setText(r.getIdioma());
        txtLocalizacao.setText(r.getLocalizacao_acervo());

        if (r.getCapa() != null) {
            imgPreview.setImage(new Image(new ByteArrayInputStream(r.getCapa())));
        } else {
            imgPreview.setImage(null);
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
