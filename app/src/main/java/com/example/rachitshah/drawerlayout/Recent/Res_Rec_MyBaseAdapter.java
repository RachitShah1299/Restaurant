package com.example.rachitshah.drawerlayout.Recent;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rachitshah.drawerlayout.R;
import com.example.rachitshah.drawerlayout.Res_Recent;

import java.util.ArrayList;

public class Res_Rec_MyBaseAdapter extends BaseAdapter {
    ArrayList<Res_Rec_DataModel> Res_Rec_DataModelArrayList;
    Context context;
    public Res_Rec_MyBaseAdapter(Res_Recent mainActivity, ArrayList<Res_Rec_DataModel> Res_Rec_DataModelArrayList)
    {
        context = mainActivity;
        this.Res_Rec_DataModelArrayList = Res_Rec_DataModelArrayList;

    }

    @Override
    public int getCount() {
        return Res_Rec_DataModelArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return Res_Rec_DataModelArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.res_rec_sample,null);

        ImageView volimg;
        TextView volname;
        TextView deloc;
        TextView rid;
        TextView reqid;
        TextView stat;
        TextView stts;
        TextView acpt;
        TextView aon;



        volimg=(ImageView)view.findViewById(R.id.img);
        volname=(TextView)view.findViewById(R.id.name);
        deloc=(TextView)view.findViewById(R.id.loc);
        rid=(TextView)view.findViewById(R.id.rid);
        reqid=(TextView)view.findViewById(R.id.req);
        stat=(TextView)view.findViewById(R.id.stat);
        stts=(TextView)view.findViewById(R.id.stt);
        acpt=(TextView)view.findViewById(R.id.dl);
        aon=(TextView)view.findViewById(R.id.dld);


        volname.setText(Res_Rec_DataModelArrayList.get(i).getVolname());
        deloc.setText(Res_Rec_DataModelArrayList.get(i).getdlocs());
        reqid.setText(Res_Rec_DataModelArrayList.get(i).getReqids());
        aon.setText(Res_Rec_DataModelArrayList.get(i).getDons());
        return view;
    }
}
