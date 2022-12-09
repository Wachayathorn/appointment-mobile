package com.example.appointment;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.io.IOException;

import dmax.dialog.SpotsDialog;

public class HomeTeacher_Page extends AppCompatActivity {

    private String userID;
    private FirebaseDatabase db;
    private FirebaseAuth FireAuth;
    private DatabaseReference myRef;
    private String resultUsername;
    private String teacherID;
    private TextView TeacherName;
    private Switch swNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_teacher__page);

        TeacherName = (TextView) findViewById(R.id.txname);
        swNotification = (Switch) findViewById(R.id.sw_notification);

        FireAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        myRef = db.getReference("Users-Teacher");

        FirebaseUser userCur = FireAuth.getCurrentUser();
        userID = userCur.getUid();
        String userEmail = userCur.getEmail();

        int intIndex = userEmail.indexOf("@");
        if (intIndex == -1) {
            Log.v("Test", "Error !");
        } else {
            resultUsername = userEmail.substring(0, intIndex);
        }

        if (userID != null) {
            myRef.orderByChild("Username").equalTo(resultUsername).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    try {
                        teacherID = dataSnapshot.child("uID").getValue().toString();
                        TeacherName.setText(dataSnapshot.child("Name_prefix").getValue().toString() + dataSnapshot.child("Name").getValue().toString());
                        // Set send notification
                        String notiValue = dataSnapshot.child("Notification").getValue().toString();
                        if (notiValue.equals("off") || notiValue == null) {
                            swNotification.setChecked(false);
                        } else {
                            swNotification.setChecked(true);
                        }
                    } catch (Exception e) {
                        try {
                            FirebaseInstanceId.getInstance().deleteInstanceId();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        } finally {
                            FirebaseAuth.getInstance().signOut();
                            Intent Index = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(Index);
                            Toast.makeText(HomeTeacher_Page.this, "ไม่มีข้อมูลของท่านอยู่ในระบบ โปรดติดต่อแอดมิน", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        } else {
            Toast.makeText(HomeTeacher_Page.this, "Error !", Toast.LENGTH_LONG).show();
        }

        swNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(HomeTeacher_Page.this, "เปิดการแจ้งเตือน", Toast.LENGTH_LONG).show();
                    myRef.child(teacherID).child("Notification").setValue("on");
                } else {
                    Toast.makeText(HomeTeacher_Page.this, "ปิดการแจ้งเตือน", Toast.LENGTH_LONG).show();
                    myRef.child(teacherID).child("Notification").setValue("off");
                }
            }
        });
    }

    public void onBackPressed() {
    }

    public void onClickLogout(View view) {
//        Intent popUp = new Intent(getApplicationContext(), PopUp.class);
//        startActivity(popUp);
        signout();
    }

    public void onClickAppTeacher(View view) {
        Intent AppTeacher = new Intent(getApplicationContext(), TeacherApp_Page.class);
        startActivity(AppTeacher);
    }

    public void onClickAcceptTeacher(View view) {
        Intent AccTeacher = new Intent(getApplicationContext(), TeacherAccept_Page.class);
        startActivity(AccTeacher);
    }

    public void onClickTeacherUpdateTime(View view) {
        Intent UpdateTime = new Intent(getApplicationContext(), TeacherUpdateTime_Page.class);
        startActivity(UpdateTime);
    }

    public void signout() {
        final SpotsDialog spotDialog = (SpotsDialog) new SpotsDialog.Builder().setContext(HomeTeacher_Page.this).setMessage("กำลังออกจากระบบ").setTheme(R.style.CustomSpotDialog).setCancelable(false).build();
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
