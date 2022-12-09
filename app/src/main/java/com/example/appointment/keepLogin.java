package com.example.appointment;

import android.app.Application;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class keepLogin extends Application {

//    private String uID;

    @Override
    public void onCreate() {
        super.onCreate();

//        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
//        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
//
//        if (firebaseUser != null){
//            uID = firebaseUser.getEmail();
//
//            int usernameLength = uID.length();
//            char userchk = '1';
//            char uschar0 = uID.charAt(0);
//            char uschar1 = uID.charAt(1);
//            if (usernameLength == 24 && (uschar0 == userchk && uschar1 == userchk)){
//                startActivity(new Intent(getApplicationContext() , HomeUser_Page.class));
//            }else{
//                startActivity(new Intent(getApplicationContext() , HomeTeacher_Page.class));
//            }
//
//        }
    }

}
