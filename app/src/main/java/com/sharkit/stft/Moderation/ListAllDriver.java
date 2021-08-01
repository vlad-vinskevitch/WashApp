package com.sharkit.stft.Moderation;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.sharkit.stft.Adapters.DriverListAdapter;
import com.sharkit.stft.R;
import com.sharkit.stft.ui.Admin;
import com.sharkit.stft.ui.Driver;
import com.sharkit.stft.ui.Variable;

import java.util.ArrayList;

public class ListAllDriver extends Fragment {

    private TabLayout tabLayout;
    private ListView listView;
    private EditText text;
    private ArrayList<Driver> drivers;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.list_all_driver, container, false);
        findView(root);
        getDriverList();
        onClick();
        return root;
    }

    private void onClick() {
        text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(text.getText())){
                    getDriverList();
                }else {
                    getCurrentDriver(s.toString());
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void findView(View root) {
        text = root.findViewById(R.id.find_xml);
        listView = root.findViewById(R.id.list_driver_xml);
        tabLayout = root.findViewById(R.id.tab_xml);
    }

    private void getCurrentDriver(String find) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        drivers = new ArrayList<>();
        db.collection("Drivers")
                .whereEqualTo("owner", Variable.getFirm())
                .whereArrayContains("tag", find)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots){
                        Driver driver = snapshot.toObject(Driver.class);
                        drivers.add(driver);
                    }
                    setAdapter();
                });

    }

    private void setAdapter() {
        DriverListAdapter adapter = new DriverListAdapter(drivers, getContext());
        listView.setAdapter(adapter);
    }

    private void getDriverList() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        drivers = new ArrayList<>();
        db.collection("Drivers")
                .whereEqualTo("owner", Variable.getFirm())
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots){
                        Driver driver = snapshot.toObject(Driver.class);
                        drivers.add(driver);
                    }
                    setAdapter();
                });

    }
}
