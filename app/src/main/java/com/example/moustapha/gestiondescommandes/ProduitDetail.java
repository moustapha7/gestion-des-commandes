package com.example.moustapha.gestiondescommandes;

import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.moustapha.gestiondescommandes.Database.Database;
import com.example.moustapha.gestiondescommandes.Model.Order;
import com.example.moustapha.gestiondescommandes.Model.Produit;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ProduitDetail extends AppCompatActivity {

    TextView produit_name,produit_prix,produit_description;
    ImageView produit_image;

    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btnCart;
    ElegantNumberButton numberButton;

    String ProduitId="";
    FirebaseDatabase database;
    DatabaseReference produits;

    Produit currentProduit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produit_detail);


        //firebase
        database= FirebaseDatabase.getInstance();
        produits =database.getReference("Produit");

        //init view

       numberButton = (ElegantNumberButton)findViewById(R.id.number_button);
       btnCart=(FloatingActionButton)findViewById(R.id.btnCart);



       btnCart.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               new Database(getBaseContext()).addToCart(new Order
                       (
                       ProduitId,
                       currentProduit.getName(),
                       numberButton.getNumber(),
                       currentProduit.getPrix(),
                       currentProduit.getNombre()

               ));
               Toast.makeText(ProduitDetail.this, "Produit ajoué au Panier", Toast.LENGTH_SHORT).show();
           }
       });





       produit_description= (TextView)findViewById(R.id.produit_descritpion);
        produit_name= (TextView)findViewById(R.id.produit_name);
        produit_prix= (TextView)findViewById(R.id.produit_prix);

        produit_image= (ImageView) findViewById(R.id.img_produit);

        collapsingToolbarLayout =(CollapsingToolbarLayout)findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.collapsedAppbar);


        //obtenir les produits à partir de l'intent
        if(getIntent() !=null)
            ProduitId =getIntent().getStringExtra("ProduitId");

        if(!ProduitId.isEmpty())
        {
            gettDetailProduit(ProduitId);
        }
    }

    private void gettDetailProduit(String ProduitId)
    {
        produits.child(ProduitId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                currentProduit =dataSnapshot.getValue(Produit.class);

                //set image

                Picasso.with(getBaseContext()).load(currentProduit.getImage())
                        .into(produit_image);

                collapsingToolbarLayout.setTitle(currentProduit.getName());



                produit_prix.setText(currentProduit.getPrix());
                produit_name.setText(currentProduit.getName());
                produit_description.setText(currentProduit.getDescription());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
