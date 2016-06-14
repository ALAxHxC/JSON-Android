package com.prueba.controlador.persistencia.archivos;

import com.prueba.modelo.entidades.Categoria;

import java.util.LinkedHashMap;

/**
 * Created by Daniel on 12/06/2016.
 */
public class CategoriaControlador {
    LinkedHashMap<String, Categoria> listaCategorias;

    public CategoriaControlador() {
        this.listaCategorias = new LinkedHashMap<>();
    }

    public void Agregar(Categoria category) {
        listaCategorias.put(category.getCategoria().getAttributes().getImId(), category);
    }

    public LinkedHashMap<String, Categoria> getListaCategorias() {
        return listaCategorias;
    }



    public boolean existe(String id) {
        return listaCategorias.containsKey(id);

    }
}
