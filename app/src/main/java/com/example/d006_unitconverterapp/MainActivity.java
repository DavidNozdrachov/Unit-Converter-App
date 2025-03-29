package com.example.d006_unitconverterapp;

import android.os.Bundle;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private EditText valueEditText;
    private Spinner conversionsSpinner;
    private Button conversionButton;
    private TextView resultTextView;

    private String[] conversionOptions = {
            "Choose an option",
            "Centimeters -> Meters",
            "Meters -> Kilometers",
            "Celsius -> Fahrenheit",
            "Fahrenheit -> Celsius",
            "Grams -> Kilograms",
    };
    private ArrayAdapter<String> conversionsAdapter;
    private String selectedOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        valueEditText = (EditText) findViewById(R.id.valueEditText);
        conversionsSpinner = (Spinner) findViewById(R.id.conversionsSpinner);
        conversionButton = (Button) findViewById(R.id.conversionButton);
        resultTextView = (TextView) findViewById(R.id.resultTextView);

        valueEditText.setKeyListener(DigitsKeyListener.getInstance(true, true));

        conversionsAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, conversionOptions);
        conversionsSpinner.setAdapter(conversionsAdapter);

        conversionsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedOption = conversionOptions[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedOption = "Choose an option";
            }
        });

        conversionButton.setOnClickListener(v -> {
            handleClick();
        });
    }

    private void handleClick() {
        String stringValue = valueEditText.getText().toString().trim();

        if (stringValue.isEmpty()) {
            Toast.makeText(MainActivity.this, "Value cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        double doubleValue = Double.parseDouble(stringValue);
        double resultValue;
        String resultUnit;

        switch (selectedOption) {
            case "Centimeters -> Meters":
                resultValue = doubleValue / 100.0;
                resultUnit = "m";
                break;

            case "Meters -> Kilometers":
                resultValue = doubleValue / 1000.0;
                resultUnit = "km";
                break;

            case "Celsius -> Fahrenheit":
                resultValue = (doubleValue * (9.0 / 5.0)) + 32.0;
                resultUnit = "°F";
                break;

            case "Fahrenheit -> Celsius":
                resultValue = (doubleValue - 32.0) * (5.0 / 9.0);
                resultUnit = "°C";
                break;

            case "Grams -> Kilograms":
                resultValue = doubleValue / 1000.0;
                resultUnit = "kg";
                break;

            default:
                resultValue = 0.0;
                resultUnit = "";
        }

        if (resultUnit.isEmpty()) {
            Toast.makeText(MainActivity.this, "Choose a valid option", Toast.LENGTH_SHORT).show();
            return;
        }

        resultTextView.setText(String.format("%.5f", resultValue) + resultUnit);
    }
}