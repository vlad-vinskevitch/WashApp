package com.sharkit.stft.Adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.sharkit.stft.R;
import com.sharkit.stft.ui.Driver;
import com.sharkit.stft.ui.Variable;

import java.util.ArrayList;
import java.util.Calendar;

public class DriverListAdapter extends BaseAdapter {
    private ArrayList<Driver> mGroup;
    private Context mContext;
    private TextView owner, driver, car;
    private RelativeLayout layout;

    public DriverListAdapter(ArrayList<Driver> mGroup, Context mContext){
        this.mContext = mContext;
        this.mGroup = mGroup;
    }

    @Override
    public int getCount() {
        return mGroup.size();
    }

    @Override
    public Object getItem(int position) {
        return mGroup.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.admin_list_client_item, null);
        }
        findView(convertView);

        car.setText(mGroup.get(position).getCar());
        owner.setText(mGroup.get(position).getOwner());
        driver.setText((mGroup.get(position).getDriver()));

        layout.setOnClickListener(v -> {
            Variable.setDriver(mGroup.get(position).getDriver());
            Variable.setEmail(mGroup.get(position).getEmail());
            Calendar calendar = Calendar.getInstance();
            Variable.setTime(calendar.getTimeInMillis());
            NavController navController;
            if (Variable.getRole().equals("??????????????????")) {
                navController = Navigation.findNavController((Activity) mContext, R.id.nav_host_fragment);
            }else {
                navController = Navigation.findNavController((Activity) mContext, R.id.nav_firm_fragment);
            }
            navController.navigate(R.id.nav_wash_list_driver);

        });


        return convertView;
    }

    private void findView(View convertView) {
        layout = convertView.findViewById(R.id.linn);
        car = convertView.findViewById(R.id.name_car_xml);
        owner = convertView.findViewById(R.id.owner_xml);
        driver = convertView.findViewById(R.id.name_xml);
    }
}
