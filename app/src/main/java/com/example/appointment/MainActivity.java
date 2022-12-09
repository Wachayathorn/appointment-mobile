package com.example.appointment;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Path;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.Objects;

import dmax.dialog.SpotsDialog;

public class MainActivity extends AppCompatActivity {

    EditText Username;
    EditText Password;
    Button btnLogin;

    private FirebaseAuth mAuth;
    private FirebaseDatabase db;
    private DatabaseReference myRef;
    private String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Username = findViewById(R.id.tbUsername);
        Password = findViewById(R.id.tbPassword);
        btnLogin = findViewById(R.id.btLogin);

        mAuth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignIn(Username.getText().toString().trim(), Password.getText().toString().trim());
            }
        });
    }

    private void SignIn(final String username, String password) {

        user = username + "@cpe.ac.th";

        if (!username.isEmpty()) {
            if (!password.isEmpty()) {
                final SpotsDialog spotDialog = (SpotsDialog) new SpotsDialog.Builder().setContext(MainActivity.this).setMessage("กำลังเข้าสู่ระบบ").setTheme(R.style.CustomSpotDialog).setCancelable(false).build();
                spotDialog.show();
                mAuth.signInWithEmailAndPassword(user, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            int usernameLength = user.length();
                            char userchk = '1';
                            char uschar0 = user.charAt(0);
                            char uschar1 = user.charAt(1);
                            if (usernameLength == 24 && (uschar0 == userchk && uschar1 == userchk)) {
                                db = FirebaseDatabase.getInstance();
                                myRef = db.getReference("Users-Student");
                                String token = FirebaseInstanceId.getInstance().getToken();
                                myRef.child(username).child("Token").setValue(token);

                                Intent home = new Intent(getApplicationContext(), HomeUser_Page.class);
                                startActivity(home);
                            } else {
                                db = FirebaseDatabase.getInstance();
                                myRef = db.getReference("Users-Teacher");
                                final String token = FirebaseInstanceId.getInstance().getToken();
                                myRef.orderByChild("Username").equalTo(username.toLowerCase()).addChildEventListener(new ChildEventListener() {
                                    @Override
                                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                                        String uID = dataSnapshot.child("uID").getValue().toString();
                                        db.getReference("Users-Teacher/" + uID).child("Token").setValue(token);
                                        Intent home = new Intent(getApplicationContext(), HomeTeacher_Page.class);
                                        startActivity(home);
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
                            }
                        } else {
                            spotDialog.dismiss();
                            Toast.makeText(MainActivity.this, "ชื่อผู้ใช้หรือรหัสผ่านไม่ถูกต้อง !", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            } else {
                Toast.makeText(MainActivity.this, "กรุณาใส่รหัสผ่าน !", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(MainActivity.this, "กรุณาใส่ชื่อผู้ใช้ !", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onBackPressed() {

    }

}
