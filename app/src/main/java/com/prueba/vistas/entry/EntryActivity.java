package com.prueba.vistas.entry;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.prueba.R;
import com.prueba.controlador.conexion.TareaConexionImagen;
import com.prueba.controlador.persistencia.archivos.EntryControlador;
import com.prueba.modelo.entidades.entry.Entry;
import com.prueba.modelo.entidades.entry.ImImage;
import com.prueba.modelo.entidades.entry.ImName;
import com.prueba.vistas.listas.Dispositivo;

import org.json.JSONArray;
import org.json.JSONException;

import static com.prueba.vistas.listas.Dispositivo.isTablet;


public class EntryActivity extends AppCompatActivity {
    private int position;

    private Entry entry;
    private TextView name, id, bundle, sumary, realse, categoria, autor, precio, moneda, title;
    private ImageView imagen;

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
        setContentView(R.layout.activity_entry);

        orientacion();
        cargarVista();
        cargarViews();
        cargarListeners();

    }

    public void cargarVista() {
        getSupportActionBar().setTitle(getResources().getString(R.string.resumen_app));
        position = getIntent().getIntExtra(getResources().getString(R.string.id_extra), 0);
        entry = cargarEntry(position);

        name = (TextView) findViewById(R.id.textViewEntryName);
        id = (TextView) findViewById(R.id.textViewIDO);
        precio = (TextView) findViewById(R.id.textViewPriceI);
        moneda = (TextView) findViewById(R.id.textViewAtributeP);
        realse = (TextView) findViewById(R.id.textViewRealseDate);
        sumary = (TextView) findViewById(R.id.textViewSumaryI);
        categoria = (TextView) findViewById(R.id.textViewCategoria);
        autor = (TextView) findViewById(R.id.textViewAutorI);
        bundle = (TextView) findViewById(R.id.textViewBundleI);
        title = (TextView) findViewById(R.id.textViewRealseAtributes);
    }

    public void cargarViews() {

        name.setText(entry.getImName().getLabel());
        sumary.setText(entry.getSummary().getLabel());
        moneda.setText(entry.getImPrice().getAttributes().getCurrency());
        precio.setText(entry.getImPrice().getAttributes().getAmount());
        realse.setText(entry.getImReleaseDate().getLabel());
        categoria.setText(entry.getCategory().getAttributes().getLabel());
        autor.setText(entry.getImArtist().getLabel());
        id.setText(entry.getCategory().getAttributes().getImId());
        bundle.setText(entry.getRights().getLabel());
        title.setText(entry.getTitle().getLabel());

        cargarImagen();

    }

    public void cargarImagen() {
        EntryControlador en = new EntryControlador(this);
        imagen = (ImageView) findViewById(R.id.imageViewEntry);
        TareaConexionImagen cargaimagen = new TareaConexionImagen(this, imagen, entry.getImName().getLabel() + getResources().getString(R.string.ex_img));
        if (!cargaimagen.ValidarConexion()) {
            setImg(entry, cargaimagen);
        }

    }

    private void setImg(Entry entry, TareaConexionImagen conexionImagen) {
        EntryControlador entryControlador = new EntryControlador(this);
        ImImage imagen;
        if (Dispositivo.isTablet(this)) {
            imagen = entryControlador.mayorImagen(entry.getImImage());
        } else {
            imagen = entryControlador.menorImagen(entry.getImImage());
        }
        conexionImagen.execute(imagen.getLabel());


    }

    private void cargarListeners() {
        categoria.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    String url = entry.getCategory().getAttributes().getScheme();
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }
                return false;
            }
        });
        autor.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {

                    String url = entry.getImArtist().getAttributes().getHref();
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }
                return false;
            }
        });
    }



    private Entry cargarEntry(int position) {
        Gson gson = new Gson();
        EntryControlador entrysC = new EntryControlador(this);
        try {
            JSONArray array = new JSONArray(entrysC.leer());
            Entry entry = gson.fromJson(array.get(position).toString(), Entry.class);
            entry.setImName(new ImName(array.getJSONObject(position).getJSONObject("im:name").getString("label")));
            entry.setImImage(entrysC.setImges(array.getJSONObject(position)));
            entry.setImPrice(entrysC.cargarPrice(array.getJSONObject(position)));
            entry.setImReleaseDate(entrysC.cargarRealse(array.getJSONObject(position)));
            entry.setCategory(entrysC.cargarCategoria(array.getJSONObject(position)));
            entry.setImArtist(entrysC.cargarArtista(array.getJSONObject(position)));


            return entry;
        } catch (JSONException e) {
            Log.println(Log.ASSERT, "JSON", e.toString());
            e.printStackTrace();
            return null;
        }
    }


}
