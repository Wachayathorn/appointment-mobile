package com.example.appointment;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.io.IOException;

import dmax.dialog.SpotsDialog;

public class HomeUser_Page extends AppCompatActivity {

    private String userID;
    private FirebaseDatabase db;
    private FirebaseAuth FireAuth;
    private DatabaseReference myRef;
    private TextView textName;
    private String value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_user__page);

        textName = (TextView) findViewById(R.id.textname);

        FireAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        myRef = db.getReference("Users-Student");

        FirebaseUser userCur = FireAuth.getCurrentUser();
        userID = userCur.getUid();
        String userEmail = userCur.getEmail();
        String findID = userEmail.substring(0, 14);

        if (userID != null) {
            myRef.child(findID).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    try {
                        value = dataSnapshot.child("Name").getValue().toString();
                        textName.setText(value);
                    } catch (Exception e) {
                        try {
                            FirebaseInstanceId.getInstance().deleteInstanceId();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        } finally {
                            FirebaseAuth.getInstance().signOut();
                            Intent Index = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(Index);
                            Toast.makeText(HomeUser_Page.this, "ไม่มีข้อมูลของท่านอยู่ในระบบ โปรดติดต่อแอดมิน", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        } else {
            Toast.makeText(HomeUser_Page.this, "Error !", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
    }

    public void onClickProfile(View view) {
        Intent profile = new Intent(getApplicationContext(), ProfileUser_Page.class);
        startActivity(profile);
    }

    public void onClickChooseApp(View view) {
        Intent Appointment = new Intent(getApplicationContext(), AppointmentUser_Page.class);
        startActivity(Appointment);
    }

    public void btLogout(View view) {
//        Intent popUp = new Intent(getApplicationContext(), PopUp.class);
//        startActivity(popUp);
        signout();
    }

    public void onClickCheckTimeUser(View view) {
        Intent checktime = new Intent(getApplicationContext(), UserCheckTime_Page.class);
        startActivity(checktime);
    }

    public void onClickStatus(View view) {
        Intent status = new Intent(getApplicationContext(), StatusAppUser_Page.class);
        startActivity(status);
    }

    public void onClickNews(View view) {
        Intent news = new Intent(getApplicationContext(), NewsUser.class);
        startActivity(news);
    }

    public void signout() {
        final SpotsDialog spotDialog = (SpotsDialog) new SpotsDialog.Builder().setContext(HomeUser_Page.this).setMessage("กำลังออกจากระบบ").setTheme(R.style.CustomSpotDialog).setCancelable(false).build();
        spotDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    FirebaseInstanceId.getInstance().deleteInstanceId();
                } catch (IOException e) {
                    Log.v("TEST", "M : " + e);
                } finally {
                    FirebaseAuth.getInstance().signOut();
                    spotDialog.dismiss();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
            }
        }).start();
    }
}
