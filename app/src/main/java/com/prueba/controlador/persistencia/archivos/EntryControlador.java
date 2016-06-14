package com.prueba.controlador.persistencia.archivos;

import android.content.Context;
import android.util.Log;

import com.prueba.modelo.entidades.entry.Attributes;
import com.prueba.modelo.entidades.entry.Attributes_artist;
import com.prueba.modelo.entidades.entry.Attributes_category;
import com.prueba.modelo.entidades.entry.Attributes_price;
import com.prueba.modelo.entidades.entry.Attributes_releaseDate;
import com.prueba.modelo.entidades.entry.Category;
import com.prueba.modelo.entidades.entry.ImArtist;
import com.prueba.modelo.entidades.entry.ImImage;
import com.prueba.modelo.entidades.entry.ImPrice;
import com.prueba.modelo.entidades.entry.ImReleaseDate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel on 09/06/2016.
 */
public class EntryControlador {
    String entry = "entry.json";
    File json;
    Context mContext;

    public EntryControlador(Context mContext) {
        this.mContext = mContext;
        json = new File(mContext.getFilesDir(), entry);

    }

    public boolean existe() {
        return json.exists();

    }

    public boolean crear(String texto) {
        try {
            OutputStreamWriter fout =
                    new OutputStreamWriter(
                            mContext.openFileOutput(entry, Context.MODE_PRIVATE));

            fout.write(texto);
            fout.close();
            return true;
        } catch (Exception ex) {
            Log.println(Log.ASSERT, "FILES", ex.toString());
            return false;
        }
    }

    public String leer() {
        try {
            BufferedReader fin =
                    new BufferedReader(
                            new InputStreamReader(
                                    mContext.openFileInput(entry)));

            String texto = fin.readLine();
            fin.close();
            return texto;
        } catch (Exception ex) {
            Log.println(Log.ASSERT, "FILE", ex.toString());
            return null;
        }

    }

    public List<ImImage> setImges(JSONObject objeto) throws JSONException {
        List<ImImage> lista = new ArrayList<>();
        JSONArray array = objeto.getJSONArray("im:image");
        for (int i = 0; i < array.length(); i++) {
            ImImage imagen = new ImImage();
            imagen.setLabel(array.getJSONObject(i).getString("label"));
            imagen.setAttributes(new Attributes(array.getJSONObject(i).getJSONObject("attributes").getString("height")));
            lista.add(imagen);
        }
        return lista;
    }


    public ImImage menorImagen(List<ImImage> lista) {
        ImImage aux = lista.get(0);
        for (int i = 1; i < lista.size(); i++) {
            if (Integer.parseInt(aux.getAttributes().getHeight()) <
                    Integer.parseInt(lista.get(i).getAttributes().getHeight())
                    )
                aux = lista.get(i);
        }
        return aux;
    }

    public ImImage mayorImagen(List<ImImage> lista) {
        ImImage aux = lista.get(0);
        for (int i = 1; i < lista.size(); i++) {
            if (Integer.parseInt(aux.getAttributes().getHeight()) >
                    Integer.parseInt(lista.get(i).getAttributes().getHeight())
                    )
                aux = lista.get(i);
        }
        return aux;
    }

    public ImPrice cargarPrice(JSONObject object) throws JSONException {
        ImPrice price = new ImPrice();
        price.setLabel(object.getJSONObject("im:price").getString("label"));
        Attributes_price atributos = new Attributes_price();
        atributos.setAmount(object.getJSONObject("im:price").getJSONObject("attributes").getString("amount"));
        atributos.setCurrency(object.getJSONObject("im:price").getJSONObject("attributes").getString("currency"));
        price.setAttributes(atributos);
        return price;
    }

    public ImReleaseDate cargarRealse(JSONObject object) throws JSONException {
        ImReleaseDate realse = new ImReleaseDate();
        realse.setLabel(object.getJSONObject("im:releaseDate").getString("label"));
        Attributes_releaseDate atributos = new Attributes_releaseDate();
        atributos.setLabel(object.getJSONObject("im:releaseDate").getJSONObject("attributes").getString("label"));
        realse.setAttributes(atributos);
        return realse;
    }

    public Category cargarCategoria(JSONObject object) throws JSONException {
        Category category = new Category();
        Attributes_category atributos = new Attributes_category();
        atributos.setLabel(object.getJSONObject("category").getJSONObject("attributes").getString("label"));
        atributos.setImId(object.getJSONObject("category").getJSONObject("attributes").getString("im:id"));
        atributos.setTerm(object.getJSONObject("category").getJSONObject("attributes").getString("term"));
        atributos.setScheme(object.getJSONObject("category").getJSONObject("attributes").getString("scheme"));
        category.setAttributes(atributos);
        return category;
    }

    public ImArtist cargarArtista(JSONObject object) throws JSONException {
        ImArtist artis = new ImArtist();
        Attributes_artist artista = new Attributes_artist();
        artis.setLabel(object.getJSONObject("im:artist").getString("label"));
        artista.setHref(object.getJSONObject("im:artist").getJSONObject("attributes").getString("href"));
        artis.setAttributes(artista);
        return artis;
    }
}
