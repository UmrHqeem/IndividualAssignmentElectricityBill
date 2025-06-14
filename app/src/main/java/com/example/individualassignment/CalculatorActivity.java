package com.example.individualassignment;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

public class CalculatorActivity extends AppCompatActivity {

    EditText editTextUnits;
    Spinner spinnerMonth, spinnerRebate;
    Button buttonCalculate;
    TextView textViewResult;
    BillDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        editTextUnits = findViewById(R.id.editTextUnits);
        spinnerMonth = findViewById(R.id.spinnerMonth);
        spinnerRebate = findViewById(R.id.spinnerRebate);
        buttonCalculate = findViewById(R.id.buttonCalculate);
        textViewResult = findViewById(R.id.textViewResult);

        db = new BillDatabase(this);

        // Setup month spinner
        String[] months = {"January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"};
        ArrayAdapter<String> monthAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, months);
        spinnerMonth.setAdapter(monthAdapter);

        // Setup rebate spinner
        String[] rebates = {"0%", "1%", "2%", "3%", "4%", "5%"};
        ArrayAdapter<String> rebateAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, rebates);
        spinnerRebate.setAdapter(rebateAdapter);

        Button buttonViewList = findViewById(R.id.buttonViewList);
        Button buttonAbout = findViewById(R.id.buttonAbout);

        buttonViewList.setOnClickListener(v -> {
            Intent intent = new Intent(CalculatorActivity.this, ListViewActivity.class);
            startActivity(intent);
        });

        buttonAbout.setOnClickListener(v -> {
            Intent intent = new Intent(CalculatorActivity.this, AboutActivity.class);
            startActivity(intent);
        });

        buttonCalculate.setOnClickListener(v -> {
            String unitStr = editTextUnits.getText().toString().trim();
            if (unitStr.isEmpty()) {
                Toast.makeText(this, "Please enter units used.", Toast.LENGTH_SHORT).show();
                return;
            }

            double units = Double.parseDouble(unitStr);
            String month = spinnerMonth.getSelectedItem().toString();
            int rebate = Integer.parseInt(spinnerRebate.getSelectedItem().toString().replace("%", ""));

            double total = calculateTotalCharges(units);
            double finalCost = total - (total * rebate / 100.0);

            textViewResult.setText(String.format("Final Cost: RM %.2f", finalCost));

            db.insertBill(month, units, rebate, total, finalCost);
        });
    }

    private double calculateTotalCharges(double units) {
        double total = 0;
        if (units <= 200) {
            total = units * 0.218;
        } else if (units <= 300) {
            total = (200 * 0.218) + ((units - 200) * 0.334);
        } else if (units <= 600) {
            total = (200 * 0.218) + (100 * 0.334) + ((units - 300) * 0.516);
        } else {
            total = (200 * 0.218) + (100 * 0.334) + (300 * 0.516) + ((units - 600) * 0.546);
        }
        return total;
    }
}