package com.example.moustapha.gestiondescommandes.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moustapha.gestiondescommandes.Interface.ItemClickListener;
import com.example.moustapha.gestiondescommandes.R;

/**
 * Created by moustapha on 01/08/18.
 */

public class ProduitViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView produit_name;
    public ImageView produit_image;

    private ItemClickListener itemClickListener;

    public ProduitViewHolder(View itemView) {
        super(itemView);

        produit_name = (TextView) itemView.findViewById(R.id.produit_name);
        produit_image = (ImageView) itemView.findViewById(R.id.produit_image);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), false);
    }
}
