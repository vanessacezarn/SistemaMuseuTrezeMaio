package eglv.sistemagerenciamentoacervos.controller;

import eglv.sistemagerenciamentoacervos.dao.EditoraDAO;
import eglv.sistemagerenciamentoacervos.model.Editora;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;


public class EditoraController {
    @FXML private Label lblCadEditora;
    @FXML private Label lblNome;
    @FXML private Label lblLocalizacao;
    @FXML private Label lblPais;
    @FXML private Label lblEstado;
    @FXML private TextField txtNome;
    @FXML private TextField txtPais;
    @FXML private TextField txtEstado;
    @FXML private Button btnSalvar;

    @FXML private Label lblEditar;
    @FXML private Label lblBusca;
    @FXML private Label lblId;
    @FXML private Label lblEscolha;
    @FXML private TextField txtBusca;
    @FXML private TableView<Editora> tblEditoras;
    @FXML private TableColumn<Editora, Number> colId;
    @FXML private TableColumn<Editora, String> colNome;
    @FXML private TableColumn<Editora, String> colLocal;
    @FXML private TextField txtId;
    @FXML private Button btnEditar;


    private final EditoraDAO dao = new EditoraDAO();
    private final ObservableList<Editora> dados = FXCollections.observableArrayList();


    private void preencherFormulario(Editora e) {
        if (e == null) return;
        txtId.setText(String.valueOf(e.getId_editora()));
        txtNome.setText(e.getNome());
        String loc = e.getLocalizacao();
        if (loc != null && loc.contains(" , ")) {
            String[] partes = loc.split(" , ", 2);
            String estado = partes[0];
            String pais = partes[1];
            txtEstado.setText(estado);
            txtPais.setText(pais);
        } else {
            txtEstado.setText("");
            txtPais.setText(loc);
        }
    }

    private void recarregarTabela() {
        try {
            dados.setAll(dao.listar());
        } catch (SQLException e) {
            showError("Banco de dados", e.getMessage());
        }
    }

    private void filtrarTabela(String nome) {
        if (nome == null || nome.isBlank()) {
            recarregarTabela();
            return;
        }
        try {
            dados.setAll(dao.buscarPorNome(nome)); //lista filtrada
        } catch (SQLException e) {
            showError("Banco de dados", e.getMessage());
        }
    }
    private void limpar(){
        txtBusca.clear();
        txtId.clear();
        txtNome.clear();
        txtPais.clear();
        txtEstado.clear();
    }
    @FXML
    public void initialize() {
        colId.setCellValueFactory(c -> new javafx.beans.property.SimpleIntegerProperty(c.getValue().getId_editora()));
        colNome.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getNome()));
        colLocal.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getLocalizacao()));

        tblEditoras.setItems(dados);
        tblEditoras.getSelectionModel().selectedItemProperty().addListener((obs, old, sel) -> preencherFormulario(sel));

        txtBusca.textProperty().addListener((obs, old, value) -> filtrarTabela(value.toUpperCase()));
        recarregarTabela();
    }
    @FXML
    private void btnSalvar() {
        try {
            String nome = txtNome.getText() == null ? "" : txtNome.getText().trim().toUpperCase();
            String localizacao;
            String pais = txtPais.getText().trim().toUpperCase();
            String estado = txtEstado.getText().trim().toUpperCase();
            if (!pais.isEmpty() && !estado.isEmpty()) {
                localizacao = estado + " , " + pais;
            } else {
                localizacao = pais + estado; // um deles pode estar vazio
            }
            validar(nome);
            Editora e = new Editora(null,nome, localizacao);
            dao.inserir(e);

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
            String nome = txtNome.getText() == null ? "" : txtNome.getText().trim().toUpperCase();
            String localizacao;
            String pais = txtPais.getText().trim().toUpperCase();
            String estado = txtEstado.getText().trim().toUpperCase();

            if (!pais.isEmpty() && !estado.isEmpty()) {
                localizacao = estado + " , " + pais;
            } else {
                localizacao = pais + estado; // um deles pode estar vazio
            }
            validar(nome);
            String idStr = txtId.getText();
            Editora e = new Editora((idStr == null || idStr.isBlank()) ? null : Integer.parseInt(idStr),nome, localizacao);
            dao.atualizar(e);

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

    private void validar(String nome) {
        if (nome.isBlank()) throw new IllegalArgumentException("Nome é obrigatório.");

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
