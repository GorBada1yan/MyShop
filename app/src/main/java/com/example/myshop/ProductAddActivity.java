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

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
    private String Description, Price, Pname, Contacts;
    private String saveCurrentDate, saveCurrentTime, productRandomKey;
    private static final int GALLERYPICK = 1;
    private ProgressDialog loadingBar;
    private StorageReference ProductImageRef;
    private DatabaseReference ProductsRef;
    private String categoryRent = "";
    private String categoryType = "";
    private String categoryTypeofMachine = "";
    private String categorySubTypeofMachine = "";

    private Uri ImageUri;
    private EditText product_name_add, product_description_add, product_price_add, product_contact_add, product_location_add;
    private Spinner category_spinner1, category_spinner2,  category_spinner_comerch , category_spinner_lich;
    private Spinner category_spinner_bus, category_spinner_gruz, category_spinner_selxoz, category_spinner_stroi;
    private Spinner category_spinner_auto, category_spinner_moped, category_spinner_velosiped, category_spinner_moto;
    private String downloadImageUrl;

    private FirebaseStorage storage;
    private StorageReference storageRef;
    private List<Uri> selectedImages = new ArrayList<>();



    private String category_spinner1_value = "";
    private String category_spinner2_value = "";
    private String category_spinner_comerch_value = "";
    private String category_spinner_lich_value = "";
    private String category_spinner_bus_value = "";
    private String category_spinner_gruz_value = "";
    private String category_spinner_selxoz_value = "";
    private String category_spinner_stroi_value = "";
    private String category_spinner_auto_value = "";
    private String category_spinner_moped_value = "";
    private String category_spinner_velosiped_value = "";
    private String category_spinner_moto_value = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_add);
        init();
       init2();
        setupSelectedImagesRecyclerView();





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

        category_spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category_spinner1_value = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        category_spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category_spinner2_value = parent.getItemAtPosition(position).toString();
                if (category_spinner2_value.equals("Камерческий транспорт")){
                    category_spinner_comerch.setVisibility(View.VISIBLE);
                }else  category_spinner_comerch.setVisibility(View.INVISIBLE);
                if (category_spinner2_value.equals("Личный транспорт")){
                    category_spinner_lich.setVisibility(View.VISIBLE);
                }else category_spinner_lich.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });





        category_spinner_comerch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    category_spinner_comerch_value = parent.getItemAtPosition(position).toString();
                    if (category_spinner_comerch_value.equals("Выберите"))category_spinner_comerch_value ="";
                    else
                    if (category_spinner_comerch_value.equals("Автобус")) {

                        category_spinner_bus.setVisibility(View.VISIBLE);
                    } else category_spinner_bus.setVisibility(View.INVISIBLE);
                    if (category_spinner_comerch_value.equals("Грузовик")) {

                        category_spinner_gruz.setVisibility(View.VISIBLE);
                    } else category_spinner_gruz.setVisibility(View.INVISIBLE);
                    if (category_spinner_comerch_value.equals("Сельскохозяйственный транспорт")) {

                        category_spinner_selxoz.setVisibility(View.VISIBLE);
                    } else category_spinner_selxoz.setVisibility(View.INVISIBLE);
                    if (category_spinner_comerch_value.equals("Строительская и тяжолая техника")) {

                        category_spinner_stroi.setVisibility(View.VISIBLE);
                    } else category_spinner_stroi.setVisibility(View.INVISIBLE);



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });


        category_spinner_lich.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 category_spinner_lich_value = parent.getItemAtPosition(position).toString();
                 if (category_spinner_lich_value.equals("Выберите"))category_spinner_lich_value = "";
                 else
                if (category_spinner_lich_value.equals("Автомобили"))
                    category_spinner_auto.setVisibility(View.VISIBLE);
                else category_spinner_auto.setVisibility(View.INVISIBLE);
                if (category_spinner_lich_value.equals("Мотоциклы"))
                    category_spinner_moto.setVisibility(View.VISIBLE);
                else category_spinner_moto.setVisibility(View.INVISIBLE);
                if (category_spinner_lich_value.equals("Велосипеды"))
                    category_spinner_velosiped.setVisibility(View.VISIBLE);
                else category_spinner_velosiped.setVisibility(View.INVISIBLE);
                if (category_spinner_lich_value.equals("Мопеды"))
                    category_spinner_moped.setVisibility(View.VISIBLE);
                else category_spinner_moped.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        category_spinner_bus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               category_spinner_bus_value = parent.getItemAtPosition(position).toString();
               if (category_spinner_bus_value.equals("Выберите"))category_spinner_bus_value = "";

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        category_spinner_gruz.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               category_spinner_gruz_value = parent.getItemAtPosition(position).toString();
               if (category_spinner_gruz_value.equals("Выберите"))category_spinner_gruz_value = "";

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });


        category_spinner_selxoz.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               category_spinner_selxoz_value = parent.getItemAtPosition(position).toString();
               if (category_spinner_selxoz_value.equals("Выберите"))category_spinner_selxoz_value = "";

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });



        category_spinner_stroi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category_spinner_stroi_value = parent.getItemAtPosition(position).toString();
                if (category_spinner_stroi_value.equals("Выберите"))category_spinner_stroi_value = "";

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        category_spinner_auto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category_spinner_auto_value = parent.getItemAtPosition(position).toString();
                if (category_spinner_auto_value.equals("Выберите"))category_spinner_auto_value = "";

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        category_spinner_moped.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               category_spinner_moped_value = parent.getItemAtPosition(position).toString();
               if (category_spinner_moped_value.equals("Выберите"))category_spinner_moped_value = "";

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        category_spinner_velosiped.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               category_spinner_velosiped_value = parent.getItemAtPosition(position).toString();
               if (category_spinner_velosiped_value.equals("Выберите"))category_spinner_velosiped_value = "";


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        category_spinner_moto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category_spinner_moto_value = parent.getItemAtPosition(position).toString();
                if (category_spinner_moto_value.equals("Выберите"))category_spinner_moto_value = "";

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });







    }

    private void ValidateProductData() {
        Description = product_description_add.getText().toString();
        Price = product_price_add.getText().toString();
        Pname = product_name_add.getText().toString();
        Contacts = product_contact_add.getText().toString();

        if (selectedImages.isEmpty()) {
            Toast.makeText(this, "Добавьте изображение товара.", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(TextUtils.isEmpty(Description)){
            Toast.makeText(this, "Добавьте описание товара.", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Price)){
            Toast.makeText(this, "Добавьте стоимость товара.", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Pname)){
            Toast.makeText(this, "Добавьте название товара.", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(Contacts)){
            Toast.makeText(this, "Добавьте контакты для связи", Toast.LENGTH_SHORT).show();
        }else {
            StoreProductInformation();
            category();

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
        productMap.put("image", downloadImageUrl);
        productMap.put("date", saveCurrentDate);
        productMap.put("time", saveCurrentTime);
        productMap.put("description", Description);
        productMap.put("categoryRent", categoryRent);
        productMap.put("categoryType", categoryType);
        productMap.put("categoryTypeofMachine", categoryTypeofMachine);
        productMap.put("categorySubTypeofMachine", categorySubTypeofMachine);
        productMap.put("price", Price);
        productMap.put("pname", Pname);
        productMap.put("contacts", Contacts);

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
        product_name_add = findViewById(R.id.product_name_add);
        product_description_add = findViewById(R.id.product_description_add);
        product_price_add = findViewById(R.id.product_price_add);
        product_contact_add = findViewById(R.id.product_contact_add);

        category_spinner1 = findViewById(R.id.category_spinner1);
        category_spinner2 = findViewById(R.id.category_spinner2);
        category_spinner_comerch = findViewById(R.id.category_spinner_comerch);
        category_spinner_lich = findViewById(R.id.category_spinner_lich);
        productImage = findViewById(R.id.product_image_add);

        ProductImageRef = FirebaseStorage.getInstance().getReference().child("Product Images");
        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Products");
        loadingBar = new ProgressDialog(this);

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference().child("product_images"); // "product_images" - это путь, по которому будут сохраняться изображения



    }

    private void init2(){
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.category_spinner1, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category_spinner1.setAdapter(adapter1);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.category_spinner2, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category_spinner2.setAdapter(adapter2);

        ArrayAdapter<CharSequence> adaptercomerch = ArrayAdapter.createFromResource(this, R.array.category_spinner_comerch, android.R.layout.simple_spinner_item);
        adaptercomerch.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category_spinner_comerch.setAdapter(adaptercomerch);

        ArrayAdapter<CharSequence> adapterlich = ArrayAdapter.createFromResource(this, R.array.category_spinner_lich, android.R.layout.simple_spinner_item);
        adapterlich.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category_spinner_lich.setAdapter(adapterlich);

        category_spinner_bus = findViewById(R.id.category_spinner_bus);
        ArrayAdapter<CharSequence> adapterbus = ArrayAdapter.createFromResource(this, R.array.category_spinner_bus, android.R.layout.simple_spinner_item);
        adapterbus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category_spinner_bus.setAdapter(adapterbus);

        category_spinner_gruz = findViewById(R.id.category_spinner_gruz);
        ArrayAdapter<CharSequence> adaptergruz = ArrayAdapter.createFromResource(this, R.array.category_spinner_gruz, android.R.layout.simple_spinner_item);
        adaptergruz.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category_spinner_gruz.setAdapter(adaptergruz);

        category_spinner_selxoz = findViewById(R.id.category_spinner_selxoz);
        ArrayAdapter<CharSequence> adapterselxoz = ArrayAdapter.createFromResource(this, R.array.category_spinner_selxoz, android.R.layout.simple_spinner_item);
        adapterselxoz.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category_spinner_selxoz.setAdapter(adapterselxoz);

        category_spinner_stroi = findViewById(R.id.category_spinner_stroi);
        ArrayAdapter<CharSequence> adapterstroi = ArrayAdapter.createFromResource(this, R.array.category_spinner_stroi, android.R.layout.simple_spinner_item);
        adapterstroi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category_spinner_stroi.setAdapter(adapterstroi);


        category_spinner_auto = findViewById(R.id.category_spinner_auto);
        ArrayAdapter<CharSequence> adapterauto = ArrayAdapter.createFromResource(this, R.array.category_spinner_auto, android.R.layout.simple_spinner_item);
        adapterauto.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category_spinner_auto.setAdapter(adapterauto);

        category_spinner_velosiped = findViewById(R.id.category_spinner_velosiped);
        ArrayAdapter<CharSequence> adaptervelosiped = ArrayAdapter.createFromResource(this, R.array.category_spinner_velosiped, android.R.layout.simple_spinner_item);
        adaptervelosiped.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category_spinner_velosiped.setAdapter(adaptervelosiped);

        category_spinner_moped = findViewById(R.id.category_spinner_moped);
        ArrayAdapter<CharSequence> adaptermoped = ArrayAdapter.createFromResource(this, R.array.category_spinner_moped, android.R.layout.simple_spinner_item);
        adaptermoped.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category_spinner_moped.setAdapter(adaptermoped);


        category_spinner_moto = findViewById(R.id.category_spinner_moto);
        ArrayAdapter<CharSequence> adaptermoto = ArrayAdapter.createFromResource(this, R.array.category_spinner_moto, android.R.layout.simple_spinner_item);
        adaptermoto.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category_spinner_moto.setAdapter(adaptermoto);

    }

    private void category(){
        categoryRent = category_spinner1_value;
        categoryType = category_spinner2_value;
        categoryTypeofMachine = category_spinner_comerch_value + category_spinner_lich_value;
        categorySubTypeofMachine = category_spinner_bus_value+ category_spinner_bus_value + category_spinner_selxoz_value + category_spinner_stroi_value+ category_spinner_auto_value + category_spinner_velosiped_value + category_spinner_moped_value + category_spinner_moto_value;



    }


    private void setupSelectedImagesRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.selected_images_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        // Create an adapter for the RecyclerView
        SelectedImagesAdapter adapter = new SelectedImagesAdapter(selectedImages);
        recyclerView.setAdapter(adapter);
    }


}