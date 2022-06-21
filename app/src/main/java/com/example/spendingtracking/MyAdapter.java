package com.example.spendingtracking;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;

import io.realm.Realm;
import io.realm.RealmResults;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView titleOutput, placeOutput, amountOutput, descriptionOutput, timeOutput;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titleOutput = itemView.findViewById(R.id.titleOutput);
            placeOutput = itemView.findViewById(R.id.placeOutput);
            amountOutput = itemView.findViewById(R.id.amountOutput);
            descriptionOutput = itemView.findViewById(R.id.descriptionOutput);
            timeOutput = itemView.findViewById(R.id.timeOutput);
        }
    }

    Context context;
    RealmResults<SpendingItem> spendingItemsList;

    public MyAdapter(Context context, RealmResults<SpendingItem> spendingItemsList) {
        this.context = context;
        this.spendingItemsList = spendingItemsList;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {
        SpendingItem spendingItem = spendingItemsList.get(position);

        holder.titleOutput.setText(spendingItem.getTitle());
        holder.placeOutput.setText(spendingItem.getPlace());
        holder.amountOutput.setText(spendingItem.getAmount());
        holder.descriptionOutput.setText(spendingItem.getDescription());

        String formattedTime = DateFormat.getTimeInstance().format(spendingItem.createdTime);
        holder.timeOutput.setText(formattedTime);


        // here the user will long click to choose to delete the spending item
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                PopupMenu menu = new PopupMenu(context, view);
                menu.getMenu().add("DELETE");
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (menuItem.getTitle().equals("DELETE")) {
                            Realm realm = Realm.getDefaultInstance();

                            // delete the spending item from the list
                            realm.beginTransaction();
                            spendingItem.deleteFromRealm();
                            realm.commitTransaction();
                            Toast.makeText(context, "Note deleted", Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    }
                });

                menu.show();
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return spendingItemsList.size();
    }
}
