package com.sharkit.stft.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sharkit.stft.R;
import com.sharkit.stft.ui.Admin;
import com.sharkit.stft.ui.StaticAdmin;
import com.sharkit.stft.ui.Variable;

import java.util.ArrayList;

public class ModerationListAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Admin> mGroup;
    private Button change;
    private TextView name;
    private LinearLayout linearLayout;


    public ModerationListAdapter(Context mContext, ArrayList<Admin> mGroup){
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
            convertView = inflater.inflate(R.layout.moderator_list_item, null);
        }
        findView(convertView);
        name.setText(mGroup.get(position).getName());
        onClick(position);

        return convertView;
    }

    private void onClick(int position) {
        NavController navController = Navigation.findNavController((Activity) mContext, R.id.nav_host_fragment);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Variable.setFirm(mGroup.get(position).getName());
                navController.navigate(R.id.nav_list_all_drivers);
            }
        });
        change.setOnClickListener(v -> {
            StaticAdmin.setName(mGroup.get(position).getName());
            StaticAdmin.setPassword(mGroup.get(position).getPassword());
            StaticAdmin.setRole(mGroup.get(position).getRole());
            StaticAdmin.setEmail(mGroup.get(position).getEmail());
            navController.navigate(R.id.nav_change_admin);
        });

    }

    private void findView(View convertView) {
        change = convertView.findViewById(R.id.change_xml);
        name = convertView.findViewById(R.id.name_xml);
        linearLayout = convertView.findViewById(R.id.main_xml);
    }
}
