package com.prueba.vistas.categoria;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.GridView;

import com.google.gson.Gson;
import com.prueba.R;
import com.prueba.controlador.persistencia.archivos.CategoriaControlador;
import com.prueba.controlador.persistencia.archivos.EntryControlador;
import com.prueba.modelo.entidades.Categoria;
import com.prueba.modelo.entidades.entry.Category;
import com.prueba.modelo.entidades.entry.Entry;
import com.prueba.modelo.entidades.entry.ImName;
import com.prueba.vistas.listas.listaCategoria;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import static com.prueba.vistas.listas.Dispositivo.isTablet;

public class CategoriasActivity extends AppCompatActivity {

    private CategoriaControlador controlador;
    private GridView grid;
    private EntryControlador entrysC;

    private void orientacion() {
        if (isTablet(this)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        orientacion();
        Log.println(Log.ASSERT, "NUEVA COFIG", "NEUVA");
        //here you can handle orientation change
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorias);
        orientacion();
        getSupportActionBar().setTitle(getResources().getString(R.string.categorias));
        controlador = new CategoriaControlador();
        grid = (GridView) findViewById(R.id.gridViewcategorias);
        entrysC = new EntryControlador(this);
        cargarCategorias();
        cargarGrid();
    }

    public void cargarGrid() {
        try {
            listaCategoria adapter = new listaCategoria(this, controlador.getListaCategorias());
            grid.setAdapter(adapter);
        } catch (Exception ex) {
            Log.println(Log.ASSERT, "lista", ex.toString());
        }
    }

    public List<Entry> obtenerEntrys() {
        Gson gson = new Gson();

        List<Entry> listaEntry = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(entrysC.leer());
            for (int i = 0; i < array.length(); i++) {
                Entry entry = gson.fromJson(array.get(i).toString(), Entry.class);
                entry.setImName(new ImName(array.getJSONObject(i).getJSONObject("im:name").getString("label")));
                entry.setCategory(entrysC.cargarCategoria(array.getJSONObject(i)));
                entry.setImImage(entrysC.setImges(array.getJSONObject(i)));
                listaEntry.add(entry);
            }
            Log.println(Log.ASSERT, "lista", listaEntry.size() + "");
            return listaEntry;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

    private void cargarCategorias() {
        List<Entry> lista = obtenerEntrys();
        for (Entry entry : lista) {
            if (controlador.existe(entry.getCategory().getAttributes().getImId()))
                continue;

            Category category = entry.getCategory();
            Categoria categoria = new Categoria(category,
                    entry.getImName().getLabel() + getResources().getString(R.string.ex_img),
                    entrysC.mayorImagen(entry.getImImage()));

            controlador.Agregar(categoria);


        }
        Log.println(Log.ASSERT, "CATEGORIA", controlador.getListaCategorias().size() + "");
    }
}
