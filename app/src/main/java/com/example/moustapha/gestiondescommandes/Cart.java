package com.example.moustapha.gestiondescommandes;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moustapha.gestiondescommandes.Common.Common;
import com.example.moustapha.gestiondescommandes.Database.Database;
import com.example.moustapha.gestiondescommandes.Model.Order;
import com.example.moustapha.gestiondescommandes.Model.Request;
import com.example.moustapha.gestiondescommandes.ViewHolder.CartAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Cart extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference requests;

    TextView txtTottalPrice;
    Button btnPlace;


    List<Order> carte = new ArrayList<>();
    CartAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        //firebase

        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Requests");


        //init
        recyclerView = (RecyclerView) findViewById(R.id.listCart);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        txtTottalPrice = (TextView) findViewById(R.id.total);
        btnPlace = (Button) findViewById(R.id.btnPlaceOrder);


        btnPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog();
            }
        });


//        loadListProduit();
    }

    private void showAlertDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Cart.this);
        alertDialog.setTitle("Addresse Residence !");
        alertDialog.setMessage(" Saisir votre Adresse");

        final EditText edtAddress = new EditText(Cart.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        edtAddress.setLayoutParams(lp);
        alertDialog.setView(edtAddress);
        alertDialog.setIcon(R.drawable.ic_shopping_cart_black_24dp);
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // crrer a new request
                Request request = new Request(
                        Common.currentUser.getLogin(),
                        edtAddress.getText().toString(),
                        txtTottalPrice.getText().toString(),
                        carte

                );

                //Envoyé les données vers firebase
                requests.child(String.valueOf(System.currentTimeMillis()))
                        .setValue(request);


                //delete cart
                new Database(getBaseContext()).clearToCart();
                Toast.makeText(Cart.this, "Merci ordre des choix ", Toast.LENGTH_SHORT).show();
                finish();

            }
        });


        alertDialog.setNegativeButton("Non", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();

    }


    private void loadListProduit() {
        carte = new Database(this).getCarts();
        adapter = new CartAdapter(carte, this);
        recyclerView.setAdapter(adapter);

        //calcul total prix

        int total = 0;
        for (Order order : carte)

            total += (Integer.parseInt(order.getPrix()) * Integer.parseInt(order.getQuantity()));

        Locale locale = new Locale("en", "US");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
        txtTottalPrice.setText((fmt.format(total)));


    }
}
