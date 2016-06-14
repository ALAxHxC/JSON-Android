package com.prueba.modelo.entidades;

import com.prueba.modelo.entidades.entry.Category;
import com.prueba.modelo.entidades.entry.ImImage;

/**
 * Created by Daniel on 10/06/2016.
 */
public class Categoria {
    private Category categoria;
    private String archivo;
    private ImImage image;

    public Categoria(Category categoria, String archivo, ImImage imImage) {
        this.categoria = categoria;
        this.archivo = archivo;
        this.image = imImage;
    }

    public Category getCategoria() {
        return categoria;
    }

    public String getArchivo() {
        return archivo;
    }

    public ImImage getImage() {
        return image;
    }
}
