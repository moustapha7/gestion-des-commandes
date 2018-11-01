package com.example.moustapha.gestiondescommandes.Model;

/**
 * Created by moustapha on 01/08/18.
 */

public class User
{
    private String login;
    private  String password;


    public User()
    {

    }

    public User(String log, String pwd)
    {

        login= log;

        password=pwd;

    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



}
