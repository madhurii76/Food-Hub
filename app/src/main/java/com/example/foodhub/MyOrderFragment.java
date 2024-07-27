package com.example.foodhub;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodhub.Adapter.MyOrderAdapter;
import com.example.foodhub.models.MyOrderModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class MyOrderFragment extends Fragment {

    FirebaseFirestore db;
    FirebaseAuth auth;
    RecyclerView recyclerView;
    MyOrderAdapter orderAdapter;
    List<MyOrderModel> orderList;
    ProgressBar progressBar;
    TextView noOrdersText;

    public MyOrderFragment() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_my_orders, container, false);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        progressBar = root.findViewById(R.id.progressbar);
        recyclerView = root.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        orderList = new ArrayList<>();
        orderAdapter = new MyOrderAdapter(getActivity(), orderList);
        recyclerView.setAdapter(orderAdapter);

        fetchOrders();

        return root;
    }

    private void fetchOrders() {
        progressBar.setVisibility(View.VISIBLE);
        db.collection("CurrentUser").document(auth.getUid())
                .collection("Orders").get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                            MyOrderModel order = documentSnapshot.toObject(MyOrderModel.class);
                            orderList.add(order);
                        }
                        orderAdapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);

                        if (orderList.isEmpty()) {
                            noOrdersText.setVisibility(View.VISIBLE);
                        } else {
                            noOrdersText.setVisibility(View.GONE);
                        }
                    } else {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "Failed to fetch orders", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
