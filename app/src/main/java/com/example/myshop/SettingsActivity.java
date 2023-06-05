    package com.example.myshop;

    import androidx.appcompat.app.AppCompatActivity;

    import android.content.Intent;
    import android.net.Uri;
    import android.os.Bundle;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.TextView;
    import com.bumptech.glide.Glide;
    import com.example.myshop.Adapters.ProductAdapter;
    import com.example.myshop.Model.Products;
    import com.example.myshop.ViewHolder.CarViewHolder;
    import com.example.myshop.ViewHolder.ProductViewHolder;
    import com.firebase.ui.database.FirebaseRecyclerAdapter;
    import com.firebase.ui.database.FirebaseRecyclerOptions;
    import com.google.firebase.database.DataSnapshot;
    import com.google.firebase.database.DatabaseError;
    import com.google.firebase.database.ValueEventListener;
    import androidx.annotation.NonNull;
    import androidx.recyclerview.widget.GridLayoutManager;
    import androidx.recyclerview.widget.RecyclerView;


    import com.google.firebase.auth.FirebaseAuth;
    import com.google.firebase.auth.FirebaseUser;
    import com.google.firebase.database.DatabaseReference;
    import com.google.firebase.database.FirebaseDatabase;
    import com.google.firebase.storage.FirebaseStorage;
    import com.google.firebase.storage.StorageReference;
    import com.squareup.picasso.Picasso;

    import de.hdodenhof.circleimageview.CircleImageView;

    public class SettingsActivity extends AppCompatActivity {
        private CircleImageView account_image;
        private TextView fullname, semail, sphone;
        private TextView editProfile, close;
        DatabaseReference ProductsRef;
        private RecyclerView recyclerView;
        RecyclerView.LayoutManager layoutManager;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_settings);
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(currentUser.getUid());
            semail = findViewById(R.id.email_settings);
            sphone=findViewById(R.id.phone_settings);
            account_image=findViewById(R.id.account_image_settings);
            fullname=findViewById(R.id.fullname_settings);
            ProductsRef = FirebaseDatabase.getInstance().getReference().child("Products");
            recyclerView = findViewById(R.id.product_recycler_view);
            recyclerView.setHasFixedSize(true);
            layoutManager = new GridLayoutManager(this, calculateSpanCount());
            recyclerView.setLayoutManager(layoutManager);
            close=findViewById(R.id.close_setting);
            editProfile = findViewById(R.id.edit_settings);
            FirebaseRecyclerOptions<Products> options = new FirebaseRecyclerOptions.Builder<Products>()
                    .setQuery(ProductsRef.orderByChild("userId").equalTo(currentUser.getUid()), Products.class).build();
            FirebaseRecyclerAdapter<Products, CarViewHolder> adapter = new FirebaseRecyclerAdapter<Products, CarViewHolder>(options) {
                @Override
                protected void onBindViewHolder(@NonNull CarViewHolder holder, int position, @NonNull Products model) {
                    int reversedPosition = getItemCount() - position - 1;
                    Products reversedModel = getItem(reversedPosition);
                    holder.txtProductName.setText(model.getCar_mark());
                    holder.txtProductModel.setText(model.getModel());
                    holder.txtProductPrice.setText("Цена : " + model.getPrice() + "$");
                    Picasso.get().load(model.getImage()).into(holder.imageView);
                    holder.pid = model.getPid();
                }
                @Override
                public Products getItem(int position) {
                    return super.getItem(getItemCount() - position - 1);
                }
                @Override
                public int getItemCount() {
                    return super.getItemCount();
                }
                @NonNull
                @Override
                public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items_layout, parent, false);
                    CarViewHolder holder = new CarViewHolder(view);
                    return holder;
                }
            };
            recyclerView.setAdapter(adapter);
            adapter.startListening();
            userRef.child("name").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String name = dataSnapshot.getValue(String.class);
                    fullname.setText(name);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
            userRef.child("phone").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String phone = dataSnapshot.getValue(String.class);
                    sphone.setText(phone);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
            userRef.child("email").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String email = dataSnapshot.getValue(String.class);
                    semail.setText(email);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
            userRef.child("photoUrl").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String photoUrl = dataSnapshot.getValue(String.class);
                    if (photoUrl != null){
                    Glide.with(SettingsActivity.this)
                            .load(photoUrl)
                            .into(account_image);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent closeIntent = new Intent(SettingsActivity.this, HomeActivity.class);
                    startActivity(closeIntent);
                }
            });
            editProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent editIntent = new Intent(SettingsActivity.this, ProfileEditActivity.class);
                    startActivity(editIntent);
                }
            });
        }
        private int calculateSpanCount() {
            int screenWidth = getResources().getDisplayMetrics().widthPixels;
            int itemWidth = getResources().getDimensionPixelSize(R.dimen.product_item_width);
            int spanCount = screenWidth / itemWidth;
            return Math.max(spanCount, 1);
        }
    }