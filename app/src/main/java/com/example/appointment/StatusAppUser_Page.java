package com.example.appointment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class StatusAppUser_Page extends AppCompatActivity {

    private String userID;
    private FirebaseDatabase db;
    private FirebaseAuth FireAuth;
    private DatabaseReference myRef;

    private ListView listStatus;
    private TextView textempty;
    private String textID;
    private List<RetrieveStatusUser> ReStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_app_user__page);

        listStatus = (ListView) findViewById(R.id.listviewStatusUser);
        textempty = (TextView) findViewById(R.id.textlistviewempty);
        listStatus.setEmptyView(textempty);

        ReStatus = new ArrayList<>();

        FireAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        myRef = db.getReference("Users-Student");

        FirebaseUser userCur = FireAuth.getCurrentUser();
        userID = userCur.getUid();
        String userEmail = userCur.getEmail();
        String findID = userEmail.substring(0, 14);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final CollectionReference myFirestore = db.collection("Appointment");

        if (userID != null) {
            myRef.child(findID).addValueEventListener(new ValueEventListener() {
                @SuppressLint("ResourceType")
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    final String value = dataSnapshot.child("Student_ID").getValue().toString();
                    textID = value;

                    myFirestore.whereEqualTo("student_ID", textID)
                            .whereEqualTo("status", "รอการตอบรับ").orderBy("timestamp", Query.Direction.ASCENDING)
                            .get()
                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    if (!queryDocumentSnapshots.isEmpty()) {
                                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                        for (DocumentSnapshot d : list) {
                                            RetrieveStatusUser r = d.toObject(RetrieveStatusUser.class);
                                            ReStatus.add(r);
                                        }
                                        RetrieveStatusUserAdapter StatusAdapter = new RetrieveStatusUserAdapter(StatusAppUser_Page.this, ReStatus);
                                        listStatus.setAdapter(StatusAdapter);
                                    }
                                }
                            });

                    myFirestore.whereEqualTo("student_ID", textID)
                            .whereEqualTo("status", "อนุมัติ").orderBy("timestamp", Query.Direction.ASCENDING)
                            .get()
                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    if (!queryDocumentSnapshots.isEmpty()) {
                                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                        for (DocumentSnapshot d : list) {
                                            RetrieveStatusUser r = d.toObject(RetrieveStatusUser.class);
                                            ReStatus.add(r);
                                        }
                                        RetrieveStatusUserAdapter StatusAdapter = new RetrieveStatusUserAdapter(StatusAppUser_Page.this, ReStatus);
                                        listStatus.setAdapter(StatusAdapter);
                                    }
                                }
                            });

                    myFirestore.whereEqualTo("student_ID", textID)
                            .whereEqualTo("status", "ปฏิเสธ").orderBy("timestamp", Query.Direction.ASCENDING)
                            .get()
                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                                    if (!queryDocumentSnapshots.isEmpty()) {
                                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                        for (DocumentSnapshot d : list) {
                                            RetrieveStatusUser r = d.toObject(RetrieveStatusUser.class);
                                            ReStatus.add(r);
                                        }
                                        RetrieveStatusUserAdapter StatusAdapter = new RetrieveStatusUserAdapter(StatusAppUser_Page.this, ReStatus);
                                        listStatus.setAdapter(StatusAdapter);
                                    }
                                }
                            });

                    myFirestore.whereEqualTo("student_ID", textID)
                            .whereEqualTo("status", "กำลังดำเนินเอกสาร").orderBy("timestamp", Query.Direction.ASCENDING)
                            .get()
                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    if (!queryDocumentSnapshots.isEmpty()) {
                                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                        for (DocumentSnapshot d : list) {
                                            RetrieveStatusUser r = d.toObject(RetrieveStatusUser.class);
                                            ReStatus.add(r);
                                        }
                                        RetrieveStatusUserAdapter StatusAdapter = new RetrieveStatusUserAdapter(StatusAppUser_Page.this, ReStatus);
                                        listStatus.setAdapter(StatusAdapter);
                                    }
                                }
                            });

                    myFirestore.whereEqualTo("student_ID", textID)
                            .whereEqualTo("status", "ดำเนินเอกสารเสร็จสิ้น").whereEqualTo("barcode", null).orderBy("timestamp", Query.Direction.ASCENDING)
                            .get()
                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    if (!queryDocumentSnapshots.isEmpty()) {
                                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                        for (DocumentSnapshot d : list) {
                                            RetrieveStatusUser r = d.toObject(RetrieveStatusUser.class);
                                            ReStatus.add(r);
                                        }
                                        RetrieveStatusUserAdapter StatusAdapter = new RetrieveStatusUserAdapter(StatusAppUser_Page.this, ReStatus);
                                        listStatus.setAdapter(StatusAdapter);
                                    }
                                }
                            });

                    myFirestore.whereEqualTo("student_ID", textID)
                            .whereEqualTo("status", "กำลังยืมอุปกรณ์").orderBy("timestamp", Query.Direction.ASCENDING)
                            .get()
                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    if (!queryDocumentSnapshots.isEmpty()) {
                                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                        for (DocumentSnapshot d : list) {
                                            RetrieveStatusUser r = d.toObject(RetrieveStatusUser.class);
                                            ReStatus.add(r);
                                        }
                                        RetrieveStatusUserAdapter StatusAdapter = new RetrieveStatusUserAdapter(StatusAppUser_Page.this, ReStatus);
                                        listStatus.setAdapter(StatusAdapter);
                                    }
                                }
                            });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else {
            Toast.makeText(StatusAppUser_Page.this, "Error !", Toast.LENGTH_LONG).show();
        }
    }

}
