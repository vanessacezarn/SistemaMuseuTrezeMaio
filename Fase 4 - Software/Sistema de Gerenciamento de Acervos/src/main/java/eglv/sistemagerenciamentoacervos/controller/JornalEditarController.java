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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.file.Files;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class JornalEditarController {
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

    //JORNAL
    @FXML
    private TableView<Jornal> tblJornal;
    @FXML
    private TableColumn<Jornal, String> colCodigo;
    @FXML
    private TableColumn<Jornal, String> colTitulo;
    @FXML
    private TextField txtBusca;

    private final JornalDAO Jdao = new JornalDAO();
    private ObservableList<Jornal> listaOriginalJornal = FXCollections.observableArrayList();
    private FilteredList<Jornal> listaFiltradaJornal;

    @FXML private ComboBox<String> cmbEscolha;
    @FXML
    public void initialize() {
        if(cmbEscolha != null){
            cmbEscolha.setItems(FXCollections.observableArrayList("Código","Título"));
            txtBusca.clear();
        }

        if (cmbEscolha != null && txtBusca != null) {
            cmbEscolha.valueProperty().addListener((obs, oldV, newV) -> {
                txtBusca.clear();  // limpa busca ao trocar de campo
            });
        }

        tblJornal.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> {
            if (newV != null) {
                preencherCampos(newV);
            }
        });


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

        //JORNAL
        colCodigo.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getCodigo_jornal()));
        colTitulo.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getTitulo()));
        tblJornal.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        carregarJornal();
        configurarFiltroJornal();
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

    //JORNAL
    private void carregarJornal() {
        try {
            JornalDAO jDAO = new JornalDAO();
            listaOriginalJornal.setAll(jDAO.listar());
            listaFiltradaJornal = new FilteredList<>(listaOriginalJornal, p -> true);
            tblJornal.setItems(listaFiltradaJornal);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void configurarFiltroJornal() {
        txtBusca.textProperty().addListener((obs, oldValue, newValue) -> {

            // se o usuário digitou mas não escolheu Código/Título ainda
            if (cmbEscolha.getValue() == null) {
                if (!newValue.isBlank()) {
                    showError("Busca inválida", "Selecione um campo em 'Buscar por' antes de pesquisar.");
                }
                txtBusca.clear();
                return;
            }

            filtrarTabela(newValue.toLowerCase());
        });
    }


    private void filtrarTabela(String valor) {
        if (valor == null || valor.isBlank()) {
            carregarJornal();
            return;
        }
        String escolha = cmbEscolha.getValue();
        if (escolha == null) {
            return;
        }
        try {
            switch (escolha) {
                case "Código":
                    listaOriginalJornal.setAll(Jdao.buscarPorCodigo(valor));
                    break;

                case "Título":
                    listaOriginalJornal.setAll(Jdao.buscarPorTitulo(valor));
                    break;

                default:
                    carregarJornal();
            }
        } catch (SQLException e) {
            showError("Banco de dados", e.getMessage());
        }
    }

    //IMAGEM CAPA
    private byte[] imagemCapa;

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

    //SELECIONAR
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

    private void preencherCampos(Jornal j) {

        txtCodigo.setText(j.getCodigo_jornal());
        txtPais.setText(j.getPais());
        txtEstado.setText(j.getEstado());
        txtCidade.setText(j.getCidade());
        txtLocalizacao.setText(j.getLocalizacao_acervo());
        txtPaginas.setText(j.getNumero_paginas());
        txtEdicao.setText(j.getEdicao());
        txtIdioma.setText(j.getIdioma());
        txtTitulo.setText(j.getTitulo());
        txtSubtitulo.setText(j.getSubtitulo());

        /*
        if (j.getCapa() != null) {
            Image img = new Image(new ByteArrayInputStream(j.getCapa()));
            imgCapa.setImage(img);
        } else {
            imgCapa.setImage(null);
        }
        */

        //editora assunto colaborador
    }

    @FXML private Button btnEditar;
    @FXML
    private void btnEditar() {
        try {
            Jornal selecionado =  tblJornal.getSelectionModel().getSelectedItem();;
            if (selecionado == null || selecionado.getId_jornal() == null) {
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.setTitle("Atenção");
                a.setHeaderText("Seleção inválida");
                a.setContentText("Nenhum jornal foi selecionado para atualizar.");
                a.showAndWait();
                return;
            }

            Editora editoraSelecionada = tblEditora.getSelectionModel().getSelectedItem();

            Jornal j = new Jornal();
            j.setId_jornal(selecionado.getId_jornal());
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

            Jdao.atualizar(j);

            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("Sucesso");
            a.setHeaderText("Jornal atualizado");
            a.setContentText("O jornal foi atualizado com sucesso!");
            a.showAndWait();

            limparCampos();

        } catch (Exception e) {
            e.printStackTrace();
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Erro");
            a.setHeaderText("Não foi possível atualizar");
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
