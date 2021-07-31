package com.sharkit.stft.Moderation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.sharkit.stft.Adapters.ModerationListAdapter;
import com.sharkit.stft.R;
import com.sharkit.stft.ui.Admin;

import java.util.ArrayList;

public class ModeratorList extends Fragment {
    private ArrayList<Admin> admins;
    private  ListView listView;
    private Button firms, moderator;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.moderator_list, container, false);
        findView(root);
        getAdminList("Фірма");
        onClick();
        return root;
    }

    private void onClick() {
        firms.setOnClickListener(v -> getAdminList("Фірма"));
        moderator.setOnClickListener(v -> getAdminList("Модератор"));
    }


    private void findView(View root) {
        listView = root.findViewById(R.id.list);
        firms = root.findViewById(R.id.firms_xml);
        moderator = root.findViewById(R.id.moderator_xml);
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
