package com.prueba.controlador.persistencia.basededatos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by daniel on 09/06/2016.
 */
public class BaseDeDatosPrueba extends SQLiteOpenHelper {

    public BaseDeDatosPrueba(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Entidades.autor);
        db.execSQL(Entidades.link);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Entidades.autor);
        db.execSQL(Entidades.link);
    }
}
