package com.example.myshop.ViewHolder;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myshop.InfoProdActivity;
import com.example.myshop.Interface.ItemClickListner;
import com.example.myshop.R;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtProductName, txtProductDescription, txtProductPrice;
    public ImageView imageView;
    public ItemClickListner listner;

    public ProductViewHolder(View itemView) {
        super(itemView);

        imageView = itemView.findViewById(R.id.product_image);
        txtProductName = itemView.findViewById(R.id.product_name);
        txtProductDescription = itemView.findViewById(R.id.product_description);
        txtProductPrice = itemView.findViewById(R.id.product_price);


        itemView.setOnClickListener(this);
        imageView.setOnClickListener(this);
    }

    public void setItemClickListner(ItemClickListner listner) {
        this.listner = listner;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == imageView.getId()) {

            Intent intent = new Intent(view.getContext(), InfoProdActivity.class);


            view.getContext().startActivity(intent);
        } else {

            listner.onClick(view, getAdapterPosition(), false);
        }
    }
}
