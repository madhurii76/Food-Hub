package com.example.foodhub.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodhub.Adapter.MyOrderAdapter;
import com.example.foodhub.R;
import com.example.foodhub.models.MyOrderModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class OrderPlacedActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private RecyclerView recyclerView;
    private MyOrderAdapter orderAdapter;
    private List<MyOrderModel> orderList;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_placed);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        progressBar = findViewById(R.id.progressbar);
        recyclerView = findViewById(R.id.recyclerview);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        orderList = new ArrayList<>();
        orderAdapter = new MyOrderAdapter(this, orderList);
        recyclerView.setAdapter(orderAdapter);

      fetchOrders();
    }

    private void fetchOrders() {
        progressBar.setVisibility(View.VISIBLE);
        db.collection("CurrentUser").document(auth.getUid())
                .collection("MyOrders").get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                            MyOrderModel order = documentSnapshot.toObject(MyOrderModel.class);
                            orderList.add(order);
                        }
                        orderAdapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);


                    } else {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(OrderPlacedActivity.this, "Failed to fetch orders", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
