package com.example.moustapha.gestiondescommandes.Model;

import java.util.List;

/**
 * Created by moustapha on 03/08/18.
 */

public class Request
{
    private String phone;
    private String address;
    private String total;
    private String name;
    private String status;
    private List<Order> produits; //list des produits par ordre

    public Request()
    {

    }


    public Request(String phone, String address, String total, List<Order> produits) {
        this.phone = phone;
        this.address = address;
        this.total = total;
        this.name = name;
        this.produits = produits;
        this.status="0";

    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Order> getProduits() {
        return produits;
    }

    public void setProduits(List<Order> produits) {
        this.produits = produits;
    }


}
