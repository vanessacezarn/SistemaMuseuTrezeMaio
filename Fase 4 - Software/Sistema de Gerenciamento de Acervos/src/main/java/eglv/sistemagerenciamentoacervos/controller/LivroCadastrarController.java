package eglv.sistemagerenciamentoacervos.controller;

import eglv.sistemagerenciamentoacervos.dao.*;
import eglv.sistemagerenciamentoacervos.model.*;

import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
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
import java.util.List;

public class LivroCadastrarController {

    // ====================== CAMPOS DO LIVRO ============================
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

    private byte[] capaBytes = null;

    // ====================== EDITORA ============================
    @FXML private TextField txtPesquisaEditora;
    @FXML private TableView<Editora> tblEditora;
    @FXML private TableColumn<Editora, String> colEditora;

    private FilteredList<Editora> listaEditorasFiltrada;

    // ====================== ASSUNTO ============================
    @FXML private TextField txtPesquisaAssunto;
    @FXML private TableView<Assunto> tblAssunto;
    @FXML private TableColumn<Assunto, String> colAssunto;

    private FilteredList<Assunto> listaAssuntosFiltrada;

    // ====================== COLABORADOR ============================
    @FXML private TextField txtPesquisaColaborador;
    @FXML private TableView<Colaborador> tblColaborador;
    @FXML private TableColumn<Colaborador, String> colColaborador;
    @FXML private TableColumn<Colaborador, String> colTipo;

    private FilteredList<Colaborador> listaColaboradoresFiltrada;

    // ====================== DAO ============================
    private LivroDAO livroDAO = new LivroDAO();
    private EditoraDAO editoraDAO = new EditoraDAO();
    private AssuntoDAO assuntoDAO = new AssuntoDAO();
    private ColaboradorDAO colaboradorDAO = new ColaboradorDAO();


    // =====================================================================
    //                          INITIALIZE()
    // =====================================================================
    @FXML
    private void initialize() {

        carregarEditoras();
        carregarAssuntos();
        carregarColaboradores();

        configurarSelecoes();
        configurarFiltros();
    }


    // =====================================================================
    //                  CARREGAMENTO DE EDITORAS / ASSUNTOS / COLABORADORES
    // =====================================================================

    private void carregarEditoras() {
        try {
            List<Editora> lista = editoraDAO.listarParaLivro();
            listaEditorasFiltrada = new FilteredList<>(FXCollections.observableArrayList(lista));
            tblEditora.setItems(listaEditorasFiltrada);

            colEditora.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getNome()));

        } catch (SQLException e) {
            mostrarAlerta("Erro ao carregar editoras: " + e.getMessage());
        }
    }

    private void carregarAssuntos() {
        try {
            List<Assunto> lista = assuntoDAO.listar();
            listaAssuntosFiltrada = new FilteredList<>(FXCollections.observableArrayList(lista));
            tblAssunto.setItems(listaAssuntosFiltrada);

            colAssunto.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getDescricao()));

            tblAssunto.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        } catch (SQLException e) {
            mostrarAlerta("Erro ao carregar assuntos: " + e.getMessage());
        }
    }

    private void carregarColaboradores() {
        try {
            List<Colaborador> lista = colaboradorDAO.listar();
            listaColaboradoresFiltrada = new FilteredList<>(FXCollections.observableArrayList(lista));
            tblColaborador.setItems(listaColaboradoresFiltrada);

            colColaborador.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getNome()));
            colTipo.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getTipo()));

            tblColaborador.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        } catch (SQLException e) {
            mostrarAlerta("Erro ao carregar colaboradores: " + e.getMessage());
        }
    }


    // =====================================================================
    //                     FILTROS DE PESQUISA EM TEMPO REAL
    // =====================================================================

    private void configurarFiltros() {

        txtPesquisaEditora.textProperty().addListener((obs, antigo, novo) -> {
            listaEditorasFiltrada.setPredicate(
                    e -> e.getNome().toLowerCase().contains(novo.toLowerCase())
            );
        });

        txtPesquisaAssunto.textProperty().addListener((obs, antigo, novo) -> {
            listaAssuntosFiltrada.setPredicate(
                    a -> a.getDescricao().toLowerCase().contains(novo.toLowerCase())
            );
        });

        txtPesquisaColaborador.textProperty().addListener((obs, ant, novo) -> {
            listaColaboradoresFiltrada.setPredicate(
                    c -> c.getNome().toLowerCase().contains(novo.toLowerCase())
                            || c.getTipo().toLowerCase().contains(novo.toLowerCase())
            );
        });
    }


    // =====================================================================
    //                       SELEÇÃO DOS ITENS
    // =====================================================================

    private void configurarSelecoes() {
        tblEditora.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tblAssunto.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tblColaborador.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }


    // =====================================================================
    //                           SALVAR LIVRO
    // =====================================================================

    @FXML
    private void salvarLivro() {

        try {

            // Criar livro
            Livro livro = new Livro();
            livro.setCodigo_livro(txtCodigo.getText());
            livro.setTitulo(txtTitulo.getText());
            livro.setSubtitulo(txtSubtitulo.getText());
            livro.setIsbn(txtISBN.getText());
            livro.setAno_publicacao(parseInteger(txtAno.getText()));
            livro.setLocalizacao_acervo(txtLocalizacao.getText());
            livro.setNumero_paginas(txtPaginas.getText());
            livro.setEdicao(txtEdicao.getText());
            livro.setIdioma(txtIdioma.getText());
            livro.setQuantidade(parseInteger(txtQuantidade.getText()));
            livro.setCapa(capaBytes);

            // EDITORA
            Editora editora = tblEditora.getSelectionModel().getSelectedItem();
            if (editora != null) {
                livro.setEditora(editora);
            }

            // ASSUNTOS
            livro.setAssuntos(tblAssunto.getSelectionModel().getSelectedItems());

            // COLABORADORES
            livro.setColaboradores(tblColaborador.getSelectionModel().getSelectedItems());

            // Salvar no banco
            livroDAO.inserir(livro);

            mostrarAlerta("Livro salvo com sucesso!");
            limparCampos();

        } catch (SQLException e) {
            mostrarAlerta("Erro ao salvar livro: " + e.getMessage());
        }
    }


    // =====================================================================
    //                         SELECIONAR CAPA
    // =====================================================================

    @FXML
    private void selecionarCapa() {

        FileChooser chooser = new FileChooser();
        chooser.setTitle("Selecionar Capa");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imagens", "*.png", "*.jpg", "*.jpeg"));

        File file = chooser.showOpenDialog(null);
        if (file != null) {
            try (FileInputStream fis = new FileInputStream(file)) {
                capaBytes = fis.readAllBytes();
                imgPreview.setImage(new Image(new ByteArrayInputStream(capaBytes)));
            } catch (IOException e) {
                mostrarAlerta("Erro ao carregar imagem: " + e.getMessage());
            }
        }
    }


    // =====================================================================
    //                            OUTROS MÉTODOS
    // =====================================================================

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
        capaBytes = null;

        tblEditora.getSelectionModel().clearSelection();
        tblAssunto.getSelectionModel().clearSelection();
        tblColaborador.getSelectionModel().clearSelection();
    }

    @FXML
    private void sair() {
        Stage stage = (Stage) txtTitulo.getScene().getWindow();
        stage.close();
    }

    private Integer parseInteger(String v) {
        try {
            return (v == null || v.isEmpty()) ? null : Integer.valueOf(v);
        } catch (Exception e) {
            return null;
        }
    }

    private void mostrarAlerta(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informação");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
