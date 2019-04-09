package com.example.rachitshah.drawerlayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class res_profile extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    String path;
    Uri imageUri;
    ImageView img, back;
    Button save;
    ImageView imageView;
    DatabaseReference myref;
    FirebaseAuth mauth;

    String key1;
    private static final int PICK_IMAGE = 100;
    EditText rename, owname, email, phone, address, password;

    String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";

    int resposition;
    String resname, remail, ownname, raddress, rpassword, rphone;
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

/*
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //show the activity in full screen
*/

        setContentView(R.layout.activity_res_profile);


        myref = FirebaseDatabase.getInstance().getReference("Restuarant");
        rename = (EditText) findViewById(R.id.rname);
        owname = (EditText) findViewById(R.id.oname);
        email = (EditText) findViewById(R.id.email);
        phone = (EditText) findViewById(R.id.phone);
        address = (EditText) findViewById(R.id.address);
        password = (EditText) findViewById(R.id.password);
        img = (ImageView) findViewById(R.id.profile);
        save = (Button) findViewById(R.id.save);
        back = (ImageView) findViewById(R.id.backbtn);

        /*imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(res_profile.this, MainActivity.class);
                startActivity(intent);

            }
        });*/



        getData();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialogbox = new Dialog();
                dialogbox.show(getSupportFragmentManager(), "Custom Dialog");
                savedata();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Boolean notnull;

                notnull = isnotnull();

                if (notnull) {


                    String rrname, rowner, rrphone, rraddress, rremail, rrpassword;
                    rrname = rename.getText().toString().trim();
                    rowner = owname.getText().toString().trim();
                    rrphone = phone.getText().toString().trim();
                    rraddress = address.getText().toString().trim();
                    rremail = email.getText().toString().trim();
                    //rrpassword = password.getText().toString().trim();

                    Restuarants rest = new Restuarants();

                  /*  rest.setKey(key1);
                    rest.setRname(rrname);
                    rest.setRowner(rowner);
                    rest.setRemail(rremail);
                    rest.setPasswrd(rrpassword);
                    rest.setMob(rrphone);
                    rest.setRaddress(rraddress);

                    myref.child(key1).setValue(rest);*/

             /*       myref.child(key1).child("rname").removeValue();
                    myref.child(key1).child("oname").removeValue();
                    myref.child(key1).child("mob").removeValue();
                    myref.child(key1).child("passwd").removeValue();
                    myref.child(key1).child("remail").removeValue();
                    myref.child(key1).child("raddress").removeValue();
*/

                    myref.child(key1).child("rname").setValue(rrname);
                    myref.child(key1).child("rowner").setValue(rowner);
                    myref.child(key1).child("mob").setValue(rrphone);
                    myref.child(key1).child("remail").setValue(rremail);
                    myref.child(key1).child("raddress").setValue(rraddress);

                    Toast.makeText(res_profile.this, "Profile Updated", Toast.LENGTH_SHORT).show();


                } else {
                    Toast.makeText(res_profile.this, "Something is Wrong!!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getData() {

        SharedPreferences sharedPreferences = getSharedPreferences("Restaurant", Context.MODE_PRIVATE);
        key1 = sharedPreferences.getString("Key", "");
        resname = sharedPreferences.getString("Rname", "");
        ownname = sharedPreferences.getString("Oname", "");
        remail = sharedPreferences.getString("Email", "");
        rphone = sharedPreferences.getString("Phone", "");
        raddress = sharedPreferences.getString("Address", "");
        rpassword = sharedPreferences.getString("Password", "");
//        img.setImageURI(Uri.parse(sharedPreferences.getString("Profile", "???")));

        Log.e("his", "KEY " + key1);




        rename.setText(resname);
        owname.setText(ownname);
        email.setText(remail);
        phone.setText(rphone);
        address.setText(raddress);
        password.setText(rpassword);


    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }

    private Boolean isnotnull() {
        boolean notnull = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = remail;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        Boolean check = true;
        if (resname.length() == 0) {
            rename.setError("Restaurant name cannot be Empty");
            check = false;
        }
        if (ownname.length() == 0) {
            owname.setError("Owner Name cannot be Empty");
            check = false;
        }

        if (!matcher.matches()) {
            email.setError("Email not Valid");
            check = false;
        }
        if (rpassword.length() == 0) {
            password.setError("Password cannot be Empty");
            check = false;
        }
        if (rphone.length() != 10) {
            phone.setError("Phone cannot be Empty");
            check = false;
        }
        if (raddress.length() == 0) {
            address.setError("Address cannot be Empty");
            check = false;
        }
        if (check == true) {
            return true;
        } else {
            return false;
        }

    }

    private void savedata() {
        SharedPreferences sharedPreferences = getSharedPreferences("Restaurant", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Rname", rename.getText().toString());
        editor.putString("Oname", owname.getText().toString());
        editor.putString("Email", email.getText().toString());
        editor.putString("Phone", phone.getText().toString());
        editor.putString("Address", address.getText().toString());
        editor.putString("Password", password.getText().toString());
        editor.putString("Profile", path);

        editor.commit();

        Toast toast = Toast.makeText(res_profile.this, "Profile Updated Successfully", Toast.LENGTH_SHORT);
        toast.show();

    }
}
