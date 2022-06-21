package com.example.spendingtracking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.realm.Sort;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MaterialButton addSpendingItemBtn = findViewById(R.id.addNewItemBtn);
        addSpendingItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddSpendingItemsActivity.class));
            }
        });

        Realm.init(getApplicationContext());
        Realm realm = Realm.getDefaultInstance();

        // sorted the List of spending items by the createdTime
        RealmResults<SpendingItem> spendingItemsList = realm.where(SpendingItem.class).findAllSorted("createdTime", Sort.DESCENDING);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MyAdapter myAdapter = new MyAdapter(getApplicationContext(), spendingItemsList);
        recyclerView.setAdapter(myAdapter);

        spendingItemsList.addChangeListener(new RealmChangeListener<RealmResults<SpendingItem>>() {
            @Override
            public void onChange(RealmResults<SpendingItem> spendingItems) {
                myAdapter.notifyDataSetChanged();
            }
        });
    }
}