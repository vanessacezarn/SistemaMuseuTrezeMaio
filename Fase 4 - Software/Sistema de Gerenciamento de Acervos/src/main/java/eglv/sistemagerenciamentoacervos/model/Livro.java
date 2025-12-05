package eglv.sistemagerenciamentoacervos.model;

import java.util.ArrayList;
import java.util.List;

public class Livro {

    private Integer id_livro;
    private String codigo_livro;
    private String titulo;
    private String subtitulo;
    private String isbn;
    private Integer ano_publicacao;
    private String localizacao_acervo;
    private String numero_paginas;
    private String edicao;
    private String idioma;
    private Integer quantidade;
    private byte[] capa;

    private Editora editora;

    private List<Assunto> assuntos = new ArrayList<>();
    private List<Colaborador> colaboradores = new ArrayList<>();

    public Livro() {}

    public Integer getId_livro() { return id_livro; }
    public void setId_livro(Integer id_livro) { this.id_livro = id_livro; }

    public String getCodigo_livro() { return codigo_livro; }
    public void setCodigo_livro(String codigo_livro) { this.codigo_livro = codigo_livro; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getSubtitulo() { return subtitulo; }
    public void setSubtitulo(String subtitulo) { this.subtitulo = subtitulo; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public Integer getAno_publicacao() { return ano_publicacao; }
    public void setAno_publicacao(Integer ano_publicacao) { this.ano_publicacao = ano_publicacao; }

    public String getLocalizacao_acervo() { return localizacao_acervo; }
    public void setLocalizacao_acervo(String localizacao_acervo) { this.localizacao_acervo = localizacao_acervo; }

    public String getNumero_paginas() { return numero_paginas; }
    public void setNumero_paginas(String numero_paginas) { this.numero_paginas = numero_paginas; }

    public String getEdicao() { return edicao; }
    public void setEdicao(String edicao) { this.edicao = edicao; }

    public String getIdioma() { return idioma; }
    public void setIdioma(String idioma) { this.idioma = idioma; }

    public Integer getQuantidade() { return quantidade; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }

    public byte[] getCapa() { return capa; }
    public void setCapa(byte[] capa) { this.capa = capa; }

    public Editora getEditora() { return editora; }
    public void setEditora(Editora editora) { this.editora = editora; }

    public List<Assunto> getAssuntos() { return assuntos; }
    public void setAssuntos(List<Assunto> assuntos) { this.assuntos = assuntos; }

    public List<Colaborador> getColaboradores() { return colaboradores; }
    public void setColaboradores(List<Colaborador> colaboradores) { this.colaboradores = colaboradores; }
}