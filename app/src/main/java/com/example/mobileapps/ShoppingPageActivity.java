package com.example.mobileapps;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.*;

import java.util.ArrayList;

public class ShoppingPageActivity extends AppCompatActivity {

    EditText editItem;
    Button btnAdd;
    RecyclerView recyclerView;
    DBHelper dbHelper;
    ItemAdapter adapter;
    ArrayList<ShoppingItemActivity> itemList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_item);

        editItem = findViewById(R.id.editItem);
        btnAdd = findViewById(R.id.btnAdd);
        recyclerView = findViewById(R.id.recyclerView);

        dbHelper = new DBHelper(this);
        itemList = dbHelper.getAllItems();

        adapter = new ItemAdapter(itemList, dbHelper);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        btnAdd.setOnClickListener(v -> {
            String name = editItem.getText().toString().trim();
            if (!name.isEmpty()) {
                dbHelper.insertItem(name);
                itemList.clear();
                itemList.addAll(dbHelper.getAllItems());
                adapter.notifyDataSetChanged();
                editItem.setText("");
            }
        });

        // Swipe to delete
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) { return false; }

            @Override public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int pos = viewHolder.getAdapterPosition();
                dbHelper.deleteItem(itemList.get(pos).getId());
                itemList.remove(pos);
                adapter.notifyItemRemoved(pos);
            }
        }).attachToRecyclerView(recyclerView);
    }
}
