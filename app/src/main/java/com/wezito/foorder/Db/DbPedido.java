package com.wezito.foorder.Db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.wezito.foorder.Model.Order;

import java.util.ArrayList;

public class DbPedido extends DbHelper{

    Context context;

    public DbPedido(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long InsertOrder(String dishes, Integer quantity, Integer price){
        long id = 0;
        try{
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("dishes", dishes);
            values.put("quantity", quantity);
            values.put("price", price);

            id = db.insert(TABLE_ORDENES, null, values);
        }catch (Exception ex){
            ex.toString();
        }

        return id;
    }

    public ArrayList<Order> showOrders(){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Order> orderList = new ArrayList<>();
        Order order = null;
        Cursor cursorOrder = null;

        cursorOrder = db.rawQuery("SELECT * FROM " + TABLE_ORDENES, null);

        if(cursorOrder.moveToFirst()){
            do {
                order = new Order();
                order.setId(cursorOrder.getInt(0));
                order.setName(cursorOrder.getString(1));
                order.setQuantity(cursorOrder.getInt(2));
                order.setPrice(cursorOrder.getInt(3));

                orderList.add(order);
            }while(cursorOrder.moveToNext());
        }
        cursorOrder.close();

        return orderList;
    }
}
