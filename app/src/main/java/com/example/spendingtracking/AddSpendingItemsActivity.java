package com.example.spendingtracking;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import io.realm.Realm;

public class AddSpendingItemsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_spending_item);

        EditText titleInput = findViewById(R.id.titleInput);
        EditText placeInput = findViewById(R.id.placeInput);
        EditText amountInput = findViewById(R.id.amountInput);
        EditText descriptionInput = findViewById(R.id.descriptionInput);
        MaterialButton saveBtn = findViewById(R.id.saveBtn);

        Realm.init(getApplicationContext());
        Realm realm = Realm.getDefaultInstance();

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = titleInput.getText().toString();
                String place = placeInput.getText().toString();
                int amount = Integer.parseInt(amountInput.getText().toString());
                String description = descriptionInput.getText().toString();
                long createdTime = System.currentTimeMillis();

                // realm object there is used to save the list of spending items for the user
                realm.beginTransaction();

                // create an object of the class SpendingItem
                SpendingItem spendingItem = realm.createObject(SpendingItem.class);
                spendingItem.setTitle(title);
                spendingItem.setPlace(place);
                spendingItem.setAmount(amount);
                spendingItem.setDescription(description);
                realm.commitTransaction();

                // create a Toast to notify the user
                Toast.makeText(getApplicationContext(), "Spending item saved", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
