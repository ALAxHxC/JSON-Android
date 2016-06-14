package com.prueba.controlador.persistencia.basededatos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.prueba.modelo.entidades.Link;

import java.util.ArrayList;

/**
 * Created by daniel on 09/06/2016.
 */
public class ControladorLink {
    private BaseDeDatosPrueba basededatos;

    public ControladorLink(BaseDeDatosPrueba basededatos) {
        this.basededatos = basededatos;
    }

    public ArrayList<Link> buscarLink() {
        SQLiteDatabase bd = basededatos.getWritableDatabase();
        try {

            Cursor fila = bd.rawQuery(consulta(), null);
            return obtenerLinks(fila);
        } catch (Exception ex) {
            Log.println(Log.ASSERT, "SQL", ex.toString());
            return null;
        } finally {
            bd.close();
        }

    }

    public ArrayList<Link> obtenerLinks(Cursor consulta) {
        ArrayList<Link> lista = new ArrayList<>();
        if (consulta.moveToFirst()) {
            do {
                Link link = new Link();
                link.setRel(consulta.getString(0));
                link.setType(consulta.getString(1));
                link.setHref(consulta.getString(2));
                lista.add(link);

            } while (consulta.moveToNext());
            return lista;
        } else {
            return null;
        }

    }

    public long RegistrarLink(Link link) {
        SQLiteDatabase bd = basededatos.getWritableDatabase();
        try {
            ContentValues valores = new ContentValues();
            valores.put(Entidades.link_rel, link.getRel());
            valores.put(Entidades.link_href, link.getHref());
            valores.put(Entidades.link_type, (link.getType() == null || link.getType().equalsIgnoreCase("")) ? " " : link.getType());
            return bd.insert(Entidades.link_table, null, valores);
        } catch (Exception ex) {
            Log.println(Log.ASSERT, "SQL", ex.toString());
            ex.printStackTrace();
            return 0;
        } finally {
            bd.close();
        }
    }

    public String consulta() {
        return "SELECT * FROM link";
    }

}
