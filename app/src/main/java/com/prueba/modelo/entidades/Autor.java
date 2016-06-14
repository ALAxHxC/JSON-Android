package com.prueba.modelo.entidades;

/**
 * Created by daniel on 09/06/2016.
 */
public class Autor {
    private String name, url, updated, rights, title, icon, id;

    public Autor() {
    }

    public Autor(String name, String url, String updated, String rights, String title, String icon, String id) {
        this.name = name;
        this.url = url;
        this.updated = updated;
        this.rights = rights;
        this.title = title;
        this.icon = icon;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getRights() {
        return rights;
    }

    public void setRights(String rights) {
        this.rights = rights;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
