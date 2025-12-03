package eglv.sistemagerenciamentoacervos.model;

import java.sql.Blob;
import java.util.Date;

public class Jornal {
    private Integer id_jornal;
    private Integer codigo_jornal;
    private String pais;
    private String estado;
    private String cidade;
    private Date data;
    private String localizacao_acervo;
    private String numero_paginas;
    private String edicao;
    private String idioma;
    private String titulo;
    private String subtitulo;
    private Integer quantidade;
    //private Blob capa; ??
    private Editora editora;

    public Jornal() {
    }

    /*
    public Jornal(Integer id_jornal, Integer codigo_jornal, String pais, String estado, String cidade, Date data, String localizacao_acervo, String numero_paginas, String edicao, String idioma, String titulo, Integer quantidade, String subtitulo, Blob capa, Editora editora) {
        this.id_jornal = id_jornal;
        this.codigo_jornal = codigo_jornal;
        this.pais = pais;
        this.estado = estado;
        this.cidade = cidade;
        this.data = data;
        this.localizacao_acervo = localizacao_acervo;
        this.numero_paginas = numero_paginas;
        this.edicao = edicao;
        this.idioma = idioma;
        this.titulo = titulo;
        this.quantidade = quantidade;
        this.subtitulo = subtitulo;
        this.capa = capa;
        this.editora = editora;
    }
     */

    public Integer getId_jornal() {
        return id_jornal;
    }

    public void setId_jornal(Integer id_jornal) {
        this.id_jornal = id_jornal;
    }

    public Integer getCodigo_jornal() {
        return codigo_jornal;
    }

    public void setCodigo_jornal(Integer codigo_jornal) {
        this.codigo_jornal = codigo_jornal;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getLocalizacao_acervo() {
        return localizacao_acervo;
    }

    public void setLocalizacao_acervo(String localizacao_acervo) {
        this.localizacao_acervo = localizacao_acervo;
    }

    public String getNumero_paginas() {
        return numero_paginas;
    }

    public void setNumero_paginas(String numero_paginas) {
        this.numero_paginas = numero_paginas;
    }

    public String getEdicao() {
        return edicao;
    }

    public void setEdicao(String edicao) {
        this.edicao = edicao;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSubtitulo() {
        return subtitulo;
    }

    public void setSubtitulo(String subtitulo) {
        this.subtitulo = subtitulo;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    /*
    public Blob getCapa() {
        return capa;
    }

    public void setCapa(Blob capa) {
        this.capa = capa;
    }
     */

    public Editora getEditora() {
        return editora;
    }

    public void setEditora(Editora editora) {
        this.editora = editora;
    }
}
