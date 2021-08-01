package com.sharkit.stft;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.sharkit.stft.Notification.ToastComplete;
import com.sharkit.stft.ui.Admin;
import com.sharkit.stft.ui.Variable;

public class Login extends AppCompatActivity implements View.OnClickListener {
    private Button login, qr_code;
    private EditText password, email;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        findView();
        onClickView();

//        if (mAuth.getCurrentUser().getUid() != null){
//            Intent intent = new Intent(this, Moderator.class);
//            startActivity(intent);
//        }
    }

    private void onClickView() {
        login.setOnClickListener(this);
        qr_code.setOnClickListener(this);
    }

    private void findView() {
        login = findViewById(R.id.login_xml);
        qr_code = findViewById(R.id.qr_code_xml);
        password = findViewById(R.id.password_xml);
        email = findViewById(R.id.email_xml);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_xml:
                loginProfile();
                break;
            case R.id.qr_code_xml:
                scanQRCode();
                break;
        }
    }

    private void scanQRCode() {
    }

    private void loginProfile() {

        Intent moderator = new Intent(this, Moderator.class);
        Intent firm = new Intent(this, Moderator.class);
        mAuth.signInWithEmailAndPassword(email.getText().toString() + "@stft.com", password.getText().toString())
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                Log.d("TAGA", "success");
                db.collection("Admins")
                        .whereEqualTo("email", authResult.getUser().getEmail())
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                Log.d("TAGA", queryDocumentSnapshots.size()+"");
                                for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots){
                                    Admin admin = snapshot.toObject(Admin.class);
                                    Log.d("TAGA", admin.getRole());
                                    if (admin.getRole().equals("Модератор")){
                                        startActivity(moderator);
                                    } else if(admin.getRole().equals("Фірма")){
                                        Variable.setFirm(admin.getName());
                                        startActivity(firm);
                                    }
                                }
                            }
                        });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                try {
                    throw new ToastComplete(getApplicationContext(), "fail");
                } catch (ToastComplete toastComplete) {
                    toastComplete.printStackTrace();
                }
            }
        });


    }
}