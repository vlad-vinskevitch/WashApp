package com.sharkit.stft;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.sharkit.stft.Notification.ToastComplete;
import com.sharkit.stft.databinding.ModeratorBinding;
import com.sharkit.stft.settings.CaptureAct;
import com.sharkit.stft.ui.Driver;
import com.sharkit.stft.ui.NewWash;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Moderator extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
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
        navController.navigate(R.id.nav_registration_client);
    }
    public void onClickScanQR(MenuItem item){
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setOrientationLocked(true);
        integrator.setPrompt("Отсканируйте продукт который хотите добавить");
        integrator.setBeepEnabled(true);
        integrator.setCaptureActivity(CaptureAct.class);
        integrator.initiateScan();
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);

        if (result.getContents() != null) {
            readFromFS(result.getContents());

        } else {
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
            navController.navigate(R.id.nav_moderation_list);
            Toast.makeText(getBaseContext(), "error", Toast.LENGTH_SHORT).show();
        }

    }

    private void readFromFS(String contents) {

        db.collection("Drivers")
                .whereEqualTo("email", contents)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots){
                            Driver driver = snapshot.toObject(Driver.class);
                            AlertDialog(driver);
                        }
                    }
                });
    }

    private void AlertDialog(Driver driver) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        LayoutInflater inflater = LayoutInflater.from(getBaseContext());
        View view = inflater.inflate(R.layout.alert_new_wash, null);
        TextView textView = view.findViewById(R.id.text_xml);
        EditText editText = view.findViewById(R.id.number_xml);
        textView.setText("Буде записано в базу " + driver.getDriver() + ", Фірма: " + driver.getOwner());
        dialog.setPositiveButton("Добре", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                writeWash(driver, editText.getText().toString());
            }
        });
        dialog.setView(view);
        dialog.show();
    }

    private void writeWash(Driver driver, String s) {
        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

        NewWash newWash = new NewWash();
        newWash.setEmail(driver.getEmail());
        newWash.setTime(calendar.getTimeInMillis());
        newWash.setData(format.format(calendar.getTimeInMillis()));
        newWash.setNumber(Integer.parseInt(s));
        newWash.setMonth(calendar.get(Calendar.MONTH)+1+"");
        newWash.setYear(calendar.get(Calendar.YEAR)+"");

        db.collection("Wash")
                .add(newWash)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        NavController navController = Navigation.findNavController(Moderator.this, R.id.nav_host_fragment);
                        try {
                            throw new ToastComplete(Moderator.this, "Мийка успішно додана");
                        } catch (ToastComplete toastComplete) {
                            toastComplete.printStackTrace();
                        }
                        navController.navigate(R.id.nav_moderation_list);
                    }
                });
    }


}