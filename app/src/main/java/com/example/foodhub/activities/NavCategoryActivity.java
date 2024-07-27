package com.example.foodhub.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodhub.Adapter.NavCategoryDetailedAdapter;
import com.example.foodhub.R;
import com.example.foodhub.models.NavCategoryDetailedModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class NavCategoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<NavCategoryDetailedModel> list;
    private NavCategoryDetailedAdapter adapter;
    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private ProgressBar progressBar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_nav_category);

        // Handle window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);

        String type = getIntent().getStringExtra("type");
        recyclerView = findViewById(R.id.nav_cat_det_rec);
        recyclerView.setVisibility(View.GONE);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        adapter = new NavCategoryDetailedAdapter(this, list);
        recyclerView.setAdapter(adapter);



        // DRINKS
        if (type != null && type.equalsIgnoreCase("drink")) {
            db.collection("NavCategoryDetailed")
                    .whereEqualTo("type", "drink")
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful() && task.getResult() != null) {
                            for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                                NavCategoryDetailedModel model = documentSnapshot.toObject(NavCategoryDetailedModel.class);
                                if (model != null) {
                                    list.add(model);
                                }
                            }
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(NavCategoryActivity.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
                        }
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    });
        }
//        else {
//            progressBar.setVisibility(View.GONE);
//            Toast.makeText(this, "No category type specified", Toast.LENGTH_SHORT).show();
//        }


        // FRUITS
        if (type != null && type.equalsIgnoreCase("fruit")) {
            db.collection("NavCategoryDetailed")
                    .whereEqualTo("type", "fruit")
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful() && task.getResult() != null) {
                            for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                                NavCategoryDetailedModel model = documentSnapshot.toObject(NavCategoryDetailedModel.class);
                                if (model != null) {
                                    list.add(model);
                                }
                            }
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(NavCategoryActivity.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
                        }
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    });
        }

        // MEAT & SEAFOODS

        if (type != null && type.equalsIgnoreCase("meat")) {
            db.collection("NavCategoryDetailed")
                    .whereEqualTo("type", "meat")
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful() && task.getResult() != null) {
                            for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                                NavCategoryDetailedModel model = documentSnapshot.toObject(NavCategoryDetailedModel.class);
                                if (model != null) {
                                    list.add(model);
                                }
                            }
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(NavCategoryActivity.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
                        }
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    });
        }

        //Vegetable

        if (type != null && type.equalsIgnoreCase("Vegetable")) {
            db.collection("NavCategoryDetailed")
                    .whereEqualTo("type", "Vegetable")
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful() && task.getResult() != null) {
                            for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                                NavCategoryDetailedModel model = documentSnapshot.toObject(NavCategoryDetailedModel.class);
                                if (model != null) {
                                    list.add(model);
                                }
                            }
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(NavCategoryActivity.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
                        }
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    });
        }

        // SWEETS

        if (type != null && type.equalsIgnoreCase("sweet")) {
            db.collection("NavCategoryDetailed")
                    .whereEqualTo("type", "sweet")
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful() && task.getResult() != null) {
                            for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                                NavCategoryDetailedModel model = documentSnapshot.toObject(NavCategoryDetailedModel.class);
                                if (model != null) {
                                    list.add(model);
                                }
                            }
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(NavCategoryActivity.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
                        }
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    });
        }


        //BAKERY EGGS AND BREAD

        if (type != null && type.equalsIgnoreCase("bakery")) {
            db.collection("NavCategoryDetailed")
                    .whereEqualTo("type", "bakery")
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful() && task.getResult() != null) {
                            for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                                NavCategoryDetailedModel model = documentSnapshot.toObject(NavCategoryDetailedModel.class);
                                if (model != null) {
                                    list.add(model);
                                }
                            }
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(NavCategoryActivity.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
                        }
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    });
        }

        //CEREALS & BREAKFAST

        if (type != null && type.equalsIgnoreCase("breakfast")) {
            db.collection("NavCategoryDetailed")
                    .whereEqualTo("type", "breakfast")
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful() && task.getResult() != null) {
                            for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                                NavCategoryDetailedModel model = documentSnapshot.toObject(NavCategoryDetailedModel.class);
                                if (model != null) {
                                    list.add(model);
                                }
                            }
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(NavCategoryActivity.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
                        }
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    });
        }
    }
}
