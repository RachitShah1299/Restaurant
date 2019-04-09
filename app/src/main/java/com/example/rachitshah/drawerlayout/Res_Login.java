package com.example.rachitshah.drawerlayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Res_Login extends AppCompatActivity {
    TextView lin, fpass, signup;
    String email, passwrd;
    EditText username, password;
    String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
    FirebaseAuth mauth;
    FirebaseAuth.AuthStateListener mauthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

     /*   this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //show the activity in full screen
*/

        setContentView(R.layout.activity_res__login);


        username = (EditText) findViewById(R.id.usrusr);
        password = (EditText) findViewById(R.id.pswrdd);
        fpass = findViewById(R.id.res_forg_pass);
        lin = findViewById(R.id.lin);
        mauth = FirebaseAuth.getInstance();
        mauthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                //Intent it = new Intent(Res_Login.this, MainActivity.class);
                //startActivity(it);
//                Toast.makeText(Res_Login.this,"Hi",Toast.LENGTH_SHORT).show();
                    }
        };

        signup = findViewById(R.id.sup);


        lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = username.getText().toString().trim();
                CharSequence inputStr = email;
                Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(inputStr);
                passwrd = password.getText().toString();

/*

                if (!matcher.matches()) {
                    username.setError("Email not Valid");
                } else {
                    signin();
                }
*/

                if (email.isEmpty()) {
                    username.setError("Email cannot be Empty");
                }


                if (matcher.matches()) {
                    //usrusr.setError("Email not Valid");
                    if (passwrd.isEmpty()) {
                        password.setError("Password cannot be Null");
                    } else {
                        signin();

                    }


                }


            }
        });

        fpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Res_Login.this, Forgotpassword.class);
                startActivity(it);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Res_Login.this, Res_Signup.class);
                startActivity(intent);

            }
        });


    }

    private void signin() {
        mauth.signInWithEmailAndPassword(email, passwrd).addOnCompleteListener(Res_Login.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(passwrd)) {
                    Toast.makeText(Res_Login.this, "Fields are empty", Toast.LENGTH_SHORT).show();
                } else {
                    if (!task.isSuccessful()) {
                        Log.e("Resaaa", "Check" + email);
                        Toast.makeText(Res_Login.this, "Invalid Credentials, Please try again", Toast.LENGTH_SHORT).show();
                    } else {
                        savedata();
                        Intent it = new Intent(Res_Login.this, MainActivity.class);
                        startActivity(it);

                    }
                }
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();

        mauth.addAuthStateListener(mauthListener);
    }


    private void savedata() {
        SharedPreferences sharedPreferences = getSharedPreferences("Restaurant_log", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Log.e("Email","Email is"+ email);
        editor.putString("Email", email);
        editor.commit();

    }
}
