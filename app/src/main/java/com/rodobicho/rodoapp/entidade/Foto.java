package com.rodobicho.rodoapp.entidade;

public class Foto {
    private Long id;
    private byte[] urlFoto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(byte[] urlFoto) {
        this.urlFoto = urlFoto;
    }

    @Override
    public String toString() {
        return "{" +
                "url='" + urlFoto + '\'' +
                '}';
    }
}
