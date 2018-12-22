package com.example.moustapha.gestiondescommandes;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import com.example.moustapha.gestiondescommandes.Interface.ItemClickListener;
import com.example.moustapha.gestiondescommandes.Model.Produit;
import com.example.moustapha.gestiondescommandes.ViewHolder.ProduitViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProduitListe extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseDatabase database;
    DatabaseReference produitListe;

    String categoryId = "";

    FirebaseRecyclerAdapter<Produit, ProduitViewHolder> adapter;


    //Fonction recherche

    FirebaseRecyclerAdapter<Produit, ProduitViewHolder> searchAdapter;
    List<String> suggestList = new ArrayList<>();
    MaterialSearchBar materialSearchBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produit_liste);

        //Firebase

        database = FirebaseDatabase.getInstance();
        produitListe = database.getReference("Produit");

        recyclerView = (RecyclerView) findViewById(R.id.recycler_produit);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //obtenir l'intent cliqué pour le produit
        if (getIntent() != null)
            categoryId = getIntent().getStringExtra("CategoryId");
        if (!categoryId.isEmpty() && categoryId != null) {
            loadListProduit(categoryId);
        }


        //fonctionnalités de recherches

        materialSearchBar = (MaterialSearchBar) findViewById(R.id.searchBar);
        materialSearchBar.setHint("Saisir le produit");

        loadSuggest();  //charger la suggestion au niveau de firebase

        materialSearchBar.setLastSuggestions(suggestList);
        materialSearchBar.setCardViewElevation(10);
        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                List<String> suggest = new ArrayList<>();
                for (String search : suggestList) {
                    if (search.toLowerCase().contains(materialSearchBar.getText().toLowerCase()))
                        suggest.add(search);
                }

                materialSearchBar.setLastSuggestions(suggest);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                if (!enabled)
                    recyclerView.setAdapter(adapter);
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                startSearch(text);
            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });


    }

    private void startSearch(CharSequence text) {
        searchAdapter = new FirebaseRecyclerAdapter<Produit, ProduitViewHolder>(

                Produit.class,
                R.layout.produit_item,
                ProduitViewHolder.class,
                produitListe.orderByChild("Name").equalTo(text.toString())


        ) {
            @Override
            protected void populateViewHolder(ProduitViewHolder viewHolder, Produit model, int position) {

                viewHolder.produit_name.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.produit_image);

                final Produit local = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View viex, int position, boolean isLongClick) {
                        Intent proDetail = new Intent(ProduitListe.this, ProduitDetail.class);


                        //CategoryId est une cle
                        proDetail.putExtra("ProduitId", adapter.getRef(position).getKey());
                        startActivity(proDetail);
                    }
                });

            }
        };
        recyclerView.setAdapter(searchAdapter);
    }

    private void loadSuggest() {
        produitListe.orderByChild("MenuId").equalTo(categoryId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            Produit item = postSnapshot.getValue(Produit.class);
                            suggestList.add(item.getName()); //ajouter le nom du produit suggéré

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    private void loadListProduit(String categoryId) {
        adapter = new FirebaseRecyclerAdapter<Produit, ProduitViewHolder>(Produit.class,
                R.layout.produit_item, ProduitViewHolder.class,
                produitListe.orderByChild("MenuId").equalTo(categoryId)) // select *from produit where MenuId=
        {
            @Override
            protected void populateViewHolder(ProduitViewHolder viewHolder, Produit model, int position) {
                viewHolder.produit_name.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.produit_image);

                final Produit local = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View viex, int position, boolean isLongClick) {
                        Intent proDetail = new Intent(ProduitListe.this, ProduitDetail.class);


                        //CategoryId est une cle
                        proDetail.putExtra("ProduitId", adapter.getRef(position).getKey());
                        startActivity(proDetail);
                    }
                });

            }
        };

        //Set Adapter
        Log.d("TAG", "" + adapter.getItemCount());
        recyclerView.setAdapter(adapter);
    }
}
