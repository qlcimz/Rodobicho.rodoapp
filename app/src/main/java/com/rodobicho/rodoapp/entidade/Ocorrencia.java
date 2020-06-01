package com.rodobicho.rodoapp.entidade;


import java.util.Date;
import java.util.List;

public class Ocorrencia {
    private Long id;
    private List<Foto> fotos;
    private String created_at;
    private String descricao;
    private Local local;
    private Usuario usuario;

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

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "Ocorrencia{" +
                "descricao='" + descricao + '\'' +
                ", local=" + local +
                ", fotos=" + fotos +
                ", usuario=" + usuario +
                '}';
    }
}
