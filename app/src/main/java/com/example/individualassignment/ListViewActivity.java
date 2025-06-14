package com.example.individualassignment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ListViewActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<String> billSummaries;
    ArrayList<Integer> billIds;
    BillDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        listView = findViewById(R.id.listView);
        billSummaries = new ArrayList<>();
        billIds = new ArrayList<>();
        db = new BillDatabase(this);

        Cursor cursor = db.getAllBills();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String month = cursor.getString(1);
            double finalCost = cursor.getDouble(5);

            billIds.add(id);
            billSummaries.add(month + " - RM " + String.format("%.2f", finalCost));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, billSummaries);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            int selectedId = billIds.get(position);
            Intent intent = new Intent(ListViewActivity.this, DetailActivity.class);
            intent.putExtra("billId", selectedId);
            startActivity(intent);
        });
    }
}