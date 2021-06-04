package com.example.rc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import static android.content.ContentValues.TAG;

public class Summary extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        TextView details = findViewById(R.id.details_tv);
        Intent i = getIntent();
        String i_data = i.getStringExtra("response");
//        Log.d("res", i_data);
        details.setText(i_data);
    }
}