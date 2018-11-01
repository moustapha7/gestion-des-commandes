package com.example.moustapha.gestiondescommandes.Model;

/**
 * Created by moustapha on 01/08/18.
 */

public class Produit
{
    private String Name;
    private String Image;
    private String Description;
    private String Prix;
    private String Nombre;
    private String MenuId;

    public Produit() {
    }

    public Produit(String name, String image, String description, String prix, String nombre, String menuId) {
        Name = name;
        Image = image;
        Description = description;
        Prix = prix;
        Nombre = nombre;
        MenuId = menuId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getPrix() {
        return Prix;
    }

    public void setPrix(String prix) {
        Prix = prix;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getMenuId() {
        return MenuId;
    }

    public void setMenuId(String menuId) {
        MenuId = menuId;
    }
}

