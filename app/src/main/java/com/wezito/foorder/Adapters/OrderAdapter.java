package com.wezito.foorder.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wezito.foorder.Model.Order;
import com.wezito.foorder.R;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    ArrayList<Order> orderArrayList;

    public OrderAdapter(ArrayList<Order> orderArrayList){
        this.orderArrayList = orderArrayList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_shopping_item, null, false);

        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        String quantity = String.valueOf(orderArrayList.get(position).getQuantity());
        String price = String.valueOf(orderArrayList.get(position).getPrice());

        holder.quantity_food.setText(quantity);
        holder.name_food.setText(orderArrayList.get(position).getName());
        holder.price_food.setText(price);
    }

    @Override
    public int getItemCount() {
        return orderArrayList.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {

        private TextView quantity_food, name_food, price_food;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);

            quantity_food = itemView.findViewById(R.id.quantity_food);
            name_food = itemView.findViewById(R.id.name_food);
            price_food = itemView.findViewById(R.id.price_food);
        }
    }
}
