package com.example.appointment;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class UserCheckTime_Page extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spChooseTimeTeacher;
    private ArrayList<String> mylist;
    private TextView textDate;
    private ArrayList<String> allTeacherID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_check_time__page);

        textDate = (TextView) findViewById(R.id.textCurrentWeek);
        spChooseTimeTeacher = (Spinner) findViewById(R.id.spChooseCheckTimeUser);
        spChooseTimeTeacher.setOnItemSelectedListener(this);

        allTeacherID = new ArrayList<String>();
        // จำเป็นต้องใส่ add ไว้ก่อน
        mylist = new ArrayList<String>();
        mylist.add("--- เลือกอาจารย์ ---");
        // Query from Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final CollectionReference myTime = db.collection("Timetable");
        final FirebaseDatabase RTDB = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = RTDB.getReference("Users-Teacher");

        SimpleDateFormat form = new SimpleDateFormat("dd/MM/yyyy");
        final Calendar now = Calendar.getInstance();
        String[] days = new String[7];
        int delta = -now.get(GregorianCalendar.DAY_OF_WEEK) + 1;
        now.add(Calendar.DAY_OF_MONTH, delta);
        for (int i = 0; i < 7; i++) {
            days[i] = form.format(now.getTime());
            now.add(Calendar.DAY_OF_MONTH, 1);
        }
        textDate.setText("ตารางเวลาวัน : จันทร์, " + days[1] + " - ศุกร์, " + days[5]);
        myTime.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<String> allName = new ArrayList<String>();
                            for (final QueryDocumentSnapshot document : task.getResult()) {
                                if (!allName.contains(document.getString("teacher_ID").toString())) {
                                    allName.add(document.getString("teacher_ID").toString());
                                    if (document.getString("teacher_ID").toString().equals("ฝ่ายธุรการ") || document.getString("teacher_ID").toString().equals("ฝ่ายบริการ")) {
                                        mylist.add(document.getString("teacher_ID").toString());
                                        allTeacherID.add(document.getString("teacher_ID").toString());
                                    } else {
                                        myRef.child(document.getString("teacher_ID").toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                if (dataSnapshot.getValue() != null) {
                                                    String name = dataSnapshot.child("Name_prefix").getValue().toString() + dataSnapshot.child("Name").getValue().toString();
                                                    mylist.add(name);
                                                    allTeacherID.add(dataSnapshot.child("uID").getValue().toString());
                                                } else {
                                                    mylist.add(dataSnapshot.child("uID").getValue().toString());
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                    }
                                }
                            }
                        } else {
                            Log.v("TEST", "Error query");
                        }
                    }
                });

        ArrayAdapter<String> adaptertimeteacher = new ArrayAdapter<String>(this, R.layout.spinnertext, mylist);
        spChooseTimeTeacher.setAdapter(adaptertimeteacher);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final CollectionReference myTime = db.collection("Timetable");
        String nameTeacher = parent.getItemAtPosition(position).toString();
        String chooseTeacherTime = "1";
        if (parent.getSelectedItemPosition() != 0) {
            if (nameTeacher.equals("ฝ่ายธุรการ") || nameTeacher.equals("ฝ่ายบริการ")) {
                chooseTeacherTime = nameTeacher;
            } else {
                chooseTeacherTime = allTeacherID.get(parent.getSelectedItemPosition() - 1);
            }
        }

        myTime.document(chooseTeacherTime + "_จันทร์").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    Boolean mon1 = (Boolean) documentSnapshot.getBoolean("9:00 น - 9:15 น");
                    TextView textmon1 = (TextView) findViewById(R.id.timemon1);
                    if (mon1 == null || mon1 == false) {
                        textmon1.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textmon1.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean mon2 = (Boolean) documentSnapshot.getBoolean("9:15 น - 9:30 น");
                    TextView textmon2 = (TextView) findViewById(R.id.timemon2);
                    if (mon2 == null || mon2 == false) {
                        textmon2.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textmon2.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean mon3 = (Boolean) documentSnapshot.getBoolean("9:30 น - 9:45 น");
                    TextView textmon3 = (TextView) findViewById(R.id.timemon3);
                    if (mon3 == null || mon3 == false) {
                        textmon3.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textmon3.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean mon4 = (Boolean) documentSnapshot.getBoolean("9:45 น - 10:00 น");
                    TextView textmon4 = (TextView) findViewById(R.id.timemon4);
                    if (mon4 == null || mon4 == false) {
                        textmon4.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textmon4.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean mon5 = (Boolean) documentSnapshot.getBoolean("10:00 น - 10:15 น");
                    TextView textmon5 = (TextView) findViewById(R.id.timemon5);
                    if (mon5 == null || mon5 == false) {
                        textmon5.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textmon5.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean mon6 = (Boolean) documentSnapshot.getBoolean("10:15 น - 10:30 น");
                    TextView textmon6 = (TextView) findViewById(R.id.timemon6);
                    if (mon6 == null || mon6 == false) {
                        textmon6.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textmon6.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean mon7 = (Boolean) documentSnapshot.getBoolean("10:30 น - 10:45 น");
                    TextView textmon7 = (TextView) findViewById(R.id.timemon7);
                    if (mon7 == null || mon7 == false) {
                        textmon7.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textmon7.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean mon8 = (Boolean) documentSnapshot.getBoolean("10:45 น - 11:00 น");
                    TextView textmon8 = (TextView) findViewById(R.id.timemon8);
                    if (mon8 == null || mon8 == false) {
                        textmon8.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textmon8.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean mon9 = (Boolean) documentSnapshot.getBoolean("11:00 น - 11:15 น");
                    TextView textmon9 = (TextView) findViewById(R.id.timemon9);
                    if (mon9 == null || mon9 == false) {
                        textmon9.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textmon9.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean mon10 = (Boolean) documentSnapshot.getBoolean("11:15 น - 11:30 น");
                    TextView textmon10 = (TextView) findViewById(R.id.timemon10);
                    if (mon10 == null || mon10 == false) {
                        textmon10.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textmon10.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean mon11 = (Boolean) documentSnapshot.getBoolean("11:30 น - 11:45 น");
                    TextView textmon11 = (TextView) findViewById(R.id.timemon11);
                    if (mon11 == null || mon11 == false) {
                        textmon11.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textmon11.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean mon12 = (Boolean) documentSnapshot.getBoolean("11:45 น - 12:00 น");
                    TextView textmon12 = (TextView) findViewById(R.id.timemon12);
                    if (mon12 == null || mon12 == false) {
                        textmon12.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textmon12.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean mon13 = (Boolean) documentSnapshot.getBoolean("12:00 น - 12:15 น");
                    TextView textmon13 = (TextView) findViewById(R.id.timemon13);
                    if (mon13 == null || mon13 == false) {
                        textmon13.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textmon13.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean mon14 = (Boolean) documentSnapshot.getBoolean("12:15 น - 12:30 น");
                    TextView textmon14 = (TextView) findViewById(R.id.timemon14);
                    if (mon14 == null || mon14 == false) {
                        textmon14.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textmon14.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean mon15 = (Boolean) documentSnapshot.getBoolean("12:30 น - 12:45 น");
                    TextView textmon15 = (TextView) findViewById(R.id.timemon15);
                    if (mon15 == null || mon15 == false) {
                        textmon15.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textmon15.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean mon16 = (Boolean) documentSnapshot.getBoolean("12:45 น - 13:00 น");
                    TextView textmon16 = (TextView) findViewById(R.id.timemon16);
                    if (mon16 == null || mon16 == false) {
                        textmon16.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textmon16.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean mon17 = (Boolean) documentSnapshot.getBoolean("13:00 น - 13:15 น");
                    TextView textmon17 = (TextView) findViewById(R.id.timemon17);
                    if (mon17 == null || mon17 == false) {
                        textmon17.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textmon17.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean mon18 = (Boolean) documentSnapshot.getBoolean("13:15 น - 13:30 น");
                    TextView textmon18 = (TextView) findViewById(R.id.timemon18);
                    if (mon18 == null || mon18 == false) {
                        textmon18.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textmon18.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean mon19 = (Boolean) documentSnapshot.getBoolean("13:30 น - 13:45 น");
                    TextView textmon19 = (TextView) findViewById(R.id.timemon19);
                    if (mon19 == null || mon19 == false) {
                        textmon19.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textmon19.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean mon20 = (Boolean) documentSnapshot.getBoolean("13:45 น - 14:00 น");
                    TextView textmon20 = (TextView) findViewById(R.id.timemon20);
                    if (mon20 == null || mon20 == false) {
                        textmon20.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textmon20.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean mon21 = (Boolean) documentSnapshot.getBoolean("14:00 น - 14:15 น");
                    TextView textmon21 = (TextView) findViewById(R.id.timemon21);
                    if (mon21 == null || mon21 == false) {
                        textmon21.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textmon21.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean mon22 = (Boolean) documentSnapshot.getBoolean("14:15 น - 14:30 น");
                    TextView textmon22 = (TextView) findViewById(R.id.timemon22);
                    if (mon22 == null || mon22 == false) {
                        textmon22.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textmon22.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean mon23 = (Boolean) documentSnapshot.getBoolean("14:30 น - 14:45 น");
                    TextView textmon23 = (TextView) findViewById(R.id.timemon23);
                    if (mon23 == null || mon23 == false) {
                        textmon23.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textmon23.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean mon24 = (Boolean) documentSnapshot.getBoolean("14:45 น - 15:00 น");
                    TextView textmon24 = (TextView) findViewById(R.id.timemon24);
                    if (mon24 == null || mon24 == false) {
                        textmon24.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textmon24.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean mon25 = (Boolean) documentSnapshot.getBoolean("15:00 น - 15:15 น");
                    TextView textmon25 = (TextView) findViewById(R.id.timemon25);
                    if (mon25 == null || mon25 == false) {
                        textmon25.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textmon25.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean mon26 = (Boolean) documentSnapshot.getBoolean("15:15 น - 15:30 น");
                    TextView textmon26 = (TextView) findViewById(R.id.timemon26);
                    if (mon26 == null || mon26 == false) {
                        textmon26.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textmon26.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean mon27 = (Boolean) documentSnapshot.getBoolean("15:30 น - 15:45 น");
                    TextView textmon27 = (TextView) findViewById(R.id.timemon27);
                    if (mon27 == null || mon27 == false) {
                        textmon27.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textmon27.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean mon28 = (Boolean) documentSnapshot.getBoolean("15:45 น - 16:00 น");
                    TextView textmon28 = (TextView) findViewById(R.id.timemon28);
                    if (mon28 == null || mon28 == false) {
                        textmon28.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textmon28.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean mon29 = (Boolean) documentSnapshot.getBoolean("16:00 น - 16:15 น");
                    TextView textmon29 = (TextView) findViewById(R.id.timemon29);
                    if (mon29 == null || mon29 == false) {
                        textmon29.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textmon29.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean mon30 = (Boolean) documentSnapshot.getBoolean("16:15 น - 16:30 น");
                    TextView textmon30 = (TextView) findViewById(R.id.timemon30);
                    if (mon30 == null || mon30 == false) {
                        textmon30.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textmon30.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean mon31 = (Boolean) documentSnapshot.getBoolean("16:30 น - 16:45 น");
                    TextView textmon31 = (TextView) findViewById(R.id.timemon31);
                    if (mon31 == null || mon31 == false) {
                        textmon31.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textmon31.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean mon32 = (Boolean) documentSnapshot.getBoolean("16:45 น - 17:00 น");
                    TextView textmon32 = (TextView) findViewById(R.id.timemon32);
                    if (mon32 == null || mon32 == false) {
                        textmon32.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textmon32.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean mon33 = (Boolean) documentSnapshot.getBoolean("17:00 น - 17:15 น");
                    TextView textmon33 = (TextView) findViewById(R.id.timemon33);
                    if (mon33 == null || mon33 == false) {
                        textmon33.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textmon33.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean mon34 = (Boolean) documentSnapshot.getBoolean("17:15 น - 17:30 น");
                    TextView textmon34 = (TextView) findViewById(R.id.timemon34);
                    if (mon34 == null || mon34 == false) {
                        textmon34.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textmon34.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean mon35 = (Boolean) documentSnapshot.getBoolean("17:30 น - 17:45 น");
                    TextView textmon35 = (TextView) findViewById(R.id.timemon35);
                    if (mon35 == null || mon35 == false) {
                        textmon35.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textmon35.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean mon36 = (Boolean) documentSnapshot.getBoolean("17:45 น - 18:00 น");
                    TextView textmon36 = (TextView) findViewById(R.id.timemon36);
                    if (mon36 == null || mon36 == false) {
                        textmon36.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textmon36.setBackgroundResource(R.color.NoFreeTime);
                    }
                }
            }
        });

        myTime.document(chooseTeacherTime + "_อังคาร").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    Boolean tue1 = (Boolean) documentSnapshot.getBoolean("9:00 น - 9:15 น");
                    TextView texttue1 = (TextView) findViewById(R.id.timetue1);
                    if (tue1 == null || tue1 == false) {
                        texttue1.setBackgroundResource(R.color.FreeTime);
                    } else {
                        texttue1.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean tue2 = (Boolean) documentSnapshot.getBoolean("9:15 น - 9:30 น");
                    TextView texttue2 = (TextView) findViewById(R.id.timetue2);
                    if (tue2 == null || tue2 == false) {
                        texttue2.setBackgroundResource(R.color.FreeTime);
                    } else {
                        texttue2.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean tue3 = (Boolean) documentSnapshot.getBoolean("9:30 น - 9:45 น");
                    TextView texttue3 = (TextView) findViewById(R.id.timetue3);
                    if (tue3 == null || tue3 == false) {
                        texttue3.setBackgroundResource(R.color.FreeTime);
                    } else {
                        texttue3.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean tue4 = (Boolean) documentSnapshot.getBoolean("9:45 น - 10:00 น");
                    TextView texttue4 = (TextView) findViewById(R.id.timetue4);
                    if (tue4 == null || tue4 == false) {
                        texttue4.setBackgroundResource(R.color.FreeTime);
                    } else {
                        texttue4.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean tue5 = (Boolean) documentSnapshot.getBoolean("10:00 น - 10:15 น");
                    TextView texttue5 = (TextView) findViewById(R.id.timetue5);
                    if (tue5 == null || tue5 == false) {
                        texttue5.setBackgroundResource(R.color.FreeTime);
                    } else {
                        texttue5.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean tue6 = (Boolean) documentSnapshot.getBoolean("10:15 น - 10:30 น");
                    TextView texttue6 = (TextView) findViewById(R.id.timetue6);
                    if (tue6 == null || tue6 == false) {
                        texttue6.setBackgroundResource(R.color.FreeTime);
                    } else {
                        texttue6.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean tue7 = (Boolean) documentSnapshot.getBoolean("10:30 น - 10:45 น");
                    TextView texttue7 = (TextView) findViewById(R.id.timetue7);
                    if (tue7 == null || tue7 == false) {
                        texttue7.setBackgroundResource(R.color.FreeTime);
                    } else {
                        texttue7.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean tue8 = (Boolean) documentSnapshot.getBoolean("10:45 น - 11:00 น");
                    TextView texttue8 = (TextView) findViewById(R.id.timetue8);
                    if (tue8 == null || tue8 == false) {
                        texttue8.setBackgroundResource(R.color.FreeTime);
                    } else {
                        texttue8.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean tue9 = (Boolean) documentSnapshot.getBoolean("11:00 น - 11:15 น");
                    TextView texttue9 = (TextView) findViewById(R.id.timetue9);
                    if (tue9 == null || tue9 == false) {
                        texttue9.setBackgroundResource(R.color.FreeTime);
                    } else {
                        texttue9.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean tue10 = (Boolean) documentSnapshot.getBoolean("11:15 น - 11:30 น");
                    TextView texttue10 = (TextView) findViewById(R.id.timetue10);
                    if (tue10 == null || tue10 == false) {
                        texttue10.setBackgroundResource(R.color.FreeTime);
                    } else {
                        texttue10.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean tue11 = (Boolean) documentSnapshot.getBoolean("11:30 น - 11:45 น");
                    TextView texttue11 = (TextView) findViewById(R.id.timetue11);
                    if (tue11 == null || tue11 == false) {
                        texttue11.setBackgroundResource(R.color.FreeTime);
                    } else {
                        texttue11.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean tue12 = (Boolean) documentSnapshot.getBoolean("11:45 น - 12:00 น");
                    TextView texttue12 = (TextView) findViewById(R.id.timetue12);
                    if (tue12 == null || tue12 == false) {
                        texttue12.setBackgroundResource(R.color.FreeTime);
                    } else {
                        texttue12.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean tue13 = (Boolean) documentSnapshot.getBoolean("12:00 น - 12:15 น");
                    TextView texttue13 = (TextView) findViewById(R.id.timetue13);
                    if (tue13 == null || tue13 == false) {
                        texttue13.setBackgroundResource(R.color.FreeTime);
                    } else {
                        texttue13.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean tue14 = (Boolean) documentSnapshot.getBoolean("12:15 น - 12:30 น");
                    TextView texttue14 = (TextView) findViewById(R.id.timetue14);
                    if (tue14 == null || tue14 == false) {
                        texttue14.setBackgroundResource(R.color.FreeTime);
                    } else {
                        texttue14.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean tue15 = (Boolean) documentSnapshot.getBoolean("12:30 น - 12:45 น");
                    TextView texttue15 = (TextView) findViewById(R.id.timetue15);
                    if (tue15 == null || tue15 == false) {
                        texttue15.setBackgroundResource(R.color.FreeTime);
                    } else {
                        texttue15.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean tue16 = (Boolean) documentSnapshot.getBoolean("12:45 น - 13:00 น");
                    TextView texttue16 = (TextView) findViewById(R.id.timetue16);
                    if (tue16 == null || tue16 == false) {
                        texttue16.setBackgroundResource(R.color.FreeTime);
                    } else {
                        texttue16.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean tue17 = (Boolean) documentSnapshot.getBoolean("13:00 น - 13:15 น");
                    TextView texttue17 = (TextView) findViewById(R.id.timetue17);
                    if (tue17 == null || tue17 == false) {
                        texttue17.setBackgroundResource(R.color.FreeTime);
                    } else {
                        texttue17.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean tue18 = (Boolean) documentSnapshot.getBoolean("13:15 น - 13:30 น");
                    TextView texttue18 = (TextView) findViewById(R.id.timetue18);
                    if (tue18 == null || tue18 == false) {
                        texttue18.setBackgroundResource(R.color.FreeTime);
                    } else {
                        texttue18.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean tue19 = (Boolean) documentSnapshot.getBoolean("13:30 น - 13:45 น");
                    TextView texttue19 = (TextView) findViewById(R.id.timetue19);
                    if (tue19 == null || tue19 == false) {
                        texttue19.setBackgroundResource(R.color.FreeTime);
                    } else {
                        texttue19.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean tue20 = (Boolean) documentSnapshot.getBoolean("13:45 น - 14:00 น");
                    TextView texttue20 = (TextView) findViewById(R.id.timetue20);
                    if (tue20 == null || tue20 == false) {
                        texttue20.setBackgroundResource(R.color.FreeTime);
                    } else {
                        texttue20.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean tue21 = (Boolean) documentSnapshot.getBoolean("14:00 น - 14:15 น");
                    TextView texttue21 = (TextView) findViewById(R.id.timetue21);
                    if (tue21 == null || tue21 == false) {
                        texttue21.setBackgroundResource(R.color.FreeTime);
                    } else {
                        texttue21.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean tue22 = (Boolean) documentSnapshot.getBoolean("14:15 น - 14:30 น");
                    TextView texttue22 = (TextView) findViewById(R.id.timetue22);
                    if (tue22 == null || tue22 == false) {
                        texttue22.setBackgroundResource(R.color.FreeTime);
                    } else {
                        texttue22.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean tue23 = (Boolean) documentSnapshot.getBoolean("14:30 น - 14:45 น");
                    TextView texttue23 = (TextView) findViewById(R.id.timetue23);
                    if (tue23 == null || tue23 == false) {
                        texttue23.setBackgroundResource(R.color.FreeTime);
                    } else {
                        texttue23.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean tue24 = (Boolean) documentSnapshot.getBoolean("14:45 น - 15:00 น");
                    TextView texttue24 = (TextView) findViewById(R.id.timetue24);
                    if (tue24 == null || tue24 == false) {
                        texttue24.setBackgroundResource(R.color.FreeTime);
                    } else {
                        texttue24.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean tue25 = (Boolean) documentSnapshot.getBoolean("15:00 น - 15:15 น");
                    TextView texttue25 = (TextView) findViewById(R.id.timetue25);
                    if (tue25 == null || tue25 == false) {
                        texttue25.setBackgroundResource(R.color.FreeTime);
                    } else {
                        texttue25.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean tue26 = (Boolean) documentSnapshot.getBoolean("15:15 น - 15:30 น");
                    TextView texttue26 = (TextView) findViewById(R.id.timetue26);
                    if (tue26 == null || tue26 == false) {
                        texttue26.setBackgroundResource(R.color.FreeTime);
                    } else {
                        texttue26.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean tue27 = (Boolean) documentSnapshot.getBoolean("15:30 น - 15:45 น");
                    TextView texttue27 = (TextView) findViewById(R.id.timetue27);
                    if (tue27 == null || tue27 == false) {
                        texttue27.setBackgroundResource(R.color.FreeTime);
                    } else {
                        texttue27.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean tue28 = (Boolean) documentSnapshot.getBoolean("15:45 น - 16:00 น");
                    TextView texttue28 = (TextView) findViewById(R.id.timetue28);
                    if (tue28 == null || tue28 == false) {
                        texttue28.setBackgroundResource(R.color.FreeTime);
                    } else {
                        texttue28.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean tue29 = (Boolean) documentSnapshot.getBoolean("16:00 น - 16:15 น");
                    TextView texttue29 = (TextView) findViewById(R.id.timetue29);
                    if (tue29 == null || tue29 == false) {
                        texttue29.setBackgroundResource(R.color.FreeTime);
                    } else {
                        texttue29.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean tue30 = (Boolean) documentSnapshot.getBoolean("16:15 น - 16:30 น");
                    TextView texttue30 = (TextView) findViewById(R.id.timetue30);
                    if (tue30 == null || tue30 == false) {
                        texttue30.setBackgroundResource(R.color.FreeTime);
                    } else {
                        texttue30.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean tue31 = (Boolean) documentSnapshot.getBoolean("16:30 น - 16:45 น");
                    TextView texttue31 = (TextView) findViewById(R.id.timetue31);
                    if (tue31 == null || tue31 == false) {
                        texttue31.setBackgroundResource(R.color.FreeTime);
                    } else {
                        texttue31.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean tue32 = (Boolean) documentSnapshot.getBoolean("16:45 น - 17:00 น");
                    TextView texttue32 = (TextView) findViewById(R.id.timetue32);
                    if (tue32 == null || tue32 == false) {
                        texttue32.setBackgroundResource(R.color.FreeTime);
                    } else {
                        texttue32.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean tue33 = (Boolean) documentSnapshot.getBoolean("17:00 น - 17:15 น");
                    TextView texttue33 = (TextView) findViewById(R.id.timetue33);
                    if (tue33 == null || tue33 == false) {
                        texttue33.setBackgroundResource(R.color.FreeTime);
                    } else {
                        texttue33.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean tue34 = (Boolean) documentSnapshot.getBoolean("17:15 น - 17:30 น");
                    TextView texttue34 = (TextView) findViewById(R.id.timetue34);
                    if (tue34 == null || tue34 == false) {
                        texttue34.setBackgroundResource(R.color.FreeTime);
                    } else {
                        texttue34.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean tue35 = (Boolean) documentSnapshot.getBoolean("17:30 น - 17:45 น");
                    TextView texttue35 = (TextView) findViewById(R.id.timetue35);
                    if (tue35 == null || tue35 == false) {
                        texttue35.setBackgroundResource(R.color.FreeTime);
                    } else {
                        texttue35.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean tue36 = (Boolean) documentSnapshot.getBoolean("17:45 น - 18:00 น");
                    TextView texttue36 = (TextView) findViewById(R.id.timetue36);
                    if (tue36 == null || tue36 == false) {
                        texttue36.setBackgroundResource(R.color.FreeTime);
                    } else {
                        texttue36.setBackgroundResource(R.color.NoFreeTime);
                    }
                }
            }
        });

        myTime.document(chooseTeacherTime + "_พุธ").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    Boolean wed1 = (Boolean) documentSnapshot.getBoolean("9:00 น - 9:15 น");
                    TextView textwed1 = (TextView) findViewById(R.id.timewed1);
                    if (wed1 == null || wed1 == false) {
                        textwed1.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textwed1.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean wed2 = (Boolean) documentSnapshot.getBoolean("9:15 น - 9:30 น");
                    TextView textwed2 = (TextView) findViewById(R.id.timewed2);
                    if (wed2 == null || wed2 == false) {
                        textwed2.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textwed2.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean wed3 = (Boolean) documentSnapshot.getBoolean("9:30 น - 9:45 น");
                    TextView textwed3 = (TextView) findViewById(R.id.timewed3);
                    if (wed3 == null || wed3 == false) {
                        textwed3.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textwed3.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean wed4 = (Boolean) documentSnapshot.getBoolean("9:45 น - 10:00 น");
                    TextView textwed4 = (TextView) findViewById(R.id.timewed4);
                    if (wed4 == null || wed4 == false) {
                        textwed4.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textwed4.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean wed5 = (Boolean) documentSnapshot.getBoolean("10:00 น - 10:15 น");
                    TextView textwed5 = (TextView) findViewById(R.id.timewed5);
                    if (wed5 == null || wed5 == false) {
                        textwed5.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textwed5.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean wed6 = (Boolean) documentSnapshot.getBoolean("10:15 น - 10:30 น");
                    TextView textwed6 = (TextView) findViewById(R.id.timewed6);
                    if (wed6 == null || wed6 == false) {
                        textwed6.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textwed6.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean wed7 = (Boolean) documentSnapshot.getBoolean("10:30 น - 10:45 น");
                    TextView textwed7 = (TextView) findViewById(R.id.timewed7);
                    if (wed7 == null || wed7 == false) {
                        textwed7.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textwed7.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean wed8 = (Boolean) documentSnapshot.getBoolean("10:45 น - 11:00 น");
                    TextView textwed8 = (TextView) findViewById(R.id.timewed8);
                    if (wed8 == null || wed8 == false) {
                        textwed8.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textwed8.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean wed9 = (Boolean) documentSnapshot.getBoolean("11:00 น - 11:15 น");
                    TextView textwed9 = (TextView) findViewById(R.id.timewed9);
                    if (wed9 == null || wed9 == false) {
                        textwed9.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textwed9.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean wed10 = (Boolean) documentSnapshot.getBoolean("11:15 น - 11:30 น");
                    TextView textwed10 = (TextView) findViewById(R.id.timewed10);
                    if (wed10 == null || wed10 == false) {
                        textwed10.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textwed10.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean wed11 = (Boolean) documentSnapshot.getBoolean("11:30 น - 11:45 น");
                    TextView textwed11 = (TextView) findViewById(R.id.timewed11);
                    if (wed11 == null || wed11 == false) {
                        textwed11.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textwed11.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean wed12 = (Boolean) documentSnapshot.getBoolean("11:45 น - 12:00 น");
                    TextView textwed12 = (TextView) findViewById(R.id.timewed12);
                    if (wed12 == null || wed12 == false) {
                        textwed12.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textwed12.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean wed13 = (Boolean) documentSnapshot.getBoolean("12:00 น - 12:15 น");
                    TextView textwed13 = (TextView) findViewById(R.id.timewed13);
                    if (wed13 == null || wed13 == false) {
                        textwed13.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textwed13.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean wed14 = (Boolean) documentSnapshot.getBoolean("12:15 น - 12:30 น");
                    TextView textwed14 = (TextView) findViewById(R.id.timewed14);
                    if (wed14 == null || wed14 == false) {
                        textwed14.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textwed14.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean wed15 = (Boolean) documentSnapshot.getBoolean("12:30 น - 12:45 น");
                    TextView textwed15 = (TextView) findViewById(R.id.timewed15);
                    if (wed15 == null || wed15 == false) {
                        textwed15.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textwed15.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean wed16 = (Boolean) documentSnapshot.getBoolean("12:45 น - 13:00 น");
                    TextView textwed16 = (TextView) findViewById(R.id.timewed16);
                    if (wed16 == null || wed16 == false) {
                        textwed16.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textwed16.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean wed17 = (Boolean) documentSnapshot.getBoolean("13:00 น - 13:15 น");
                    TextView textwed17 = (TextView) findViewById(R.id.timewed17);
                    if (wed17 == null || wed17 == false) {
                        textwed17.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textwed17.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean wed18 = (Boolean) documentSnapshot.getBoolean("13:15 น - 13:30 น");
                    TextView textwed18 = (TextView) findViewById(R.id.timewed18);
                    if (wed18 == null || wed18 == false) {
                        textwed18.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textwed18.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean wed19 = (Boolean) documentSnapshot.getBoolean("13:30 น - 13:45 น");
                    TextView textwed19 = (TextView) findViewById(R.id.timewed19);
                    if (wed19 == null || wed19 == false) {
                        textwed19.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textwed19.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean wed20 = (Boolean) documentSnapshot.getBoolean("13:45 น - 14:00 น");
                    TextView textwed20 = (TextView) findViewById(R.id.timewed20);
                    if (wed20 == null || wed20 == false) {
                        textwed20.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textwed20.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean wed21 = (Boolean) documentSnapshot.getBoolean("14:00 น - 14:15 น");
                    TextView textwed21 = (TextView) findViewById(R.id.timewed21);
                    if (wed21 == null || wed21 == false) {
                        textwed21.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textwed21.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean wed22 = (Boolean) documentSnapshot.getBoolean("14:15 น - 14:30 น");
                    TextView textwed22 = (TextView) findViewById(R.id.timewed22);
                    if (wed22 == null || wed22 == false) {
                        textwed22.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textwed22.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean wed23 = (Boolean) documentSnapshot.getBoolean("14:30 น - 14:45 น");
                    TextView textwed23 = (TextView) findViewById(R.id.timewed23);
                    if (wed23 == null || wed23 == false) {
                        textwed23.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textwed23.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean wed24 = (Boolean) documentSnapshot.getBoolean("14:45 น - 15:00 น");
                    TextView textwed24 = (TextView) findViewById(R.id.timewed24);
                    if (wed24 == null || wed24 == false) {
                        textwed24.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textwed24.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean wed25 = (Boolean) documentSnapshot.getBoolean("15:00 น - 15:15 น");
                    TextView textwed25 = (TextView) findViewById(R.id.timewed25);
                    if (wed25 == null || wed25 == false) {
                        textwed25.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textwed25.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean wed26 = (Boolean) documentSnapshot.getBoolean("15:15 น - 15:30 น");
                    TextView textwed26 = (TextView) findViewById(R.id.timewed26);
                    if (wed26 == null || wed26 == false) {
                        textwed26.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textwed26.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean wed27 = (Boolean) documentSnapshot.getBoolean("15:30 น - 15:45 น");
                    TextView textwed27 = (TextView) findViewById(R.id.timewed27);
                    if (wed27 == null || wed27 == false) {
                        textwed27.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textwed27.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean wed28 = (Boolean) documentSnapshot.getBoolean("15:45 น - 16:00 น");
                    TextView textwed28 = (TextView) findViewById(R.id.timewed28);
                    if (wed28 == null || wed28 == false) {
                        textwed28.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textwed28.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean wed29 = (Boolean) documentSnapshot.getBoolean("16:00 น - 16:15 น");
                    TextView textwed29 = (TextView) findViewById(R.id.timewed29);
                    if (wed29 == null || wed29 == false) {
                        textwed29.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textwed29.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean wed30 = (Boolean) documentSnapshot.getBoolean("16:15 น - 16:30 น");
                    TextView textwed30 = (TextView) findViewById(R.id.timewed30);
                    if (wed30 == null || wed30 == false) {
                        textwed30.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textwed30.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean wed31 = (Boolean) documentSnapshot.getBoolean("16:30 น - 16:45 น");
                    TextView textwed31 = (TextView) findViewById(R.id.timewed31);
                    if (wed31 == null || wed31 == false) {
                        textwed31.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textwed31.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean wed32 = (Boolean) documentSnapshot.getBoolean("16:45 น - 17:00 น");
                    TextView textwed32 = (TextView) findViewById(R.id.timewed32);
                    if (wed32 == null || wed32 == false) {
                        textwed32.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textwed32.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean wed33 = (Boolean) documentSnapshot.getBoolean("17:00 น - 17:15 น");
                    TextView textwed33 = (TextView) findViewById(R.id.timewed33);
                    if (wed33 == null || wed33 == false) {
                        textwed33.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textwed33.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean wed34 = (Boolean) documentSnapshot.getBoolean("17:15 น - 17:30 น");
                    TextView textwed34 = (TextView) findViewById(R.id.timewed34);
                    if (wed34 == null || wed34 == false) {
                        textwed34.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textwed34.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean wed35 = (Boolean) documentSnapshot.getBoolean("17:30 น - 17:45 น");
                    TextView textwed35 = (TextView) findViewById(R.id.timewed35);
                    if (wed35 == null || wed35 == false) {
                        textwed35.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textwed35.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean wed36 = (Boolean) documentSnapshot.getBoolean("17:45 น - 18:00 น");
                    TextView textwed36 = (TextView) findViewById(R.id.timewed36);
                    if (wed36 == null || wed36 == false) {
                        textwed36.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textwed36.setBackgroundResource(R.color.NoFreeTime);
                    }
                }
            }
        });

        myTime.document(chooseTeacherTime + "_พฤหัสบดี").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    Boolean thu1 = (Boolean) documentSnapshot.getBoolean("9:00 น - 9:15 น");
                    TextView textthu1 = (TextView) findViewById(R.id.timethu1);
                    if (thu1 == null || thu1 == false) {
                        textthu1.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textthu1.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean thu2 = (Boolean) documentSnapshot.getBoolean("9:15 น - 9:30 น");
                    TextView textthu2 = (TextView) findViewById(R.id.timethu2);
                    if (thu2 == null || thu2 == false) {
                        textthu2.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textthu2.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean thu3 = (Boolean) documentSnapshot.getBoolean("9:30 น - 9:45 น");
                    TextView textthu3 = (TextView) findViewById(R.id.timethu3);
                    if (thu3 == null || thu3 == false) {
                        textthu3.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textthu3.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean thu4 = (Boolean) documentSnapshot.getBoolean("9:45 น - 10:00 น");
                    TextView textthu4 = (TextView) findViewById(R.id.timethu4);
                    if (thu4 == null || thu4 == false) {
                        textthu4.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textthu4.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean thu5 = (Boolean) documentSnapshot.getBoolean("10:00 น - 10:15 น");
                    TextView textthu5 = (TextView) findViewById(R.id.timethu5);
                    if (thu5 == null || thu5 == false) {
                        textthu5.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textthu5.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean thu6 = (Boolean) documentSnapshot.getBoolean("10:15 น - 10:30 น");
                    TextView textthu6 = (TextView) findViewById(R.id.timethu6);
                    if (thu6 == null || thu6 == false) {
                        textthu6.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textthu6.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean thu7 = (Boolean) documentSnapshot.getBoolean("10:30 น - 10:45 น");
                    TextView textthu7 = (TextView) findViewById(R.id.timethu7);
                    if (thu7 == null || thu7 == false) {
                        textthu7.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textthu7.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean thu8 = (Boolean) documentSnapshot.getBoolean("10:45 น - 11:00 น");
                    TextView textthu8 = (TextView) findViewById(R.id.timethu8);
                    if (thu8 == null || thu8 == false) {
                        textthu8.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textthu8.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean thu9 = (Boolean) documentSnapshot.getBoolean("11:00 น - 11:15 น");
                    TextView textthu9 = (TextView) findViewById(R.id.timethu9);
                    if (thu9 == null || thu9 == false) {
                        textthu9.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textthu9.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean thu10 = (Boolean) documentSnapshot.getBoolean("11:15 น - 11:30 น");
                    TextView textthu10 = (TextView) findViewById(R.id.timethu10);
                    if (thu10 == null || thu10 == false) {
                        textthu10.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textthu10.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean thu11 = (Boolean) documentSnapshot.getBoolean("11:30 น - 11:45 น");
                    TextView textthu11 = (TextView) findViewById(R.id.timethu11);
                    if (thu11 == null || thu11 == false) {
                        textthu11.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textthu11.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean thu12 = (Boolean) documentSnapshot.getBoolean("11:45 น - 12:00 น");
                    TextView textthu12 = (TextView) findViewById(R.id.timethu12);
                    if (thu12 == null || thu12 == false) {
                        textthu12.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textthu12.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean thu13 = (Boolean) documentSnapshot.getBoolean("12:00 น - 12:15 น");
                    TextView textthu13 = (TextView) findViewById(R.id.timethu13);
                    if (thu13 == null || thu13 == false) {
                        textthu13.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textthu13.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean thu14 = (Boolean) documentSnapshot.getBoolean("12:15 น - 12:30 น");
                    TextView textthu14 = (TextView) findViewById(R.id.timethu14);
                    if (thu14 == null || thu14 == false) {
                        textthu14.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textthu14.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean thu15 = (Boolean) documentSnapshot.getBoolean("12:30 น - 12:45 น");
                    TextView textthu15 = (TextView) findViewById(R.id.timethu15);
                    if (thu15 == null || thu15 == false) {
                        textthu15.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textthu15.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean thu16 = (Boolean) documentSnapshot.getBoolean("12:45 น - 13:00 น");
                    TextView textthu16 = (TextView) findViewById(R.id.timethu16);
                    if (thu16 == null || thu16 == false) {
                        textthu16.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textthu16.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean thu17 = (Boolean) documentSnapshot.getBoolean("13:00 น - 13:15 น");
                    TextView textthu17 = (TextView) findViewById(R.id.timethu17);
                    if (thu17 == null || thu17 == false) {
                        textthu17.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textthu17.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean thu18 = (Boolean) documentSnapshot.getBoolean("13:15 น - 13:30 น");
                    TextView textthu18 = (TextView) findViewById(R.id.timethu18);
                    if (thu18 == null || thu18 == false) {
                        textthu18.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textthu18.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean thu19 = (Boolean) documentSnapshot.getBoolean("13:30 น - 13:45 น");
                    TextView textthu19 = (TextView) findViewById(R.id.timethu19);
                    if (thu19 == null || thu19 == false) {
                        textthu19.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textthu19.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean thu20 = (Boolean) documentSnapshot.getBoolean("13:45 น - 14:00 น");
                    TextView textthu20 = (TextView) findViewById(R.id.timethu20);
                    if (thu20 == null || thu20 == false) {
                        textthu20.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textthu20.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean thu21 = (Boolean) documentSnapshot.getBoolean("14:00 น - 14:15 น");
                    TextView textthu21 = (TextView) findViewById(R.id.timethu21);
                    if (thu21 == null || thu21 == false) {
                        textthu21.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textthu21.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean thu22 = (Boolean) documentSnapshot.getBoolean("14:15 น - 14:30 น");
                    TextView textthu22 = (TextView) findViewById(R.id.timethu22);
                    if (thu22 == null || thu22 == false) {
                        textthu22.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textthu22.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean thu23 = (Boolean) documentSnapshot.getBoolean("14:30 น - 14:45 น");
                    TextView textthu23 = (TextView) findViewById(R.id.timethu23);
                    if (thu23 == null || thu23 == false) {
                        textthu23.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textthu23.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean thu24 = (Boolean) documentSnapshot.getBoolean("14:45 น - 15:00 น");
                    TextView textthu24 = (TextView) findViewById(R.id.timethu24);
                    if (thu24 == null || thu24 == false) {
                        textthu24.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textthu24.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean thu25 = (Boolean) documentSnapshot.getBoolean("15:00 น - 15:15 น");
                    TextView textthu25 = (TextView) findViewById(R.id.timethu25);
                    if (thu25 == null || thu25 == false) {
                        textthu25.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textthu25.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean thu26 = (Boolean) documentSnapshot.getBoolean("15:15 น - 15:30 น");
                    TextView textthu26 = (TextView) findViewById(R.id.timethu26);
                    if (thu26 == null || thu26 == false) {
                        textthu26.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textthu26.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean thu27 = (Boolean) documentSnapshot.getBoolean("15:30 น - 15:45 น");
                    TextView textthu27 = (TextView) findViewById(R.id.timethu27);
                    if (thu27 == null || thu27 == false) {
                        textthu27.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textthu27.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean thu28 = (Boolean) documentSnapshot.getBoolean("15:45 น - 16:00 น");
                    TextView textthu28 = (TextView) findViewById(R.id.timethu28);
                    if (thu28 == null || thu28 == false) {
                        textthu28.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textthu28.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean thu29 = (Boolean) documentSnapshot.getBoolean("16:00 น - 16:15 น");
                    TextView textthu29 = (TextView) findViewById(R.id.timethu29);
                    if (thu29 == null || thu29 == false) {
                        textthu29.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textthu29.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean thu30 = (Boolean) documentSnapshot.getBoolean("16:15 น - 16:30 น");
                    TextView textthu30 = (TextView) findViewById(R.id.timethu30);
                    if (thu30 == null || thu30 == false) {
                        textthu30.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textthu30.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean thu31 = (Boolean) documentSnapshot.getBoolean("16:30 น - 16:45 น");
                    TextView textthu31 = (TextView) findViewById(R.id.timethu31);
                    if (thu31 == null || thu31 == false) {
                        textthu31.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textthu31.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean thu32 = (Boolean) documentSnapshot.getBoolean("16:45 น - 17:00 น");
                    TextView textthu32 = (TextView) findViewById(R.id.timethu32);
                    if (thu32 == null || thu32 == false) {
                        textthu32.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textthu32.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean thu33 = (Boolean) documentSnapshot.getBoolean("17:00 น - 17:15 น");
                    TextView textthu33 = (TextView) findViewById(R.id.timethu33);
                    if (thu33 == null || thu33 == false) {
                        textthu33.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textthu33.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean thu34 = (Boolean) documentSnapshot.getBoolean("17:15 น - 17:30 น");
                    TextView textthu34 = (TextView) findViewById(R.id.timethu34);
                    if (thu34 == null || thu34 == false) {
                        textthu34.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textthu34.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean thu35 = (Boolean) documentSnapshot.getBoolean("17:30 น - 17:45 น");
                    TextView textthu35 = (TextView) findViewById(R.id.timethu35);
                    if (thu35 == null || thu35 == false) {
                        textthu35.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textthu35.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean thu36 = (Boolean) documentSnapshot.getBoolean("17:45 น - 18:00 น");
                    TextView textthu36 = (TextView) findViewById(R.id.timethu36);
                    if (thu36 == null || thu36 == false) {
                        textthu36.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textthu36.setBackgroundResource(R.color.NoFreeTime);
                    }
                }
            }
        });

        myTime.document(chooseTeacherTime + "_ศุกร์").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    Boolean fri1 = (Boolean) documentSnapshot.getBoolean("9:00 น - 9:15 น");
                    TextView textfri1 = (TextView) findViewById(R.id.timefri1);
                    if (fri1 == null || fri1 == false) {
                        textfri1.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textfri1.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean fri2 = (Boolean) documentSnapshot.getBoolean("9:15 น - 9:30 น");
                    TextView textfri2 = (TextView) findViewById(R.id.timefri2);
                    if (fri2 == null || fri2 == false) {
                        textfri2.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textfri2.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean fri3 = (Boolean) documentSnapshot.getBoolean("9:30 น - 9:45 น");
                    TextView textfri3 = (TextView) findViewById(R.id.timefri3);
                    if (fri3 == null || fri3 == false) {
                        textfri3.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textfri3.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean fri4 = (Boolean) documentSnapshot.getBoolean("9:45 น - 10:00 น");
                    TextView textfri4 = (TextView) findViewById(R.id.timefri4);
                    if (fri4 == null || fri4 == false) {
                        textfri4.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textfri4.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean fri5 = (Boolean) documentSnapshot.getBoolean("10:00 น - 10:15 น");
                    TextView textfri5 = (TextView) findViewById(R.id.timefri5);
                    if (fri5 == null || fri5 == false) {
                        textfri5.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textfri5.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean fri6 = (Boolean) documentSnapshot.getBoolean("10:15 น - 10:30 น");
                    TextView textfri6 = (TextView) findViewById(R.id.timefri6);
                    if (fri6 == null || fri6 == false) {
                        textfri6.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textfri6.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean fri7 = (Boolean) documentSnapshot.getBoolean("10:30 น - 10:45 น");
                    TextView textfri7 = (TextView) findViewById(R.id.timefri7);
                    if (fri7 == null || fri7 == false) {
                        textfri7.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textfri7.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean fri8 = (Boolean) documentSnapshot.getBoolean("10:45 น - 11:00 น");
                    TextView textfri8 = (TextView) findViewById(R.id.timefri8);
                    if (fri8 == null || fri8 == false) {
                        textfri8.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textfri8.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean fri9 = (Boolean) documentSnapshot.getBoolean("11:00 น - 11:15 น");
                    TextView textfri9 = (TextView) findViewById(R.id.timefri9);
                    if (fri9 == null || fri9 == false) {
                        textfri9.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textfri9.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean fri10 = (Boolean) documentSnapshot.getBoolean("11:15 น - 11:30 น");
                    TextView textfri10 = (TextView) findViewById(R.id.timefri10);
                    if (fri10 == null || fri10 == false) {
                        textfri10.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textfri10.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean fri11 = (Boolean) documentSnapshot.getBoolean("11:30 น - 11:45 น");
                    TextView textfri11 = (TextView) findViewById(R.id.timefri11);
                    if (fri11 == null || fri11 == false) {
                        textfri11.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textfri11.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean fri12 = (Boolean) documentSnapshot.getBoolean("11:45 น - 12:00 น");
                    TextView textfri12 = (TextView) findViewById(R.id.timefri12);
                    if (fri12 == null || fri12 == false) {
                        textfri12.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textfri12.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean fri13 = (Boolean) documentSnapshot.getBoolean("12:00 น - 12:15 น");
                    TextView textfri13 = (TextView) findViewById(R.id.timefri13);
                    if (fri13 == null || fri13 == false) {
                        textfri13.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textfri13.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean fri14 = (Boolean) documentSnapshot.getBoolean("12:15 น - 12:30 น");
                    TextView textfri14 = (TextView) findViewById(R.id.timefri14);
                    if (fri14 == null || fri14 == false) {
                        textfri14.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textfri14.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean fri15 = (Boolean) documentSnapshot.getBoolean("12:30 น - 12:45 น");
                    TextView textfri15 = (TextView) findViewById(R.id.timefri15);
                    if (fri15 == null || fri15 == false) {
                        textfri15.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textfri15.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean fri16 = (Boolean) documentSnapshot.getBoolean("12:45 น - 13:00 น");
                    TextView textfri16 = (TextView) findViewById(R.id.timefri16);
                    if (fri16 == null || fri16 == false) {
                        textfri16.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textfri16.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean fri17 = (Boolean) documentSnapshot.getBoolean("13:00 น - 13:15 น");
                    TextView textfri17 = (TextView) findViewById(R.id.timefri17);
                    if (fri17 == null || fri17 == false) {
                        textfri17.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textfri17.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean fri18 = (Boolean) documentSnapshot.getBoolean("13:15 น - 13:30 น");
                    TextView textfri18 = (TextView) findViewById(R.id.timefri18);
                    if (fri18 == null || fri18 == false) {
                        textfri18.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textfri18.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean fri19 = (Boolean) documentSnapshot.getBoolean("13:30 น - 13:45 น");
                    TextView textfri19 = (TextView) findViewById(R.id.timefri19);
                    if (fri19 == null || fri19 == false) {
                        textfri19.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textfri19.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean fri20 = (Boolean) documentSnapshot.getBoolean("13:45 น - 14:00 น");
                    TextView textfri20 = (TextView) findViewById(R.id.timefri20);
                    if (fri20 == null || fri20 == false) {
                        textfri20.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textfri20.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean fri21 = (Boolean) documentSnapshot.getBoolean("14:00 น - 14:15 น");
                    TextView textfri21 = (TextView) findViewById(R.id.timefri21);
                    if (fri21 == null || fri21 == false) {
                        textfri21.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textfri21.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean fri22 = (Boolean) documentSnapshot.getBoolean("14:15 น - 14:30 น");
                    TextView textfri22 = (TextView) findViewById(R.id.timefri22);
                    if (fri22 == null || fri22 == false) {
                        textfri22.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textfri22.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean fri23 = (Boolean) documentSnapshot.getBoolean("14:30 น - 14:45 น");
                    TextView textfri23 = (TextView) findViewById(R.id.timefri23);
                    if (fri23 == null || fri23 == false) {
                        textfri23.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textfri23.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean fri24 = (Boolean) documentSnapshot.getBoolean("14:45 น - 15:00 น");
                    TextView textfri24 = (TextView) findViewById(R.id.timefri24);
                    if (fri24 == null || fri24 == false) {
                        textfri24.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textfri24.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean fri25 = (Boolean) documentSnapshot.getBoolean("15:00 น - 15:15 น");
                    TextView textfri25 = (TextView) findViewById(R.id.timefri25);
                    if (fri25 == null || fri25 == false) {
                        textfri25.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textfri25.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean fri26 = (Boolean) documentSnapshot.getBoolean("15:15 น - 15:30 น");
                    TextView textfri26 = (TextView) findViewById(R.id.timefri26);
                    if (fri26 == null || fri26 == false) {
                        textfri26.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textfri26.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean fri27 = (Boolean) documentSnapshot.getBoolean("15:30 น - 15:45 น");
                    TextView textfri27 = (TextView) findViewById(R.id.timefri27);
                    if (fri27 == null || fri27 == false) {
                        textfri27.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textfri27.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean fri28 = (Boolean) documentSnapshot.getBoolean("15:45 น - 16:00 น");
                    TextView textfri28 = (TextView) findViewById(R.id.timefri28);
                    if (fri28 == null || fri28 == false) {
                        textfri28.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textfri28.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean fri29 = (Boolean) documentSnapshot.getBoolean("16:00 น - 16:15 น");
                    TextView textfri29 = (TextView) findViewById(R.id.timefri29);
                    if (fri29 == null || fri29 == false) {
                        textfri29.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textfri29.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean fri30 = (Boolean) documentSnapshot.getBoolean("16:15 น - 16:30 น");
                    TextView textfri30 = (TextView) findViewById(R.id.timefri30);
                    if (fri30 == null || fri30 == false) {
                        textfri30.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textfri30.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean fri31 = (Boolean) documentSnapshot.getBoolean("16:30 น - 16:45 น");
                    TextView textfri31 = (TextView) findViewById(R.id.timefri31);
                    if (fri31 == null || fri31 == false) {
                        textfri31.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textfri31.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean fri32 = (Boolean) documentSnapshot.getBoolean("16:45 น - 17:00 น");
                    TextView textfri32 = (TextView) findViewById(R.id.timefri32);
                    if (fri32 == null || fri32 == false) {
                        textfri32.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textfri32.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean fri33 = (Boolean) documentSnapshot.getBoolean("17:00 น - 17:15 น");
                    TextView textfri33 = (TextView) findViewById(R.id.timefri33);
                    if (fri33 == null || fri33 == false) {
                        textfri33.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textfri33.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean fri34 = (Boolean) documentSnapshot.getBoolean("17:15 น - 17:30 น");
                    TextView textfri34 = (TextView) findViewById(R.id.timefri34);
                    if (fri34 == null || fri34 == false) {
                        textfri34.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textfri34.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean fri35 = (Boolean) documentSnapshot.getBoolean("17:30 น - 17:45 น");
                    TextView textfri35 = (TextView) findViewById(R.id.timefri35);
                    if (fri35 == null || fri35 == false) {
                        textfri35.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textfri35.setBackgroundResource(R.color.NoFreeTime);
                    }
                    Boolean fri36 = (Boolean) documentSnapshot.getBoolean("17:45 น - 18:00 น");
                    TextView textfri36 = (TextView) findViewById(R.id.timefri36);
                    if (fri36 == null || fri36 == false) {
                        textfri36.setBackgroundResource(R.color.FreeTime);
                    } else {
                        textfri36.setBackgroundResource(R.color.NoFreeTime);
                    }
                }
            }
        });
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
