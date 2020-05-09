package com.rodobicho.rodoapp.entidade;

public class Foto {
    private Long id;
    private String urlFoto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    @Override
    public String toString() {
        return "{" +
                "url='" + urlFoto + '\'' +
                '}';
    }
}
