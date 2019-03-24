package com.example.rachitshah.drawerlayout;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

class RecentDisplay extends BaseAdapter {

    Context context;
    ArrayList<FoodRequestAcceptance> foodRequestAcceptances;




    public RecentDisplay(Context context, ArrayList<FoodRequestAcceptance> foodRequestAcceptances) {
        this.context=context;
        this.foodRequestAcceptances=foodRequestAcceptances;
    }

    @Override
    public int getCount() {
        return foodRequestAcceptances.size();
    }

    @Override
    public Object getItem(int position) {
        return foodRequestAcceptances.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = layoutInflater.inflate(R.layout.sample,parent,false);

        TextView name,address,status,date;
        name=(TextView)convertView.findViewById(R.id.name);
        address=(TextView)convertView.findViewById(R.id.loc);
        Log.e("FOOOOD","ID:"+foodRequestAcceptances.get(position).getVname());

        status=(TextView)convertView.findViewById(R.id.stt);
        date=(TextView)convertView.findViewById(R.id.dld);


        name.setText(foodRequestAcceptances.get(position).getVname());
        address.setText(foodRequestAcceptances.get(position).getLoc());
        status.setText(foodRequestAcceptances.get(position).getStatus());
        date.setText(foodRequestAcceptances.get(position).getFddate());



        return convertView;
    }
}
