package eglv.sistemagerenciamentoacervos.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {

    // ========================== CADASTROS ==========================
    @FXML
    private void cadastrarAssuntos() throws IOException {
        abrirView("/eglv/sistemagerenciamentoacervos/view/AssuntoCadView.fxml", "Cadastrar Assuntos");
    }

    @FXML
    private void cadastrarColaboradores() throws IOException {
        abrirView("/eglv/sistemagerenciamentoacervos/view/ColaboradorCadView.fxml", "Cadastrar Colaboradores");
    }

    @FXML
    private void cadastrarEditoras() throws IOException {
        abrirView("/eglv/sistemagerenciamentoacervos/view/EditoraCadView.fxml", "Cadastrar Editoras");
    }

    @FXML
    private void cadastrarExemplares() throws IOException {
        abrirView("/eglv/sistemagerenciamentoacervos/view/ExemplarCadView.fxml", "Cadastrar Exemplares");
    }

    @FXML
    private void cadastrarJornais() throws IOException {
        abrirView("/eglv/sistemagerenciamentoacervos/view/JornalCadView.fxml", "Cadastrar Jornais");
    }

    @FXML
    private void cadastrarLivros() throws IOException {
        abrirView("/eglv/sistemagerenciamentoacervos/view/LivroCadView.fxml", "Cadastrar Livros");
    }

    @FXML
    private void cadastrarRevistas() throws IOException {
        abrirView("/eglv/sistemagerenciamentoacervos/view/RevistaCadView.fxml", "Cadastrar Revistas");
    }

    // ========================== LISTAR ==========================
    @FXML
    private void listarAssuntos() throws IOException {
        abrirView("/eglv/sistemagerenciamentoacervos/view/AssuntoListarView.fxml", "Listar Assuntos");
    }

    @FXML
    private void listarColaboradores() throws IOException {
        abrirView("/eglv/sistemagerenciamentoacervos/view/ColaboradorListarView.fxml", "Listar Colaboradores");
    }

    @FXML
    private void listarEditoras() throws IOException {
        abrirView("/eglv/sistemagerenciamentoacervos/view/EditoraListarView.fxml", "Listar Editoras");
    }

    @FXML
    private void listarExemplares() throws IOException {
        abrirView("/eglv/sistemagerenciamentoacervos/view/ExemplarListarView.fxml", "Listar Exemplares");
    }

    @FXML
    private void listarJornais() throws IOException {
        abrirView("/eglv/sistemagerenciamentoacervos/view/JornalListarView.fxml", "Listar Jornais");
    }

    @FXML
    private void listarLivros() throws IOException {
        abrirView("/eglv/sistemagerenciamentoacervos/view/LivroListarView.fxml", "Listar Livros");
    }

    @FXML
    private void listarRevistas() throws IOException {
        abrirView("/eglv/sistemagerenciamentoacervos/view/RevistaListarView.fxml", "Listar Revistas");
    }

    // ========================== EDITAR ==========================
    @FXML
    private void editarAssuntos() throws IOException {
        abrirView("/eglv/sistemagerenciamentoacervos/view/AssuntoEditarView.fxml", "Editar Assuntos");
    }

    @FXML
    private void editarColaboradores() throws IOException {
        abrirView("/eglv/sistemagerenciamentoacervos/view/ColaboradorEditarView.fxml", "Editar Colaboradores");
    }

    @FXML
    private void editarEditoras() throws IOException {
        abrirView("/eglv/sistemagerenciamentoacervos/view/EditoraEditarView.fxml", "Editar Editoras");
    }

    @FXML
    private void editarExemplares() throws IOException {
        abrirView("/eglv/sistemagerenciamentoacervos/view/ExemplarEditarView.fxml", "Editar Exemplares");
    }

    @FXML
    private void editarJornais() throws IOException {
        abrirView("/eglv/sistemagerenciamentoacervos/view/JornalEditarView.fxml", "Editar Jornais");
    }

    @FXML
    private void editarLivros() throws IOException {
        abrirView("/eglv/sistemagerenciamentoacervos/view/LivroEditarView.fxml", "Editar Livros");
    }

    @FXML
    private void editarRevistas() throws IOException {
        abrirView("/eglv/sistemagerenciamentoacervos/view/RevistaEditarView.fxml", "Editar Revistas");
    }

    // ========================== EXCLUIR ==========================
    @FXML
    private void excluirAssuntos() throws IOException {
        abrirView("/eglv/sistemagerenciamentoacervos/view/AssuntoExcluirView.fxml", "Excluir Assuntos");
    }

    @FXML
    private void excluirColaboradores() throws IOException {
        abrirView("/eglv/sistemagerenciamentoacervos/view/ColaboradorExcluirView.fxml", "Excluir Colaboradores");
    }

    @FXML
    private void excluirEditoras() throws IOException {
        abrirView("/eglv/sistemagerenciamentoacervos/view/EditoraExcluirView.fxml", "Excluir Editoras");
    }

    @FXML
    private void excluirExemplares() throws IOException {
        abrirView("/eglv/sistemagerenciamentoacervos/view/ExemplarExcluirView.fxml", "Excluir Exemplares");
    }

    @FXML
    private void excluirJornais() throws IOException {
        abrirView("/eglv/sistemagerenciamentoacervos/view/JornalExcluirView.fxml", "Excluir Jornais");
    }

    @FXML
    private void excluirLivros() throws IOException {
        abrirView("/eglv/sistemagerenciamentoacervos/view/LivroExcluirView.fxml", "Excluir Livros");
    }

    @FXML
    private void excluirRevistas() throws IOException {
        abrirView("/eglv/sistemagerenciamentoacervos/view/RevistaExcluirView.fxml", "Excluir Revistas");
    }

    // ========================== UTILITÁRIO ==========================
    private void abrirView(String fxmlPath, String titulo) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
        Stage stage = new Stage();
        stage.setTitle(titulo);
        stage.setScene(new Scene(root));
        stage.show();
    }
}