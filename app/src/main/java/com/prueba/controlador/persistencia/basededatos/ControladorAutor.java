package com.prueba.controlador.persistencia.basededatos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.prueba.modelo.entidades.Autor;

/**
 * Created by daniel on 09/06/2016.
 */
public class ControladorAutor {
    private BaseDeDatosPrueba basededatos;

    public ControladorAutor(BaseDeDatosPrueba basededatos) {
        this.basededatos = basededatos;
    }

    public Autor BuscarAutor() {
        SQLiteDatabase bd = basededatos.getWritableDatabase();
        try {

            Cursor fila = bd.rawQuery(consultaAutor(), null);
            if (fila.moveToFirst()) {
                Autor autor = new Autor();
                autor.setName(fila.getString(0));
                autor.setUrl(fila.getString(1));
                autor.setUpdated(fila.getString(2));
                autor.setRights(fila.getString(3));
                autor.setTitle(fila.getString(4));
                autor.setIcon(fila.getString(5));
                autor.setIcon(fila.getString(6));
                return autor;
            } else {
                return null;
            }
        } catch (Exception ex) {

            Log.println(Log.ASSERT, "SQL", "SQL" + ex.toString());
            ex.printStackTrace();
            return null;
        } finally {
            bd.close();
        }
    }

    public String consultaAutor() {
        return "SELECT * FROM " + Entidades.autor_table;

    }

    public Long RegistrarAutor(Autor autor) {
        ContentValues valores = new ContentValues();
        valores.put(Entidades.autor_name, autor.getName());
        valores.put(Entidades.autor_url, autor.getUrl());
        valores.put(Entidades.autor_icon, autor.getIcon());
        valores.put(Entidades.autor_id, autor.getId());
        valores.put(Entidades.autor_rigths, autor.getRights());
        valores.put(Entidades.autor_title, autor.getTitle());
        valores.put(Entidades.autor_updated, autor.getUpdated());
        SQLiteDatabase bd = basededatos.getWritableDatabase();
        try {
            return bd.insert(Entidades.autor_table, null, valores);
        } catch (Exception ex) {

            Log.println(Log.ASSERT, "SQL", ex.toString());
            return Long.valueOf(0);
        } finally {

            bd.close();
        }

    }
}
