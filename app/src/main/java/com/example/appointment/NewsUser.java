package com.example.appointment;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class NewsUser extends AppCompatActivity {

    private String userID;
    private FirebaseDatabase db;
    private FirebaseAuth FireAuth;
    private DatabaseReference myRef;

    private ListView listNews;
    private TextView textempty;
    private String textID;
    private List<RetrieveNewsUser> ReceiveNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_user);

        listNews = (ListView) findViewById(R.id.listviewNews);
        textempty = (TextView) findViewById(R.id.textlistviewemptynews);
        listNews.setEmptyView(textempty);

        ReceiveNews = new ArrayList<RetrieveNewsUser>();

        FireAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        myRef = db.getReference("Users-Student");

        FirebaseUser userCur = FireAuth.getCurrentUser();
        userID = userCur.getUid();
        String userEmail = userCur.getEmail();
        String findID = userEmail.substring(0, 14);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final CollectionReference myFirestore = db.collection("Message");

        if (userID != null) {
            myRef.child(findID).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    final String value = dataSnapshot.child("Student_ID").getValue().toString();
                    textID = value;

                    myFirestore.whereArrayContains("student_ID", textID)
                            .whereEqualTo("status", "1").orderBy("timestamp", Query.Direction.ASCENDING)
                            .get()
                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    if (!queryDocumentSnapshots.isEmpty()) {
                                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                        for (DocumentSnapshot d : list) {
                                            RetrieveNewsUser r = d.toObject(RetrieveNewsUser.class);
                                            ReceiveNews.add(r);
                                        }
                                        RetrieveNewsUserAdapter NewsAdapter = new RetrieveNewsUserAdapter(NewsUser.this, ReceiveNews);
                                        listNews.setAdapter(NewsAdapter);
                                    }
                                }
                            });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        } else {
            Toast.makeText(NewsUser.this, "Error !", Toast.LENGTH_LONG).show();
        }

    }
}
