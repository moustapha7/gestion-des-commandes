package com.example.moustapha.gestiondescommandes.Model;

/**
 * Created by moustapha on 03/08/18.
 */

public class Order {
    private String ProductId;
    private String ProductName;
    private String Quantity;
    private String Prix;
    private String Discount;

    public Order() {

    }

    public Order(String productId, String productName, String quantity, String prix, String discount) {
        ProductId = productId;
        ProductName = productName;
        Quantity = quantity;
        Prix = prix;
        Discount = discount;
    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getPrix() {
        return Prix;
    }

    public void setPrix(String prix) {
        Prix = prix;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }
}
