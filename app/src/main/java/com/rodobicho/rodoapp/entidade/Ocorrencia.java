package com.rodobicho.rodoapp.entidade;


import java.util.Date;
import java.util.List;

public class Ocorrencia {
    private Long id;
    private List<Foto> fotos;
    private Date data;
    private String descricao;
    private Local local;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Foto> getFotos() {
        return fotos;
    }

    public void setFotos(List<Foto> fotos) {
        this.fotos = fotos;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Local getLocal() {
        return local;
    }

    public void setLocal(Local local) {
        this.local = local;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return "Ocorrencia{" +
                "descricao='" + descricao + '\'' +
                ", local=" + local +
                ", fotos=" + fotos +
                '}';
    }
}
