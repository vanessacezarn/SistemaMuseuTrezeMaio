package eglv.sistemagerenciamentoacervos.model;

public class Colaborador {
    private Integer id;
    private String nome;
    private String sobrenome;
    private String nacionalidade;
    private String tipo;

    public Colaborador() {
    }

    public Colaborador(Integer id, String nome, String sobrenome, String nacionalidade, String tipo) {
        this.id = id;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.nacionalidade = nacionalidade;
        this.tipo = tipo;
    }

    public Integer getId() {return id; }

    public void setId(Integer id) {this.id = id;}

    public String getNome() {return nome; }

    public void setNome(String nome) {this.nome = nome; }

    public String getSobrenome() { return sobrenome;  }

    public void setSobrenome(String sobrenome) {this.sobrenome = sobrenome;}

    public String getNacionalidade() {return nacionalidade;}

    public void setNacionalidade(String nacionalidade) {this.nacionalidade = nacionalidade;}

    public String getTipo() {return tipo; }

    public void setTipo(String tipo) {this.tipo = tipo;}
}
