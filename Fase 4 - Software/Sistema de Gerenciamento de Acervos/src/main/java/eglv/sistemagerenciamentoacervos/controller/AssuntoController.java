package eglv.sistemagerenciamentoacervos.controller;

import eglv.sistemagerenciamentoacervos.dao.AssuntoDAO;
import eglv.sistemagerenciamentoacervos.model.Assunto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.SQLException;

public class AssuntoController {
    @FXML private Label lblCadastro;
    @FXML private Label lblDescricao;
    @FXML private TextArea txaDescricao;
    @FXML private Button btnSalvar;

    @FXML private Label lblEditar;
    @FXML private Label lblBusca;
    @FXML private Label lblEscolha;
    @FXML private TextField txtBusca;
    @FXML private TableView<Assunto> tblAssuntos;
    @FXML private TableColumn<Assunto, String> colDescricao;
    @FXML private Button btnEditar;

    @FXML private Label lblExcluir;
    @FXML private Button btnExcluir;
    @FXML private Button btnSair;

    private final AssuntoDAO dao = new AssuntoDAO();
    private final ObservableList<Assunto> dados = FXCollections.observableArrayList();

    private void preencherFormulario(Assunto a) {
        if (a == null) return;
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
            dados.setAll(dao.buscarPorNome(descricao));
        } catch (SQLException e) {
            showError("Banco de dados", e.getMessage());
        }
    }

    private void limpar(){
        txaDescricao.clear();

    }
    @FXML
    public void initialize() {
        if (tblAssuntos != null) {
            colDescricao.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getDescricao()));

            tblAssuntos.setItems(dados);
            tblAssuntos.getSelectionModel().selectedItemProperty().addListener((obs, old, sel) -> preencherFormulario(sel));
        }
        if(txtBusca != null) {
            txtBusca.textProperty().addListener((obs, old, value) -> filtrarTabela(value.toUpperCase()));
        }
        recarregarTabela();
    }

    @FXML public void btnSalvar() {
        try {
            String descricao = txaDescricao.getText() == null ? "" : txaDescricao.getText().trim().toLowerCase();
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
            Assunto selecionado = tblAssuntos.getSelectionModel().getSelectedItem();
            if (selecionado == null) {
                showError("Atenção", "Selecione um registro para editar.");
                return;
            }
            String descricao = txaDescricao.getText() == null ? "" : txaDescricao.getText().trim().toLowerCase();
            validar(descricao);
            Assunto a = new Assunto(selecionado.getId_assunto(),descricao);
            dao.atualizar(a);

            showInfo("Sucesso", "Registro editado com sucesso.");
            limpar();
            txtBusca.clear();
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
            Assunto selecionado = tblAssuntos.getSelectionModel().getSelectedItem();
            if (selecionado == null) {
                showError("Atenção", "Selecione um registro para excluir.");
                return;
            }

            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Excluir o registro selecionado?", ButtonType.YES, ButtonType.NO);
            confirm.setHeaderText("Confirmação");
            confirm.showAndWait();

            if (confirm.getResult() == ButtonType.YES) {
                dao.excluir(selecionado.getId_assunto());
                recarregarTabela();
                limpar();
                txtBusca.clear();
            }
        } catch (SQLException e) {
            showError("Banco de dados", e.getMessage());
        }
    }
    @FXML
    private void btnSair() {
        Stage stage = (Stage) btnSair.getScene().getWindow();
        stage.close();
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
