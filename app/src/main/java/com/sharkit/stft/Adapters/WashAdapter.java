package com.sharkit.stft.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sharkit.stft.R;
import com.sharkit.stft.ui.NewWash;

import java.util.ArrayList;

public class WashAdapter extends BaseAdapter {
    private ArrayList<NewWash> mGroup;
    private Context mContext;
    private TextView data, number;

    public WashAdapter (ArrayList<NewWash> mGroup, Context mContext){
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
            convertView = inflater.inflate(R.layout.list_driver_item, null);
        }
        findView(convertView);
        data.setText(mGroup.get(position).getData());
        number.setText(String.valueOf(mGroup.get(position).getNumber()));

        return convertView;
    }

    private void findView(View convertView) {
        data = convertView.findViewById(R.id.data_xmls);
        number = convertView.findViewById(R.id.number_xmls);

    }
}
