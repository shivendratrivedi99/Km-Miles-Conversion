package com.singularity.km_miles_conversion;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    Button convertMilesToKm;
    Button convertKmToMiles;
    ScrollView scrollView;
    EditText milesField;
    EditText kmField;
    DecimalFormat decimalFormat = new DecimalFormat("##.##");
    Switch autoCalculate;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scrollView = findViewById(R.id.scroll_view);
        convertMilesToKm = findViewById(R.id.convert_miles_to_km);
        convertKmToMiles = findViewById(R.id.convert_km_to_miles);
        milesField = findViewById(R.id.miles_field);
        kmField = findViewById(R.id.km_field);
        autoCalculate = findViewById(R.id.auto_calculate);
        setOnClickOnButtons();


        autoCalculate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                count++;
                if (count % 2 != 0) {
                    convertKmToMiles.setVisibility(View.INVISIBLE);
                    convertMilesToKm.setVisibility(View.INVISIBLE);

                    //Toast to display help
                    Toast.makeText(getApplicationContext(), "Just type in Miles or Kilometres and see the result", Toast.LENGTH_LONG).show();

                } else {
                    convertKmToMiles.setVisibility(View.VISIBLE);
                    convertMilesToKm.setVisibility(View.VISIBLE);
                }
                milesField.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (autoCalculate.isChecked() && milesField.hasFocus()) {
                            try {
                                kmField.setText(decimalFormat.format(1.609 * Double.parseDouble(milesField.getText().toString())));
                            } catch (Exception e) {
                                kmField.setText(decimalFormat.format(0));
                            }
                        }

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });

                kmField.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (autoCalculate.isChecked() && kmField.hasFocus()) {
                            try {
                                milesField.setText(decimalFormat.format(Double.parseDouble(kmField.getText().toString()) / 1.609));
                            } catch (Exception e) {
                                milesField.setText(decimalFormat.format(0));
                            }
                        }

                        if (kmField.hasFocus() && !autoCalculate.isChecked()) {
                            scrollView.post(new Runnable() {
                                @Override
                                public void run() {
                                    scrollView.fullScroll(1);
                                }
                            });
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });

            }
        });
    }

    private void setOnClickOnButtons() {
        convertMilesToKm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double km;
                try {
                    km = 1.609 * Double.parseDouble(milesField.getText().toString());
                } catch (Exception e) {
                    km = 0;
                }
                kmField.setText(decimalFormat.format(km));
            }
        });

        convertKmToMiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double miles;
                try {
                    miles = Double.parseDouble(kmField.getText().toString()) / 1.609;
                } catch (Exception e) {
                    miles = 0;
                }
                milesField.setText(decimalFormat.format(miles));
            }
        });
    }
}
