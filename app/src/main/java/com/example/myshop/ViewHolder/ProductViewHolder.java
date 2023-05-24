package com.example.myshop.ViewHolder;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myshop.InfoProdActivity;
import com.example.myshop.Interface.ItemClickListner;
import com.example.myshop.R;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtProductName, txtProductPrice, txtProductModel;
    public ImageView imageView;
    public ItemClickListner listner;
    public String pid;

    public ProductViewHolder(View itemView) {
        super(itemView);

        imageView = itemView.findViewById(R.id.product_image);
        txtProductName = itemView.findViewById(R.id.product_name);
        txtProductPrice = itemView.findViewById(R.id.product_price);
        txtProductModel = itemView.findViewById(R.id.product_name_);
        itemView.setOnClickListener(this);
        imageView.setOnClickListener(this);
    }

    public void setItemClickListner(ItemClickListner listner) {
        this.listner = listner;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == imageView.getId()) {
            // Uri imageUri = getImageUri();
            Intent intent = new Intent(view.getContext(), InfoProdActivity.class);
            intent.putExtra("productId", pid);
            view.getContext().startActivity(intent);
        } else {
            listner.onClick(view, getAdapterPosition(), false);
        }
    }
    private Uri getImageUri() {
        // Получаем URI изображения
        Drawable drawable = imageView.getDrawable();
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        String path = MediaStore.Images.Media.insertImage(itemView.getContext().getContentResolver(), bitmap, "Image", null);
        return Uri.parse(path);
    }
}