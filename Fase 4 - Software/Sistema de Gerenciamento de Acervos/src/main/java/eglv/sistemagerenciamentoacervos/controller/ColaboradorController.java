package eglv.sistemagerenciamentoacervos.controller;

import eglv.sistemagerenciamentoacervos.dao.ColaboradorDAO;
import eglv.sistemagerenciamentoacervos.model.Colaborador;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import javax.swing.*;
import java.sql.SQLException;

public class ColaboradorController {
    @FXML private Label lblCadastro;
    @FXML private Label lblNome;
    @FXML private Label lblSobrenome;
    @FXML private Label lblNacionalidade;
    @FXML private Label lblTipo;
    @FXML private TextField txtNome;
    @FXML private TextField txtSobrenome;
    @FXML private TextField txtNacionalidade;
    @FXML private ComboBox<String> cmbTipo;
    @FXML private Button btnSalvar;

    @FXML private Label lblEditar;
    @FXML private Label lblBuscar;
    @FXML private Label lblDigite;
    @FXML private Label lblEscolha;
    @FXML private ComboBox<String> cmbEscolha;
    @FXML private TextField txtBusca;
    @FXML private TableView<Colaborador> tblColaborador;
    @FXML private TableColumn<Colaborador, String> colNome;
    @FXML private TableColumn<Colaborador, String> colSobrenome;
    @FXML private TableColumn<Colaborador, String> colNacionalidade;
    @FXML private TableColumn<Colaborador, String> colTipo;
    @FXML private Button btnEditar;

    @FXML private Label lblExcluir;
    @FXML private Button btnExcluir;

    @FXML private Button btnSair;


    private final ColaboradorDAO dao = new ColaboradorDAO();
    private final ObservableList<Colaborador> dados = FXCollections.observableArrayList();


    @FXML public void initialize(){
        if(cmbTipo!=null){
            cmbTipo.setItems(FXCollections.observableArrayList("Autor", "Editor", "Organizador","Colaborador","Tradutor","Ilustrador","Outro"));
        }
        if (cmbEscolha != null){
            cmbEscolha.setItems(FXCollections.observableArrayList("Nome", "Sobrenome", "Nacionalidade","Tipo","Nome e Sobrenome"));
            txtBusca.clear();
        }
        if (tblColaborador != null) {
            colNome.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getNome()));
            colSobrenome.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getSobrenome()));
            colNacionalidade.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getNacionalidade()));
            colTipo.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getTipo()));

            tblColaborador.setItems(dados);
            tblColaborador.getSelectionModel().selectedItemProperty().addListener((obs, old, sel) -> preencherFormulario(sel));
        }
        if (txtBusca != null) {
            txtBusca.textProperty().addListener((obs, old, value) -> {
                if (cmbEscolha.getValue() == null) {
                    if (!value.isBlank()) {
                        showError("Busca inválida", "Selecione um campo em 'Buscar por' antes de pesquisar.");
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

                case "Sobrenome":
                    dados.setAll(dao.buscarPorSobrenome(valor));
                    break;

                case "Nacionalidade":
                    dados.setAll(dao.buscarPorNacionalidade(valor));
                    break;

                case "Tipo":
                    dados.setAll(dao.buscarPorTipo(valor));
                    break;
                case "Nome e Sobrenome":
                    dados.setAll(dao.buscarNomeSobrenome(valor));
                    break;
                default:
                    recarregarTabela();
            }

        } catch (SQLException e) {
            showError("Banco de dados", e.getMessage());
        }
    }


    private void preencherFormulario(Colaborador c) {
        if (c == null) return;
        txtNome.setText(c.getNome());
        txtSobrenome.setText(c.getSobrenome());
        txtNacionalidade.setText(c.getNacionalidade());
        cmbTipo.setValue(c.getTipo());
    }

    private void recarregarTabela() {
        try {
            dados.setAll(dao.listar());
        } catch (SQLException e) {
            showError("Banco de dados", e.getMessage());
        }
    }

    private void limpar(){
        txtNome.clear();
        txtSobrenome.clear();
        txtNacionalidade.clear();
        cmbTipo.setValue(null);
    }

    @FXML public void btnSalvar() {
        try {
            String Nome = txtNome.getText() == null ? "" : txtNome.getText().trim().toLowerCase();
            String Sobrenome = txtSobrenome.getText() == null ? "" : txtSobrenome.getText().toLowerCase();
            String Nacionalidade = txtNacionalidade.getText() == null ? "" : txtNacionalidade.getText().toLowerCase();
            String Tipo = cmbTipo.getValue();
            validar(Nome, Sobrenome, Tipo);

            Colaborador c = new Colaborador(null,Nome, Sobrenome, Nacionalidade, Tipo.toLowerCase());
            dao.inserir(c);

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
            Colaborador selecionado = tblColaborador.getSelectionModel().getSelectedItem();
            if (selecionado == null) {
                showError("Atenção", "Selecione um registro para editar.");
                return;
            }
            String Nome = txtNome.getText() == null ? "" : txtNome.getText().trim().toLowerCase();
            String Sobrenome = txtSobrenome.getText() == null ? "" : txtSobrenome.getText().toLowerCase();
            String Nacionalidade = txtNacionalidade.getText() == null ? "" : txtNacionalidade.getText().toLowerCase();
            String Tipo = cmbTipo.getValue().toLowerCase();
            validar(Nome, Sobrenome, Tipo);

            Colaborador c = new Colaborador(selecionado.getId_colaborador(),Nome,Sobrenome,Nacionalidade,Tipo);
            dao.atualizar(c);

            showInfo("Sucesso", "Registro editado com sucesso.");
            limpar();
            txtBusca.clear();
            cmbEscolha.setValue(null);
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
            Colaborador selecionado = tblColaborador.getSelectionModel().getSelectedItem();
            if (selecionado == null) {
                showError("Atenção", "Selecione um registro para excluir.");
                return;
            }

            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Excluir o registro selecionado?", ButtonType.YES, ButtonType.NO);
            confirm.setHeaderText("Confirmação");
            confirm.showAndWait();

            if (confirm.getResult() == ButtonType.YES) {
                dao.excluir(selecionado.getId_colaborador());
                limpar();
                txtBusca.clear();
                cmbEscolha.setValue(null);
                recarregarTabela();

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



    private void validar(String Nome, String Sobrenome, String Tipo) {
        if (Nome.isBlank()) throw new IllegalArgumentException("Nome é obrigatório.");
        if (Sobrenome.isBlank()) throw new IllegalArgumentException("Sobrenome é obrigatório");
        if (Tipo == null || Tipo.isBlank())
            throw new IllegalArgumentException("Tipo é obrigatório.");

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
