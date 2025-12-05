package eglv.sistemagerenciamentoacervos.controller;

import eglv.sistemagerenciamentoacervos.dao.JornalDAO;
import eglv.sistemagerenciamentoacervos.model.Jornal;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.SQLException;

public class JornalListarController {
    @FXML
    private TableView<Jornal> tblJornal;
    @FXML
    private TableColumn<Jornal, String> colCodigo;
    @FXML
    private TableColumn<Jornal, String> colTitulo;
    @FXML
    private TableColumn<Jornal, String> colData;
    @FXML
    private TableColumn<Jornal, String> colCidade;
    @FXML
    private TableColumn<Jornal, String> colEditora;
    @FXML
    private TextField txtBusca;

    private final JornalDAO Jdao = new JornalDAO();
    private ObservableList<Jornal> listaOriginalJornal = FXCollections.observableArrayList();
    private FilteredList<Jornal> listaFiltradaJornal;

    @FXML private ComboBox<String> cmbEscolha;
    @FXML
    public void initialize() {
        if(cmbEscolha != null){
            cmbEscolha.setItems(FXCollections.observableArrayList("Código","Título", "Data", "Cidade", "Editora"));
            txtBusca.clear();
        }

        if (cmbEscolha != null && txtBusca != null) {
            cmbEscolha.valueProperty().addListener((obs, oldV, newV) -> {
                txtBusca.clear();  // limpa busca ao trocar de campo
            });
        }

        colCodigo.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getCodigo_jornal()));
        colTitulo.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getTitulo()));
        colData.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getData().toString()));
        colCidade.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getCidade()));
        colEditora.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getEditora().getNome()));

        tblJornal.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        carregarJornal();
        configurarFiltroJornal();
    }

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

                case "Data":
                    listaOriginalJornal.setAll(Jdao.buscarPorData(valor));
                    break;

                case "Cidade":
                    listaOriginalJornal.setAll(Jdao.buscarPorCidade(valor));
                    break;

                case "Editora":
                    listaOriginalJornal.setAll(Jdao.buscarPorEditora(valor));
                    break;

                default:
                    carregarJornal();
            }
        } catch (SQLException e) {
            showError("Banco de dados", e.getMessage());
        }
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
