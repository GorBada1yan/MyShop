package com.example.myshop.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.myshop.Model.Products;
import com.example.myshop.R;
import com.example.myshop.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

public class ProductAdapter extends FirebaseRecyclerAdapter<Products, ProductViewHolder> {

    public ProductAdapter(@NonNull FirebaseRecyclerOptions<Products> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull Products model) {
        int reversedPosition = getItemCount() - position - 1;
        Products reversedModel = getItem(reversedPosition);

        holder.txtProductName.setText(model.getCar_mark());
        holder.txtProductModel.setText(model.getCar_name());
        holder.txtProductPrice.setText("Цена: " + model.getPrice() + "$");
        Picasso.get().load(model.getImage()).into(holder.imageView);
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(com.example.myshop.R.layout.product_items_layout, parent, false);
        return new ProductViewHolder(view);
    }
}
