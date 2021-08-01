package com.sharkit.stft;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Bundle;

public class Firm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firm);
        NavController navController = Navigation.findNavController(this,R.id.nav_firm_fragment);
        navController.navigate(R.id.nav_list_all_drivers);
    }
}