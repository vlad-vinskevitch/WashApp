package com.sharkit.stft.Moderation;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sharkit.stft.Notification.ToastComplete;
import com.sharkit.stft.R;
import com.sharkit.stft.settings.Validation;
import com.sharkit.stft.ui.Admin;

import java.util.ArrayList;

public class RegistrationAdmin extends Fragment {

    private Button register;
    private EditText name, password, email;
    private Spinner role;
    private ArrayList<String> tags;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.registration_admin, container, false);
        findView(root);
        onClick();
        return root;
    }

    private void onClick() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),R.array.level_access,R.layout.support_simple_spinner_dropdown_item);
        role.setAdapter(adapter);
        register.setOnClickListener(v -> {
            if (Validation.isValidAdminRegistration(email.getText().toString().trim(),
                    name.getText().toString().trim(),
                    password.getText().toString().trim(),
                    getContext())){
                createNewAdmin();
            }
        });
    }

    private void generateKey(String inputText) {
        String inputString = inputText.toLowerCase();
        String [] tagArray = inputString.split(" ");
        tags = new ArrayList<>();



        for (String word : tagArray){
            String a = "";
            char [] b = inputString.toCharArray();

            for (int i = 0; i < b.length; i++){
                a += b[i];
                tags.add(a);
            }
            inputString = inputString.replace(word, "").trim();
        }
    }

    private void createNewAdmin() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        generateKey(name.getText().toString());
        mAuth.createUserWithEmailAndPassword(email.getText().toString().trim() + "@stft.com", password.getText().toString().trim())
                .addOnSuccessListener(authResult -> {
                    Admin admin = new Admin();
                    admin.setName(name.getText().toString());
                    admin.setPassword(password.getText().toString());
                    admin.setRole(role.getSelectedItem().toString());
                    admin.setTag(tags);
                    admin.setEmail(email.getText().toString()+ "@stft.com ");

                    db.collection("Admins").document(name.getText().toString())
                            .set(admin)
                            .addOnSuccessListener(documentReference -> {
                                try {
                                    throw new ToastComplete(getContext(), "Користувач доданий");
                                } catch (ToastComplete toastComplete) {
                                    toastComplete.printStackTrace();
                                }
                                NavController navController = Navigation.findNavController(requireActivity(),R.id.nav_host_fragment);
                                navController.navigate(R.id.nav_moderation_list);
                            });
                }).addOnFailureListener(e -> {

            Log.d("TAGA", e.getMessage());
                    try {
                        throw new ToastComplete(getContext(), "Користувач з такою поштою вже існує");
                    } catch (ToastComplete toastComplete) {
                        toastComplete.printStackTrace();
                    }
                });
    }

    private void findView(View root) {
        email = root.findViewById(R.id.email_xml);
        register = root.findViewById(R.id.register_xml);
        name = root.findViewById(R.id.name_xml);
        password = root.findViewById(R.id.password_xml);
        role = root.findViewById(R.id.role_xml);
    }

}
