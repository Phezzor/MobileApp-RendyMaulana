package com.example.mobileapps;

import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private final ArrayList<ShoppingItemActivity> items;
    private final DBHelper dbHelper;

    public ItemAdapter(ArrayList<ShoppingItemActivity> items, DBHelper dbHelper) {
        this.items = items;
        this.dbHelper = dbHelper;
    }

    @NonNull
    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ViewHolder holder, int position) {
        ShoppingItemActivity item = items.get(position);
        holder.textItem.setText(item.getName());
        holder.checkBox.setChecked(item.isBought());

        holder.checkBox.setOnCheckedChangeListener(null); // prevent unwanted triggering
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            dbHelper.updateItemStatus(item.getId(), isChecked);
            item.setBought(isChecked);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        TextView textItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkBox);
            textItem = itemView.findViewById(R.id.textItem);
        }
    }
}
