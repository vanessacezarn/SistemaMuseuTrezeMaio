package eglv.sistemagerenciamentoacervos.model;

public class Editora {
    private Integer id_editora;
    private String nome;
    private String localizacao;

    public Editora() {
    }

    public Editora(Integer id_editora, String nome, String localizacao) {
        this.id_editora = id_editora;
        this.nome = nome;
        this.localizacao = localizacao;
    }

    public Integer getId_editora() {
        return id_editora;
    }

    public void setId_editora(Integer id) {
        this.id_editora = id_editora;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }
}
