package com.example.appointment;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Splashpage extends AppCompatActivity {
    private ImageView imsplash;
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashpage);
        imsplash = findViewById(R.id.imsplash);
        text = findViewById(R.id.text1);
        Animation splash = AnimationUtils.loadAnimation(this, R.anim.fade);
        imsplash.startAnimation(splash);
        text.startAnimation(splash);
        final Intent i = new Intent(this, MainActivity.class);
        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(1200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

                    if (firebaseUser != null) {
                        String userEmail = firebaseUser.getEmail();

                        int usernameLength = userEmail.length();
                        char userchk = '1';
                        char uschar0 = userEmail.charAt(0);
                        char uschar1 = userEmail.charAt(1);
                        if (usernameLength == 24 && (uschar0 == userchk && uschar1 == userchk)) {
                            startActivity(new Intent(getApplicationContext(), HomeUser_Page.class));
                        } else {
                            startActivity(new Intent(getApplicationContext(), HomeTeacher_Page.class));
                        }
                    } else {
                        startActivity(i);
                        finish();
                    }
                }
            }
        };
        timer.start();
    }
}
