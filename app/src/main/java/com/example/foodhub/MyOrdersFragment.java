package com.example.foodhub;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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

public class MyOrdersFragment extends Fragment {

    private static final String TAG = "MyOrderFragment";
    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private RecyclerView recyclerView;
    private MyOrderAdapter orderAdapter;
    private List<MyOrderModel> orderList;
    private ProgressBar progressBar;
    private TextView noOrdersText;
    private Button buttonLoadOrders;
    private ImageView imageView;

    public MyOrdersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_orders, container, false);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        progressBar = view.findViewById(R.id.progressbar);
        recyclerView = view.findViewById(R.id.recyclerview);
        noOrdersText = view.findViewById(R.id.no_orders_text);
        buttonLoadOrders = view.findViewById(R.id.button_load_orders);
        imageView = view.findViewById(R.id.imageView3);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        orderList = new ArrayList<>();
        orderAdapter = new MyOrderAdapter(getContext(), orderList);
        recyclerView.setAdapter(orderAdapter);

        buttonLoadOrders.setOnClickListener(v -> fetchOrders());

        return view;
    }

    private void fetchOrders() {
        progressBar.setVisibility(View.VISIBLE);
        noOrdersText.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        imageView.setVisibility(View.GONE);

        String userId = auth.getUid();
        if (userId == null) {
            progressBar.setVisibility(View.GONE);
            noOrdersText.setVisibility(View.VISIBLE);
            Toast.makeText(getContext(), "User not authenticated", Toast.LENGTH_SHORT).show();
            return;
        }

        db.collection("CurrentUser").document(userId)
                .collection("MyOrders").get().addOnCompleteListener(task -> {
                    progressBar.setVisibility(View.GONE);
                    if (task.isSuccessful() && !task.getResult().isEmpty()) {
                        orderList.clear();
                        for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                            MyOrderModel order = documentSnapshot.toObject(MyOrderModel.class);
                            if (order != null) {
                                orderList.add(order);
                                Log.d(TAG, "Order fetched: " + order.getProductName());
                            }
                        }
                        if (orderList.isEmpty()) {
                            noOrdersText.setVisibility(View.VISIBLE);
                        } else {
                            recyclerView.setVisibility(View.VISIBLE);
                        }
                        orderAdapter.notifyDataSetChanged();
                    } else {
                        noOrdersText.setVisibility(View.VISIBLE);
                        imageView.setVisibility(View.VISIBLE); // Show imageView when no orders found
                        Toast.makeText(getContext(), "No orders found", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(e -> {
                    progressBar.setVisibility(View.GONE);
                    noOrdersText.setVisibility(View.VISIBLE);
                    imageView.setVisibility(View.VISIBLE); // Show imageView on failure
                    Log.e(TAG, "Failed to fetch orders", e);
                    Toast.makeText(getContext(), "Failed to fetch orders", Toast.LENGTH_SHORT).show();
                });
    }
}
