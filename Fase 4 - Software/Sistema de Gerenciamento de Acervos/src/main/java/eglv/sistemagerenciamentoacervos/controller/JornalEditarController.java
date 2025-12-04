package eglv.sistemagerenciamentoacervos.controller;

import eglv.sistemagerenciamentoacervos.dao.AssuntoDAO;
import eglv.sistemagerenciamentoacervos.dao.ColaboradorDAO;
import eglv.sistemagerenciamentoacervos.dao.EditoraDAO;
import eglv.sistemagerenciamentoacervos.model.Assunto;
import eglv.sistemagerenciamentoacervos.model.Colaborador;
import eglv.sistemagerenciamentoacervos.model.Editora;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Files;
import java.sql.SQLException;

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

    @FXML
    private void salvarJornal() {
        System.out.println("Salvar jornal chamado!");
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
