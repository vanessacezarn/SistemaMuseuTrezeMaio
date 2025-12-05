package eglv.sistemagerenciamentoacervos.controller;

import eglv.sistemagerenciamentoacervos.dao.RevistaDAO;
import eglv.sistemagerenciamentoacervos.model.Revista;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;

public class RevistaListarController {

    @FXML
    private TableView<Revista> tblRevista;
    @FXML
    private TableColumn<Revista, String> colCodigo;
    @FXML
    private TableColumn<Revista, String> colTitulo;
    @FXML
    private TableColumn<Revista, Integer> colAno;
    @FXML
    private TableColumn<Revista, Integer> colMes;
    @FXML
    private TableColumn<Revista, String> colEditora;
    @FXML
    private TableColumn<Revista, String> colAssuntos;
    @FXML
    private TableColumn<Revista, String> colColaboradores;

    @FXML
    private ComboBox<String> cmbFiltroCampo;
    @FXML
    private TextField txtFiltroValor;

    private ObservableList<Revista> revistasObs;
    private final RevistaDAO revistaDAO = new RevistaDAO();

    @FXML
    public void initialize() {
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo_revista"));
        colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colAno.setCellValueFactory(new PropertyValueFactory<>("ano"));
        colMes.setCellValueFactory(new PropertyValueFactory<>("mes"));

        colEditora.setCellValueFactory(data -> {
            if (data.getValue().getEditora() != null) {
                return new ReadOnlyStringWrapper(data.getValue().getEditora().getNome());
            }
            return new ReadOnlyStringWrapper("");
        });

        colAssuntos.setCellValueFactory(data -> {
            String assuntos = data.getValue().getAssuntos().stream()
                    .map(a -> a.getDescricao())
                    .reduce((a1, a2) -> a1 + ", " + a2)
                    .orElse("");
            return new ReadOnlyStringWrapper(assuntos);
        });

        colColaboradores.setCellValueFactory(data -> {
            String colaboradores = data.getValue().getColaboradores().stream()
                    .map(c -> ((c.getNome() != null ? c.getNome() : "") + " " + (c.getSobrenome() != null ? c.getSobrenome() : "")).trim())
                    .reduce((c1, c2) -> c1 + ", " + c2)
                    .orElse("");
            return new ReadOnlyStringWrapper(colaboradores);
        });

        carregarTabela();

        cmbFiltroCampo.setItems(FXCollections.observableArrayList(
                "Código", "Título", "Ano", "Mês", "Editora", "Assunto", "Colaborador"
        ));
        cmbFiltroCampo.getSelectionModel().selectFirst();

        txtFiltroValor.textProperty().addListener((obs, oldV, newV) -> aplicarFiltros());
    }

    private void carregarTabela() {
        try {
            List<Revista> lista = revistaDAO.listarTodosCompleto();
            revistasObs = FXCollections.observableArrayList(lista);
            tblRevista.setItems(revistasObs);
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarErro("Erro ao listar revistas: " + e.getMessage());
        }
    }

    private void aplicarFiltros() {
        String campo = cmbFiltroCampo.getValue();
        String valor = txtFiltroValor.getText().toLowerCase();

        ObservableList<Revista> filtrados = revistasObs.filtered(r -> {
            if (valor.isEmpty()) return true;

            switch (campo) {
                case "Código":
                    return r.getCodigo_revista() != null && r.getCodigo_revista().toLowerCase().contains(valor);
                case "Título":
                    return r.getTitulo() != null && r.getTitulo().toLowerCase().contains(valor);
                case "Ano":
                    return r.getAno() != null && r.getAno().toString().contains(valor);
                case "Mês":
                    return r.getMes() != null && r.getMes().toString().contains(valor);
                case "Editora":
                    return r.getEditora() != null && r.getEditora().getNome().toLowerCase().contains(valor);
                case "Assunto":
                    return r.getAssuntos().stream().anyMatch(a -> a.getDescricao().toLowerCase().contains(valor));
                case "Colaborador":
                    return r.getColaboradores().stream().anyMatch(c -> c.getNome().toLowerCase().contains(valor));
                default:
                    return true;
            }
        });

        tblRevista.setItems(filtrados);
    }

    @FXML
    public void btnSair() {
        Stage stage = (Stage) txtFiltroValor.getScene().getWindow();
        stage.close();
    }

    private void mostrarErro(String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK);
        a.show();
    }
}
