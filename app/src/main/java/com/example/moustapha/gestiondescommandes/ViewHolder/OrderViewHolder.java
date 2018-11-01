package com.example.moustapha.gestiondescommandes.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.moustapha.gestiondescommandes.Interface.ItemClickListener;
import com.example.moustapha.gestiondescommandes.R;

/**
 * Created by moustapha on 07/08/18.
 */

public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{

    public TextView txtOrderId,txtOrderStatus,txtOrderPhone,txtOrderAddress;

    private ItemClickListener itemClickListener;

    public OrderViewHolder(View itemView) {
        super(itemView);

        txtOrderAddress =(TextView)itemView.findViewById(R.id.order_address);
        txtOrderStatus =(TextView)itemView.findViewById(R.id.order_status);
        txtOrderId =(TextView)itemView.findViewById(R.id.order_id);
        txtOrderPhone =(TextView)itemView.findViewById(R.id.order_phone);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v)
    {
        itemClickListener.onClick(v,getAdapterPosition(),false);


    }
}
