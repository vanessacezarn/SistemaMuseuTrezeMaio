package eglv.sistemagerenciamentoacervos.controller;

import eglv.sistemagerenciamentoacervos.dao.AssuntoDAO;
import eglv.sistemagerenciamentoacervos.dao.ColaboradorDAO;
import eglv.sistemagerenciamentoacervos.dao.EditoraDAO;
import eglv.sistemagerenciamentoacervos.dao.JornalDAO;
import eglv.sistemagerenciamentoacervos.model.Assunto;
import eglv.sistemagerenciamentoacervos.model.Colaborador;
import eglv.sistemagerenciamentoacervos.model.Editora;
import eglv.sistemagerenciamentoacervos.model.Jornal;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

//import java.awt.*;
import java.io.File;
import java.nio.file.Files;
import java.sql.SQLException;

public class JornalCadController {
    private final JornalDAO jdao = new JornalDAO();

    //EDITORA
    @FXML
    private TableView<Editora> tblEditora;
    @FXML
    private TableColumn<Editora, String> colEditora;
    @FXML
    private TextField txtPesquisaEditora;

    private final EditoraDAO Edao = new EditoraDAO();
    private ObservableList<Editora> listaOriginal = FXCollections.observableArrayList();
    private FilteredList<Editora> listaFiltrada;

    //ASSUNTO
    @FXML
    private TableView<Assunto> tblAssunto;
    @FXML
    private TableColumn<Assunto, String> colAssunto;
    @FXML
    private TextField txtPesquisaAssunto;

    private final AssuntoDAO assuntoDAO = new AssuntoDAO();
    private ObservableList<Assunto> listaAssuntos = FXCollections.observableArrayList();
    private FilteredList<Assunto> listaAssuntosFiltrada;

    //COLABORADOR
    @FXML private TableView<Colaborador> tblColaborador;
    @FXML private TableColumn<Colaborador, String> colColaborador;
    @FXML private TableColumn<Colaborador, String> colTipo;
    @FXML private TextField txtPesquisaColaborador;

    private final ColaboradorDAO colaboradorDAO = new ColaboradorDAO();
    private ObservableList<Colaborador> listaColaboradores = FXCollections.observableArrayList();
    private FilteredList<Colaborador> listaColaboradoresFiltrada;

