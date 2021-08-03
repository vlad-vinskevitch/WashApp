package com.sharkit.stft;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.tabs.TabLayout;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.sharkit.stft.ui.Variable;

import java.util.Calendar;

public class Drivers extends AppCompatActivity {
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drivers);
        Calendar calendar = Calendar.getInstance();
        Variable.setTime(calendar.getTimeInMillis());
        NavController navController = Navigation.findNavController(this, R.id.nav_driver_fragment);
        navController.navigate(R.id.nav_wash_list_driver);
        findView();
        onClick();
    }

    private void onClick() {

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(Drivers.this);
                LayoutInflater inflater = LayoutInflater.from(getBaseContext());
                View view = inflater.inflate(R.layout.my_qr_code, null);
                ImageView imageView = view.findViewById(R.id.qr_code_xml);
                createQRCode(imageView);

                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        dialog.dismiss();
                    }
                });
                dialog.setView(view);
                dialog.show();
            }
        });
    }




    private void createQRCode(ImageView imageView) {

        MultiFormatWriter writer = new MultiFormatWriter() ;



        try {
            BitMatrix matrix = writer.encode(Variable.getEmail(), BarcodeFormat.QR_CODE, 500,500);
//            if{
//
//                matrix = writer.encode(Variable.getEmail(), BarcodeFormat.QR_CODE, 500,500);
//            }else{
//                matrix =  = writer.encode(Variable.getEmail(), BarcodeFormat.QR_CODE, 500,500);
//            }


            BarcodeEncoder encoder = new BarcodeEncoder();
            Bitmap bitmap = encoder.createBitmap(matrix);
            imageView.setImageBitmap(bitmap);
            Log.d("TAGA","success");

        } catch (WriterException e) {
            Log.d("TAGA", e.getMessage());
            e.printStackTrace();
        }

    }





    private void findView() {
        button = findViewById(R.id.button);
    }
}