package com.prueba.controlador.persistencia.basededatos;

/**
 * Created by daniel on 09/06/2016.
 */
public class Entidades {
    public static final String autor = "CREATE TABLE IF NOT EXISTS author (\n" +
            "name TEXT PRIMARY KEY, " +
            "url TEXT NOT NULL, " +
            "updated TEXT NOT NULL, " +
            "rights TEXT NOT NULL, " +
            "title TEXT NOT NULL, " +
            "icon TEXT NOT NULL, " +
            "id TEXT NOT NULL " +
            ");";
    public static final String autor_table = "author";
    public static final String autor_name = "name";
    public static final String autor_url = "url";
    public static final String autor_updated = "updated";
    public static final String autor_rigths = "rights";
    public static final String autor_title = "title";
    public static final String autor_icon = "icon";
    public static final String autor_id = "id";

    public static final String link = "CREATE TABLE IF NOT EXISTS link(" +
            "rel TEXT PRIMARY KEY, " +
            "type TEXT , " +
            "href TEXT NOT NULL " +
            ");";
    public static final String link_table = "link";
    public static final String link_rel = "rel";
    public static final String link_href = "href";
    public static final String link_type="type";
    public static final String limpia_autor="DELETE FROM author";
    public static final String limpia_link="DELETE FROM link";
}
