package com.example.matrix.matrix;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {

    NumberPicker picker1, picker2;
    Button inputBtn;
    TextView matrixConfig;

    int columns = 1, rows = 1;

    private int cost_limit = 40;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        fab.setVisibility(View.GONE);

        picker1 = (NumberPicker) findViewById(R.id.numberPicker1);
        picker1.setMinValue(1);
        picker1.setMaxValue(10);
        picker1.setWrapSelectorWheel(false);
        picker1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                columns = newVal;
            }
        });

        picker2 = (NumberPicker) findViewById(R.id.numberPicker2);
        picker2.setMinValue(1);
        picker2.setMaxValue(10);
        picker2.setWrapSelectorWheel(false);
        picker2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                rows = newVal;
            }
        });

        inputBtn = (Button) findViewById(R.id.input_btn);
        inputBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                matrixConfig.setText("Rows : " + columns + " Cols : " + rows);
                fillTable(columns, rows, (TableLayout) findViewById(R.id.tableLayout1));
            }
        });

        matrixConfig = (TextView) findViewById(R.id.matrix_config);
        findViewById(R.id.tableLayout1).setVisibility(View.GONE);
        findViewById(R.id.cost_txt).setVisibility(View.GONE);
        findViewById(R.id.path_txt).setVisibility(View.GONE);

        findViewById(R.id.calculate_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPath();
            }
        });
    }

    private void fillTable(final int n, int m, TableLayout table) {
        findViewById(R.id.tableLayout1).setVisibility(View.VISIBLE);
        findViewById(R.id.cost_txt).setVisibility(View.VISIBLE);
        findViewById(R.id.path_txt).setVisibility(View.VISIBLE);

        int arr[][] = new int[n][m];
        table.removeAllViews();
        for (int i = 0; i < n; i++) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

            for (int j = 0; j < m; j++) {
                EditText edit = new EditText(this);
                edit.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
                edit.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                edit.setText(arr[i][j] + "");
                row.addView(edit);
            }
            table.addView(row);
        }
    }

    ArrayList<ColumnEntity> entities = new ArrayList<>();

    private void getPath() {
        entities.clear();
        TableLayout tb = (TableLayout) findViewById(R.id.tableLayout1);
        for (int i = 0; i < tb.getChildCount(); i++) {
            View child = tb.getChildAt(i);
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
            step_cost = 0;
        }

        if (total_cost < cost_limit) {
            ((TextView) findViewById(R.id.cost_txt)).setText(total_cost + "");
            ((TextView) findViewById(R.id.path_txt)).setText(path.toString() + "");
        } else {
            setInvalidData();
        }
    }

    private void setInvalidData() {
        ((TextView) findViewById(R.id.cost_txt)).setText("Invalid Data");
        ((TextView) findViewById(R.id.path_txt)).setText("Invalid Data");
    }
}
