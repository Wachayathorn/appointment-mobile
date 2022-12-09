package com.example.appointment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class TeacherApp_Page extends AppCompatActivity {

    private String userID;
    private FirebaseDatabase db;
    private FirebaseAuth FireAuth;
    private DatabaseReference myRef;

    List<RetrieveStatusUser> ReStatus;

    private TextView textempty;
    private ListView listStatus;
    private TextView textName;
    private String resultUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_app__page);

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

        ReStatus = new ArrayList<>();

        textName = (TextView) findViewById(R.id.textnameteacherapp);
        listStatus = (ListView) findViewById(R.id.listviewStatusTeacher);
        textempty = (TextView) findViewById(R.id.textlistviewempty1);
        listStatus.setEmptyView(textempty);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final CollectionReference myFirestore = db.collection("Appointment");

        if (userID != null) {
            myRef.orderByChild("Username").equalTo(resultUsername).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    textName.setText(dataSnapshot.child("Name_prefix").getValue().toString() + dataSnapshot.child("Name").getValue().toString());

                    Query query = myFirestore.whereEqualTo("teacherID", dataSnapshot.child("uID").getValue().toString()).whereEqualTo("status", "รอการตอบรับ").orderBy("timestamp", Query.Direction.ASCENDING);

                    query.get()
                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    if (!queryDocumentSnapshots.isEmpty()) {
                                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                        for (DocumentSnapshot d : list) {
                                            RetrieveStatusUser r = d.toObject(RetrieveStatusUser.class);
                                            ReStatus.add(r);
                                        }
                                        RetrieveStatusTeacherAdapter StatusAdapter = new RetrieveStatusTeacherAdapter(TeacherApp_Page.this, ReStatus);
                                        listStatus.setAdapter(StatusAdapter);
                                    }
                                }
                            });
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
            Toast.makeText(TeacherApp_Page.this, "Error !", Toast.LENGTH_LONG).show();
        }
    }
}
