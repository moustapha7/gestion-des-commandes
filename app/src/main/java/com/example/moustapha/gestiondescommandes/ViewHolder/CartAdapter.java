package com.example.moustapha.gestiondescommandes.ViewHolder;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.moustapha.gestiondescommandes.Interface.ItemClickListener;
import com.example.moustapha.gestiondescommandes.Model.Order;
import com.example.moustapha.gestiondescommandes.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by moustapha on 03/08/18.
 */


class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txt_cart_name, txt_prix;
    public ImageView img_cart_count;

    private ItemClickListener itemClickListener;

    public CartViewHolder(View itemView) {
        super(itemView);

        txt_cart_name = (TextView) itemView.findViewById(R.id.cart_item_name);
        txt_prix = (TextView) itemView.findViewById(R.id.cart_item_prix);
        img_cart_count = (ImageView) itemView.findViewById(R.id.cart_item_count);
    }

    public void setTxt_cart_name(TextView txt_cart_name) {
        this.txt_cart_name = txt_cart_name;
    }

    @Override
    public void onClick(View v) {

    }
}

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {

    private List<Order> listData = new ArrayList<>();
    private Context context;

    public CartAdapter(List<Order> listData, Context context) {
        this.listData = listData;
        this.context = context;
    }

    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.cart_layout, parent, false);
        return new CartViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {

        TextDrawable drawable = TextDrawable.builder()
                .buildRound("" + listData.get(position).getQuantity(), Color.RED);

        holder.img_cart_count.setImageDrawable(drawable);

        Locale locale = new Locale("en", "US");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);

        int prix = (Integer.parseInt(listData.get(position).getPrix())) * (Integer.parseInt(listData.get(position).getQuantity()));
        holder.txt_prix.setText(fmt.format(prix));
        holder.txt_cart_name.setText(listData.get(position).getProductName());


    }

    @Override
    public int getItemCount() {
        return listData.size();
    }
}
