package com.example.rachitshah.drawerlayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    EditText fd, fd1, fd2, fd3, fd4, qty1, qty2, qty3, qty4, qty5;
    Button submit, volinfo;
    DatabaseReference myref, myref2, myref3;
    FirebaseAuth mauth;
    String str_name, location;
    TextView headeamail, headname;
    String key, key1,rname, resemail;
    String fod, fod1, fod2, fod3, fod4, qt, qt1, qt2, qt3, qt4;
    TextView name, email, volnames;
    ArrayList<FoodRequestAcceptance> foodRequestAcceptances;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //show the activity in full screen*/

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);
        name = (TextView) header.findViewById(R.id.nav_name);
        email = (TextView) header.findViewById(R.id.nav_email);
        volnames = (TextView) findViewById(R.id.vol_name);
        foodRequestAcceptances = new ArrayList<>();
        imageView = (ImageView) header.findViewById(R.id.header_image);
        imageView.setImageResource(R.drawable.res4);
        myref = FirebaseDatabase.getInstance().getReference("Restuarant");
        myref2 = FirebaseDatabase.getInstance().getReference("FoodRequest");
        myref3 = FirebaseDatabase.getInstance().getReference("FoodRequestAcceptance");
        mauth = FirebaseAuth.getInstance();
        headname = (TextView) findViewById(R.id.nav_name);
        headeamail = (TextView) findViewById(R.id.nav_email);
        fd = (EditText) findViewById(R.id.fd);
        fd1 = (EditText) findViewById(R.id.fd1);
        fd2 = (EditText) findViewById(R.id.fd2);
        fd3 = (EditText) findViewById(R.id.fd3);
        fd4 = (EditText) findViewById(R.id.fd4);
        qty1 = (EditText) findViewById(R.id.qty);
        qty2 = (EditText) findViewById(R.id.qty1);
        qty3 = (EditText) findViewById(R.id.qty2);
        qty4 = (EditText) findViewById(R.id.qty3);
        qty5 = (EditText) findViewById(R.id.qty4);
        submit = (Button) findViewById(R.id.btn);
        volinfo = (Button) findViewById(R.id.volinfo);
        SharedPreferences sharedPreferences = getSharedPreferences("Restaurant_log", Context.MODE_PRIVATE);

        resemail = sharedPreferences.getString("Email", "");

