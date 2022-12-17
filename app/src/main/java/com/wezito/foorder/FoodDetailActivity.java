package com.wezito.foorder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.wezito.foorder.Db.DbHelper;
import com.wezito.foorder.Db.DbPedido;
import com.wezito.foorder.Model.Food;
import com.wezito.foorder.View.FoodView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FoodDetailActivity extends AppCompatActivity {

    private TextView btnInc, btnDec, tvQuantity, tvName, tvPrice, tvDescription;
    private ImageView imgFood;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private FloatingActionButton btnCart;
    private String fid = "";
    private String category = "";
    private FirebaseDatabase db;
    private DatabaseReference dbReference;
    private DateFormat dateFormat;
    private Date date;

    private String name_food;
    private int price_food;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        bindControls();
    }

    private void bindControls() {
        btnInc = findViewById(R.id.btnInc);
        btnDec = findViewById(R.id.btnDec);
        tvQuantity = findViewById(R.id.tvQuantity);
        imgFood = (ImageView) findViewById(R.id.imgFood);
        tvName = (TextView) findViewById(R.id.tvFoodName);
        tvPrice = (TextView) findViewById(R.id.tvFoodPrice);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbarDetail);
        tvDescription = (TextView) findViewById(R.id.tvDescription);

        dateFormat = new SimpleDateFormat("yyyyMMdd");
        date = new Date();
        String currentDate = dateFormat.format(date);
        db = FirebaseDatabase.getInstance();

        if (getIntent() != null) {
            fid = getIntent().getStringExtra("foodId");
            category = getIntent().getStringExtra("category");
        }
        if(!fid.isEmpty() && !category.isEmpty()) {
            switch (category) {
                case "mains":
                    dbReference = db.getReference("todaymenu").child(currentDate).child(category).child(fid);
                    break;
                case "carte_dishes":
                    dbReference = db.getReference("foods").child(category).child(fid);
                    break;
            }
            loadFood(dbReference);
        }

        btnDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeQuantity(false);
            }
        });

        btnInc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeQuantity(true);
            }
        });
    }

    private void loadFood(DatabaseReference dbReference) {
        dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Food dish = snapshot.getValue(Food.class);
                Picasso.get()
                    .load(dish.getImage())
                    .into(imgFood);
                collapsingToolbarLayout.setTitle(dish.getName());
                tvName.setText(dish.getName());
                tvDescription.setText(dish.getDescription());
                tvPrice.setText("" + dish.getPrice());

                name_food = dish.getName();
                price_food = dish.getPrice();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void changeQuantity(boolean x) {
        int quantity = Integer.parseInt(tvQuantity.getText().toString());
        if (x){
            quantity++;
            tvQuantity.setText(String.valueOf(quantity));
        } else if(quantity > 0){
            quantity--;
            tvQuantity.setText(String.valueOf(quantity));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = null;
        switch (item.getItemId()) {
            case R.id.mnHome:
                intent = new Intent(this, MainActivity.class);
                break;
            case R.id.mnCart:
                intent = new Intent(this, ShoppingCartActivity.class);
                break;
            case R.id.mnLogout:
                FirebaseAuth.getInstance().signOut();
                intent = new Intent(this, MainActivity.class);
                break;
        }
        if(item.getItemId() != R.id.mnHome){startActivity(intent);}
        return true;
    }

    public void AddToCart(View v){
        int quantity_dishes = Integer.parseInt(tvQuantity.getText().toString());
        if(quantity_dishes > 0){
            DbPedido dbPedido = new DbPedido(FoodDetailActivity.this);
            long id = dbPedido.InsertOrder(name_food, Integer.valueOf(tvQuantity.getText().toString()), (price_food * quantity_dishes));

            if(id > 0){
                Toast.makeText(FoodDetailActivity.this, "Se ha registrado su pedido con éxito", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
            }else{
                Toast.makeText(FoodDetailActivity.this, "Error al guardar su pedido", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(FoodDetailActivity.this, "Seleccione mínimo un plato", Toast.LENGTH_SHORT).show();
        }
    }
}