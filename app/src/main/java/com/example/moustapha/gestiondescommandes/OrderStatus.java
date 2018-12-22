package com.example.moustapha.gestiondescommandes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.moustapha.gestiondescommandes.Common.Common;
import com.example.moustapha.gestiondescommandes.Model.Request;
import com.example.moustapha.gestiondescommandes.ViewHolder.OrderViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OrderStatus extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference requests;
    FirebaseRecyclerAdapter<Request, OrderViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);


        //Firebase

        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Requests");

        recyclerView = (RecyclerView) findViewById(R.id.listOrders);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        loadOrders(Common.currentUser.getLogin());


    }

    private void loadOrders(String login) {
        adapter = new FirebaseRecyclerAdapter<Request, OrderViewHolder>(

                Request.class,
                R.layout.order_layout,
                OrderViewHolder.class,
                requests.orderByChild("login").equalTo(login)


        ) {
            @Override
            protected void populateViewHolder(OrderViewHolder viewHolder, Request model, int position) {

                viewHolder.txtOrderId.setText(adapter.getRef(position).getKey());
                viewHolder.txtOrderStatus.setText(convetCodeToStatus(model.getStatus()));
                viewHolder.txtOrderAddress.setText(model.getAddress());
                viewHolder.txtOrderPhone.setText(model.getPhone());
            }
        };

        recyclerView.setAdapter(adapter);
    }

    private String convetCodeToStatus(String status) {
        if (status.equals("0"))
            return "Commandé";
        else if (status.equals("1"))
            return "Sur mon Panier";
        else
            return "Shipped";
    }
}
