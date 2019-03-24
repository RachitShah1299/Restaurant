package com.example.rachitshah.drawerlayout;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Forgotpassword extends AppCompatActivity {
    EditText editText;
    FirebaseAuth mauth;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);

        btn = (Button) findViewById(R.id.btn);
        editText = (EditText) findViewById(R.id.et);
        mauth = FirebaseAuth.getInstance();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editText.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplication(), "Enter your registered email id", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    mauth.sendPasswordResetEmail(email)

                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(Forgotpassword.this, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                                        finish();
                                    } else {
                                        Toast.makeText(Forgotpassword.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                                    }
                                }


                            });
                }
            }
        });
    }
}
