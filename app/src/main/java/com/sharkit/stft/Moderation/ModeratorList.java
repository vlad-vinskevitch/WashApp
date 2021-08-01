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
import com.sharkit.stft.Adapters.ModerationListAdapter;
import com.sharkit.stft.R;
import com.sharkit.stft.ui.Admin;

import java.util.ArrayList;

public class ModeratorList extends Fragment {
    private ArrayList<Admin> admins;
    private  ListView listView;
    private TabLayout tab;
    private EditText find;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.moderator_list, container, false);
        findView(root);
        getAdminList("Фірма");
        onClick();
        return root;
    }

    private void onClick() {
        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        getAdminList("Фірма");
                        break;
                    case 1:
                        getAdminList("Модератор");
                        break;
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        find.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(find.getText())){
                    switch (tab.getSelectedTabPosition()) {
                        case 0:
                            getAdminList("Фірма");
                            break;
                        case 1:
                            getAdminList("Модератор");
                            break;
                    }
                }else{
                switch (tab.getSelectedTabPosition()) {
                    case 0:
                        getCurrentAdmin("Фірма", s.toString());
                        break;
                    case 1:
                        getCurrentAdmin("Модератор", s.toString());
                        break;
                }
                }
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void findView(View root) {
        listView = root.findViewById(R.id.list);
        tab = root.findViewById(R.id.tab_xml);
        find = root.findViewById(R.id.find_xml);
    }

    private void getCurrentAdmin(String s, String find) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        admins = new ArrayList<>();
        db.collection("Admins")
                .whereEqualTo("role", s)
                .whereArrayContains("tag", find)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots){
                        Admin admin = snapshot.toObject(Admin.class);
                        admins.add(admin);
                    }
                    setAdapter();
                });

    }
    private void getAdminList(String s) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        admins = new ArrayList<>();
        db.collection("Admins")
                .whereEqualTo("role", s)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots){
                        Admin admin = snapshot.toObject(Admin.class);
                        admins.add(admin);
                    }
                    setAdapter();
                });

    }

    private void setAdapter() {
        ModerationListAdapter adapter = new ModerationListAdapter(getContext(), admins);
        listView.setAdapter(adapter);
    }
}
