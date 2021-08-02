package com.sharkit.stft;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Bundle;

public class Drivers extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drivers);
        NavController navController = Navigation.findNavController(this, R.id.nav_driver_fragment);
        navController.navigate(R.id.nav_wash_list_driver);
    }
}