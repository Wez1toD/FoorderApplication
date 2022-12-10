package com.wezito.foorder.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;
import com.wezito.foorder.FoodDetailActivity;
import com.wezito.foorder.Model.Food;
import com.wezito.foorder.R;

public class FoodAdapter extends FirebaseRecyclerAdapter<Food, FoodAdapter.FoodViewHolder> {

    private Context context;
    public FoodAdapter(@NonNull FirebaseRecyclerOptions<Food> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull FoodViewHolder holder, int position, @NonNull Food model) {
        model.setFid("" + position);
        holder.foodName.setText(model.getName());
        String img_url = model.getImage();
        Picasso.get()
                .load(img_url)
                .into(holder.foodImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent foodDetail = new Intent(context, FoodDetailActivity.class);
                foodDetail.putExtra("foodId", model.getFid());
                context.startActivity(foodDetail);
            }
        });
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_foods_item, parent, false);
        return new FoodAdapter.FoodViewHolder(view);
    }

    class FoodViewHolder extends RecyclerView.ViewHolder {
        private TextView foodName;
        private ImageView foodImage;
        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            foodName = (TextView) itemView.findViewById(R.id.foodName);
            foodImage = (ImageView) itemView.findViewById(R.id.foodImage);
        }
    }
}