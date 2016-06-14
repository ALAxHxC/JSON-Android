package com.prueba;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.prueba.controlador.conexion.Internet;
import com.prueba.controlador.conexion.TareaConexionJSON;
import com.prueba.controlador.mensajes.Mensaje;
import com.prueba.controlador.persistencia.archivos.EntryControlador;
import com.prueba.controlador.persistencia.basededatos.BaseDeDatosPrueba;
import com.prueba.controlador.persistencia.basededatos.ControladorAutor;
import com.prueba.controlador.persistencia.basededatos.ControladorLink;
import com.prueba.controlador.persistencia.basededatos.Entidades;
import com.prueba.modelo.entidades.Autor;
import com.prueba.modelo.entidades.Link;
import com.prueba.vistas.entry.EntrysActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.prueba.vistas.listas.Dispositivo.isTablet;

public class MainActivity extends AppCompatActivity {
    private Mensaje mensajes;
    private ProgressDialog pDialog;
    private TareaConexionJSON tarea;
    private JSONObject jsonObj;
    private BaseDeDatosPrueba basedatos;
    private ControladorAutor autorC;
    private ControladorLink linkC;
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

    public void setFullScreen() {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullScreen();
        orientacion();
        mensajes = new Mensaje(this);
        entrysC = new EntryControlador(this);
        CargarImagen();
        iniciarBaseDatos();
        consultarRegistro();
    }


    private void Splash() {
        mensajes.toastmensaje(getResources().getString(R.string.caragando));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(getApplicationContext(), EntrysActivity.class);
                MainActivity.this.startActivity(mainIntent);
                MainActivity.this.finish();
            }
        }, Toast.LENGTH_SHORT + 1000);

    }

    private void CargarImagen() {
        ImageView imagen = (ImageView) findViewById(R.id.imageViewInicio);
        imagen.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.splash, null));

    }


    private void consultarRegistro() {
        boolean internet = Internet.SalidaInternet(this);

        Autor autor = autorC.BuscarAutor();
        ArrayList links = linkC.buscarLink();
        if (!internet) {
            if (autor == null || links == null || !entrysC.existe()) {

                mensajes.FinalizarApp(getResources().getString(R.string.sin_internet_error), getResources().getString(R.string.sin_internet));
                return;
            } else {
                Splash();
            }
        } else {
            iniciarDescarga();
        }

    }

    private void iniciarDescarga() {
        tarea = new TareaConexionJSON(this, mHandlerWS, pDialog);
        tarea.execute();
    }

    //JSON
    private final Handler mHandlerWS = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case 2:

                    String respuesta = msg.getData().getString("result");
                    if (respuesta.startsWith("ERROR")) {
                        mensajes.toastmensaje(getResources().getString(R.string.fallo_internet));
                    } else {
                        instanciarJson(respuesta);
                    }
                    break;
            }
        }
    };

    private void instanciarJson(String jsonStr) {
        try {
            jsonObj = new JSONObject(jsonStr);
            procesarJSON();
            Log.println(Log.ASSERT, "JSON", "OBTUVO DATOS");
        } catch (JSONException e) {
            Log.println(Log.ASSERT, "JSON", e.toString());
            e.printStackTrace();
        }
    }

    private void procesarJSON() {
        try {
            jsonObj = jsonObj.getJSONObject("feed");
            Log.println(Log.ASSERT, "json", jsonObj.getJSONObject("author").toString());
            Log.println(Log.ASSERT, "JSON", jsonObj.getJSONArray("entry").toString());

            CrearRegistros();
        } catch (JSONException e) {
            Log.println(Log.ASSERT, "JSON", e.toString());
            e.printStackTrace();
        }
    }

    //REGISTROS
    private void CrearRegistros() {
        //limpia almacenado
        limpiarbassededatos(basedatos);
        if (CrearAutor() && CrearLinks() && CrearEntrys())
            Splash();
        else
            mensajes.toastmensaje("No se registraron datos");
    }

    private boolean CrearEntrys() {
        try {
            entrysC.crear(jsonObj.getJSONArray("entry").toString());
            Log.println(Log.ASSERT, "FILE", "CREO :" + entrysC.existe());
            JSONArray array = new JSONArray(entrysC.leer());
            return entrysC.existe();
        } catch (JSONException e) {
            Log.println(Log.ASSERT, "JSON", e.toString());
            e.printStackTrace();
            return false;
        }

    }

    //BASE DE DATOS
    private void limpiarbassededatos(BaseDeDatosPrueba bd) {
        SQLiteDatabase base = bd.getWritableDatabase();
        base.execSQL(Entidades.autor);
        base.execSQL(Entidades.link);
        base.execSQL(Entidades.limpia_autor);
        base.execSQL(Entidades.limpia_link);
        bd.close();
    }

    private void iniciarBaseDatos() {
        basedatos = new BaseDeDatosPrueba(this, "prueba", null, 1);
        //  limpiarbassededatos(basedatos);
        autorC = new ControladorAutor(basedatos);
        linkC = new ControladorLink(basedatos);

    }

    private boolean CrearLinks() {
        try {
            ArrayList<Link> links = new ArrayList<>();
            JSONArray array = jsonObj.getJSONArray("link");
            for (int i = 0; i < array.length(); i++) {
                JSONObject objeto = array.getJSONObject(i);
                Link link = new Link();
                link.setRel(objeto.getJSONObject("attributes").getString("rel"));
                link.setHref(objeto.getJSONObject("attributes").getString("href"));
                try {
                    link.setType(objeto.getJSONObject("attributes").getString("type"));
                } catch (org.json.JSONException ex) {
                    link.setType(" ");
                }
                links.add(link);
            }
            return RegistrarLinks(links) > 0 ? true : false;

        } catch (JSONException e) {
            Log.println(Log.ASSERT, "JSON", e.toString());

            e.printStackTrace();
            return false;
        }


    }

    private Long RegistrarLinks(ArrayList<Link> Links) {
        Long contador = Long.valueOf(0);
        for (Link link : Links) {
            contador += linkC.RegistrarLink(link);
        }
        return contador;
    }

    private boolean CrearAutor() {
        try {
            Autor autor = new Autor();
            autor.setName(jsonObj.getJSONObject("author").getJSONObject("name").getString("label"));
            autor.setUrl(jsonObj.getJSONObject("author").getJSONObject("uri").getString("label"));
            autor.setUpdated(jsonObj.getJSONObject("updated").getString("label"));
            autor.setRights(jsonObj.getJSONObject("rights").getString("label"));
            autor.setTitle(jsonObj.getJSONObject("title").getString("label"));
            autor.setIcon(jsonObj.getJSONObject("icon").getString("label"));
            autor.setId(jsonObj.getJSONObject("id").getString("label"));
            return (autorC.RegistrarAutor(autor) > 0) ? true : false;
        } catch (Exception ex) {
            Log.println(Log.ASSERT, "JSON", ex.toString());
            ex.printStackTrace();
            return false;
        }

    }


}
