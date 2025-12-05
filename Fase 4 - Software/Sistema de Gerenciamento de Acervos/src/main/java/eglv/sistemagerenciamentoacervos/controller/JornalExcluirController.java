package eglv.sistemagerenciamentoacervos.controller;

import eglv.sistemagerenciamentoacervos.dao.JornalDAO;
import eglv.sistemagerenciamentoacervos.model.Editora;
import eglv.sistemagerenciamentoacervos.model.Jornal;
import eglv.sistemagerenciamentoacervos.model.Livro;
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

public class JornalExcluirController {
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


        colCodigo.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getCodigo_jornal()));
        colTitulo.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getTitulo()));
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

                default:
                    carregarJornal();
            }
        } catch (SQLException e) {
            showError("Banco de dados", e.getMessage());
        }
    }


    @FXML private Button btnExcluir;
    @FXML
    private void btnExcluir() {
        Jornal selecionado = tblJornal.getSelectionModel().getSelectedItem();
        if (selecionado == null || selecionado.getId_jornal() == null) {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setTitle("Atenção");
            a.setHeaderText("Seleção inválida");
            a.setContentText("Nenhum jornal foi selecionado para atualizar.");
            a.showAndWait();
            return;
        }


        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirmação de Exclusão");
        confirm.setHeaderText(null);
        confirm.setContentText("Tem certeza que deseja excluir o jornal? \"" + selecionado.getTitulo() + "\"?");
        if (confirm.showAndWait().orElse(ButtonType.CANCEL) != ButtonType.OK) {
            return;
        }

        try {
            Jdao.excluir(selecionado);
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("Sucesso");
            a.setHeaderText("Jornal excluido");
            a.setContentText("O jornal foi excluido com sucesso!");
            a.showAndWait();

        } catch (SQLException e) {
            e.printStackTrace();
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Erro");
            a.setHeaderText("Não foi possível excluir");
            a.setContentText(e.getMessage());
            a.showAndWait();
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
