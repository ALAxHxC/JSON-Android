package com.prueba.vistas.listas;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.prueba.R;
import com.prueba.controlador.conexion.TareaConexionImagen;
import com.prueba.modelo.entidades.Categoria;
import com.prueba.modelo.entidades.entry.ImImage;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by Daniel on 10/06/2016.
 */
public class listaCategoria extends BaseAdapter {
    private Context mContext;
    LinkedHashMap<String, Categoria> categorias;

    public listaCategoria(Context mContext, LinkedHashMap<String, Categoria> lista) {
        this.mContext = mContext;
        categorias = lista;
    }

    @Override
    public int getCount() {
        return categorias.size();
    }

    @Override
    public Object getItem(int position) {
        return categorias.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Categoria categoria = cargarPosition(position);
        convertView = LayoutInflater.from(mContext).inflate(R.layout.lista_categoria, null);
        TextView title = (TextView) convertView.findViewById(R.id.textViewTitleCategoria);
        TextView id = (TextView) convertView.findViewById(R.id.textViewIDCategoria);
        ImageView img = (ImageView) convertView.findViewById(R.id.imageViewCategoria);
        title.setText(categoria.getCategoria().getAttributes().getTerm());
        id.setText(categoria.getCategoria().getAttributes().getImId());
        cargarimg(img, categoria.getArchivo(), categoria.getImage());
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarUrl(categoria.getCategoria().getAttributes().getScheme());

            }
        });
        return convertView;
    }

    private Categoria cargarPosition(int position) {
        return (new ArrayList<Categoria>(categorias.values())).get(position);
    }

    public void enviarUrl(String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        mContext.startActivity(i);

    }

    private void cargarimg(ImageView imageView, String archivo, ImImage img) {
        TareaConexionImagen tarea = new TareaConexionImagen(mContext,
                imageView,
                archivo);
        if (!tarea.ValidarConexion()) {
            Log.println(Log.ASSERT, "ejecutara", "hilo" + archivo);
            tarea.execute(img.getLabel());
        }
    }

}
