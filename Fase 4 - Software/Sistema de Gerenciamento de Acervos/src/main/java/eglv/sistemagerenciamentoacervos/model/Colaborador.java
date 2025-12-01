package eglv.sistemagerenciamentoacervos.model;

public class Colaborador {
    private Integer id_colaborador;
    private String nome;
    private String sobrenome;
    private String nacionalidade;
    private String tipo;

    public Colaborador() {
    }

    public Colaborador(Integer id_colaborador, String nome, String sobrenome, String nacionalidade, String tipo) {
        this.id_colaborador = id_colaborador;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.nacionalidade = nacionalidade;
        this.tipo = tipo;
    }

    public Integer getId_colaborador() {return id_colaborador; }

    public void setId_colaborador(Integer id_colaborador) {this.id_colaborador = id_colaborador;}

    public String getNome() {return nome; }

    public void setNome(String nome) {this.nome = nome; }

    public String getSobrenome() { return sobrenome;  }

    public void setSobrenome(String sobrenome) {this.sobrenome = sobrenome;}

    public String getNacionalidade() {return nacionalidade;}

    public void setNacionalidade(String nacionalidade) {this.nacionalidade = nacionalidade;}

    public String getTipo() {return tipo; }

    public void setTipo(String tipo) {this.tipo = tipo;}
}
