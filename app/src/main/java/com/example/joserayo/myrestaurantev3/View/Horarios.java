package com.example.joserayo.myrestaurantev3.View;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.joserayo.myrestaurantev3.R;

public class Horarios extends AppCompatActivity {
RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horarios);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView=(RecyclerView)findViewById(R.id.horariosrecy);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        RecyclerView.LayoutManager layoutManager=linearLayoutManager;

        recyclerView.setLayoutManager(layoutManager);

    }

}
