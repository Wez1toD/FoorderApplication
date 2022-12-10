package com.wezito.foorder.View;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wezito.foorder.Interface.ItemClickListener;
import com.wezito.foorder.R;

public class FoodView extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView foodName;
    public ImageView foodImage;

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public FoodView(@NonNull View itemView) {
        super(itemView);
        foodName = (TextView) itemView.findViewById(R.id.foodName);
        foodImage = (ImageView) itemView.findViewById(R.id.foodImage);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition(), false);
    }
}
