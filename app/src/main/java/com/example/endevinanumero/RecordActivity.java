package com.example.endevinanumero;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class RecordActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        List<String> users = new LinkedList<String>();
        List<String> usersTries = new LinkedList<String>();
        users.add(getIntent().getStringExtra("user"));
        usersTries.add(Integer.toString(getIntent().getIntExtra("intentos",1)));

        TableRow.LayoutParams params1=new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT,1.0f);
        TableRow.LayoutParams params2=new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        TableLayout tbl=(TableLayout) findViewById(R.id.tl);

        //Creating new tablerows and textviews
        TableRow row=new TableRow(this);
        TextView txt1=new TextView(this);
        TextView txt2=new TextView(this);

        //setting the text
        txt1.setText(users.get(0));
        txt2.setText(usersTries.get(0));
        txt1.setLayoutParams(params1);
        txt2.setLayoutParams(params1);

        //the textviews have to be added to the row created
        row.addView(txt1);
        row.addView(txt2);
        row.setLayoutParams(params2);
        tbl.addView(row);

    }
}