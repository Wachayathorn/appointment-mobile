package com.example.appointment;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.List;

public class ProfileUser_Page extends AppCompatActivity {

    private String userID;
    private FirebaseDatabase db;
    private FirebaseAuth FireAuth;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_user__page);

        FireAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        myRef = db.getReference("Users-Student");
        FirebaseUser userCur = FireAuth.getCurrentUser();
        userID = userCur.getUid();
        String userEmail = userCur.getEmail();
        String findID = userEmail.substring(0, 14);

        final TextView nameCurrent = (TextView) findViewById(R.id.textViewname);
        final TextView idCurrent = (TextView) findViewById(R.id.textViewid);
        final TextView sectionCurrent = (TextView) findViewById(R.id.text_section);
        final TextView advisorCurrent = (TextView) findViewById(R.id.text_advisor);

        if (userID != null) {
            myRef.child(findID).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    String id = dataSnapshot.child("Student_ID").getValue().toString();
                    idCurrent.setText(id);
                    String name = dataSnapshot.child("Name").getValue().toString();
                    nameCurrent.setText(name);
                    String advisorID = dataSnapshot.child("AdvisorID").getValue().toString();

                    FirebaseFirestore fr = FirebaseFirestore.getInstance();
                    final CollectionReference myFirestore = fr.collection("Section");

                    myFirestore.document(dataSnapshot.child("Section_ID").getValue().toString()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    sectionCurrent.setText(document.getData().get("section").toString());
                                }
                            }
                        }
                    });

                    db.getReference("Users-Teacher").child(advisorID).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            advisorCurrent.setText(dataSnapshot.child("Name_prefix").getValue().toString() + dataSnapshot.child("Name").getValue().toString());
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        } else {
            Toast.makeText(ProfileUser_Page.this, "Error !", Toast.LENGTH_LONG).show();
        }
    }

}



