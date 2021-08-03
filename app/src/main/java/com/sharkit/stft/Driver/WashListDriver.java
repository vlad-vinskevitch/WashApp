package com.sharkit.stft.Driver;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.sharkit.stft.Adapters.WashAdapter;
import com.sharkit.stft.R;
import com.sharkit.stft.ui.NewWash;
import com.sharkit.stft.ui.Variable;

import java.util.ArrayList;
import java.util.Calendar;

public class WashListDriver extends Fragment {
    private Button current, cal;
    private ListView listView;
    private ArrayList<NewWash> list;
    private  NavController navController;
    private Calendar calendar = Calendar.getInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.wash_list_driver, container, false);
        findView(root);
        calendar.setTimeInMillis(Variable.getTime());
        onClick();
        setAllList();


        return root;
    }

    private void onClick() {

        if (Variable.getRole().equals("Модератор")){
            navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        }else if (Variable.getRole().equals("Фірма")){
            navController = Navigation.findNavController(getActivity(), R.id.nav_firm_fragment);
        }else if (Variable.getRole().equals("Водій")){
            navController = Navigation.findNavController(getActivity(), R.id.nav_driver_fragment);
        }

        current.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar instance = Calendar.getInstance();
                Variable.setTime(instance.getTimeInMillis());
                navController.navigate(R.id.nav_wash_list_driver);
            }
        });
        cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.nav_calendar);
            }
        });
    }


    private void setAllList() {
        list = new ArrayList<>();
        Log.d("TAGA" , calendar.get(Calendar.YEAR) + "." + calendar.get(Calendar.MONTH));
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Wash")
                .whereEqualTo("email", Variable.getEmail())
                .whereEqualTo("year", calendar.get(Calendar.YEAR)+"")
                .whereEqualTo("month", calendar.get(Calendar.MONTH) + 1 +"")
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
        current = root.findViewById(R.id.current);
        cal = root.findViewById(R.id.calendar);
    }
}
