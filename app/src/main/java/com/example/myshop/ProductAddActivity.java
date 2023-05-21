package com.example.myshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import android.widget.Spinner;

import com.example.myshop.Adapters.SelectedImagesAdapter;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class ProductAddActivity extends AppCompatActivity {
    private TextView back_add, add_add;
    private ImageView productImage;
    private String Description, Price,  Contacts;
    private String saveCurrentDate, saveCurrentTime, productRandomKey;
    private static final int GALLERYPICK = 1;
    private ProgressDialog loadingBar;
    private StorageReference ProductImageRef;
    private DatabaseReference ProductsRef;
    private Spinner car_mark, car_year, car_kuzov, car_motor, car_bublik;
    private String Scar_mark, Scar_year, Scar_kuzov, Scar_motor, Scar_bublik;


    private Uri ImageUri;
    private EditText  product_description_add, product_price_add, product_contact_add;

    private String downloadImageUrl;
    private FirebaseStorage storage;
    private StorageReference storageRef;
    private List<Uri> selectedImages = new ArrayList<>();
    private String userId = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_add);
        init();
        spinner();


        setupSelectedImagesRecyclerView();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        userId = currentUser.getUid();
        productImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenGallery();
            }
        });
        add_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidateProductData();
            }
        });
        back_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backfromadd = new Intent(ProductAddActivity.this , HomeActivity.class);
                startActivity(backfromadd);
            }
        });

    }

    private void ValidateProductData() {
        Description = product_description_add.getText().toString();
        Price = product_price_add.getText().toString();
        Contacts = product_contact_add.getText().toString();


        if (selectedImages.isEmpty()) {
            Toast.makeText(this, "Добавьте изображение товара.", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(TextUtils.isEmpty(Description)){
            Toast.makeText(this, "Добавьте описание", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Price)){
            Toast.makeText(this, "Добавьте стоимость", Toast.LENGTH_SHORT).show();
        }else if (Scar_bublik.equals("Руль*")){
            Toast.makeText(this, "Категория (Руль) должна быть заполнена", Toast.LENGTH_SHORT).show();
        }else if (Scar_kuzov.equals("Тип кузова*")){
            Toast.makeText(this, "Категория (Тип кузова*) должна быть заполнена", Toast.LENGTH_SHORT).show();
        }else if (Scar_mark.equals("Марка*")){
            Toast.makeText(this, "Категория (Марка*) должна быть заполнена", Toast.LENGTH_SHORT).show();
        }else if (Scar_motor.equals("Мотор*")){
            Toast.makeText(this, "Категория (Мотор*) должна быть заполнена", Toast.LENGTH_SHORT).show();
        }else if (Scar_year.equals("Год выпуска*")){
            Toast.makeText(this, "Категория (Год выпуска*) должна быть заполнена", Toast.LENGTH_SHORT).show();
        }else {
            StoreProductInformation();
        }
    }



    private void StoreProductInformation() {

        loadingBar.setTitle("Загрузка данных");
        loadingBar.setMessage("Пожалуйста, подождите...");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("ddMMyyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HHmmss");
        saveCurrentTime = currentTime.format(calendar.getTime());

        productRandomKey = saveCurrentDate + saveCurrentTime;

        final List<String> imageUrls = new ArrayList<>();
        final int[] uploadedImages = {0};
        if (!selectedImages.isEmpty()) {
            downloadImageUrl = selectedImages.get(0).toString();
        }

        for (int i = 0; i < selectedImages.size(); i++) {
            final int index = i;
            final StorageReference filePath = ProductImageRef.child(productRandomKey + index + ".jpg");
            UploadTask uploadTask = filePath.putFile(selectedImages.get(i));




            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    return filePath.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        imageUrls.add(downloadUri.toString());
                    }

                    uploadedImages[0]++;
                    if (uploadedImages[0] == selectedImages.size()) {
                        saveProductWithImageUrls(imageUrls);
                    }
                }
            });
        }
    }




    private void saveProductWithImageUrls(List<String> imageUrls) {
        HashMap<String, Object> productMap = new HashMap<>();

        // Add other product information to the HashMap

        productMap.put("image*", imageUrls);
        productMap.put("pid", productRandomKey);
        productMap.put("image", imageUrls.get(0));
        productMap.put("date", saveCurrentDate);
        productMap.put("time", saveCurrentTime);
        productMap.put("description", Description);
        productMap.put("car_kuzov", Scar_kuzov);
        productMap.put("car_motor", Scar_motor);
        productMap.put("car_bublik", Scar_bublik);
        productMap.put("car_mark", Scar_mark);
        productMap.put("car_year",Scar_year);

        productMap.put("price", Price);

        productMap.put("contacts", Contacts);
        productMap.put("userId" , userId);

        ProductsRef.child(productRandomKey).updateChildren(productMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            loadingBar.dismiss();
                            Toast.makeText(ProductAddActivity.this, "Товар добавлен", Toast.LENGTH_SHORT).show();

                            Intent addedIntent = new Intent(ProductAddActivity.this, HomeActivity.class);
                            startActivity(addedIntent);
                        } else {
                            String message = task.getException().toString();
                            Toast.makeText(ProductAddActivity.this, "Ошибка: "+ message, Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                        }
                    }
                });
    }

    private void OpenGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(galleryIntent, GALLERYPICK);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERYPICK && resultCode == RESULT_OK && data != null) {
            if (data.getClipData() != null) {
                int count = data.getClipData().getItemCount();
                for (int i = 0; i < count; i++) {
                    Uri imageUri = data.getClipData().getItemAt(i).getUri();
                    selectedImages.add(imageUri);
                }
            } else if (data.getData() != null) {
                Uri imageUri = data.getData();
                selectedImages.add(imageUri);
            }
            setupSelectedImagesRecyclerView();
        }
    }




    private void init(){
        back_add = findViewById(R.id.back_add);
        add_add = findViewById(R.id.add_add);
        product_description_add = findViewById(R.id.product_description_add);
        product_price_add = findViewById(R.id.product_price_add);
        product_contact_add = findViewById(R.id.product_contact_add);
        car_mark = findViewById(R.id.car_mark);
        car_year = findViewById(R.id.car_year);
        car_kuzov = findViewById(R.id.car_kuzov);
        car_motor = findViewById(R.id.car_motor);
        car_bublik = findViewById(R.id.car_bublik);
        productImage = findViewById(R.id.product_image_add);
        ProductImageRef = FirebaseStorage.getInstance().getReference().child("Product Images");
        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Products");
        loadingBar = new ProgressDialog(this);
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference().child("product_images"); // "product_images" - это путь, по которому будут сохраняться изображения


    }
    private void setupSelectedImagesRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.selected_images_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        // Create an adapter for the RecyclerView
        SelectedImagesAdapter adapter = new SelectedImagesAdapter(selectedImages);
        recyclerView.setAdapter(adapter);
    }
    private void spinner(){
        car_mark.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Scar_mark = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
        car_motor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Scar_motor = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
        car_bublik.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Scar_bublik = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
        car_kuzov.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Scar_kuzov = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
        car_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Scar_year = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }


}