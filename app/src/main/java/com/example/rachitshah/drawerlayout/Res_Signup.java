package com.example.rachitshah.drawerlayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Res_Signup extends AppCompatActivity {
    String path;
    Uri imageUri;
    ImageView img;
    DatabaseReference myref;
    FirebaseAuth mauth;
    TextView txt, sup;
    String key;
    private static final int PICK_IMAGE = 100;
    EditText rname, oname, email, password, mono, address, gender, lic;
    String name,owner,remail,passwrd,mob,raddress,rlic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //show the activity in full screen
*/
        setContentView(R.layout.activity_res__signup);

        mauth= FirebaseAuth.getInstance();
        sup = (TextView) findViewById(R.id.sbtn);
        txt = (TextView) findViewById(R.id.txt);
        myref = FirebaseDatabase.getInstance().getReference("Restuarant");
        rname = (EditText) findViewById(R.id.rname);
        oname = (EditText) findViewById(R.id.oname);
        img = (ImageView) findViewById(R.id.profile);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.pass);
        mono = (EditText) findViewById(R.id.mono);
        address = (EditText) findViewById(R.id.add);
        lic = (EditText) findViewById(R.id.lic);

        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Res_Signup.this, Res_Login.class);
                startActivity(intent);
            }
        });

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery, PICK_IMAGE);
            }
        });


        sup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean notnull;

                notnull = isnotnull();
                savedata();
                if (notnull) {
                    savedata();

                    name = rname.getText().toString().trim();
                    owner = oname.getText().toString().trim();
                    remail = email.getText().toString().trim();
                    passwrd = password.getText().toString().trim();
                    mob = mono.getText().toString().trim();
                    raddress = address.getText().toString().trim();
                    rlic = lic.getText().toString().trim();

                    mauth.createUserWithEmailAndPassword(remail,passwrd).addOnCompleteListener(Res_Signup.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Log.e("Resaaa","Check" + remail);
                                Toast.makeText(Res_Signup.this,"Invalid Data",Toast.LENGTH_SHORT).show();
                            }
                            else{



                                FirebaseUser user = mauth.getCurrentUser();
                                key = user.getUid();


                                Restuarants rest = new Restuarants();
                                rest.setKey(key);
                                rest.setRname(name);
                                rest.setRowner(owner);
                                rest.setRemail(remail);
                                rest.setPasswrd(passwrd);
                                rest.setMob(mob);
                                rest.setRaddress(raddress);
                                rest.setRlic(rlic);

                                myref.child(key).setValue(rest);
                                savedata();
                                Intent it = new Intent(Res_Signup.this, Res_Login.class);
                                startActivity(it);

                            }
                        }
                    });
                } else {
                    Toast toast = Toast.makeText(Res_Signup.this, "Something is Wrong", Toast.LENGTH_LONG);
                    toast.show();
                }


            }
        });

    }

    private Boolean isnotnull() {
        boolean notnull = false;
        String n, e, p, ph, add, licno, gen, o;
        n = rname.getText().toString();
        e = email.getText().toString();
        p = password.getText().toString();
        ph = mono.getText().toString();
        add = address.getText().toString();
        licno = lic.getText().toString();
        o = oname.getText().toString();
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = e;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        Boolean check = true;
        if (n.length() == 0) {
            rname.setError("Restaurant name cannot be Empty");
            check = false;
        }
        if (o.length() == 0) {
            oname.setError("Owner Name cannot be Empty");
            check = false;
        }

        if (!matcher.matches()) {
            email.setError("Email not Valid");
            check = false;
        }
        if (p.length() == 0) {
            password.setError("Password cannot be Empty");
            check = false;
        }
        if (ph.length() != 10) {
            mono.setError("Phone No not Valid");
            check = false;
        }
        if (add.length() == 0) {
            address.setError("Address cannot be Empty");
            check = false;
        }
        if (licno.length() == 0) {
            lic.setError("License cannot be Empty");
            check = false;
        }
        if (check == true) {
            return true;
        } else {
            return false;
        }
    }

    private void savedata() {
        SharedPreferences sharedPreferences = getSharedPreferences("Restaurant", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Key",key);
        Log.e("Registration","key is "+key);
        editor.putString("Rname", rname.getText().toString());
        editor.putString("Oname", oname.getText().toString());
        editor.putString("Email", email.getText().toString());
        editor.putString("Phone", mono.getText().toString());
        editor.putString("Address", address.getText().toString());
        editor.putString("Password", password.getText().toString());
        editor.putString("Lic", lic.getText().toString());
        editor.putString("Profile", path);

        editor.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            img.setImageURI(imageUri);
            path = String.valueOf(imageUri);
        }
    }


}
