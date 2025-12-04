package eglv.sistemagerenciamentoacervos.controller;

import eglv.sistemagerenciamentoacervos.dao.EditoraDAO;
import eglv.sistemagerenciamentoacervos.model.Editora;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.SQLException;

public class EditoraController {
    @FXML private Label lblCadEditora;
    @FXML private Label lblNome;
    @FXML private Label lblLocalizacao;
    @FXML private Label lblPais;
    @FXML private Label lblEstado;
    @FXML private Label lblCidade;
    @FXML private TextField txtCidade;
    @FXML private TextField txtNome;
    @FXML private TextField txtPais;
    @FXML private TextField txtEstado;
    @FXML private Button btnSalvar;

    @FXML private Label lblEditar;
    @FXML private Label lblBusca;
    @FXML private Label lblEscolha;
    @FXML private TextField txtBusca;
    @FXML private TableView<Editora> tblEditoras;
    @FXML private TableColumn<Editora, Number> colId;
    @FXML private TableColumn<Editora, String> colNome;
    @FXML private TableColumn<Editora, String> colLocal;
    @FXML private Button btnEditar;

    @FXML private Label lblExcluir;
    @FXML private Button btnExcluir;

    @FXML private Label lblDigite;
    @FXML private ComboBox<String> cmbEscolha;
    @FXML private Button btnSair;


    private final EditoraDAO dao = new EditoraDAO();
    private final ObservableList<Editora> dados = FXCollections.observableArrayList();


    private void preencherFormulario(Editora e) {
        if (e == null) return;
        limpar();
        txtNome.setText(e.getNome());
        String loc = e.getLocalizacao();
        String[] partes = loc.split("\\s*,\\s*");
        switch (partes.length){
            case 3:
                txtCidade.setText(partes[0]);
                txtEstado.setText(partes[1]);
                txtPais.setText(partes[2]);
                break;
            case 2:
                txtEstado.setText(partes[0]);
                txtPais.setText(partes[1]);
                break;
            case 1:
                txtPais.setText(partes[0]);
                break;
        }
    }

    private void recarregarTabela() {
        try {
            dados.setAll(dao.listar());
        } catch (SQLException e) {
            showError("Banco de dados", e.getMessage());
        }
    }

    private void filtrarTabela(String valor) {
        if (valor == null || valor.isBlank()) {
            recarregarTabela();
            return;
        }
        String escolha = cmbEscolha.getValue();
        if (escolha == null) {
            return;
        }
        try {
            switch (escolha) {
                case "Nome":
                    dados.setAll(dao.buscarPorNome(valor));
                    break;

                case "Localização":
                    dados.setAll(dao.buscarPorLocalizacao(valor));
                    break;

                default:
                    recarregarTabela();
            }
        } catch (SQLException e) {
            showError("Banco de dados", e.getMessage());
        }
    }
    private void limpar(){
        txtNome.clear();
        txtPais.clear();
        txtEstado.clear();
        txtCidade.clear();
    }
    @FXML
    public void initialize() {
        if(cmbEscolha != null){
            cmbEscolha.setItems(FXCollections.observableArrayList("Nome","Localização"));
            txtBusca.clear();
        }
        if(tblEditoras!=null) {
            colNome.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getNome()));
            colLocal.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getLocalizacao()));

            tblEditoras.setItems(dados);
            tblEditoras.getSelectionModel().selectedItemProperty().addListener((obs, old, sel) -> preencherFormulario(sel));
        }
        if (txtBusca != null){
            txtBusca.textProperty().addListener((obs, old, value) -> {
                if (cmbEscolha == null ||cmbEscolha.getValue() == null) {
                    if (!value.isBlank()) {
                        showError("Busca inválida","Selecione um campo em 'Buscar por' antes de pesquisar.");
                    }
                    txtBusca.clear();
                    return;
                }
                filtrarTabela(value.toLowerCase());

            });
        }

        if (cmbEscolha != null && txtBusca != null) {
            cmbEscolha.valueProperty().addListener((obs, old, novo) -> {
                txtBusca.clear();
            });
        }

        recarregarTabela();
    }
    @FXML
    private void btnSalvar() {
        try {
            String nome = txtNome.getText() == null ? "" : txtNome.getText().trim().toLowerCase();
            String localizacao;
            String pais = txtPais.getText().trim().toLowerCase();
            String estado = txtEstado.getText().trim().toLowerCase();
            String cidade = txtCidade.getText().trim().toLowerCase();
            if (!pais.isEmpty() && !estado.isEmpty() && !cidade.isEmpty()){
                localizacao = cidade + " , "+ estado + " , " + pais;
            } else if (!pais.isEmpty() && !estado.isEmpty()) {
                localizacao = estado + " , " + pais;
            } else if (!pais.isEmpty() && !cidade.isEmpty()) {
                localizacao = cidade + " , " + pais;
            } else if (!estado.isEmpty() && !cidade.isEmpty()) {
                localizacao = cidade + " , " + estado;
            } else  {
                localizacao = pais + estado + cidade;
            }
            validar(nome,pais);
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
            Editora selecionada = tblEditoras.getSelectionModel().getSelectedItem();
            if (selecionada == null) {
                showError("Atenção", "Selecione um registro para editar.");
                return;
            }
            String nome = txtNome.getText() == null ? "" : txtNome.getText().trim().toLowerCase();
            String localizacao;
            String pais = txtPais.getText().trim().toLowerCase();
            String estado = txtEstado.getText().trim().toLowerCase();
            String cidade = txtCidade.getText().trim().toLowerCase();
            if (!pais.isEmpty() && !estado.isEmpty() && !cidade.isEmpty()){
                localizacao = cidade + " , "+ estado + " , " + pais;
            } else if (!pais.isEmpty() && !estado.isEmpty()) {
                localizacao = estado + " , " + pais;
            } else if (!pais.isEmpty() && !cidade.isEmpty()) {
                localizacao = cidade + " , " + pais;
            } else if (!estado.isEmpty() && !cidade.isEmpty()) {
                localizacao = cidade + " , " + estado;
            } else  {
                localizacao = pais + estado + cidade;
            }
            validar(nome,pais);
            Editora e = new Editora( selecionada.getId_editora(),nome, localizacao);
            dao.atualizar(e);

            showInfo("Sucesso", "Registro editado com sucesso.");
            limpar();
            cmbEscolha.setValue(null);
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
            Editora selecionada = tblEditoras.getSelectionModel().getSelectedItem();
            if (selecionada == null) {
                showError("Atenção", "Selecione um registro para excluir.");
                return;
            }
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Excluir o registro selecionado?", ButtonType.YES, ButtonType.NO);
            confirm.setHeaderText("Confirmação");
            confirm.showAndWait();

            if (confirm.getResult() == ButtonType.YES) {
                dao.excluir(selecionada.getId_editora());
                recarregarTabela();
                limpar();
                txtBusca.clear();
                cmbEscolha.setValue(null);
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

    private void validar(String nome,String pais) {
        if (nome.isBlank()) throw new IllegalArgumentException("Nome é obrigatório.");
        if(pais.isBlank()) throw new IllegalArgumentException("Pais é obrigatório");
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
