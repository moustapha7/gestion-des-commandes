package com.example.moustapha.gestiondescommandes.Model;

/**
 * Created by moustapha on 01/08/18.
 */

public class Category
{
    private  String Name;
    private String Image;

    public Category()
    {

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

    public Category(String name, String image)
    {

        Name=name;
        Image=image;

    }
}
