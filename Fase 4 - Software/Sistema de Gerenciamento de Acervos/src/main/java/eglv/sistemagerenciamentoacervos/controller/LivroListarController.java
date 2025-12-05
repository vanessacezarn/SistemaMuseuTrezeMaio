package eglv.sistemagerenciamentoacervos.controller;

import eglv.sistemagerenciamentoacervos.dao.LivroDAO;
import eglv.sistemagerenciamentoacervos.model.Livro;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;

public class LivroListarController {

    @FXML
    private TableView<Livro> tblLivros;
    @FXML
    private TableColumn<Livro, String> colCodigo;
    @FXML
    private TableColumn<Livro, String> colTitulo;
    @FXML
    private TableColumn<Livro, String> colISBN;
    @FXML
    private TableColumn<Livro, Integer> colAno;
    @FXML
    private TableColumn<Livro, String> colEditora;
    @FXML
    private TableColumn<Livro, String> colAssuntos;
    @FXML
    private TableColumn<Livro, String> colColaboradores;

    @FXML
    private ComboBox<String> cmbFiltroCampo;
    @FXML
    private TextField txtFiltroValor;

    private ObservableList<Livro> livrosObs;
    private final LivroDAO livroDAO = new LivroDAO();

    @FXML
    public void initialize() {
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo_livro"));
        colTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        colISBN.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        colAno.setCellValueFactory(new PropertyValueFactory<>("ano_publicacao"));

        colEditora.setCellValueFactory(data -> {
            if (data.getValue().getEditora() != null) {
                return new ReadOnlyStringWrapper(data.getValue().getEditora().getNome());
            }
            return new ReadOnlyStringWrapper("");
        });

        // Assuntos: junta todas as descrições em uma string
        colAssuntos.setCellValueFactory(data -> {
            String assuntos = data.getValue().getAssuntos().stream()
                    .map(a -> a.getDescricao())
                    .reduce((a1, a2) -> a1 + ", " + a2)
                    .orElse("");
            return new ReadOnlyStringWrapper(assuntos);
        });

        // Colaboradores: junta todos os nomes em uma string
        colColaboradores.setCellValueFactory(data -> {
            String colaboradores = data.getValue().getColaboradores().stream()
                    .map(c -> ((c.getNome() != null ? c.getNome() : "") + " " + (c.getSobrenome() != null ? c.getSobrenome() : "")).trim())
                    .reduce((c1, c2) -> c1 + ", " + c2)
                    .orElse("");
            return new ReadOnlyStringWrapper(colaboradores);
        });

        carregarTabela();

        cmbFiltroCampo.setItems(FXCollections.observableArrayList(
                "Código", "Título", "ISBN", "Ano", "Editora", "Assunto", "Colaborador"
        ));
        cmbFiltroCampo.getSelectionModel().selectFirst();

        txtFiltroValor.textProperty().addListener((obs, oldV, newV) -> aplicarFiltros());
    }

    private void carregarTabela() {
        try {
            // Usa o novo metodo que traz livro + editora + assuntos + colaboradores
            List<Livro> lista = livroDAO.listarTodosCompleto();
            livrosObs = FXCollections.observableArrayList(lista);
            tblLivros.setItems(livrosObs);

            System.out.println("Qtd livros carregados: " + lista.size());
            for (Livro l : lista) {
                System.out.println("Livro: " + l.getTitulo());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarErro("Erro ao listar livros: " + e.getMessage());
        }
    }

    private void aplicarFiltros() {
        String campo = cmbFiltroCampo.getValue();
        String valor = txtFiltroValor.getText().toLowerCase();

        ObservableList<Livro> filtrados = livrosObs.filtered(l -> {
            if (valor.isEmpty()) return true; // mostra todos se não digitou nada

            switch (campo) {
                case "Código":
                    return l.getCodigo_livro() != null && l.getCodigo_livro().toLowerCase().contains(valor);
                case "Título":
                    return l.getTitulo() != null && l.getTitulo().toLowerCase().contains(valor);
                case "ISBN":
                    return l.getIsbn() != null && l.getIsbn().toLowerCase().contains(valor);
                case "Ano":
                    return l.getAno_publicacao() != null && l.getAno_publicacao().toString().contains(valor);
                case "Editora":
                    return l.getEditora() != null && l.getEditora().getNome().toLowerCase().contains(valor);
                case "Assunto":
                    return l.getAssuntos().stream().anyMatch(a -> a.getDescricao().toLowerCase().contains(valor));
                case "Colaborador":
                    return l.getColaboradores().stream().anyMatch(c -> c.getNome().toLowerCase().contains(valor));
                default:
                    return true;
            }
        });

        tblLivros.setItems(filtrados);
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
