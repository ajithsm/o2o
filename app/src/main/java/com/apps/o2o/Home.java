package com.apps.o2o;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import o2o.R;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void goToScanningActivity(View V){
        String sSelectedButton = (String) V.getTag();
        Intent iScanner = new Intent(Home.this, Scanning.class);
        iScanner.putExtra("store", sSelectedButton);
        startActivity(iScanner);
    }
}