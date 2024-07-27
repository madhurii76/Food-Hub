package com.example.foodhub.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodhub.R;
import com.example.foodhub.activities.DetailedActivity;
import com.example.foodhub.models.ViewAllModel;

import java.util.List;

public class ViewAllAdapter extends RecyclerView.Adapter<ViewAllAdapter.ViewHolder> {
    private final Context context;
    private final List<ViewAllModel> viewAllModelList;

    public ViewAllAdapter(Context context, List<ViewAllModel> viewAllModelList) {
        this.context = context;
        this.viewAllModelList = viewAllModelList;
    }

    @NonNull
    @Override
    public ViewAllAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_all_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewAllAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ViewAllModel model = viewAllModelList.get(position);

        Glide.with(context).load(model.getImg_url()).into(holder.imageView);
        holder.name.setText(model.getName());
        holder.description.setText(model.getDescription());
        holder.rating.setText(model.getRating());
//
//        String priceText;
//
//        // Adjust price text based on item type
//        if(model.getName().equals("egg")) {
//            priceText = model.getPrice() + "/dozen";
//        } else if(model.getName().equals("milk")) {
//            priceText = model.getPrice() + "/litre";
//        } else {
//            priceText = model.getPrice() + "/kg"; // Default for other products
//        }
        String priceText = model.getPrice() + "/kg";

        // Set the price text
        if (model.getType().equals("egg")) {
            priceText = model.getPrice() + "/dozen";
        } else if (model.getType().equals("milk")) {
            priceText = model.getPrice() + "/litre";
        }

        holder.price.setText(priceText);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailedActivity.class);
            intent.putExtra("detail",viewAllModelList.get(position));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return viewAllModelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name, description, price, rating;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.view_img);
            name = itemView.findViewById(R.id.view_name);
            description = itemView.findViewById(R.id.view_description);
            price = itemView.findViewById(R.id.view_price);
            rating = itemView.findViewById(R.id.view_rating);
        }
    }
}
