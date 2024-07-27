package com.example.foodhub.Adapter;

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
import com.example.foodhub.activities.NavCategoryActivity;
import com.example.foodhub.models.NavCategoryDetailedModel;
import com.example.foodhub.models.ViewAllModel;

import java.util.List;

public class NavCategoryDetailedAdapter extends RecyclerView.Adapter<NavCategoryDetailedAdapter.ViewHolder> {

    Context context;
    List<NavCategoryDetailedModel> navCategoryDetailedModelList;

    public NavCategoryDetailedAdapter(Context context, List<NavCategoryDetailedModel> navCategoryDetailedModelList) {
        this.context = context;
        this.navCategoryDetailedModelList = navCategoryDetailedModelList;
    }


    @NonNull
    @Override
    public NavCategoryDetailedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.nav_category_detailed_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull NavCategoryDetailedAdapter.ViewHolder holder, int position) {
     //   ViewAllModel model = viewAllModelList.get(position);
        Glide.with(context).load(navCategoryDetailedModelList.get(position).getImg_url()).into(holder.imageView);
        holder.name.setText(navCategoryDetailedModelList.get(position).getName());
    //   holder.price.setText(navCategoryDetailedModelList.get(position).getPrice());
       // holder.price.setText("Price :$ "+ navCategoryDetailedModelList.get(position).getPrice()+"/kg");

        String priceText = navCategoryDetailedModelList.get(position).getPrice() + "/kg";

        // Set the price text
        if (navCategoryDetailedModelList.get(position).getType().equals("bakery")) {
            priceText = navCategoryDetailedModelList.get(position).getPrice() + "/g";
        } else if (navCategoryDetailedModelList.get(position).getType().equals("drink")) {
            priceText = navCategoryDetailedModelList.get(position).getPrice() + "/litre";
        } else if(navCategoryDetailedModelList.get(position).getType().equals("breakfast")){
            priceText=navCategoryDetailedModelList.get(position).getPrice()+"/mg";
        }

        holder.price.setText(priceText);





    }

    @Override
    public int getItemCount() {
        return navCategoryDetailedModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView, addItem, removeItem;
        TextView name,price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.cat_nav_img);
            name=itemView.findViewById(R.id.nav_cat_name);
            price=itemView.findViewById(R.id.price);
            addItem=itemView.findViewById(R.id.add_item);
            removeItem=itemView.findViewById(R.id.remove_item);
        }
    }

}
