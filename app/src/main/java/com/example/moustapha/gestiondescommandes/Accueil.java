package com.example.moustapha.gestiondescommandes;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moustapha.gestiondescommandes.Common.Common;
import com.example.moustapha.gestiondescommandes.Interface.ItemClickListener;
import com.example.moustapha.gestiondescommandes.Model.Category;
import com.example.moustapha.gestiondescommandes.Model.Produit;
import com.example.moustapha.gestiondescommandes.ViewHolder.MenuViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

public class Accueil extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseDatabase database;
    DatabaseReference category;
    TextView txtFullName;
    RecyclerView recycl_menu;
    RecyclerView.LayoutManager layoutManager;


    FirebaseRecyclerAdapter<Category,MenuViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Menu");

        setSupportActionBar(toolbar);






        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cartIntent =new Intent(Accueil.this,Cart.class);
                startActivity(cartIntent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //Set name for user : recuperer le nom du user connecté
        View headerView=navigationView.getHeaderView(0);
        txtFullName= (TextView) headerView.findViewById(R.id.txtFullName);
        txtFullName.setText(Common.currentUser.getLogin());


        //initialisation de fire base
        database = FirebaseDatabase.getInstance();
        category = database.getReference("Category");

        // charger le menu

        recycl_menu = (RecyclerView)findViewById(R.id.recycler_menu);
        recycl_menu.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycl_menu.setLayoutManager(layoutManager);

        loadMenu();



    }


    public void loadMenu()
    {
        adapter=new FirebaseRecyclerAdapter<Category, MenuViewHolder>(Category.class,R.layout.menu_item,MenuViewHolder.class,category)
        {

            @Override
            protected void populateViewHolder(MenuViewHolder viewHolder, Category model, int position) {

                viewHolder.txtMenuName.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage())
                        .into(viewHolder.imageView);

                final Category clickItem= model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view,int position, boolean isLongClick)
                    {
                        //Obtenir category id et envoyer dans une new activity
                        Intent proList = new Intent(Accueil.this,ProduitListe.class);


                        //CategoryId est une cle
                        proList.putExtra("CategoryId",adapter.getRef(position).getKey());
                        startActivity(proList);
                    }
                });

            }
        } ;
       recycl_menu.setAdapter(adapter);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.accueil, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


       if (id == R.id.nav_produitListe)
        {

            Intent lp = new Intent(Accueil.this,ProduitListe.class);
            startActivity(lp);
        }


       else  if (id == R.id.nav_carts)
        {

            Intent car = new Intent(Accueil.this,Cart.class);
            startActivity(car);
        }
       else  if (id == R.id.nav_orders)
       {

           Intent orst = new Intent(Accueil.this,OrderStatus.class);
           startActivity(orst);
       }

       else  if (id == R.id.nav_logout)
       {
           Intent dec = new Intent(Accueil.this,SignIn.class);
           dec.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_CLEAR_TOP);
           startActivity(dec);

       }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