/* name.setText("Salim Quershi");
        email.setText("burgerKhalo67@gmail.com");*/

        setheader();
        Log.e("Rnameee", "is: " + rname);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                fod = fd.getText().toString().trim();
                fod1 = fd1.getText().toString().trim();
                fod2 = fd2.getText().toString().trim();
                fod3 = fd3.getText().toString().trim();
                fod4 = fd4.getText().toString().trim();

                qt = qty1.getText().toString().trim();
                qt1 = qty2.getText().toString().trim();
                qt2 = qty3.getText().toString().trim();
                qt3 = qty4.getText().toString().trim();
                qt4 = qty5.getText().toString().trim();


                Boolean check;

                check = checkfood();

                if (check) {
                    Toast.makeText(MainActivity.this, "Food Request Sent", Toast.LENGTH_SHORT).show();

                    volinfo.setVisibility(v.VISIBLE);
                    volnames.setVisibility(v.VISIBLE);
                }


            }
        });

        volinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myref3.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Date c = Calendar.getInstance().getTime();
                            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                            String formattedDate = df.format(c);

                            FoodRequestAcceptance users = snapshot.getValue(FoodRequestAcceptance.class);
                            if (formattedDate.compareTo(users.getFddate()) == 0) {
                                foodRequestAcceptances.add(users);
                                volnames.setText(users.getVname());
                            }
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Restuarants users = snapshot.getValue(Restuarants.class);
                    if(resemail.equals(users.getRemail())){
                        str_name = users.getRname();
                        Log.e("Email","Email is"+resemail);
                    }
                    Log.e("Resname","REsname is"+str_name);


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void setheader() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);
        name = (TextView) header.findViewById(R.id.nav_name);
        email = (TextView) header.findViewById(R.id.nav_email);

        imageView = (ImageView) header.findViewById(R.id.header_image);

        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    Restuarants users = snapshot.getValue(Restuarants.class);
                    if(resemail.equals(users.getRemail())){
                        str_name = users.getRname();
                        Log.e("Email","Email is"+resemail);
                        name.setText(str_name);
                        email.setText(resemail);

                    }
                    Log.e("Resnaem","REsname is"+str_name);

                }

               }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        imageView.setImageURI(Uri.parse(sharedPreferences.getString("Profile", "???")));

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_history) {
            Intent intent = new Intent(MainActivity.this, Res_Recent.class);
            startActivity(intent);

        } else if (id == R.id.nav_ep) {
            //  Toast toast = Toast.makeText(this, "EditProfile", Toast.LENGTH_LONG);
            //toast.show();
            Intent intent = new Intent(MainActivity.this, res_profile.class);
            startActivity(intent);


        } else if (id == R.id.nav_feedback) {
            //Toast.makeText(MainActivity.this, "Feedback", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, res_feedback.class);
            startActivity(intent);

        } else if (id == R.id.nav_log) {
            finish();

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public boolean checkfood() {
        Boolean check = true;
        FirebaseUser user = mauth.getCurrentUser();
        key1 = user.getUid();

        if (fod.length() == 0) {
            Toast.makeText(MainActivity.this, "Enter the food description", Toast.LENGTH_SHORT).show();
            check = false;
        } else {
            if (qt.length() == 0) {
                Toast.makeText(MainActivity.this, "Enter the food quantity", Toast.LENGTH_SHORT).show();
                check = false;
            } else {
                if (fod1.length() == 0 && qt1.length() == 0) {

                    Date c = Calendar.getInstance().getTime();
                    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                    String formattedDate = df.format(c);
                    String desc = qt + " " + fod;

                    FoodRequest foodRequest = new FoodRequest();
                    foodRequest.setKey(key1);
                    foodRequest.setRname(str_name);

                    foodRequest.setAddress(location);
                    foodRequest.setdesc(desc);
                    foodRequest.setFddate(formattedDate);

                    myref2.child(key1).setValue(foodRequest);
                    //myref.child(key1).setValue(key1);

                    /*myref.child(key1).child("rname").setValue(rname);
                    myref.child(key1).child("Address").setValue(location);
                    myref.child(key1).child("Date:").setValue(formattedDate);
                    myref.child(key1).child("Food Description").setValue(qt + " " + fod);
*/
                } else {
                    if (fod1.length() == 0) {
                        Toast.makeText(MainActivity.this, "Enter the food description", Toast.LENGTH_SHORT).show();
                        check = false;
                    } else {
                        if (qt1.length() == 0) {
                            Toast.makeText(MainActivity.this, "Enter the food quantity", Toast.LENGTH_SHORT).show();
                            check = false;
                        } else {
                            if (fod2.length() == 0 && qt2.length() == 0) {

                                Date c = Calendar.getInstance().getTime();
                                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                                String formattedDate = df.format(c);
                                String desc = qt + " " + fod + "    " + qt1 + " " + fod1;

                                FoodRequest foodRequest = new FoodRequest();
                                foodRequest.setKey(key1);
                                foodRequest.setRname(str_name);
                                foodRequest.setAddress(location);
                                foodRequest.setdesc(desc);
                                foodRequest.setFddate(formattedDate);

                                myref2.child(key1).setValue(foodRequest);

                                /*myref.child("key").setValue(key1);
                                myref.child(key1).child("rname").setValue(rname);
                                myref.child(key1).child("Address").setValue(location);
                                myref.child(key1).child("Date:").setValue(formattedDate);
                                myref.child(key1).child("Food Description").setValue(qt + " " + fod + "    " + qt1 + " " + fod1);*/

                            } else {
                                if (fod2.length() == 0) {
                                    Toast.makeText(MainActivity.this, "Enter the food description", Toast.LENGTH_SHORT).show();
                                    check = false;
                                } else {
                                    if (qt2.length() == 0) {
                                        Toast.makeText(MainActivity.this, "Enter the food quantity", Toast.LENGTH_SHORT).show();
                                        check = false;
                                    } else {
                                        if (fod3.length() == 0 && qt3.length() == 0) {

                                            Date c = Calendar.getInstance().getTime();
                                            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                                            String formattedDate = df.format(c);
                                            String desc = qt + " " + fod + "    " + qt1 + " " + fod1 + "   " + qt2 + " " + fod2;

                                            FoodRequest foodRequest = new FoodRequest();
                                            foodRequest.setKey(key1);
                                            foodRequest.setRname(str_name);
                                            foodRequest.setAddress(location);
                                            foodRequest.setdesc(desc);
                                            foodRequest.setFddate(formattedDate);

                                            myref2.child(key1).setValue(foodRequest);

                                            /*myref.child("key").setValue(key1);
                                            myref.child(key1).child("rname").setValue(rname);
                                            myref.child(key1).child("Address").setValue(location);
                                            myref.child(key1).child("Date:").setValue(formattedDate);
                                            myref.child(key1).child("Food Description").setValue(qt + " " + fod + "    " + qt1 + " " + fod1 + "   " + qt2 + " " + fod2);*/

                                        } else {
                                            if (fod3.length() == 0) {
                                                Toast.makeText(MainActivity.this, "Enter the food description", Toast.LENGTH_SHORT).show();
                                                check = false;
                                            } else {
                                                if (qt3.length() == 0) {
                                                    Toast.makeText(MainActivity.this, "Enter the food quantity", Toast.LENGTH_SHORT).show();
                                                    check = false;
                                                } else {
                                                    if (fod4.length() == 0 && qt4.length() == 0) {

                                                        Date c = Calendar.getInstance().getTime();
                                                        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                                                        String formattedDate = df.format(c);
                                                        String desc = qt + " " + fod + "    " + qt1 + " " + fod1 + "   " + qt2 + " " + fod2 + "   " + qt3 + " " + fod3;

                                                        FoodRequest foodRequest = new FoodRequest();
                                                        foodRequest.setKey(key1);
                                                        foodRequest.setRname(str_name);
                                                        foodRequest.setAddress(location);
                                                        foodRequest.setdesc(desc);
                                                        foodRequest.setFddate(formattedDate);

                                                        myref2.child(key1).setValue(foodRequest);

                                                        /*myref.child("key").setValue(key1);
                                                        myref.child(key1).child("rname").setValue(rname);
                                                        myref.child(key1).child("Address").setValue(location);
                                                        myref.child(key1).child("Date:").setValue(formattedDate);
                                                        myref.child(key1).child("Food Description").setValue(qt + " " + fod + "    " + qt1 + " " + fod1 + "   " + qt2 + " " + fod2 + "   " + qt3 + " " + fod3);*/


                                                    } else {
                                                        if (fod4.length() == 0) {
                                                            Toast.makeText(MainActivity.this, "Enter the food description", Toast.LENGTH_SHORT).show();
                                                            check = false;
                                                        } else {
                                                            if (qt4.length() == 0) {
                                                                Toast.makeText(MainActivity.this, "Enter the food quantity", Toast.LENGTH_SHORT).show();
                                                                check = false;
                                                            } else {

                                                                Date c = Calendar.getInstance().getTime();
                                                                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                                                                String formattedDate = df.format(c);
                                                                String desc = qt + " " + fod + "    " + qt1 + " " + fod1 + "   " + qt2 + " " + fod2 + "   " + qt3 + " " + fod3 + "   " + qt4 + " " + fod4;

                                                                FoodRequest foodRequest = new FoodRequest();
                                                                foodRequest.setKey(key1);
                                                                foodRequest.setRname(str_name);
                                                                foodRequest.setAddress(location);
                                                                foodRequest.setdesc(desc);
                                                                foodRequest.setFddate(formattedDate);

                                                                myref2.child(key1).setValue(foodRequest);

                                                                /*myref.child("key").setValue(key1);
                                                                myref.child(key1).child("rname").setValue(rname);
                                                                myref.child(key1).child("Address").setValue(location);
                                                                myref.child(key1).child("Date:").setValue(formattedDate);
                                                                myref.child(key1).child("Food Description").setValue(qt + " " + fod + "   " + qt1 + " " + fod1 + "   " + qt2 + " " + fod2 + "   " + qt3 + " " + fod3 + "   " + qt4 + " " + fod4);*/


                                                            }
                                                        }
                                                    }

                                                }
                                            }
                                        }

                                    }
                                }
                            }

                        }
                    }
                }
            }
        }
        if (check == true) {
            return true;
        } else {
            return false;
        }
    }

}
