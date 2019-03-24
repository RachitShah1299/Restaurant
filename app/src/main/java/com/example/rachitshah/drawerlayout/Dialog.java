package com.example.rachitshah.drawerlayout;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import static android.widget.Toast.*;

public class Dialog extends AppCompatDialogFragment {

    private Dialoglistner Listner;
    EditText old,newpass,repass;
    Button changebtn;
    DatabaseReference myref;
    String key1,rpassword;

    @Override
    public android.app.Dialog onCreateDialog(Bundle savedInstanceState) {
        final EditText old, newpass, repass;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_layout, null);

        myref=FirebaseDatabase.getInstance().getReference("Restuarant");
        old = (EditText) view.findViewById(R.id.OldPass);
        newpass = (EditText) view.findViewById(R.id.NewPass);
        repass = (EditText) view.findViewById(R.id.RePass);
        changebtn=(Button)view.findViewById(R.id.change_btn);



        changebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getdata();
                String s1, s2, s3;
                s1 = old.getText().toString();
                s2 = newpass.getText().toString();
                s3 = repass.getText().toString();

                if(rpassword.compareTo(s1)==0){

                    if(s2.length()==0) {
                        Toast toast = makeText(getActivity(), "Password cannot be empty", LENGTH_SHORT);
                        toast.show();

                    }
                    else {
                        if (s2.compareTo(s3) == 0) {
                            myref.child(key1).child("passwrd").setValue(s2);
                            Toast toast = makeText(getActivity(), "Password updated", LENGTH_SHORT);
                            toast.show();


                        }
                        else{
                            Toast toast = makeText(getActivity(), "Password should match", LENGTH_SHORT);
                            toast.show();

                        }
                    }

                }

            }
        });






/*        builder.setView(view)
                .setTitle("Change Password")
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (s2.compareTo(s3) == 0) {
                            Listner.applyText(s1, s2);
                            Toast toast = makeText(getActivity(), "Password Updated", LENGTH_SHORT);
                            toast.show();

                        } else {

                            Toast toast = makeText(getActivity(), "Password did not match, please try again", LENGTH_SHORT);
                            toast.show();

                        }


                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });*/

        return builder.create();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            Listner = (Dialoglistner) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "Must Implement Inteface");

        }

    }

    public interface Dialoglistner {
        void applyText(String s1, String s2);

    }
    private void getdata(){
        SharedPreferences sharedPreferences= getContext().getSharedPreferences("Restuarant", Context.MODE_PRIVATE);
        key1 = sharedPreferences.getString("Key", "");
        rpassword = sharedPreferences.getString("Password", "");
    }
}
