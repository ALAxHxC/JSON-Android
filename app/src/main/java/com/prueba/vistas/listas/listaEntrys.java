package com.prueba.vistas.listas;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.prueba.R;
import com.prueba.controlador.conexion.TareaConexionImagen;
import com.prueba.controlador.persistencia.archivos.EntryControlador;
import com.prueba.modelo.entidades.entry.Entry;
import com.prueba.modelo.entidades.entry.ImImage;
import com.prueba.vistas.imagenes.Fondos;

import java.util.ArrayList;

/**
 * Created by Daniel on 10/06/2016.
 */
public class listaEntrys extends BaseAdapter {
    Context mContext;
    Fondos fondos;
    ArrayList<Entry> listaEntry;
    int contador = 0;

    public listaEntrys(Context mContext, ArrayList<Entry> listaEntry) {
        this.mContext = mContext;
        this.listaEntry = listaEntry;
        fondos = new Fondos(mContext);
    }

    @Override
    public int getCount() {
        return listaEntry.size();
    }

    @Override
    public Object getItem(int position) {
        return listaEntry.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Entry entry = listaEntry.get(position);
        convertView = LayoutInflater.from(mContext).inflate(R.layout.lista_entrys, null);
        TextView autor = (TextView) convertView.findViewById(R.id.textViewAutorEntry);
        TextView name = (TextView) convertView.findViewById(R.id.textViewNameEntry);
        ImageView img = (ImageView) convertView.findViewById(R.id.imageViewIcoEntry);
        autor.setText(entry.getRights().getLabel());
        setName(name, entry);
        setImg(img, entry);
        contador++;
        darFondo(convertView);
        return convertView;
    }

    private void setImg(ImageView imageView, Entry entry) {
        EntryControlador entryControlador = new EntryControlador(mContext);
        ImImage imagen;
        if (Dispositivo.isTablet(mContext)) {
            imagen = entryControlador.mayorImagen(entry.getImImage());
        } else {
            imagen = entryControlador.menorImagen(entry.getImImage());
        }
        cargarimg(imageView, entry, imagen);
    }

    private void cargarimg(ImageView imageView, Entry entry, ImImage img) {
        TareaConexionImagen tarea = new TareaConexionImagen(mContext,
                imageView,
                entry.getImName().getLabel() + mContext.getResources().getString(R.string.ex_img));
        if (!tarea.ValidarConexion()) {
            Log.println(Log.ASSERT, "ejecutara", "hilo" + entry.getImName().getLabel() + mContext.getResources().getString(R.string.ex_img));
            tarea.execute(img.getLabel());
        }
    }

    private void setName(TextView name, Entry entry) {
        if (entry.getImName().getLabel().contains("'")) {
            name.setText(entry.getImName().getLabel().split(" ")[0]);
        } else {
            name.setText(entry.getImName().getLabel().split("-")[0]);
        }
    }

    private void darFondo(View view) {
        if (contador == fondos.getFondos().size()) {
            contador = 0;
        }
        if (contador < fondos.getFondos().size()) {
            int sdk = android.os.Build.VERSION.SDK_INT;
            if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                view.setBackgroundDrawable(fondos.getFondos().get(contador));
            } else {
                view.setBackground(fondos.getFondos().get(contador));
            }
        }
    }
}
