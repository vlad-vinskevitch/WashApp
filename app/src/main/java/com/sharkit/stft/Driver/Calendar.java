package com.sharkit.stft.Driver;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.sharkit.stft.R;
import com.sharkit.stft.ui.Variable;

import java.lang.annotation.Native;
import java.text.SimpleDateFormat;

public class Calendar extends Fragment {
    private CalendarView calendarView;
    private NavController navController;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.calendar, container, false);
        findView(root);
        calendarChange();

        return root;
    }

    private void calendarChange() {
        if (Variable.getRole().equals("Модератор")){
            navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        }else if (Variable.getRole().equals("Фірма")){
            navController = Navigation.findNavController(getActivity(), R.id.nav_firm_fragment);
        }else if (Variable.getRole().equals("Водій")){
            navController = Navigation.findNavController(getActivity(), R.id.nav_driver_fragment);
        }
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                java.util.Calendar calendar = java.util.Calendar.getInstance();
                calendar.set(java.util.Calendar.YEAR, year);
                calendar.set(java.util.Calendar.MONTH, month);
                calendar.set(java.util.Calendar.DATE, dayOfMonth);

                SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
                Log.d("TAGA", format.format(calendar.getTimeInMillis()));
                Variable.setTime(calendar.getTimeInMillis());
                navController.navigate(R.id.nav_wash_list_driver);
            }
        });
    }

    private void findView(View root) {
        calendarView = root.findViewById(R.id.calendar);
    }
}
