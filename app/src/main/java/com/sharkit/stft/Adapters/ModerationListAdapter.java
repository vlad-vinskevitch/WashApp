package com.sharkit.stft.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.sharkit.stft.R;
import com.sharkit.stft.ui.Admin;
import com.sharkit.stft.ui.StaticAdmin;

import java.util.ArrayList;

public class ModerationListAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Admin> mGroup;
    private Button change;
    private TextView name;


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
        change.setOnClickListener(v -> {
            StaticAdmin.setName(mGroup.get(position).getName());
            StaticAdmin.setPassword(mGroup.get(position).getPassword());
            StaticAdmin.setRole(mGroup.get(position).getRole());
            StaticAdmin.setEmail(mGroup.get(position).getEmail());
            NavController navController = Navigation.findNavController((Activity) mContext, R.id.nav_host_fragment);
            navController.navigate(R.id.nav_change_admin);
        });

    }

    private void findView(View convertView) {
        change = convertView.findViewById(R.id.change_xml);
        name = convertView.findViewById(R.id.name_xml);
    }
}
