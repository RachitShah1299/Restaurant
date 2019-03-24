package com.example.rachitshah.drawerlayout;

import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.rachitshah.drawerlayout.Recent.Res_Rec_DataModel;
import com.example.rachitshah.drawerlayout.Recent.Res_Rec_MyBaseAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static java.security.AccessController.getContext;

public class Res_Recent extends AppCompatActivity {
    private DrawerLayout mdrawer;
    private ActionBarDrawerToggle mtoogle;
    ListView listView;
    ImageView imageView, back;
    DatabaseReference myref;
    ArrayList<FoodRequestAcceptance> foodRequestAcceptances;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //show the activity in full screen

        setContentView(R.layout.activity_res__recent);

        myref= FirebaseDatabase.getInstance().getReference("FoodRequestAcceptance");
        listView=(ListView)findViewById(R.id.Res_rec_liv);
        foodRequestAcceptances= new ArrayList<>();
        back = (ImageView)findViewById(R.id.bcbtn);

/*
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
*/




        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    FoodRequestAcceptance users = snapshot.getValue(FoodRequestAcceptance.class);
                    foodRequestAcceptances.add(users);

                }

                RecentDisplay recentDisplay = new RecentDisplay(Res_Recent.this,foodRequestAcceptances);
                listView.setAdapter(recentDisplay);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
