package com.example.rachitshah.drawerlayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class res_feedback extends AppCompatActivity {

    ImageView imageView;
    EditText message;
    DatabaseReference myref2;
    FirebaseAuth mauth;
    String resname,rphone,remail,msg,key1,key;
    Button submit,cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //show the activity in full screen

        setContentView(R.layout.activity_res_feedback);


        myref2= FirebaseDatabase.getInstance().getReference("Res Feedback");
        mauth=FirebaseAuth.getInstance();

        submit=(Button)findViewById(R.id.fed_submit);
        cancel=(Button)findViewById(R.id.fed_cancel);
        message=(EditText)findViewById(R.id.fed_msg);
        imageView=(ImageView)findViewById(R.id.bcbtn);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
                FirebaseUser user = mauth.getCurrentUser();
                key = user.getUid();

                msg= message.getText().toString().trim();


                Log.e("RESNAME","Name is"+resname);

                Feedback feedback = new Feedback();
                feedback.setKey(key);
                feedback.setRname(resname);
                feedback.setRemail(remail);
                feedback.setMob(rphone);
                feedback.setMsg(msg);

                myref2.child(key).setValue(feedback);
                Intent it = new Intent(res_feedback.this,MainActivity.class);
                startActivity(it);
            }
        });


        /*imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/
    }
    private void getData() {

        SharedPreferences sharedPreferences = getSharedPreferences("Restaurant", Context.MODE_PRIVATE);
        key1= sharedPreferences.getString("Key","");

        resname= sharedPreferences.getString("Rname","");
        remail= sharedPreferences.getString("Email","");
        rphone= sharedPreferences.getString("Phone","");






    }
}
