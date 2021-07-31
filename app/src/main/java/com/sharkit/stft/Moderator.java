package com.sharkit.stft;

import android.os.Bundle;
import android.view.MenuItem;

import com.sharkit.stft.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.sharkit.stft.databinding.ModeratorBinding;

public class Moderator extends AppCompatActivity {

    private ModeratorBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ModeratorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        BottomNavigationView navView = findViewById(R.id.nav_view);
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_moderator);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    public void onClickListClient(MenuItem item){

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        navController.navigate(R.id.nav_moderation_list);
    }
    public void onClickAddNewAdmin(MenuItem item){
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        navController.navigate(R.id.nav_registration_admin);
    }
    public void onClickScanQR(MenuItem item){

    }


}