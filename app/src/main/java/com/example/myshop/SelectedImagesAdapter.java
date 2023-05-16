package com.example.myshop;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class SelectedImagesAdapter extends RecyclerView.Adapter<SelectedImagesAdapter.SelectedImageViewHolder> {
    private List<Uri> imagesList;

    public SelectedImagesAdapter(List<Uri> imagesList) {
        this.imagesList = imagesList;
    }

    @NonNull
    @Override
    public SelectedImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selected_image, parent, false);
        return new SelectedImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectedImageViewHolder holder, int position) {
        Uri imageUri = imagesList.get(position);
        holder.bind(imageUri);
    }

    @Override
    public int getItemCount() {
        return imagesList.size();
    }

    public class SelectedImageViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        public SelectedImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.selected_image);
        }

        public void bind(Uri imageUri) {
            // Load the image using the imageUri into the imageView
            Glide.with(itemView.getContext())
                    .load(imageUri)
                    .placeholder(R.drawable.select_product_image) // Placeholder image while loading
                    .into(imageView);
        }
    }
}
