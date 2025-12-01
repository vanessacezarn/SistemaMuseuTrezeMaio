package eglv.sistemagerenciamentoacervos.controller;

import eglv.sistemagerenciamentoacervos.dao.AssuntoDAO;
import eglv.sistemagerenciamentoacervos.model.Assunto;
import eglv.sistemagerenciamentoacervos.model.Editora;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;

public class AssuntoController {
    @FXML private Label lblCadastro;
    @FXML private Label lblDescricao;
    @FXML private TextArea txaDescricao;
    @FXML private Button btnSalvar;

    @FXML private Label lblEditar;
    @FXML private Label lblBusca;
    @FXML private Label lblEscolha;
    @FXML private Label lblId;
    @FXML private TextField txtBusca;
    @FXML private TextField txtId;
    @FXML private TableView<Assunto> tblAssuntos;
    @FXML private TableColumn<Assunto, Number> colId;
    @FXML private TableColumn<Assunto, String> colDescricao;
    @FXML private Button btnEditar;



    private final AssuntoDAO dao = new AssuntoDAO();
    private final ObservableList<Assunto> dados = FXCollections.observableArrayList();

    private void preencherFormulario(Assunto a) {
        if (a == null) return;
        txtId.setText(String.valueOf(a.getId_assunto()));
        txaDescricao.setText(a.getDescricao());
    }

    private void recarregarTabela() {
        try {
            dados.setAll(dao.listar());
        } catch (SQLException e) {
            showError("Banco de dados", e.getMessage());
        }
    }

    private void filtrarTabela(String descricao) {
        if (descricao == null || descricao.isBlank()) {
            recarregarTabela();
            return;
        }
        try {
            dados.setAll(dao.buscarPorNome(descricao)); //lista filtrada
        } catch (SQLException e) {
            showError("Banco de dados", e.getMessage());
        }
    }

    private void limpar(){
        txtBusca.clear();
        txtId.clear();
        txaDescricao.clear();

    }
    @FXML
    public void initialize() {
        colId.setCellValueFactory(c -> new javafx.beans.property.SimpleIntegerProperty(c.getValue().getId_assunto()));
        colDescricao.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getDescricao()));

        tblAssuntos.setItems(dados);
        tblAssuntos.getSelectionModel().selectedItemProperty().addListener((obs, old, sel) -> preencherFormulario(sel));

        txtBusca.textProperty().addListener((obs, old, value) -> filtrarTabela(value.toUpperCase()));
        recarregarTabela();
    }

    @FXML public void btnSalvar() {
        try {
            String descricao = txaDescricao.getText() == null ? "" : txaDescricao.getText().trim();
            validar(descricao);

            Assunto a = new Assunto(null,descricao);
            dao.inserir(a);

            showInfo("Sucesso", "Registro salvo com sucesso.");
            limpar();
        } catch (IllegalArgumentException e) {
            showError("Validação", e.getMessage());
        } catch (SQLException e) {
            showError("Banco de dados", e.getMessage());
        }
    }

    @FXML
    private void btnEditar() {
        try {
            if (txtId.getText() == null || txtId.getText().isBlank()) {
                showError("Atenção", "Selecione um registro para editar.");
                return;
            }
            String descricao = txaDescricao.getText() == null ? "" : txaDescricao.getText().trim().toUpperCase();

            validar(descricao);
            String idStr = txtId.getText();
            Assunto a = new Assunto((idStr == null || idStr.isBlank()) ? null : Integer.parseInt(idStr),descricao);
            dao.atualizar(a);

            showInfo("Sucesso", "Registro editado com sucesso.");
            limpar();
            recarregarTabela();
        } catch (IllegalArgumentException e) {
            showError("Validação", e.getMessage());
        } catch (SQLException e) {
            showError("Banco de dados", e.getMessage());
        }
    }

    @FXML
    private void btnExcluir() {
        try {
            if (txtId.getText() == null || txtId.getText().isBlank()) {
                showError("Atenção", "Selecione um registro para excluir.");
                return;
            }
            int id = Integer.parseInt(txtId.getText());

            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Excluir o registro selecionado?", ButtonType.YES, ButtonType.NO);
            confirm.setHeaderText("Confirmação");
            confirm.showAndWait();

            if (confirm.getResult() == ButtonType.YES) {
                dao.excluir(id);
                recarregarTabela();
                limpar();
            }
        } catch (SQLException e) {
            showError("Banco de dados", e.getMessage());
        }
    }


    private void validar(String descricao) {
        if (descricao.isBlank()) throw new IllegalArgumentException("Descrição é obrigatório.");

    }
    private void showError(String title, String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle(title);
        a.setHeaderText(title);
        a.setContentText(msg);
        a.showAndWait();
    }
    private void showInfo(String title, String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(title);
        a.setHeaderText(title);
        a.setContentText(msg);
        a.showAndWait();
    }
}
