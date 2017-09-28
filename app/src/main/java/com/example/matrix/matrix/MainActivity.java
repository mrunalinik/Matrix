package com.example.matrix.matrix;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;

public class MainActivity extends AppCompatActivity {
    private AppCompatSpinner no_of_rows_spinner, no_of_columns_spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        no_of_rows_spinner = (AppCompatSpinner) findViewById(R.id.no_of_rows_spinner);
        no_of_columns_spinner = (AppCompatSpinner) findViewById(R.id.no_of_columns_spinner);
        ArrayAdapter<CharSequence> numbersSpinnerAadapter = ArrayAdapter.createFromResource(this,
                R.array.numbers_array, android.R.layout.simple_spinner_item);
        numbersSpinnerAadapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        no_of_rows_spinner.setAdapter(numbersSpinnerAadapter);
        no_of_columns_spinner.setAdapter(numbersSpinnerAadapter);

        findViewById(R.id.continue_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (no_of_columns_spinner.getSelectedItemPosition() != 0
                        && no_of_rows_spinner.getSelectedItemPosition() != 0) {
                    Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                    intent.putExtra("no_of_columns", Integer.parseInt(no_of_columns_spinner.getSelectedItem().toString()));
                    intent.putExtra("no_of_rows", Integer.parseInt(no_of_rows_spinner.getSelectedItem().toString()));
                    startActivity(intent);
                } else {
                    if(no_of_columns_spinner.getSelectedItemPosition() == 0&& no_of_rows_spinner.getSelectedItemPosition() == 0){
                        CommonUtility.showErrorAlert(MainActivity.this, getResources().getString(R.string.error_alert_dialog_rows_columns));
                    }
                    else if (no_of_columns_spinner.getSelectedItemPosition() == 0)
                        CommonUtility.showErrorAlert(MainActivity.this, getResources().getString(R.string.error_alert_dialog_columns));
                    else if (no_of_rows_spinner.getSelectedItemPosition()==0){
                        CommonUtility.showErrorAlert(MainActivity.this, getResources().getString(R.string.error_alert_dialog_rows));
                    }

                }
            }
        });
    }
}
