package com.prueba.vistas.listas;

import android.content.Context;
import android.content.res.Configuration;

/**
 * Created by Daniel on 10/06/2016.
 */
public class Dispositivo {
    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

}
