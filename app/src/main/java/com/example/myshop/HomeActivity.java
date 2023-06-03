package com.example.myshop;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myshop.Model.Products;
import com.example.myshop.ViewHolder.ProductViewHolder;
import com.example.myshop.WelcomePages.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;
import androidx.recyclerview.widget.GridLayoutManager;




public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private CircleImageView userProfileImage;
    private TextView username;
    DatabaseReference ProductsRef;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private FirebaseUser currentUser;
    private DatabaseReference databaseReference;
    private Query searchRef;
    private Spinner carMark, carKuzov, carYear, carMotor, carBublik , carModel;
    private ImageView searchImage;
    private String car_kuzovS = "";
    private String car_yearS = "";
    private String car_motorS = "";
    private String car_bublikS = "";
    private String car_markS = "";
    private String car_nameS = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Products");
        searchRef = FirebaseDatabase.getInstance().getReference().child("Products");
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        carMark = findViewById(R.id.mark);
        carModel = findViewById(R.id.model);
        carYear = findViewById(R.id.year);
        carKuzov = findViewById(R.id.kuzov);
        carMotor = findViewById(R.id.motor);
        carBublik = findViewById(R.id.bublik);
        searchImage = findViewById(R.id.search_button);
        init();

        if (currentUser != null) {
            String currentUserId = currentUser.getUid();
            databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(currentUserId);
        }
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        userProfileImage = headerView.findViewById(R.id.user_profile_image);
        username = headerView.findViewById(R.id.username);
        carMark.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                car_markS = parent.getItemAtPosition(position).toString();
                updateCarNameAdapter();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        carModel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                car_nameS = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
        carKuzov.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                car_kuzovS = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
        carBublik.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                car_bublikS = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
        carMotor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                car_motorS = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
        carYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                car_yearS = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
        if (currentUser != null) {
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    String photoUrl = snapshot.child("photoUrl").getValue(String.class);
                    String displayName = snapshot.child("name").getValue(String.class);
                    if (photoUrl !=null){
                        Picasso.get().load(photoUrl).into(userProfileImage);}
                    username.setText(displayName);
                }

                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }


        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("DriverTrade");
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Здесь будет переход в корзину", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
            }

        };
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        navigationView.setNavigationItemSelectedListener(this);
        recyclerView = findViewById(R.id.recycler_menu);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(this, calculateSpanCount());
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<Products> options = new FirebaseRecyclerOptions.Builder<Products>()
                .setQuery(ProductsRef, Products.class).build();
        FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter = new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull Products model) {
                int reversedPosition = getItemCount() - position - 1;
                Products reversedModel = getItem(reversedPosition);

                holder.txtProductName.setText(model.getCar_mark());
                holder.txtProductModel.setText(model.getModel());
                holder.txtProductPrice.setText("$"+model.getPrice());
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
            public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items_layout, parent,false);
                ProductViewHolder holder = new ProductViewHolder(view);
                return holder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
         if(id == R.id.settings){
            Intent settingsIntent = new Intent(HomeActivity.this, SettingsActivity.class);
            startActivity(settingsIntent);
        } else if(id == R.id.logout){
            Paper.book().destroy();
            Intent loginIntent = new Intent(HomeActivity.this, MainActivity.class);
            startActivity(loginIntent);
        }else if(id == R.id.add){
             Paper.book().destroy();
             Intent loginIntent = new Intent(HomeActivity.this, ProductAddActivity.class);
             startActivity(loginIntent);
         }
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return false;

    }
    private int calculateSpanCount() {
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        int itemWidth = getResources().getDimensionPixelSize(R.dimen.product_item_width); // Замените R.dimen.product_item_width на ваш ресурс ширины элемента продукта
        int spanCount = screenWidth / itemWidth;
        return Math.max(spanCount, 1); // Устанавливаем минимальное значение столбцов как 1
    }
    private void init(){
        ArrayAdapter<CharSequence> adaptermark = ArrayAdapter.createFromResource(this, R.array.car_mark, android.R.layout.simple_spinner_item);
        adaptermark.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        carMark.setAdapter(adaptermark);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.car_name, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        carModel.setAdapter(adapter);


        ArrayAdapter<CharSequence> adapterbublik = ArrayAdapter.createFromResource(this, R.array.car_bublik, android.R.layout.simple_spinner_item);
        adapterbublik.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        carBublik.setAdapter(adapterbublik);

        ArrayAdapter<CharSequence> adapterkuzov = ArrayAdapter.createFromResource(this, R.array.car_kuzov, android.R.layout.simple_spinner_item);
        adapterkuzov.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        carKuzov.setAdapter(adapterkuzov);

        ArrayAdapter<CharSequence> adaptermotor = ArrayAdapter.createFromResource(this, R.array.car_motor, android.R.layout.simple_spinner_item);
        adaptermotor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        carMotor.setAdapter(adaptermotor);

        ArrayAdapter<CharSequence> adapteryear = ArrayAdapter.createFromResource(this, R.array.car_year, android.R.layout.simple_spinner_item);
        adapteryear.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        carYear.setAdapter(adapteryear);
    }
    private void updateCarNameAdapter() {
        ArrayAdapter<CharSequence> adapter;
        if (car_markS.equals("Toyota")) {
            adapter = ArrayAdapter.createFromResource(this, R.array.car_name_Toyota, android.R.layout.simple_spinner_item);
        } else if (car_markS.equals("Nissan")) {
            adapter = ArrayAdapter.createFromResource(this, R.array.car_name_Nissan, android.R.layout.simple_spinner_item);
        }else if (car_markS.equals("Mercedes-Benz")) {
            adapter = ArrayAdapter.createFromResource(this, R.array.car_name_Mercedes, android.R.layout.simple_spinner_item);
        } else {
            adapter = ArrayAdapter.createFromResource(this, R.array.car_name, android.R.layout.simple_spinner_item);
        }
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        carModel.setAdapter(adapter);
        carModel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                car_nameS = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
        searchImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchProducts();
            }
        });
    }
    private void searchProducts() {
        searchRef = FirebaseDatabase.getInstance().getReference().child("Products");
        if (!car_markS.equals("Марка*")) {
            searchRef = searchRef.orderByChild("car_mark").equalTo(car_markS);
        }
        if (!car_nameS.equals("Модель*")) {
            searchRef = searchRef.orderByChild("model").equalTo(car_nameS);
        }
        if (!car_kuzovS.equals("Тип кузова*")) {
            searchRef = searchRef.orderByChild("car_kuzov").equalTo(car_kuzovS);
        }
        if (!car_bublikS.equals("Руль*")) {
            searchRef = searchRef.orderByChild("car_bublik").equalTo(car_bublikS);
        }
        if (!car_motorS.equals("Мотор*")) {
            searchRef = searchRef.orderByChild("car_motor").equalTo(car_motorS);
        }
        if (!car_yearS.equals("Год выпуска*")) {
            searchRef = searchRef.orderByChild("car_year").equalTo(car_yearS);
        }
        FirebaseRecyclerOptions<Products> options = new FirebaseRecyclerOptions.Builder<Products>()
                .setQuery(searchRef, Products.class)
                .build();
        FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter = new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull Products model) {
                int reversedPosition = getItemCount() - position - 1;
                Products reversedModel = getItem(reversedPosition);
                holder.txtProductName.setText(model.getCar_mark());
                holder.txtProductModel.setText(model.getModel());
                holder.txtProductPrice.setText("$"+model.getPrice());
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
            public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items_layout, parent,false);
                ProductViewHolder holder = new ProductViewHolder(view);
                return holder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}