    @FXML
    public void initialize() {
        //EDITORA
        tblEditora.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        colEditora.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNome()));
        carregarEditoras();
        configurarFiltroEditora();

        //ASSUNTO
        colAssunto.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getDescricao()));
        tblAssunto.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        carregarAssuntos();
        configurarFiltroAssuntos();

        //COLABORADOR
        colColaborador.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getNome()));
        colTipo.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getTipo()));
        tblColaborador.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        carregarColaboradores();
        configurarFiltroColaboradores();
    }

    // EDITORA
    private void carregarEditoras() {
        try {
            listaOriginal.setAll(Edao.listar());
            listaFiltrada = new FilteredList<>(listaOriginal, p -> true);
            tblEditora.setItems(listaFiltrada);
        } catch (SQLException e) {
            showError("Banco de dados", e.getMessage());
        }
    }

    private void configurarFiltroEditora() {
        txtPesquisaEditora.textProperty().addListener((obs, oldValue, newValue) -> {
            listaFiltrada.setPredicate(editora -> {
                if (newValue == null || newValue.isEmpty())
                    return true;
                String filtro = newValue.toLowerCase();
                return editora.getNome().toLowerCase().contains(filtro);
            });
        });
    }

    //ASSUNTO
    private void carregarAssuntos() {
        try {
            listaAssuntos.setAll(assuntoDAO.listar());
            listaAssuntosFiltrada = new FilteredList<>(listaAssuntos, p -> true);
            tblAssunto.setItems(listaAssuntosFiltrada);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void configurarFiltroAssuntos() {
        txtPesquisaAssunto.textProperty().addListener((obs, oldV, newV) -> {
            listaAssuntosFiltrada.setPredicate(assunto -> {
                if (newV == null || newV.isEmpty()) return true;
                return assunto.getDescricao().toLowerCase().contains(newV.toLowerCase());
            });
        });
    }

    //COLABORADOR
    private void carregarColaboradores() {
        try {
            listaColaboradores.setAll(colaboradorDAO.listar());
            listaColaboradoresFiltrada = new FilteredList<>(listaColaboradores, p -> true);
            tblColaborador.setItems(listaColaboradoresFiltrada);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void configurarFiltroColaboradores() {
        txtPesquisaColaborador.textProperty().addListener((obs, oldV, newV) -> {
            listaColaboradoresFiltrada.setPredicate(colaborador -> {
                if (newV == null || newV.isEmpty()) return true;
                return colaborador.getNome().toLowerCase().contains(newV.toLowerCase()) ||
                        colaborador.getTipo().toLowerCase().contains(newV.toLowerCase());
            });
        });
    }

    //IMAGEM CAPA
    private byte[] imagemCapa; // bytes da imagem para salvar no banco

    @FXML
    public void selecionarCapa() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Selecionar Imagem de Capa");

            // filtro para tipos de imagem
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Imagens", "*.png", "*.jpg", "*.jpeg")
            );

            File file = fileChooser.showOpenDialog(null); // abre o explorador
            if (file != null) {
                // lê o arquivo em bytes
                imagemCapa = Files.readAllBytes(file.toPath());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //SALVAR
    @FXML private TextField txtCodigo;
    @FXML private TextField txtPais;
    @FXML private TextField txtEstado;
    @FXML private TextField txtCidade;
    @FXML private DatePicker dateData;
    @FXML private TextField txtLocalizacao;
    @FXML private TextField txtPaginas;
    @FXML private TextField txtEdicao;
    @FXML private TextField txtIdioma;
    @FXML private TextField txtTitulo;
    @FXML private TextField txtSubtitulo;
    @FXML private Button btnSalvar;

    @FXML
    private void btnSalvar() {
        try {
            Editora editoraSelecionada = tblEditora.getSelectionModel().getSelectedItem();

            Jornal j = new Jornal();
            j.setCodigo_jornal(txtCodigo.getText());
            j.setPais(txtPais.getText());
            j.setEstado(txtEstado.getText());
            j.setCidade(txtCidade.getText());
            j.setData(java.sql.Date.valueOf(dateData.getValue()));
            j.setLocalizacao_acervo(txtLocalizacao.getText());
            j.setNumero_paginas(txtPaginas.getText());
            j.setEdicao(txtEdicao.getText());
            j.setIdioma(txtIdioma.getText());
            j.setTitulo(txtTitulo.getText());
            j.setSubtitulo(txtSubtitulo.getText());
            j.setCapa(imagemCapa);
            j.setQuantidade(null);
            j.setEditora(editoraSelecionada);

            j.getAssuntos().addAll(tblAssunto.getSelectionModel().getSelectedItems());

            j.getColaboradores().addAll(tblColaborador.getSelectionModel().getSelectedItems());

            jdao.inserir(j);

            jdao.inserirAssuntos(j);
            jdao.inserirColaboradores(j);

            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("Sucesso");
            a.setHeaderText("Jornal salvo");
            a.setContentText("O jornal foi salvo com sucesso!");
            a.showAndWait();

            limparCampos();

        } catch (Exception e) {
            e.printStackTrace();
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Erro");
            a.setHeaderText("Não foi possível salvar");
            a.setContentText(e.getMessage());
            a.showAndWait();
        }
    }

    private void limparCampos() {
        txtCodigo.setText("");
        txtPais.setText("");
        txtEstado.setText("");
        txtCidade.setText("");
        txtLocalizacao.setText("");
        txtPaginas.setText("");
        txtEdicao.setText("");
        txtIdioma.setText("");
        txtTitulo.setText("");
        txtSubtitulo.setText("");
        dateData.setValue(null);
        tblAssunto.getSelectionModel().clearSelection();
        tblColaborador.getSelectionModel().clearSelection();
        tblEditora.getSelectionModel().clearSelection();
    }

    private void showError(String title, String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle(title);
        a.setHeaderText(title);
        a.setContentText(msg);
        a.showAndWait();
    }

    @FXML
    private Button btnSair;

    @FXML
    private void sair() {
        Stage stage = (Stage) btnSair.getScene().getWindow();
        stage.close();
    }
}
