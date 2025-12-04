package eglv.sistemagerenciamentoacervos.model;

import java.util.ArrayList;
import java.util.List;

public class Revista {
    private Integer id_revista;
    private String codigo_revista;
    private String periodicidade;
    private Integer ano;
    private Integer mes;
    private String volume;
    private String localizacao_acervo;
    private String numero_paginas;
    private String edicao;
    private String idioma;
    private String titulo;
    private String subtitulo;
    private Integer quantidade;
    private byte[] capa;
    private Editora editora;
    private Colaborador colaboradorPrincipal;
    private List<Colaborador> colaboradores = new ArrayList<>();
    private List<Assunto> assuntos = new ArrayList<>();

    public Revista(){
    }

    public Integer getId_revista() {
        return id_revista;
    }

    public void setId_revista(Integer id_revista) {
        this.id_revista = id_revista;
    }

    public String getCodigo_revista() {
        return codigo_revista;
    }

    public void setCodigo_revista(String codigo_revista) {
        this.codigo_revista = codigo_revista;
    }

    public String getPeriodicidade() {
        return periodicidade;
    }

    public void setPeriodicidade(String periodicidade) {
        this.periodicidade = periodicidade;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
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

    public byte[] getCapa() {
        return capa;
    }

    public void setCapa(byte[] capa) {
        this.capa = capa;
    }

    public Editora getEditora() {
        return editora;
    }

    public void setEditora(Editora editora) {
        this.editora = editora;
    }

    public Colaborador getColaboradorPrincipal() {
        return colaboradorPrincipal;
    }

    public void setColaboradorPrincipal(Colaborador colaboradorPrincipal) {
        this.colaboradorPrincipal = colaboradorPrincipal;
    }

    public List<Colaborador> getColaboradores() {
        return colaboradores;
    }

    public void setColaboradores(List<Colaborador> colaboradores) {
        this.colaboradores = colaboradores;
    }

    public List<Assunto> getAssuntos() {
        return assuntos;
    }

    public void setAssuntos(List<Assunto> assuntos) {
        this.assuntos = assuntos;
    }
}
