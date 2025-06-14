package com.example.individualassignment;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    TextView detailText;
    BillDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        detailText = findViewById(R.id.detailText);
        db = new BillDatabase(this);

        int id = getIntent().getIntExtra("billId", -1);

        if (id != -1) {
            Cursor cursor = db.getBillById(id);
            if (cursor.moveToFirst()) {
                String month = cursor.getString(1);
                double units = cursor.getDouble(2);
                int rebate = cursor.getInt(3);
                double total = cursor.getDouble(4);
                double finalCost = cursor.getDouble(5);

                String result = "Month: " + month +
                        "\nUnits Used: " + units + " kWh" +
                        "\nRebate: " + rebate + "%" +
                        "\nTotal Charges: RM " + String.format("%.2f", total) +
                        "\nFinal Cost: RM " + String.format("%.2f", finalCost);

                detailText.setText(result);
            }
        }
    }
}