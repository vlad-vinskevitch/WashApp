package com.sharkit.stft.Moderation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sharkit.stft.Notification.ToastComplete;
import com.sharkit.stft.R;
import com.sharkit.stft.settings.Validation;
import com.sharkit.stft.ui.Driver;

import java.util.ArrayList;

public class RegistrationClient extends Fragment {
    private Button register;
    private TabLayout tabLayout;
    private EditText car, lifting, type, owner, drive, number, password;
    private ArrayList<String> tags;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.registration_client, container, false);
        findView(root);
        onClick();

        return root;
    }

    private void onClick() {
        NavController navController = Navigation.findNavController(requireActivity(),R.id.nav_host_fragment);
        register.setOnClickListener(v -> createDriver());
        TabLayout.Tab tab = tabLayout.getTabAt(0);
        tab.select();
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                    switch (tab.getPosition()){
                        case 0: navController.navigate(R.id.nav_registration_client);

                            break;
                        case 1: navController.navigate(R.id.nav_registration_admin);
                            break;

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void createDriver() {

        if (Validation.isValidDriverRegistration(
                password.getText().toString().trim(),
                number.getText().toString().trim(),
                car.getText().toString().trim(),
                lifting.getText().toString().trim(),
                type.getText().toString().trim(),
                owner.getText().toString().trim(),
                drive.getText().toString().trim(),
                getContext())){
            registerDriver();


        }


    }

    private void generateKey(String inputText) {
        String inputString = inputText.toLowerCase();
        String [] tagArray = inputString.split(" ");
        tags = new ArrayList<>();



        for (String word : tagArray){
            String a = "";
            char [] b = inputString.toCharArray();

            for (char c : b) {
                a += c;
                tags.add(a);
            }
            inputString = inputString.replace(word, "").trim();
        }
    }

    private void registerDriver() {
        generateKey(drive.getText().toString());
        Driver driver = new Driver();
        driver.setTag(tags);
        driver.setEmail(number.getText().toString() + "@stft.com");
        driver.setPassword(password.getText().toString());
        driver.setDriver(drive.getText().toString());
        driver.setCar(car.getText().toString());
        driver.setOwner(owner.getText().toString());
        driver.setType(type.getText().toString());
        driver.setLifting(lifting.getText().toString());
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        mAuth.createUserWithEmailAndPassword(number.getText().toString().trim() + "@stft.com", password.getText().toString())
                .addOnSuccessListener(authResult -> db.collection("Drivers").document(number.getText().toString().trim() + "@stft.com")
                        .set(driver)
                        .addOnSuccessListener(documentReference -> {
                            try {
                                throw new ToastComplete(getContext(), "Користувач доданий");
                            } catch (ToastComplete toastComplete) {
                                toastComplete.printStackTrace();
                            }
                            NavController navController = Navigation.findNavController(requireActivity(),R.id.nav_host_fragment);
                            navController.navigate(R.id.nav_moderation_list);
                        }));
    }

    private void findView(View root) {
        tabLayout = root.findViewById(R.id.tab_xml);
        number = root.findViewById(R.id.number_xml);
        register = root.findViewById(R.id.register_xml);
        car = root.findViewById(R.id.car_xml);
        lifting = root.findViewById(R.id.lifting_xml);
        type = root.findViewById(R.id.type_xml);
        owner = root.findViewById(R.id.owner_xml);
        drive = root.findViewById(R.id.driver_xml);
        password = root.findViewById(R.id.password_xml);
    }
}
