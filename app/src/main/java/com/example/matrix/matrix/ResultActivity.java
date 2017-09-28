package com.example.matrix.matrix;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class ResultActivity extends AppCompatActivity {
    private TableLayout tableLayout;
    private TextView matrixConfig;
    private int columns = 1;
    private int rows = 1;
    private int cost_limit = 40;
    private ArrayList<ColumnEntity> entities;
    private Random randomNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (getIntent().getExtras() != null) {
            columns = getIntent().getExtras().getInt("no_of_columns");
            rows = getIntent().getExtras().getInt("no_of_rows");
        }
        matrixConfig = (TextView) findViewById(R.id.matrix_config);
        tableLayout = (TableLayout) findViewById(R.id.tableLayout);
        tableLayout.setVisibility(View.GONE);
        findViewById(R.id.cost_txt).setVisibility(View.GONE);
        findViewById(R.id.path_txt).setVisibility(View.GONE);
        entities = new ArrayList<>();
        randomNumber = new Random();
        matrixConfig.setText("Rows : " + columns + " Cols : " + rows);
        fillTable(columns, rows, tableLayout);

        findViewById(R.id.find_path_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPath();
            }
        });
    }

    private void fillTable(final int n, int m, TableLayout table) {
        tableLayout.setVisibility(View.VISIBLE);
        findViewById(R.id.cost_txt).setVisibility(View.VISIBLE);
        findViewById(R.id.path_txt).setVisibility(View.VISIBLE);
        table.removeAllViews();
        for (int i = 0; i < n; i++) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            for (int j = 0; j < m; j++) {
                EditText edit = new EditText(this);
                edit.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
                edit.setPadding(24, 16, 24, 16);
                edit.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                edit.setText(String.valueOf(randomNumber.nextInt(10 - 1)+1));
                row.addView(edit);
            }
            table.addView(row);
        }
    }


    private void getPath() {
        entities.clear();
        for (int i = 0; i < tableLayout.getChildCount(); i++) {
            View child = tableLayout.getChildAt(i);
            if (child instanceof TableRow) {
                TableRow row = (TableRow) child;
                for (int x = 0; x < row.getChildCount(); x++) {
                    if (entities.size() - 1 <= x) {
                        entities.add(new ColumnEntity());
                    }

                    //entires -> zero
                    //5
                    //1 x=1 entities[0] [1]

                    EditText view = (EditText) row.getChildAt(x);
                    entities.get(x).column_weights.add(view.getText().toString());

                }
            }
        }

        int total_cost = 0;
        ArrayList<String> path = new ArrayList<>();
        for (int i = 0; i < entities.size(); i++) {
            int step_cost = 0;
            for (int j = 0; j < entities.get(i).column_weights.size(); j++) {
                try {
                    if (j == 0)
                        step_cost = Integer.parseInt(entities.get(i).column_weights.get(j));
                    if (step_cost > Integer.parseInt(entities.get(i).column_weights.get(j))) {
                        step_cost = Integer.parseInt(entities.get(i).column_weights.get(j));
                    }
                } catch (Exception e) {
                    setInvalidData();
                    break;
                }
            }
            total_cost += step_cost;
            if(step_cost>0)
            path.add(String.valueOf(step_cost));
            step_cost = 0;
        }

        if (total_cost < cost_limit) {
            ((TextView) findViewById(R.id.cost_txt)).setText("Cost : " + total_cost + "");
            ((TextView) findViewById(R.id.path_txt)).setText("Path : " + path.toString() + "");
        } else {
            setInvalidData();
        }
    }

    private void setInvalidData() {
        ((TextView) findViewById(R.id.cost_txt)).setText("Invalid Data");
        ((TextView) findViewById(R.id.path_txt)).setText("Invalid Data");
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
