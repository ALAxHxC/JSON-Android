package com.prueba.vistas.entry;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.gson.Gson;
import com.prueba.R;
import com.prueba.controlador.mensajes.Mensaje;
import com.prueba.controlador.persistencia.archivos.EntryControlador;
import com.prueba.modelo.entidades.entry.Entry;
import com.prueba.modelo.entidades.entry.ImName;
import com.prueba.vistas.categoria.CategoriasActivity;
import com.prueba.vistas.listas.listaEntrys;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import static com.prueba.vistas.listas.Dispositivo.isTablet;

public class EntrysActivity extends AppCompatActivity {
    private ArrayList<Entry> listaEntry;
    private listaEntrys adapter;
    private EntryControlador entrysC;
    private Gson gson;
    private Mensaje mensajes;
    private GridView grid;

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
        setContentView(R.layout.activity_entrys);
        orientacion();
        mensajes = new Mensaje(this);
        CargarEntrys();
        configurarVista();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_cateogrias, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.categorias:
                Intent intent = new Intent(this, CategoriasActivity.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void CargarEntrys() {
        gson = new Gson();
        entrysC = new EntryControlador(this);
        listaEntry = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(entrysC.leer());

            for (int i = 0; i < array.length(); i++) {
                Entry entry = gson.fromJson(array.get(i).toString(), Entry.class);
                entry.setImName(new ImName(array.getJSONObject(i).getJSONObject("im:name").getString("label")));
                entry.setImImage(entrysC.setImges(array.getJSONObject(i)));
                listaEntry.add(entry);
            }
        } catch (JSONException e) {
            Log.println(Log.ASSERT, "JSON", e.toString());
            e.printStackTrace();
        }


    }


    private void configurarVista() {
        getSupportActionBar().setTitle(getResources().getString(R.string.apps));
        adapter = new listaEntrys(this, listaEntry);
        grid = (GridView) findViewById(R.id.gridViewElementosEntry);

        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CargarEntry(position);
            }
        });


    }

    private void CargarEntry(int i) {
        Intent intent = new Intent(this, EntryActivity.class);
        intent.putExtra(getResources().getString(R.string.id_extra), i);
        startActivity(intent);
    }
}
