package eglv.sistemagerenciamentoacervos.model;

public class Assunto {
    private Integer id_assunto;
    private String descricao;

    public Assunto() {
    }

    public Assunto(Integer id_assunto, String descricao) {
        this.id_assunto = id_assunto;
        this.descricao = descricao;
    }

    public Integer getId_assunto() {return id_assunto; }

    public void setId_assunto(Integer id) {this.id_assunto = id; }

    public String getDescricao() {return descricao;}

    public void setDescricao(String descricao) {this.descricao = descricao; }
}
