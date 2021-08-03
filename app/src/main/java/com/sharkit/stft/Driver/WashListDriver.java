package com.sharkit.stft.Driver;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.sharkit.stft.Adapters.WashAdapter;
import com.sharkit.stft.R;
import com.sharkit.stft.ui.NewWash;
import com.sharkit.stft.ui.Variable;

import java.util.ArrayList;

public class WashListDriver extends Fragment {
    private TabLayout tabLayout;
    private ListView listView;
    private ArrayList<NewWash> list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.wash_list_driver, container, false);
        findView(root);
        setAllList();

        return root;
    }

    private void setAllList() {
        list = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Wash")
                .whereEqualTo("email", Variable.getEmail())
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots){
                        NewWash wash = snapshot.toObject(NewWash.class);
                        list.add(wash);
                    }
                    setAdapter();
                });
    }

    private void setAdapter() {
        WashAdapter adapter = new WashAdapter(list, getContext());
        listView.setAdapter(adapter);
    }

    private void findView(View root) {
        listView = root.findViewById(R.id.list_driver_xml);
        tabLayout = root.findViewById(R.id.tab_xml);
    }
}
