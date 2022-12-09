package com.example.appointment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class TeacherUpdateTime_Page extends AppCompatActivity {

    private FirebaseFirestore Firestore = FirebaseFirestore.getInstance();
    private CollectionReference updatetime = Firestore.collection("Timetable");

    private FirebaseDatabase db;
    private DatabaseReference myRef;
    private FirebaseAuth FireAuth;
    private TextView nameTeacher;
    private String nameTeacherCurrent;
    private String resultUsername;
    private TextView textDate;

    private Boolean statemon1;
    private Boolean statemon2;
    private Boolean statemon3;
    private Boolean statemon4;
    private Boolean statemon5;
    private Boolean statemon6;
    private Boolean statemon7;
    private Boolean statemon8;
    private Boolean statemon9;

    private Boolean statetue1;
    private Boolean statetue2;
    private Boolean statetue3;
    private Boolean statetue4;
    private Boolean statetue5;
    private Boolean statetue6;
    private Boolean statetue7;
    private Boolean statetue8;
    private Boolean statetue9;

    private Boolean statewed1;
    private Boolean statewed2;
    private Boolean statewed3;
    private Boolean statewed4;
    private Boolean statewed5;
    private Boolean statewed6;
    private Boolean statewed7;
    private Boolean statewed8;
    private Boolean statewed9;

    private Boolean statethu1;
    private Boolean statethu2;
    private Boolean statethu3;
    private Boolean statethu4;
    private Boolean statethu5;
    private Boolean statethu6;
    private Boolean statethu7;
    private Boolean statethu8;
    private Boolean statethu9;

    private Boolean statefri1;
    private Boolean statefri2;
    private Boolean statefri3;
    private Boolean statefri4;
    private Boolean statefri5;
    private Boolean statefri6;
    private Boolean statefri7;
    private Boolean statefri8;
    private Boolean statefri9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_update_time__page);

        nameTeacher = (TextView) findViewById(R.id.txNameTeacherUpdateTime);
        textDate = (TextView) findViewById(R.id.textCurrentWeek);

        FireAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        myRef = db.getReference("Users-Teacher");
        FirebaseUser userCur = FireAuth.getCurrentUser();
        String userID = userCur.getUid();
        String userEmail = userCur.getEmail();

        int intIndex = userEmail.indexOf("@");
        if (intIndex == -1) {
            Log.v("Test", "Error !");
        } else {
            resultUsername = userEmail.substring(0, intIndex);
        }

        SimpleDateFormat form = new SimpleDateFormat("dd/MM/yyyy");
        Calendar now = Calendar.getInstance();
        String[] days = new String[7];
        int delta = -now.get(GregorianCalendar.DAY_OF_WEEK) + 1;
        now.add(Calendar.DAY_OF_MONTH, delta);
        for (int i = 0; i < 7; i++) {
            days[i] = form.format(now.getTime());
            now.add(Calendar.DAY_OF_MONTH, 1);
        }
        textDate.setText("ตารางเวลาวัน : จันทร์, " + days[1] + " - ศุกร์, " + days[5]);

        if (userID != null) {
            myRef.orderByChild("Username").equalTo(resultUsername).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    nameTeacher.setText(dataSnapshot.child("Name_prefix").getValue().toString() + dataSnapshot.child("Name").getValue().toString());
                    nameTeacherCurrent = dataSnapshot.child("uID").getValue().toString();
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    CollectionReference myTime = db.collection("Timetable");

                    myTime.document(nameTeacherCurrent + "_จันทร์").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot.exists()) {
                                TextView teatimemon1 = (TextView) findViewById(R.id.teatimemon1);
                                Boolean mon1 = (Boolean) documentSnapshot.getBoolean("9:00 น - 9:15 น");
                                Boolean mon2 = (Boolean) documentSnapshot.getBoolean("9:15 น - 9:30 น");
                                Boolean mon3 = (Boolean) documentSnapshot.getBoolean("9:30 น - 9:45 น");
                                Boolean mon4 = (Boolean) documentSnapshot.getBoolean("9:45 น - 10:00 น");
                                if ((mon1 == null || mon1 == false) || (mon2 == null || mon2 == false) || (mon3 == null || mon3 == false) || (mon4 == null || mon4 == false)) {
                                    teatimemon1.setBackgroundResource(R.color.FreeTime);
                                    statemon1 = false;
                                } else {
                                    teatimemon1.setBackgroundResource(R.color.NoFreeTime);
                                    statemon1 = true;
                                }

                                TextView teatimemon2 = (TextView) findViewById(R.id.teatimemon2);
                                Boolean mon5 = (Boolean) documentSnapshot.getBoolean("10:00 น - 10:15 น");
                                Boolean mon6 = (Boolean) documentSnapshot.getBoolean("10:15 น - 10:30 น");
                                Boolean mon7 = (Boolean) documentSnapshot.getBoolean("10:30 น - 10:45 น");
                                Boolean mon8 = (Boolean) documentSnapshot.getBoolean("10:45 น - 11:00 น");
                                if ((mon5 == null || mon5 == false) || (mon6 == null || mon6 == false) || (mon7 == null || mon7 == false) || (mon8 == null || mon8 == false)) {
                                    teatimemon2.setBackgroundResource(R.color.FreeTime);
                                    statemon2 = false;
                                } else {
                                    teatimemon2.setBackgroundResource(R.color.NoFreeTime);
                                    statemon2 = true;
                                }

                                TextView teatimemon3 = (TextView) findViewById(R.id.teatimemon3);
                                Boolean mon9 = (Boolean) documentSnapshot.getBoolean("11:00 น - 11:15 น");
                                Boolean mon10 = (Boolean) documentSnapshot.getBoolean("11:15 น - 11:30 น");
                                Boolean mon11 = (Boolean) documentSnapshot.getBoolean("11:30 น - 11:45 น");
                                Boolean mon12 = (Boolean) documentSnapshot.getBoolean("11:45 น - 12:00 น");
                                if ((mon9 == null || mon9 == false) || (mon10 == null || mon10 == false) || (mon11 == null || mon11 == false) || (mon12 == null || mon12 == false)) {
                                    teatimemon3.setBackgroundResource(R.color.FreeTime);
                                    statemon3 = false;
                                } else {
                                    teatimemon3.setBackgroundResource(R.color.NoFreeTime);
                                    statemon3 = true;
                                }

                                TextView teatimemon4 = (TextView) findViewById(R.id.teatimemon4);
                                Boolean mon13 = (Boolean) documentSnapshot.getBoolean("12:00 น - 12:15 น");
                                Boolean mon14 = (Boolean) documentSnapshot.getBoolean("12:15 น - 12:30 น");
                                Boolean mon15 = (Boolean) documentSnapshot.getBoolean("12:30 น - 12:45 น");
                                Boolean mon16 = (Boolean) documentSnapshot.getBoolean("12:45 น - 13:00 น");
                                if ((mon13 == null || mon13 == false) || (mon14 == null || mon14 == false) || (mon15 == null || mon15 == false) || (mon16 == null || mon16 == false)) {
                                    teatimemon4.setBackgroundResource(R.color.FreeTime);
                                    statemon4 = false;
                                } else {
                                    teatimemon4.setBackgroundResource(R.color.NoFreeTime);
                                    statemon4 = true;
                                }

                                TextView teatimemon5 = (TextView) findViewById(R.id.teatimemon5);
                                Boolean mon17 = (Boolean) documentSnapshot.getBoolean("13:00 น - 13:15 น");
                                Boolean mon18 = (Boolean) documentSnapshot.getBoolean("13:15 น - 13:30 น");
                                Boolean mon19 = (Boolean) documentSnapshot.getBoolean("13:30 น - 13:45 น");
                                Boolean mon20 = (Boolean) documentSnapshot.getBoolean("13:45 น - 14:00 น");
                                if ((mon17 == null || mon17 == false) || (mon18 == null || mon18 == false) || (mon19 == null || mon19 == false) || (mon20 == null || mon20 == false)) {
                                    teatimemon5.setBackgroundResource(R.color.FreeTime);
                                    statemon5 = false;
                                } else {
                                    teatimemon5.setBackgroundResource(R.color.NoFreeTime);
                                    statemon5 = true;
                                }

                                TextView teatimemon6 = (TextView) findViewById(R.id.teatimemon6);
                                Boolean mon21 = (Boolean) documentSnapshot.getBoolean("14:00 น - 14:15 น");
                                Boolean mon22 = (Boolean) documentSnapshot.getBoolean("14:15 น - 14:30 น");
                                Boolean mon23 = (Boolean) documentSnapshot.getBoolean("14:30 น - 14:45 น");
                                Boolean mon24 = (Boolean) documentSnapshot.getBoolean("14:45 น - 15:00 น");
                                if ((mon21 == null || mon21 == false) || (mon22 == null || mon22 == false) || (mon23 == null || mon23 == false) || (mon24 == null || mon24 == false)) {
                                    teatimemon6.setBackgroundResource(R.color.FreeTime);
                                    statemon6 = false;
                                } else {
                                    teatimemon6.setBackgroundResource(R.color.NoFreeTime);
                                    statemon6 = true;
                                }

                                TextView teatimemon7 = (TextView) findViewById(R.id.teatimemon7);
                                Boolean mon25 = (Boolean) documentSnapshot.getBoolean("15:00 น - 15:15 น");
                                Boolean mon26 = (Boolean) documentSnapshot.getBoolean("15:15 น - 15:30 น");
                                Boolean mon27 = (Boolean) documentSnapshot.getBoolean("15:30 น - 15:45 น");
                                Boolean mon28 = (Boolean) documentSnapshot.getBoolean("15:45 น - 16:00 น");
                                if ((mon25 == null || mon25 == false) || (mon26 == null || mon26 == false) || (mon27 == null || mon27 == false) || (mon28 == null || mon28 == false)) {
                                    teatimemon7.setBackgroundResource(R.color.FreeTime);
                                    statemon7 = false;
                                } else {
                                    teatimemon7.setBackgroundResource(R.color.NoFreeTime);
                                    statemon7 = true;
                                }

                                TextView teatimemon8 = (TextView) findViewById(R.id.teatimemon8);
                                Boolean mon29 = (Boolean) documentSnapshot.getBoolean("16:00 น - 16:15 น");
                                Boolean mon30 = (Boolean) documentSnapshot.getBoolean("16:15 น - 16:30 น");
                                Boolean mon31 = (Boolean) documentSnapshot.getBoolean("16:30 น - 16:45 น");
                                Boolean mon32 = (Boolean) documentSnapshot.getBoolean("16:45 น - 17:00 น");
                                if ((mon29 == null || mon29 == false) || (mon30 == null || mon30 == false) || (mon31 == null || mon31 == false) || (mon32 == null || mon32 == false)) {
                                    teatimemon8.setBackgroundResource(R.color.FreeTime);
                                    statemon8 = false;
                                } else {
                                    teatimemon8.setBackgroundResource(R.color.NoFreeTime);
                                    statemon8 = true;
                                }

                                TextView teatimemon9 = (TextView) findViewById(R.id.teatimemon9);
                                Boolean mon33 = (Boolean) documentSnapshot.getBoolean("17:00 น - 17:15 น");
                                Boolean mon34 = (Boolean) documentSnapshot.getBoolean("17:15 น - 17:30 น");
                                Boolean mon35 = (Boolean) documentSnapshot.getBoolean("17:30 น - 17:45 น");
                                Boolean mon36 = (Boolean) documentSnapshot.getBoolean("17:45 น - 18:00 น");
                                if ((mon33 == null || mon33 == false) || (mon34 == null || mon34 == false) || (mon35 == null || mon35 == false) || (mon36 == null || mon36 == false)) {
                                    teatimemon9.setBackgroundResource(R.color.FreeTime);
                                    statemon9 = false;
                                } else {
                                    teatimemon9.setBackgroundResource(R.color.NoFreeTime);
                                    statemon9 = true;
                                }
                            }
                        }
                    });

                    myTime.document(nameTeacherCurrent + "_อังคาร").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            TextView teatimetue1 = (TextView) findViewById(R.id.teatimetue1);
                            Boolean tue1 = (Boolean) documentSnapshot.getBoolean("9:00 น - 9:15 น");
                            Boolean tue2 = (Boolean) documentSnapshot.getBoolean("9:15 น - 9:30 น");
                            Boolean tue3 = (Boolean) documentSnapshot.getBoolean("9:30 น - 9:45 น");
                            Boolean tue4 = (Boolean) documentSnapshot.getBoolean("9:45 น - 10:00 น");
                            if ((tue1 == null || tue1 == false) || (tue2 == null || tue2 == false) || (tue3 == null || tue3 == false) || (tue4 == null || tue4 == false)) {
                                teatimetue1.setBackgroundResource(R.color.FreeTime);
                                statetue1 = false;
                            } else {
                                teatimetue1.setBackgroundResource(R.color.NoFreeTime);
                                statetue1 = true;
                            }

                            TextView teatimetue2 = (TextView) findViewById(R.id.teatimetue2);
                            Boolean tue5 = (Boolean) documentSnapshot.getBoolean("10:00 น - 10:15 น");
                            Boolean tue6 = (Boolean) documentSnapshot.getBoolean("10:15 น - 10:30 น");
                            Boolean tue7 = (Boolean) documentSnapshot.getBoolean("10:30 น - 10:45 น");
                            Boolean tue8 = (Boolean) documentSnapshot.getBoolean("10:45 น - 11:00 น");
                            if ((tue5 == null || tue5 == false) || (tue6 == null || tue6 == false) || (tue7 == null || tue7 == false) || (tue8 == null || tue8 == false)) {
                                teatimetue2.setBackgroundResource(R.color.FreeTime);
                                statetue2 = false;
                            } else {
                                teatimetue2.setBackgroundResource(R.color.NoFreeTime);
                                statetue2 = true;
                            }

                            TextView teatimetue3 = (TextView) findViewById(R.id.teatimetue3);
                            Boolean tue9 = (Boolean) documentSnapshot.getBoolean("11:00 น - 11:15 น");
                            Boolean tue10 = (Boolean) documentSnapshot.getBoolean("11:15 น - 11:30 น");
                            Boolean tue11 = (Boolean) documentSnapshot.getBoolean("11:30 น - 11:45 น");
                            Boolean tue12 = (Boolean) documentSnapshot.getBoolean("11:45 น - 12:00 น");
                            if ((tue9 == null || tue9 == false) || (tue10 == null || tue10 == false) || (tue11 == null || tue11 == false) || (tue12 == null || tue12 == false)) {
                                teatimetue3.setBackgroundResource(R.color.FreeTime);
                                statetue3 = false;
                            } else {
                                teatimetue3.setBackgroundResource(R.color.NoFreeTime);
                                statetue3 = true;
                            }

                            TextView teatimetue4 = (TextView) findViewById(R.id.teatimetue4);
                            Boolean tue13 = (Boolean) documentSnapshot.getBoolean("12:00 น - 12:15 น");
                            Boolean tue14 = (Boolean) documentSnapshot.getBoolean("12:15 น - 12:30 น");
                            Boolean tue15 = (Boolean) documentSnapshot.getBoolean("12:30 น - 12:45 น");
                            Boolean tue16 = (Boolean) documentSnapshot.getBoolean("12:45 น - 13:00 น");
                            if ((tue13 == null || tue13 == false) || (tue14 == null || tue14 == false) || (tue15 == null || tue15 == false) || (tue16 == null || tue16 == false)) {
                                teatimetue4.setBackgroundResource(R.color.FreeTime);
                                statetue4 = false;
                            } else {
                                teatimetue4.setBackgroundResource(R.color.NoFreeTime);
                                statetue4 = true;
                            }

                            TextView teatimetue5 = (TextView) findViewById(R.id.teatimetue5);
                            Boolean tue17 = (Boolean) documentSnapshot.getBoolean("13:00 น - 13:15 น");
                            Boolean tue18 = (Boolean) documentSnapshot.getBoolean("13:15 น - 13:30 น");
                            Boolean tue19 = (Boolean) documentSnapshot.getBoolean("13:30 น - 13:45 น");
                            Boolean tue20 = (Boolean) documentSnapshot.getBoolean("13:45 น - 14:00 น");
                            if ((tue17 == null || tue17 == false) || (tue18 == null || tue18 == false) || (tue19 == null || tue19 == false) || (tue20 == null || tue20 == false)) {
                                teatimetue5.setBackgroundResource(R.color.FreeTime);
                                statetue5 = false;
                            } else {
                                teatimetue5.setBackgroundResource(R.color.NoFreeTime);
                                statetue5 = true;
                            }

                            TextView teatimetue6 = (TextView) findViewById(R.id.teatimetue6);
                            Boolean tue21 = (Boolean) documentSnapshot.getBoolean("14:00 น - 14:15 น");
                            Boolean tue22 = (Boolean) documentSnapshot.getBoolean("14:15 น - 14:30 น");
                            Boolean tue23 = (Boolean) documentSnapshot.getBoolean("14:30 น - 14:45 น");
                            Boolean tue24 = (Boolean) documentSnapshot.getBoolean("14:45 น - 15:00 น");
                            if ((tue21 == null || tue21 == false) || (tue22 == null || tue22 == false) || (tue23 == null || tue23 == false) || (tue24 == null || tue24 == false)) {
                                teatimetue6.setBackgroundResource(R.color.FreeTime);
                                statetue6 = false;
                            } else {
                                teatimetue6.setBackgroundResource(R.color.NoFreeTime);
                                statetue6 = true;
                            }

                            TextView teatimetue7 = (TextView) findViewById(R.id.teatimetue7);
                            Boolean tue25 = (Boolean) documentSnapshot.getBoolean("15:00 น - 15:15 น");
                            Boolean tue26 = (Boolean) documentSnapshot.getBoolean("15:15 น - 15:30 น");
                            Boolean tue27 = (Boolean) documentSnapshot.getBoolean("15:30 น - 15:45 น");
                            Boolean tue28 = (Boolean) documentSnapshot.getBoolean("15:45 น - 16:00 น");
                            if ((tue25 == null || tue25 == false) || (tue26 == null || tue26 == false) || (tue27 == null || tue27 == false) || (tue28 == null || tue28 == false)) {
                                teatimetue7.setBackgroundResource(R.color.FreeTime);
                                statetue7 = false;
                            } else {
                                teatimetue7.setBackgroundResource(R.color.NoFreeTime);
                                statetue7 = true;
                            }

                            TextView teatimetue8 = (TextView) findViewById(R.id.teatimetue8);
                            Boolean tue29 = (Boolean) documentSnapshot.getBoolean("16:00 น - 16:15 น");
                            Boolean tue30 = (Boolean) documentSnapshot.getBoolean("16:15 น - 16:30 น");
                            Boolean tue31 = (Boolean) documentSnapshot.getBoolean("16:30 น - 16:45 น");
                            Boolean tue32 = (Boolean) documentSnapshot.getBoolean("16:45 น - 17:00 น");
                            if ((tue29 == null || tue29 == false) || (tue30 == null || tue30 == false) || (tue31 == null || tue31 == false) || (tue32 == null || tue32 == false)) {
                                teatimetue8.setBackgroundResource(R.color.FreeTime);
                                statetue8 = false;
                            } else {
                                teatimetue8.setBackgroundResource(R.color.NoFreeTime);
                                statetue8 = true;
                            }

                            TextView teatimetue9 = (TextView) findViewById(R.id.teatimetue9);
                            Boolean tue33 = (Boolean) documentSnapshot.getBoolean("17:00 น - 17:15 น");
                            Boolean tue34 = (Boolean) documentSnapshot.getBoolean("17:15 น - 17:30 น");
                            Boolean tue35 = (Boolean) documentSnapshot.getBoolean("17:30 น - 17:45 น");
                            Boolean tue36 = (Boolean) documentSnapshot.getBoolean("17:45 น - 18:00 น");
                            if ((tue33 == null || tue33 == false) || (tue34 == null || tue34 == false) || (tue35 == null || tue35 == false) || (tue36 == null || tue36 == false)) {
                                teatimetue9.setBackgroundResource(R.color.FreeTime);
                                statetue9 = false;
                            } else {
                                teatimetue9.setBackgroundResource(R.color.NoFreeTime);
                                statetue9 = true;
                            }
                        }
                    });

                    myTime.document(nameTeacherCurrent + "_พุธ").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            TextView teatimewed1 = (TextView) findViewById(R.id.teatimewed1);
                            Boolean wed1 = (Boolean) documentSnapshot.getBoolean("9:00 น - 9:15 น");
                            Boolean wed2 = (Boolean) documentSnapshot.getBoolean("9:15 น - 9:30 น");
                            Boolean wed3 = (Boolean) documentSnapshot.getBoolean("9:30 น - 9:45 น");
                            Boolean wed4 = (Boolean) documentSnapshot.getBoolean("9:45 น - 10:00 น");
                            if ((wed1 == null || wed1 == false) || (wed2 == null || wed2 == false) || (wed3 == null || wed3 == false) || (wed4 == null || wed4 == false)) {
                                teatimewed1.setBackgroundResource(R.color.FreeTime);
                                statewed1 = false;
                            } else {
                                teatimewed1.setBackgroundResource(R.color.NoFreeTime);
                                statewed1 = true;
                            }

                            TextView teatimewed2 = (TextView) findViewById(R.id.teatimewed2);
                            Boolean wed5 = (Boolean) documentSnapshot.getBoolean("10:00 น - 10:15 น");
                            Boolean wed6 = (Boolean) documentSnapshot.getBoolean("10:15 น - 10:30 น");
                            Boolean wed7 = (Boolean) documentSnapshot.getBoolean("10:30 น - 10:45 น");
                            Boolean wed8 = (Boolean) documentSnapshot.getBoolean("10:45 น - 11:00 น");
                            if ((wed5 == null || wed5 == false) || (wed6 == null || wed6 == false) || (wed7 == null || wed7 == false) || (wed8 == null || wed8 == false)) {
                                teatimewed2.setBackgroundResource(R.color.FreeTime);
                                statewed2 = false;
                            } else {
                                teatimewed2.setBackgroundResource(R.color.NoFreeTime);
                                statewed2 = true;
                            }

                            TextView teatimewed3 = (TextView) findViewById(R.id.teatimewed3);
                            Boolean wed9 = (Boolean) documentSnapshot.getBoolean("11:00 น - 11:15 น");
                            Boolean wed10 = (Boolean) documentSnapshot.getBoolean("11:15 น - 11:30 น");
                            Boolean wed11 = (Boolean) documentSnapshot.getBoolean("11:30 น - 11:45 น");
                            Boolean wed12 = (Boolean) documentSnapshot.getBoolean("11:45 น - 12:00 น");
                            if ((wed9 == null || wed9 == false) || (wed10 == null || wed10 == false) || (wed11 == null || wed11 == false) || (wed12 == null || wed12 == false)) {
                                teatimewed3.setBackgroundResource(R.color.FreeTime);
                                statewed3 = false;
                            } else {
                                teatimewed3.setBackgroundResource(R.color.NoFreeTime);
                                statewed3 = true;
                            }

                            TextView teatimewed4 = (TextView) findViewById(R.id.teatimewed4);
                            Boolean wed13 = (Boolean) documentSnapshot.getBoolean("12:00 น - 12:15 น");
                            Boolean wed14 = (Boolean) documentSnapshot.getBoolean("12:15 น - 12:30 น");
                            Boolean wed15 = (Boolean) documentSnapshot.getBoolean("12:30 น - 12:45 น");
                            Boolean wed16 = (Boolean) documentSnapshot.getBoolean("12:45 น - 13:00 น");
                            if ((wed13 == null || wed13 == false) || (wed14 == null || wed14 == false) || (wed15 == null || wed15 == false) || (wed16 == null || wed16 == false)) {
                                teatimewed4.setBackgroundResource(R.color.FreeTime);
                                statewed4 = false;
                            } else {
                                teatimewed4.setBackgroundResource(R.color.NoFreeTime);
                                statewed4 = true;
                            }

                            TextView teatimewed5 = (TextView) findViewById(R.id.teatimewed5);
                            Boolean wed17 = (Boolean) documentSnapshot.getBoolean("13:00 น - 13:15 น");
                            Boolean wed18 = (Boolean) documentSnapshot.getBoolean("13:15 น - 13:30 น");
                            Boolean wed19 = (Boolean) documentSnapshot.getBoolean("13:30 น - 13:45 น");
                            Boolean wed20 = (Boolean) documentSnapshot.getBoolean("13:45 น - 14:00 น");
                            if ((wed17 == null || wed17 == false) || (wed18 == null || wed18 == false) || (wed19 == null || wed19 == false) || (wed20 == null || wed20 == false)) {
                                teatimewed5.setBackgroundResource(R.color.FreeTime);
                                statewed5 = false;
                            } else {
                                teatimewed5.setBackgroundResource(R.color.NoFreeTime);
                                statewed5 = true;
                            }

                            TextView teatimewed6 = (TextView) findViewById(R.id.teatimewed6);
                            Boolean wed21 = (Boolean) documentSnapshot.getBoolean("14:00 น - 14:15 น");
                            Boolean wed22 = (Boolean) documentSnapshot.getBoolean("14:15 น - 14:30 น");
                            Boolean wed23 = (Boolean) documentSnapshot.getBoolean("14:30 น - 14:45 น");
                            Boolean wed24 = (Boolean) documentSnapshot.getBoolean("14:45 น - 15:00 น");
                            if ((wed21 == null || wed21 == false) || (wed22 == null || wed22 == false) || (wed23 == null || wed23 == false) || (wed24 == null || wed24 == false)) {
                                teatimewed6.setBackgroundResource(R.color.FreeTime);
                                statewed6 = false;
                            } else {
                                teatimewed6.setBackgroundResource(R.color.NoFreeTime);
                                statewed6 = true;
                            }

                            TextView teatimewed7 = (TextView) findViewById(R.id.teatimewed7);
                            Boolean wed25 = (Boolean) documentSnapshot.getBoolean("15:00 น - 15:15 น");
                            Boolean wed26 = (Boolean) documentSnapshot.getBoolean("15:15 น - 15:30 น");
                            Boolean wed27 = (Boolean) documentSnapshot.getBoolean("15:30 น - 15:45 น");
                            Boolean wed28 = (Boolean) documentSnapshot.getBoolean("15:45 น - 16:00 น");
                            if ((wed25 == null || wed25 == false) || (wed26 == null || wed26 == false) || (wed27 == null || wed27 == false) || (wed28 == null || wed28 == false)) {
                                teatimewed7.setBackgroundResource(R.color.FreeTime);
                                statewed7 = false;
                            } else {
                                teatimewed7.setBackgroundResource(R.color.NoFreeTime);
                                statewed7 = true;
                            }

                            TextView teatimewed8 = (TextView) findViewById(R.id.teatimewed8);
                            Boolean wed29 = (Boolean) documentSnapshot.getBoolean("16:00 น - 16:15 น");
                            Boolean wed30 = (Boolean) documentSnapshot.getBoolean("16:15 น - 16:30 น");
                            Boolean wed31 = (Boolean) documentSnapshot.getBoolean("16:30 น - 16:45 น");
                            Boolean wed32 = (Boolean) documentSnapshot.getBoolean("16:45 น - 17:00 น");
                            if ((wed29 == null || wed29 == false) || (wed30 == null || wed30 == false) || (wed31 == null || wed31 == false) || (wed32 == null || wed32 == false)) {
                                teatimewed8.setBackgroundResource(R.color.FreeTime);
                                statewed8 = false;
                            } else {
                                teatimewed8.setBackgroundResource(R.color.NoFreeTime);
                                statewed8 = true;
                            }

                            TextView teatimewed9 = (TextView) findViewById(R.id.teatimewed9);
                            Boolean wed33 = (Boolean) documentSnapshot.getBoolean("17:00 น - 17:15 น");
                            Boolean wed34 = (Boolean) documentSnapshot.getBoolean("17:15 น - 17:30 น");
                            Boolean wed35 = (Boolean) documentSnapshot.getBoolean("17:30 น - 17:45 น");
                            Boolean wed36 = (Boolean) documentSnapshot.getBoolean("17:45 น - 18:00 น");
                            if ((wed33 == null || wed33 == false) || (wed34 == null || wed34 == false) || (wed35 == null || wed35 == false) || (wed36 == null || wed36 == false)) {
                                teatimewed9.setBackgroundResource(R.color.FreeTime);
                                statewed9 = false;
                            } else {
                                teatimewed9.setBackgroundResource(R.color.NoFreeTime);
                                statewed9 = true;
                            }
                        }
                    });

                    myTime.document(nameTeacherCurrent + "_พฤหัสบดี").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            TextView teatimethu1 = (TextView) findViewById(R.id.teatimethu1);
                            Boolean thu1 = (Boolean) documentSnapshot.getBoolean("9:00 น - 9:15 น");
                            Boolean thu2 = (Boolean) documentSnapshot.getBoolean("9:15 น - 9:30 น");
                            Boolean thu3 = (Boolean) documentSnapshot.getBoolean("9:30 น - 9:45 น");
                            Boolean thu4 = (Boolean) documentSnapshot.getBoolean("9:45 น - 10:00 น");
                            if ((thu1 == null || thu1 == false) || (thu2 == null || thu2 == false) || (thu3 == null || thu3 == false) || (thu4 == null || thu4 == false)) {
                                teatimethu1.setBackgroundResource(R.color.FreeTime);
                                statethu1 = false;
                            } else {
                                teatimethu1.setBackgroundResource(R.color.NoFreeTime);
                                statethu1 = true;
                            }

                            TextView teatimethu2 = (TextView) findViewById(R.id.teatimethu2);
                            Boolean thu5 = (Boolean) documentSnapshot.getBoolean("10:00 น - 10:15 น");
                            Boolean thu6 = (Boolean) documentSnapshot.getBoolean("10:15 น - 10:30 น");
                            Boolean thu7 = (Boolean) documentSnapshot.getBoolean("10:30 น - 10:45 น");
                            Boolean thu8 = (Boolean) documentSnapshot.getBoolean("10:45 น - 11:00 น");
                            if ((thu5 == null || thu5 == false) || (thu6 == null || thu6 == false) || (thu7 == null || thu7 == false) || (thu8 == null || thu8 == false)) {
                                teatimethu2.setBackgroundResource(R.color.FreeTime);
                                statethu2 = false;
                            } else {
                                teatimethu2.setBackgroundResource(R.color.NoFreeTime);
                                statethu2 = true;
                            }

                            TextView teatimethu3 = (TextView) findViewById(R.id.teatimethu3);
                            Boolean thu9 = (Boolean) documentSnapshot.getBoolean("11:00 น - 11:15 น");
                            Boolean thu10 = (Boolean) documentSnapshot.getBoolean("11:15 น - 11:30 น");
                            Boolean thu11 = (Boolean) documentSnapshot.getBoolean("11:30 น - 11:45 น");
                            Boolean thu12 = (Boolean) documentSnapshot.getBoolean("11:45 น - 12:00 น");
                            if ((thu9 == null || thu9 == false) || (thu10 == null || thu10 == false) || (thu11 == null || thu11 == false) || (thu12 == null || thu12 == false)) {
                                teatimethu3.setBackgroundResource(R.color.FreeTime);
                                statethu3 = false;
                            } else {
                                teatimethu3.setBackgroundResource(R.color.NoFreeTime);
                                statethu3 = true;
                            }

                            TextView teatimethu4 = (TextView) findViewById(R.id.teatimethu4);
                            Boolean thu13 = (Boolean) documentSnapshot.getBoolean("12:00 น - 12:15 น");
                            Boolean thu14 = (Boolean) documentSnapshot.getBoolean("12:15 น - 12:30 น");
                            Boolean thu15 = (Boolean) documentSnapshot.getBoolean("12:30 น - 12:45 น");
                            Boolean thu16 = (Boolean) documentSnapshot.getBoolean("12:45 น - 13:00 น");
                            if ((thu13 == null || thu13 == false) || (thu14 == null || thu14 == false) || (thu15 == null || thu15 == false) || (thu16 == null || thu16 == false)) {
                                teatimethu4.setBackgroundResource(R.color.FreeTime);
                                statethu4 = false;
                            } else {
                                teatimethu4.setBackgroundResource(R.color.NoFreeTime);
                                statethu4 = true;
                            }

                            TextView teatimethu5 = (TextView) findViewById(R.id.teatimethu5);
                            Boolean thu17 = (Boolean) documentSnapshot.getBoolean("13:00 น - 13:15 น");
                            Boolean thu18 = (Boolean) documentSnapshot.getBoolean("13:15 น - 13:30 น");
                            Boolean thu19 = (Boolean) documentSnapshot.getBoolean("13:30 น - 13:45 น");
                            Boolean thu20 = (Boolean) documentSnapshot.getBoolean("13:45 น - 14:00 น");
                            if ((thu17 == null || thu17 == false) || (thu18 == null || thu18 == false) || (thu19 == null || thu19 == false) || (thu20 == null || thu20 == false)) {
                                teatimethu5.setBackgroundResource(R.color.FreeTime);
                                statethu5 = false;
                            } else {
                                teatimethu5.setBackgroundResource(R.color.NoFreeTime);
                                statethu5 = true;
                            }

                            TextView teatimethu6 = (TextView) findViewById(R.id.teatimethu6);
                            Boolean thu21 = (Boolean) documentSnapshot.getBoolean("14:00 น - 14:15 น");
                            Boolean thu22 = (Boolean) documentSnapshot.getBoolean("14:15 น - 14:30 น");
                            Boolean thu23 = (Boolean) documentSnapshot.getBoolean("14:30 น - 14:45 น");
                            Boolean thu24 = (Boolean) documentSnapshot.getBoolean("14:45 น - 15:00 น");
                            if ((thu21 == null || thu21 == false) || (thu22 == null || thu22 == false) || (thu23 == null || thu23 == false) || (thu24 == null || thu24 == false)) {
                                teatimethu6.setBackgroundResource(R.color.FreeTime);
                                statethu6 = false;
                            } else {
                                teatimethu6.setBackgroundResource(R.color.NoFreeTime);
                                statethu6 = true;
                            }

                            TextView teatimethu7 = (TextView) findViewById(R.id.teatimethu7);
                            Boolean thu25 = (Boolean) documentSnapshot.getBoolean("15:00 น - 15:15 น");
                            Boolean thu26 = (Boolean) documentSnapshot.getBoolean("15:15 น - 15:30 น");
                            Boolean thu27 = (Boolean) documentSnapshot.getBoolean("15:30 น - 15:45 น");
                            Boolean thu28 = (Boolean) documentSnapshot.getBoolean("15:45 น - 16:00 น");
                            if ((thu25 == null || thu25 == false) || (thu26 == null || thu26 == false) || (thu27 == null || thu27 == false) || (thu28 == null || thu28 == false)) {
                                teatimethu7.setBackgroundResource(R.color.FreeTime);
                                statethu7 = false;
                            } else {
                                teatimethu7.setBackgroundResource(R.color.NoFreeTime);
                                statethu7 = true;
                            }

                            TextView teatimethu8 = (TextView) findViewById(R.id.teatimethu8);
                            Boolean thu29 = (Boolean) documentSnapshot.getBoolean("16:00 น - 16:15 น");
                            Boolean thu30 = (Boolean) documentSnapshot.getBoolean("16:15 น - 16:30 น");
                            Boolean thu31 = (Boolean) documentSnapshot.getBoolean("16:30 น - 16:45 น");
                            Boolean thu32 = (Boolean) documentSnapshot.getBoolean("16:45 น - 17:00 น");
                            if ((thu29 == null || thu29 == false) || (thu30 == null || thu30 == false) || (thu31 == null || thu31 == false) || (thu32 == null || thu32 == false)) {
                                teatimethu8.setBackgroundResource(R.color.FreeTime);
                                statethu8 = false;
                            } else {
                                teatimethu8.setBackgroundResource(R.color.NoFreeTime);
                                statethu8 = true;
                            }

                            TextView teatimethu9 = (TextView) findViewById(R.id.teatimethu9);
                            Boolean thu33 = (Boolean) documentSnapshot.getBoolean("17:00 น - 17:15 น");
                            Boolean thu34 = (Boolean) documentSnapshot.getBoolean("17:15 น - 17:30 น");
                            Boolean thu35 = (Boolean) documentSnapshot.getBoolean("17:30 น - 17:45 น");
                            Boolean thu36 = (Boolean) documentSnapshot.getBoolean("17:45 น - 18:00 น");
                            if ((thu33 == null || thu33 == false) || (thu34 == null || thu34 == false) || (thu35 == null || thu35 == false) || (thu36 == null || thu36 == false)) {
                                teatimethu9.setBackgroundResource(R.color.FreeTime);
                                statethu9 = false;
                            } else {
                                teatimethu9.setBackgroundResource(R.color.NoFreeTime);
                                statethu9 = true;
                            }
                        }
                    });

                    myTime.document(nameTeacherCurrent + "_ศุกร์").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            TextView teatimefri1 = (TextView) findViewById(R.id.teatimefri1);
                            Boolean fri1 = (Boolean) documentSnapshot.getBoolean("9:00 น - 9:15 น");
                            Boolean fri2 = (Boolean) documentSnapshot.getBoolean("9:15 น - 9:30 น");
                            Boolean fri3 = (Boolean) documentSnapshot.getBoolean("9:30 น - 9:45 น");
                            Boolean fri4 = (Boolean) documentSnapshot.getBoolean("9:45 น - 10:00 น");
                            if ((fri1 == null || fri1 == false) || (fri2 == null || fri2 == false) || (fri3 == null || fri3 == false) || (fri4 == null || fri4 == false)) {
                                teatimefri1.setBackgroundResource(R.color.FreeTime);
                                statefri1 = false;
                            } else {
                                teatimefri1.setBackgroundResource(R.color.NoFreeTime);
                                statefri1 = true;
                            }

                            TextView teatimefri2 = (TextView) findViewById(R.id.teatimefri2);
                            Boolean fri5 = (Boolean) documentSnapshot.getBoolean("10:00 น - 10:15 น");
                            Boolean fri6 = (Boolean) documentSnapshot.getBoolean("10:15 น - 10:30 น");
                            Boolean fri7 = (Boolean) documentSnapshot.getBoolean("10:30 น - 10:45 น");
                            Boolean fri8 = (Boolean) documentSnapshot.getBoolean("10:45 น - 11:00 น");
                            if ((fri5 == null || fri5 == false) || (fri6 == null || fri6 == false) || (fri7 == null || fri7 == false) || (fri8 == null || fri8 == false)) {
                                teatimefri2.setBackgroundResource(R.color.FreeTime);
                                statefri2 = false;
                            } else {
                                teatimefri2.setBackgroundResource(R.color.NoFreeTime);
                                statefri2 = true;
                            }

                            TextView teatimefri3 = (TextView) findViewById(R.id.teatimefri3);
                            Boolean fri9 = (Boolean) documentSnapshot.getBoolean("11:00 น - 11:15 น");
                            Boolean fri10 = (Boolean) documentSnapshot.getBoolean("11:15 น - 11:30 น");
                            Boolean fri11 = (Boolean) documentSnapshot.getBoolean("11:30 น - 11:45 น");
                            Boolean fri12 = (Boolean) documentSnapshot.getBoolean("11:45 น - 12:00 น");
                            if ((fri9 == null || fri9 == false) || (fri10 == null || fri10 == false) || (fri11 == null || fri11 == false) || (fri12 == null || fri12 == false)) {
                                teatimefri3.setBackgroundResource(R.color.FreeTime);
                                statefri3 = false;
                            } else {
                                teatimefri3.setBackgroundResource(R.color.NoFreeTime);
                                statefri3 = true;
                            }

                            TextView teatimefri4 = (TextView) findViewById(R.id.teatimefri4);
                            Boolean fri13 = (Boolean) documentSnapshot.getBoolean("12:00 น - 12:15 น");
                            Boolean fri14 = (Boolean) documentSnapshot.getBoolean("12:15 น - 12:30 น");
                            Boolean fri15 = (Boolean) documentSnapshot.getBoolean("12:30 น - 12:45 น");
                            Boolean fri16 = (Boolean) documentSnapshot.getBoolean("12:45 น - 13:00 น");
                            if ((fri13 == null || fri13 == false) || (fri14 == null || fri14 == false) || (fri15 == null || fri15 == false) || (fri16 == null || fri16 == false)) {
                                teatimefri4.setBackgroundResource(R.color.FreeTime);
                                statefri4 = false;
                            } else {
                                teatimefri4.setBackgroundResource(R.color.NoFreeTime);
                                statefri4 = true;
                            }

                            TextView teatimefri5 = (TextView) findViewById(R.id.teatimefri5);
                            Boolean fri17 = (Boolean) documentSnapshot.getBoolean("13:00 น - 13:15 น");
                            Boolean fri18 = (Boolean) documentSnapshot.getBoolean("13:15 น - 13:30 น");
                            Boolean fri19 = (Boolean) documentSnapshot.getBoolean("13:30 น - 13:45 น");
                            Boolean fri20 = (Boolean) documentSnapshot.getBoolean("13:45 น - 14:00 น");
                            if ((fri17 == null || fri17 == false) || (fri18 == null || fri18 == false) || (fri19 == null || fri19 == false) || (fri20 == null || fri20 == false)) {
                                teatimefri5.setBackgroundResource(R.color.FreeTime);
                                statefri5 = false;
                            } else {
                                teatimefri5.setBackgroundResource(R.color.NoFreeTime);
                                statefri5 = true;
                            }

                            TextView teatimefri6 = (TextView) findViewById(R.id.teatimefri6);
                            Boolean fri21 = (Boolean) documentSnapshot.getBoolean("14:00 น - 14:15 น");
                            Boolean fri22 = (Boolean) documentSnapshot.getBoolean("14:15 น - 14:30 น");
                            Boolean fri23 = (Boolean) documentSnapshot.getBoolean("14:30 น - 14:45 น");
                            Boolean fri24 = (Boolean) documentSnapshot.getBoolean("14:45 น - 15:00 น");
                            if ((fri21 == null || fri21 == false) || (fri22 == null || fri22 == false) || (fri23 == null || fri23 == false) || (fri24 == null || fri24 == false)) {
                                teatimefri6.setBackgroundResource(R.color.FreeTime);
                                statefri6 = false;
                            } else {
                                teatimefri6.setBackgroundResource(R.color.NoFreeTime);
                                statefri6 = true;
                            }

                            TextView teatimefri7 = (TextView) findViewById(R.id.teatimefri7);
                            Boolean fri25 = (Boolean) documentSnapshot.getBoolean("15:00 น - 15:15 น");
                            Boolean fri26 = (Boolean) documentSnapshot.getBoolean("15:15 น - 15:30 น");
                            Boolean fri27 = (Boolean) documentSnapshot.getBoolean("15:30 น - 15:45 น");
                            Boolean fri28 = (Boolean) documentSnapshot.getBoolean("15:45 น - 16:00 น");
                            if ((fri25 == null || fri25 == false) || (fri26 == null || fri26 == false) || (fri27 == null || fri27 == false) || (fri28 == null || fri28 == false)) {
                                teatimefri7.setBackgroundResource(R.color.FreeTime);
                                statefri7 = false;
                            } else {
                                teatimefri7.setBackgroundResource(R.color.NoFreeTime);
                                statefri7 = true;
                            }

                            TextView teatimefri8 = (TextView) findViewById(R.id.teatimefri8);
                            Boolean fri29 = (Boolean) documentSnapshot.getBoolean("16:00 น - 16:15 น");
                            Boolean fri30 = (Boolean) documentSnapshot.getBoolean("16:15 น - 16:30 น");
                            Boolean fri31 = (Boolean) documentSnapshot.getBoolean("16:30 น - 16:45 น");
                            Boolean fri32 = (Boolean) documentSnapshot.getBoolean("16:45 น - 17:00 น");
                            if ((fri29 == null || fri29 == false) || (fri30 == null || fri30 == false) || (fri31 == null || fri31 == false) || (fri32 == null || fri32 == false)) {
                                teatimefri8.setBackgroundResource(R.color.FreeTime);
                                statefri8 = false;
                            } else {
                                teatimefri8.setBackgroundResource(R.color.NoFreeTime);
                                statefri8 = true;
                            }

                            TextView teatimefri9 = (TextView) findViewById(R.id.teatimefri9);
                            Boolean fri33 = (Boolean) documentSnapshot.getBoolean("17:00 น - 17:15 น");
                            Boolean fri34 = (Boolean) documentSnapshot.getBoolean("17:15 น - 17:30 น");
                            Boolean fri35 = (Boolean) documentSnapshot.getBoolean("17:30 น - 17:45 น");
                            Boolean fri36 = (Boolean) documentSnapshot.getBoolean("17:45 น - 18:00 น");
                            if ((fri33 == null || fri33 == false) || (fri34 == null || fri34 == false) || (fri35 == null || fri35 == false) || (fri36 == null || fri36 == false)) {
                                teatimefri9.setBackgroundResource(R.color.FreeTime);
                                statefri9 = false;
                            } else {
                                teatimefri9.setBackgroundResource(R.color.NoFreeTime);
                                statefri9 = true;
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
        }
    }

    public void clickmon1(View view) {
        DocumentReference update = updatetime.document(nameTeacherCurrent + "_จันทร์");
        if (statemon1 == null || statemon1 == false) {
            view.setBackgroundResource(R.color.NoFreeTime);
            this.statemon1 = true;
            update.update("9:00 น - 9:15 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("9:15 น - 9:30 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("9:30 น - 9:45 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("9:45 น - 10:00 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            Toast.makeText(TeacherUpdateTime_Page.this, "อัพเดทเวลาเสร็จสิ้น", Toast.LENGTH_LONG).show();
        } else {
            view.setBackgroundResource(R.color.FreeTime);
            this.statemon1 = false;
            update.update("9:00 น - 9:15 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("9:15 น - 9:30 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("9:30 น - 9:45 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("9:45 น - 10:00 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            Toast.makeText(TeacherUpdateTime_Page.this, "อัพเดทเวลาเสร็จสิ้น", Toast.LENGTH_LONG).show();
        }
    }

    public void clickmon2(View view) {
        DocumentReference update = updatetime.document(nameTeacherCurrent + "_จันทร์");
        if (statemon2 == null || statemon2 == false) {
            view.setBackgroundResource(R.color.NoFreeTime);
            this.statemon2 = true;
            update.update("10:00 น - 10:15 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("10:15 น - 10:30 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("10:30 น - 10:45 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("10:45 น - 11:00 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            Toast.makeText(TeacherUpdateTime_Page.this, "อัพเดทเวลาเสร็จสิ้น", Toast.LENGTH_LONG).show();
        } else {
            view.setBackgroundResource(R.color.FreeTime);
            this.statemon2 = false;
            update.update("10:00 น - 10:15 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("10:15 น - 10:30 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("10:30 น - 10:45 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("10:45 น - 11:00 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            Toast.makeText(TeacherUpdateTime_Page.this, "อัพเดทเวลาเสร็จสิ้น", Toast.LENGTH_LONG).show();
        }
    }

    public void clickmon3(View view) {
        DocumentReference update = updatetime.document(nameTeacherCurrent + "_จันทร์");
        if (statemon3 == null || statemon3 == false) {
            view.setBackgroundResource(R.color.NoFreeTime);
            this.statemon3 = true;
            update.update("11:00 น - 11:15 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("11:15 น - 11:30 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("11:30 น - 11:45 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("11:45 น - 12:00 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            Toast.makeText(TeacherUpdateTime_Page.this, "อัพเดทเวลาเสร็จสิ้น", Toast.LENGTH_LONG).show();
        } else {
            view.setBackgroundResource(R.color.FreeTime);
            this.statemon3 = false;
            update.update("11:00 น - 11:15 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("11:15 น - 11:30 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("11:30 น - 11:45 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("11:45 น - 12:00 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            Toast.makeText(TeacherUpdateTime_Page.this, "อัพเดทเวลาเสร็จสิ้น", Toast.LENGTH_LONG).show();
        }
    }

    public void clickmon4(View view) {
        DocumentReference update = updatetime.document(nameTeacherCurrent + "_จันทร์");
        if (statemon4 == null || statemon4 == false) {
            view.setBackgroundResource(R.color.NoFreeTime);
            this.statemon4 = true;
            update.update("12:00 น - 12:15 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("12:15 น - 12:30 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("12:30 น - 12:45 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("12:45 น - 13:00 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            Toast.makeText(TeacherUpdateTime_Page.this, "อัพเดทเวลาเสร็จสิ้น", Toast.LENGTH_LONG).show();
        } else {
            view.setBackgroundResource(R.color.FreeTime);
            this.statemon4 = false;
            update.update("12:00 น - 12:15 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("12:15 น - 12:30 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("12:30 น - 12:45 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("12:45 น - 13:00 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            Toast.makeText(TeacherUpdateTime_Page.this, "อัพเดทเวลาเสร็จสิ้น", Toast.LENGTH_LONG).show();
        }
    }

    public void clickmon5(View view) {
        DocumentReference update = updatetime.document(nameTeacherCurrent + "_จันทร์");
        if (statemon5 == null || statemon5 == false) {
            view.setBackgroundResource(R.color.NoFreeTime);
            this.statemon5 = true;
            update.update("13:00 น - 13:15 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("13:15 น - 13:30 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("13:30 น - 13:45 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("13:45 น - 14:00 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            Toast.makeText(TeacherUpdateTime_Page.this, "อัพเดทเวลาเสร็จสิ้น", Toast.LENGTH_LONG).show();
        } else {
            view.setBackgroundResource(R.color.FreeTime);
            this.statemon5 = false;
            update.update("13:00 น - 13:15 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("13:15 น - 13:30 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("13:30 น - 13:45 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("13:45 น - 14:00 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            Toast.makeText(TeacherUpdateTime_Page.this, "อัพเดทเวลาเสร็จสิ้น", Toast.LENGTH_LONG).show();
        }
    }


    public void clickmon6(View view) {
        DocumentReference update = updatetime.document(nameTeacherCurrent + "_จันทร์");
        if (statemon6 == null || statemon6 == false) {
            view.setBackgroundResource(R.color.NoFreeTime);
            this.statemon6 = true;
            update.update("14:00 น - 14:15 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("14:15 น - 14:30 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("14:30 น - 14:45 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("14:45 น - 15:00 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            Toast.makeText(TeacherUpdateTime_Page.this, "อัพเดทเวลาเสร็จสิ้น", Toast.LENGTH_LONG).show();
        } else {
            view.setBackgroundResource(R.color.FreeTime);
            this.statemon6 = false;
            update.update("14:00 น - 14:15 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("14:15 น - 14:30 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("14:30 น - 14:45 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("14:45 น - 15:00 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            Toast.makeText(TeacherUpdateTime_Page.this, "อัพเดทเวลาเสร็จสิ้น", Toast.LENGTH_LONG).show();
        }
    }

    public void clickmon7(View view) {
        DocumentReference update = updatetime.document(nameTeacherCurrent + "_จันทร์");
        if (statemon7 == null || statemon7 == false) {
            view.setBackgroundResource(R.color.NoFreeTime);
            this.statemon7 = true;
            update.update("15:00 น - 15:15 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("15:15 น - 15:30 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("15:30 น - 15:45 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("15:45 น - 16:00 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            Toast.makeText(TeacherUpdateTime_Page.this, "อัพเดทเวลาเสร็จสิ้น", Toast.LENGTH_LONG).show();
        } else {
            view.setBackgroundResource(R.color.FreeTime);
            this.statemon7 = false;
            update.update("15:00 น - 15:15 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("15:15 น - 15:30 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("15:30 น - 15:45 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("15:45 น - 16:00 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            Toast.makeText(TeacherUpdateTime_Page.this, "อัพเดทเวลาเสร็จสิ้น", Toast.LENGTH_LONG).show();
        }
    }

    public void clickmon8(View view) {
        DocumentReference update = updatetime.document(nameTeacherCurrent + "_จันทร์");
        if (statemon8 == null || statemon8 == false) {
            view.setBackgroundResource(R.color.NoFreeTime);
            this.statemon8 = true;
            update.update("16:00 น - 16:15 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("16:15 น - 16:30 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("16:30 น - 16:45 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("16:45 น - 17:00 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            Toast.makeText(TeacherUpdateTime_Page.this, "อัพเดทเวลาเสร็จสิ้น", Toast.LENGTH_LONG).show();
        } else {
            view.setBackgroundResource(R.color.FreeTime);
            this.statemon8 = false;
            update.update("16:00 น - 16:15 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("16:15 น - 16:30 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("16:30 น - 16:45 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("16:45 น - 17:00 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            Toast.makeText(TeacherUpdateTime_Page.this, "อัพเดทเวลาเสร็จสิ้น", Toast.LENGTH_LONG).show();
        }
    }

    public void clickmon9(View view) {
        DocumentReference update = updatetime.document(nameTeacherCurrent + "_จันทร์");
        if (statemon9 == null || statemon9 == false) {
            view.setBackgroundResource(R.color.NoFreeTime);
            this.statemon9 = true;
            update.update("17:00 น - 17:15 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("17:15 น - 17:30 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("17:30 น - 17:45 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("17:45 น - 18:00 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            Toast.makeText(TeacherUpdateTime_Page.this, "อัพเดทเวลาเสร็จสิ้น", Toast.LENGTH_LONG).show();
        } else {
            view.setBackgroundResource(R.color.FreeTime);
            this.statemon9 = false;
            update.update("17:00 น - 17:15 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("17:15 น - 17:30 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("17:30 น - 17:45 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("17:45 น - 18:00 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            Toast.makeText(TeacherUpdateTime_Page.this, "อัพเดทเวลาเสร็จสิ้น", Toast.LENGTH_LONG).show();
        }
    }

    public void clicktue1(View view) {
        DocumentReference update = updatetime.document(nameTeacherCurrent + "_อังคาร");
        if (statetue1 == null || statetue1 == false) {
            view.setBackgroundResource(R.color.NoFreeTime);
            this.statetue1 = true;
            update.update("9:00 น - 9:15 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("9:15 น - 9:30 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("9:30 น - 9:45 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("9:45 น - 10:00 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            Toast.makeText(TeacherUpdateTime_Page.this, "อัพเดทเวลาเสร็จสิ้น", Toast.LENGTH_LONG).show();
        } else {
            view.setBackgroundResource(R.color.FreeTime);
            this.statetue1 = false;
            update.update("9:00 น - 9:15 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("9:15 น - 9:30 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("9:30 น - 9:45 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("9:45 น - 10:00 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            Toast.makeText(TeacherUpdateTime_Page.this, "อัพเดทเวลาเสร็จสิ้น", Toast.LENGTH_LONG).show();
        }
    }

    public void clicktue2(View view) {
        DocumentReference update = updatetime.document(nameTeacherCurrent + "_อังคาร");
        if (statetue2 == null || statetue2 == false) {
            view.setBackgroundResource(R.color.NoFreeTime);
            this.statetue2 = true;
            update.update("10:00 น - 10:15 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("10:15 น - 10:30 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("10:30 น - 10:45 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("10:45 น - 11:00 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            Toast.makeText(TeacherUpdateTime_Page.this, "อัพเดทเวลาเสร็จสิ้น", Toast.LENGTH_LONG).show();
        } else {
            view.setBackgroundResource(R.color.FreeTime);
            this.statetue2 = false;
            update.update("10:00 น - 10:15 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("10:15 น - 10:30 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("10:30 น - 10:45 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("10:45 น - 11:00 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            Toast.makeText(TeacherUpdateTime_Page.this, "อัพเดทเวลาเสร็จสิ้น", Toast.LENGTH_LONG).show();
        }
    }

    public void clicktue3(View view) {
        DocumentReference update = updatetime.document(nameTeacherCurrent + "_อังคาร");
        if (statetue3 == null || statetue3 == false) {
            view.setBackgroundResource(R.color.NoFreeTime);
            this.statetue3 = true;
            update.update("11:00 น - 11:15 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("11:15 น - 11:30 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("11:30 น - 11:45 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("11:45 น - 12:00 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            Toast.makeText(TeacherUpdateTime_Page.this, "อัพเดทเวลาเสร็จสิ้น", Toast.LENGTH_LONG).show();
        } else {
            view.setBackgroundResource(R.color.FreeTime);
            this.statetue3 = false;
            update.update("11:00 น - 11:15 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("11:15 น - 11:30 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("11:30 น - 11:45 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("11:45 น - 12:00 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            Toast.makeText(TeacherUpdateTime_Page.this, "อัพเดทเวลาเสร็จสิ้น", Toast.LENGTH_LONG).show();
        }
    }

    public void clicktue4(View view) {
        DocumentReference update = updatetime.document(nameTeacherCurrent + "_อังคาร");
        if (statetue4 == null || statetue4 == false) {
            view.setBackgroundResource(R.color.NoFreeTime);
            this.statetue4 = true;
            update.update("12:00 น - 12:15 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("12:15 น - 12:30 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("12:30 น - 12:45 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("12:45 น - 13:00 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            Toast.makeText(TeacherUpdateTime_Page.this, "อัพเดทเวลาเสร็จสิ้น", Toast.LENGTH_LONG).show();
        } else {
            view.setBackgroundResource(R.color.FreeTime);
            this.statetue4 = false;
            update.update("12:00 น - 12:15 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("12:15 น - 12:30 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("12:30 น - 12:45 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("12:45 น - 13:00 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            Toast.makeText(TeacherUpdateTime_Page.this, "อัพเดทเวลาเสร็จสิ้น", Toast.LENGTH_LONG).show();
        }
    }

    public void clicktue5(View view) {
        DocumentReference update = updatetime.document(nameTeacherCurrent + "_อังคาร");
        if (statetue5 == null || statetue5 == false) {
            view.setBackgroundResource(R.color.NoFreeTime);
            this.statetue5 = true;
            update.update("13:00 น - 13:15 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("13:15 น - 13:30 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("13:30 น - 13:45 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("13:45 น - 14:00 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            Toast.makeText(TeacherUpdateTime_Page.this, "อัพเดทเวลาเสร็จสิ้น", Toast.LENGTH_LONG).show();
        } else {
            view.setBackgroundResource(R.color.FreeTime);
            this.statetue5 = false;
            update.update("13:00 น - 13:15 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("13:15 น - 13:30 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("13:30 น - 13:45 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("13:45 น - 14:00 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            Toast.makeText(TeacherUpdateTime_Page.this, "อัพเดทเวลาเสร็จสิ้น", Toast.LENGTH_LONG).show();
        }
    }

    public void clicktue6(View view) {
        DocumentReference update = updatetime.document(nameTeacherCurrent + "_อังคาร");
        if (statetue6 == null || statetue6 == false) {
            view.setBackgroundResource(R.color.NoFreeTime);
            this.statetue6 = true;
            update.update("14:00 น - 14:15 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("14:15 น - 14:30 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("14:30 น - 14:45 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("14:45 น - 15:00 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            Toast.makeText(TeacherUpdateTime_Page.this, "อัพเดทเวลาเสร็จสิ้น", Toast.LENGTH_LONG).show();
        } else {
            view.setBackgroundResource(R.color.FreeTime);
            this.statetue6 = false;
            update.update("14:00 น - 14:15 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("14:15 น - 14:30 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("14:30 น - 14:45 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("14:45 น - 15:00 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            Toast.makeText(TeacherUpdateTime_Page.this, "อัพเดทเวลาเสร็จสิ้น", Toast.LENGTH_LONG).show();
        }
    }

    public void clicktue7(View view) {
        DocumentReference update = updatetime.document(nameTeacherCurrent + "_อังคาร");
        if (statetue7 == null || statetue7 == false) {
            view.setBackgroundResource(R.color.NoFreeTime);
            this.statetue7 = true;
            update.update("15:00 น - 15:15 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("15:15 น - 15:30 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("15:30 น - 15:45 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("15:45 น - 16:00 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            Toast.makeText(TeacherUpdateTime_Page.this, "อัพเดทเวลาเสร็จสิ้น", Toast.LENGTH_LONG).show();
        } else {
            view.setBackgroundResource(R.color.FreeTime);
            this.statetue7 = false;
            update.update("15:00 น - 15:15 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("15:15 น - 15:30 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("15:30 น - 15:45 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("15:45 น - 16:00 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            Toast.makeText(TeacherUpdateTime_Page.this, "อัพเดทเวลาเสร็จสิ้น", Toast.LENGTH_LONG).show();
        }
    }

    public void clicktue8(View view) {
        DocumentReference update = updatetime.document(nameTeacherCurrent + "_อังคาร");
        if (statetue8 == null || statetue8 == false) {
            view.setBackgroundResource(R.color.NoFreeTime);
            this.statetue8 = true;
            update.update("16:00 น - 16:15 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("16:15 น - 16:30 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("16:30 น - 16:45 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("16:45 น - 17:00 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            Toast.makeText(TeacherUpdateTime_Page.this, "อัพเดทเวลาเสร็จสิ้น", Toast.LENGTH_LONG).show();
        } else {
            view.setBackgroundResource(R.color.FreeTime);
            this.statetue8 = false;
            update.update("16:00 น - 16:15 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("16:15 น - 16:30 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("16:30 น - 16:45 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("16:45 น - 17:00 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            Toast.makeText(TeacherUpdateTime_Page.this, "อัพเดทเวลาเสร็จสิ้น", Toast.LENGTH_LONG).show();
        }
    }

    public void clicktue9(View view) {
        DocumentReference update = updatetime.document(nameTeacherCurrent + "_อังคาร");
        if (statetue9 == null || statetue9 == false) {
            view.setBackgroundResource(R.color.NoFreeTime);
            this.statetue9 = true;
            update.update("17:00 น - 17:15 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("17:15 น - 17:30 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("17:30 น - 17:45 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("17:45 น - 18:00 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            Toast.makeText(TeacherUpdateTime_Page.this, "อัพเดทเวลาเสร็จสิ้น", Toast.LENGTH_LONG).show();
        } else {
            view.setBackgroundResource(R.color.FreeTime);
            this.statetue9 = false;
            update.update("17:00 น - 17:15 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("17:15 น - 17:30 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("17:30 น - 17:45 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("17:45 น - 18:00 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            Toast.makeText(TeacherUpdateTime_Page.this, "อัพเดทเวลาเสร็จสิ้น", Toast.LENGTH_LONG).show();
        }
    }

    public void clickwed1(View view) {
        DocumentReference update = updatetime.document(nameTeacherCurrent + "_พุธ");
        if (statewed1 == null || statewed1 == false) {
            view.setBackgroundResource(R.color.NoFreeTime);
            this.statewed1 = true;
            update.update("9:00 น - 9:15 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("9:15 น - 9:30 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("9:30 น - 9:45 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("9:45 น - 10:00 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            Toast.makeText(TeacherUpdateTime_Page.this, "อัพเดทเวลาเสร็จสิ้น", Toast.LENGTH_LONG).show();
        } else {
            view.setBackgroundResource(R.color.FreeTime);
            this.statewed1 = false;
            update.update("9:00 น - 9:15 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("9:15 น - 9:30 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("9:30 น - 9:45 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("9:45 น - 10:00 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            Toast.makeText(TeacherUpdateTime_Page.this, "อัพเดทเวลาเสร็จสิ้น", Toast.LENGTH_LONG).show();
        }
    }

    public void clickwed2(View view) {
        DocumentReference update = updatetime.document(nameTeacherCurrent + "_พุธ");
        if (statewed2 == null || statewed2 == false) {
            view.setBackgroundResource(R.color.NoFreeTime);
            this.statewed2 = true;
            update.update("10:00 น - 10:15 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("10:15 น - 10:30 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("10:30 น - 10:45 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("10:45 น - 11:00 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            Toast.makeText(TeacherUpdateTime_Page.this, "อัพเดทเวลาเสร็จสิ้น", Toast.LENGTH_LONG).show();
        } else {
            view.setBackgroundResource(R.color.FreeTime);
            this.statewed2 = false;
            update.update("10:00 น - 10:15 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("10:15 น - 10:30 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("10:30 น - 10:45 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("10:45 น - 11:00 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            Toast.makeText(TeacherUpdateTime_Page.this, "อัพเดทเวลาเสร็จสิ้น", Toast.LENGTH_LONG).show();
        }
    }

    public void clickwed3(View view) {
        DocumentReference update = updatetime.document(nameTeacherCurrent + "_พุธ");
        if (statewed3 == null || statewed3 == false) {
            view.setBackgroundResource(R.color.NoFreeTime);
            this.statewed3 = true;
            update.update("11:00 น - 11:15 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("11:15 น - 11:30 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("11:30 น - 11:45 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("11:45 น - 12:00 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            Toast.makeText(TeacherUpdateTime_Page.this, "อัพเดทเวลาเสร็จสิ้น", Toast.LENGTH_LONG).show();
        } else {
            view.setBackgroundResource(R.color.FreeTime);
            this.statewed3 = false;
            update.update("11:00 น - 11:15 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("11:15 น - 11:30 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("11:30 น - 11:45 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("11:45 น - 12:00 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            Toast.makeText(TeacherUpdateTime_Page.this, "อัพเดทเวลาเสร็จสิ้น", Toast.LENGTH_LONG).show();
        }
    }

    public void clickwed4(View view) {
        DocumentReference update = updatetime.document(nameTeacherCurrent + "_พุธ");
        if (statewed4 == null || statewed4 == false) {
            view.setBackgroundResource(R.color.NoFreeTime);
            this.statewed4 = true;
            update.update("12:00 น - 12:15 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("12:15 น - 12:30 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("12:30 น - 12:45 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("12:45 น - 13:00 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            Toast.makeText(TeacherUpdateTime_Page.this, "อัพเดทเวลาเสร็จสิ้น", Toast.LENGTH_LONG).show();
        } else {
            view.setBackgroundResource(R.color.FreeTime);
            this.statewed4 = false;
            update.update("12:00 น - 12:15 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("12:15 น - 12:30 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("12:30 น - 12:45 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("12:45 น - 13:00 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            Toast.makeText(TeacherUpdateTime_Page.this, "อัพเดทเวลาเสร็จสิ้น", Toast.LENGTH_LONG).show();
        }
    }

    public void clickwed5(View view) {
        DocumentReference update = updatetime.document(nameTeacherCurrent + "_พุธ");
        if (statewed5 == null || statewed5 == false) {
            view.setBackgroundResource(R.color.NoFreeTime);
            this.statewed5 = true;
            update.update("13:00 น - 13:15 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("13:15 น - 13:30 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("13:30 น - 13:45 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("13:45 น - 14:00 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            Toast.makeText(TeacherUpdateTime_Page.this, "อัพเดทเวลาเสร็จสิ้น", Toast.LENGTH_LONG).show();
        } else {
            view.setBackgroundResource(R.color.FreeTime);
            this.statewed5 = false;
            update.update("13:00 น - 13:15 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("13:15 น - 13:30 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("13:30 น - 13:45 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("13:45 น - 14:00 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            Toast.makeText(TeacherUpdateTime_Page.this, "อัพเดทเวลาเสร็จสิ้น", Toast.LENGTH_LONG).show();
        }
    }

    public void clickwed6(View view) {
        DocumentReference update = updatetime.document(nameTeacherCurrent + "_พุธ");
        if (statewed6 == null || statewed6 == false) {
            view.setBackgroundResource(R.color.NoFreeTime);
            this.statewed6 = true;
            update.update("14:00 น - 14:15 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("14:15 น - 14:30 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("14:30 น - 14:45 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("14:45 น - 15:00 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            Toast.makeText(TeacherUpdateTime_Page.this, "อัพเดทเวลาเสร็จสิ้น", Toast.LENGTH_LONG).show();
        } else {
            view.setBackgroundResource(R.color.FreeTime);
            this.statewed6 = false;
            update.update("14:00 น - 14:15 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("14:15 น - 14:30 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("14:30 น - 14:45 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("14:45 น - 15:00 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            Toast.makeText(TeacherUpdateTime_Page.this, "อัพเดทเวลาเสร็จสิ้น", Toast.LENGTH_LONG).show();
        }
    }

    public void clickwed7(View view) {
        DocumentReference update = updatetime.document(nameTeacherCurrent + "_พุธ");
        if (statewed7 == null || statewed7 == false) {
            view.setBackgroundResource(R.color.NoFreeTime);
            this.statewed7 = true;
            update.update("15:00 น - 15:15 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("15:15 น - 15:30 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("15:30 น - 15:45 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("15:45 น - 16:00 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            Toast.makeText(TeacherUpdateTime_Page.this, "อัพเดทเวลาเสร็จสิ้น", Toast.LENGTH_LONG).show();
        } else {
            view.setBackgroundResource(R.color.FreeTime);
            this.statewed7 = false;
            update.update("15:00 น - 15:15 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("15:15 น - 15:30 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("15:30 น - 15:45 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("15:45 น - 16:00 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            Toast.makeText(TeacherUpdateTime_Page.this, "อัพเดทเวลาเสร็จสิ้น", Toast.LENGTH_LONG).show();
        }
    }

    public void clickwed8(View view) {
        DocumentReference update = updatetime.document(nameTeacherCurrent + "_พุธ");
        if (statewed8 == null || statewed8 == false) {
            view.setBackgroundResource(R.color.NoFreeTime);
            this.statewed8 = true;
            update.update("16:00 น - 16:15 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("16:15 น - 16:30 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("16:30 น - 16:45 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("16:45 น - 17:00 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            Toast.makeText(TeacherUpdateTime_Page.this, "อัพเดทเวลาเสร็จสิ้น", Toast.LENGTH_LONG).show();
        } else {
            view.setBackgroundResource(R.color.FreeTime);
            this.statewed8 = false;
            update.update("16:00 น - 16:15 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("16:15 น - 16:30 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("16:30 น - 16:45 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("16:45 น - 17:00 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            Toast.makeText(TeacherUpdateTime_Page.this, "อัพเดทเวลาเสร็จสิ้น", Toast.LENGTH_LONG).show();
        }
    }

    public void clickwed9(View view) {
        DocumentReference update = updatetime.document(nameTeacherCurrent + "_พุธ");
        if (statewed9 == null || statewed9 == false) {
            view.setBackgroundResource(R.color.NoFreeTime);
            this.statewed9 = true;
            update.update("17:00 น - 17:15 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("17:15 น - 17:30 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("17:30 น - 17:45 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("17:45 น - 18:00 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            Toast.makeText(TeacherUpdateTime_Page.this, "อัพเดทเวลาเสร็จสิ้น", Toast.LENGTH_LONG).show();
        } else {
            view.setBackgroundResource(R.color.FreeTime);
            this.statewed9 = false;
            update.update("17:00 น - 17:15 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("17:15 น - 17:30 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("17:30 น - 17:45 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("17:45 น - 18:00 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            Toast.makeText(TeacherUpdateTime_Page.this, "อัพเดทเวลาเสร็จสิ้น", Toast.LENGTH_LONG).show();
        }
    }

    public void clickthu1(View view) {
        DocumentReference update = updatetime.document(nameTeacherCurrent + "_พฤหัสบดี");
        if (statethu1 == null || statethu1 == false) {
            view.setBackgroundResource(R.color.NoFreeTime);
            this.statethu1 = true;
            update.update("9:00 น - 9:15 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("9:15 น - 9:30 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("9:30 น - 9:45 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("9:45 น - 10:00 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            Toast.makeText(TeacherUpdateTime_Page.this, "อัพเดทเวลาเสร็จสิ้น", Toast.LENGTH_LONG).show();
        } else {
            view.setBackgroundResource(R.color.FreeTime);
            this.statethu1 = false;
            update.update("9:00 น - 9:15 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("9:15 น - 9:30 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("9:30 น - 9:45 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("9:45 น - 10:00 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            Toast.makeText(TeacherUpdateTime_Page.this, "อัพเดทเวลาเสร็จสิ้น", Toast.LENGTH_LONG).show();
        }
    }

    public void clickthu2(View view) {
        DocumentReference update = updatetime.document(nameTeacherCurrent + "_พฤหัสบดี");
        if (statethu2 == null || statethu2 == false) {
            view.setBackgroundResource(R.color.NoFreeTime);
            this.statethu2 = true;
            update.update("10:00 น - 10:15 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("10:15 น - 10:30 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("10:30 น - 10:45 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("10:45 น - 11:00 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            Toast.makeText(TeacherUpdateTime_Page.this, "อัพเดทเวลาเสร็จสิ้น", Toast.LENGTH_LONG).show();
        } else {
            view.setBackgroundResource(R.color.FreeTime);
            this.statethu2 = false;
            update.update("10:00 น - 10:15 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("10:15 น - 10:30 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("10:30 น - 10:45 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("10:45 น - 11:00 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            Toast.makeText(TeacherUpdateTime_Page.this, "อัพเดทเวลาเสร็จสิ้น", Toast.LENGTH_LONG).show();
        }
    }

    public void clickthu3(View view) {
        DocumentReference update = updatetime.document(nameTeacherCurrent + "_พฤหัสบดี");
        if (statethu3 == null || statethu3 == false) {
            view.setBackgroundResource(R.color.NoFreeTime);
            this.statethu3 = true;
            update.update("11:00 น - 11:15 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("11:15 น - 11:30 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("11:30 น - 11:45 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("11:45 น - 12:00 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            Toast.makeText(TeacherUpdateTime_Page.this, "อัพเดทเวลาเสร็จสิ้น", Toast.LENGTH_LONG).show();
        } else {
            view.setBackgroundResource(R.color.FreeTime);
            this.statethu3 = false;
            update.update("11:00 น - 11:15 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("11:15 น - 11:30 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("11:30 น - 11:45 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("11:45 น - 12:00 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            Toast.makeText(TeacherUpdateTime_Page.this, "อัพเดทเวลาเสร็จสิ้น", Toast.LENGTH_LONG).show();
        }
    }

    public void clickthu4(View view) {
        DocumentReference update = updatetime.document(nameTeacherCurrent + "_พฤหัสบดี");
        if (statethu4 == null || statethu4 == false) {
            view.setBackgroundResource(R.color.NoFreeTime);
            this.statethu4 = true;
            update.update("12:00 น - 12:15 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("12:15 น - 12:30 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("12:30 น - 12:45 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("12:45 น - 13:00 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            Toast.makeText(TeacherUpdateTime_Page.this, "อัพเดทเวลาเสร็จสิ้น", Toast.LENGTH_LONG).show();
        } else {
            view.setBackgroundResource(R.color.FreeTime);
            this.statethu4 = false;
            update.update("12:00 น - 12:15 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("12:15 น - 12:30 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("12:30 น - 12:45 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("12:45 น - 13:00 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            Toast.makeText(TeacherUpdateTime_Page.this, "อัพเดทเวลาเสร็จสิ้น", Toast.LENGTH_LONG).show();
        }
    }

    public void clickthu5(View view) {
        DocumentReference update = updatetime.document(nameTeacherCurrent + "_พฤหัสบดี");
        if (statethu5 == null || statethu5 == false) {
            view.setBackgroundResource(R.color.NoFreeTime);
            this.statethu5 = true;
            update.update("13:00 น - 13:15 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("13:15 น - 13:30 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("13:30 น - 13:45 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("13:45 น - 14:00 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            Toast.makeText(TeacherUpdateTime_Page.this, "อัพเดทเวลาเสร็จสิ้น", Toast.LENGTH_LONG).show();
        } else {
            view.setBackgroundResource(R.color.FreeTime);
            this.statethu5 = false;
            update.update("13:00 น - 13:15 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("13:15 น - 13:30 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("13:30 น - 13:45 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("13:45 น - 14:00 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            Toast.makeText(TeacherUpdateTime_Page.this, "อัพเดทเวลาเสร็จสิ้น", Toast.LENGTH_LONG).show();
        }
    }

    public void clickthu6(View view) {
        DocumentReference update = updatetime.document(nameTeacherCurrent + "_พฤหัสบดี");
        if (statethu6 == null || statethu6 == false) {
            view.setBackgroundResource(R.color.NoFreeTime);
            this.statethu6 = true;
            update.update("14:00 น - 14:15 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("14:15 น - 14:30 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("14:30 น - 14:45 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("14:45 น - 15:00 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            Toast.makeText(TeacherUpdateTime_Page.this, "อัพเดทเวลาเสร็จสิ้น", Toast.LENGTH_LONG).show();
        } else {
            view.setBackgroundResource(R.color.FreeTime);
            this.statethu6 = false;
            update.update("14:00 น - 14:15 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("14:15 น - 14:30 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("14:30 น - 14:45 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("14:45 น - 15:00 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            Toast.makeText(TeacherUpdateTime_Page.this, "อัพเดทเวลาเสร็จสิ้น", Toast.LENGTH_LONG).show();
        }
    }

    public void clickthu7(View view) {
        DocumentReference update = updatetime.document(nameTeacherCurrent + "_พฤหัสบดี");
        if (statethu7 == null || statethu7 == false) {
            view.setBackgroundResource(R.color.NoFreeTime);
            this.statethu7 = true;
            update.update("15:00 น - 15:15 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("15:15 น - 15:30 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("15:30 น - 15:45 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("15:45 น - 16:00 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            Toast.makeText(TeacherUpdateTime_Page.this, "อัพเดทเวลาเสร็จสิ้น", Toast.LENGTH_LONG).show();
        } else {
            view.setBackgroundResource(R.color.FreeTime);
            this.statethu7 = false;
            update.update("15:00 น - 15:15 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("15:15 น - 15:30 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("15:30 น - 15:45 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("15:45 น - 16:00 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            Toast.makeText(TeacherUpdateTime_Page.this, "อัพเดทเวลาเสร็จสิ้น", Toast.LENGTH_LONG).show();
        }
    }

    public void clickthu8(View view) {
        DocumentReference update = updatetime.document(nameTeacherCurrent + "_พฤหัสบดี");
        if (statethu8 == null || statethu8 == false) {
            view.setBackgroundResource(R.color.NoFreeTime);
            this.statethu8 = true;
            update.update("16:00 น - 16:15 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("16:15 น - 16:30 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("16:30 น - 16:45 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("16:45 น - 17:00 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            Toast.makeText(TeacherUpdateTime_Page.this, "อัพเดทเวลาเสร็จสิ้น", Toast.LENGTH_LONG).show();
        } else {
            view.setBackgroundResource(R.color.FreeTime);
            this.statethu8 = false;
            update.update("16:00 น - 16:15 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("16:15 น - 16:30 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("16:30 น - 16:45 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("16:45 น - 17:00 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            Toast.makeText(TeacherUpdateTime_Page.this, "อัพเดทเวลาเสร็จสิ้น", Toast.LENGTH_LONG).show();
        }
    }

    public void clickthu9(View view) {
        DocumentReference update = updatetime.document(nameTeacherCurrent + "_พฤหัสบดี");
        if (statethu9 == null || statethu9 == false) {
            view.setBackgroundResource(R.color.NoFreeTime);
            this.statethu9 = true;
            update.update("17:00 น - 17:15 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("17:15 น - 17:30 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("17:30 น - 17:45 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("17:45 น - 18:00 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            Toast.makeText(TeacherUpdateTime_Page.this, "อัพเดทเวลาเสร็จสิ้น", Toast.LENGTH_LONG).show();
        } else {
            view.setBackgroundResource(R.color.FreeTime);
            this.statethu9 = false;
            update.update("17:00 น - 17:15 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("17:15 น - 17:30 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("17:30 น - 17:45 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("17:45 น - 18:00 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            Toast.makeText(TeacherUpdateTime_Page.this, "อัพเดทเวลาเสร็จสิ้น", Toast.LENGTH_LONG).show();
        }
    }

    public void clickfri1(View view) {
        DocumentReference update = updatetime.document(nameTeacherCurrent + "_ศุกร์");
        if (statefri1 == null || statefri1 == false) {
            view.setBackgroundResource(R.color.NoFreeTime);
            this.statefri1 = true;
            update.update("9:00 น - 9:15 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("9:15 น - 9:30 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("9:30 น - 9:45 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("9:45 น - 10:00 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            Toast.makeText(TeacherUpdateTime_Page.this, "อัพเดทเวลาเสร็จสิ้น", Toast.LENGTH_LONG).show();
        } else {
            view.setBackgroundResource(R.color.FreeTime);
            this.statefri1 = false;
            update.update("9:00 น - 9:15 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("9:15 น - 9:30 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("9:30 น - 9:45 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("9:45 น - 10:00 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            Toast.makeText(TeacherUpdateTime_Page.this, "อัพเดทเวลาเสร็จสิ้น", Toast.LENGTH_LONG).show();
        }
    }

    public void clickfri2(View view) {
        DocumentReference update = updatetime.document(nameTeacherCurrent + "_ศุกร์");
        if (statefri2 == null || statefri2 == false) {
            view.setBackgroundResource(R.color.NoFreeTime);
            this.statefri2 = true;
            update.update("10:00 น - 10:15 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("10:15 น - 10:30 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("10:30 น - 10:45 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("10:45 น - 11:00 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            Toast.makeText(TeacherUpdateTime_Page.this, "อัพเดทเวลาเสร็จสิ้น", Toast.LENGTH_LONG).show();
        } else {
            view.setBackgroundResource(R.color.FreeTime);
            this.statefri2 = false;
            update.update("10:00 น - 10:15 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("10:15 น - 10:30 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("10:30 น - 10:45 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("10:45 น - 11:00 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            Toast.makeText(TeacherUpdateTime_Page.this, "อัพเดทเวลาเสร็จสิ้น", Toast.LENGTH_LONG).show();
        }
    }

    public void clickfri3(View view) {
        DocumentReference update = updatetime.document(nameTeacherCurrent + "_ศุกร์");
        if (statefri3 == null || statefri3 == false) {
            view.setBackgroundResource(R.color.NoFreeTime);
            this.statefri3 = true;
            update.update("11:00 น - 11:15 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("11:15 น - 11:30 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("11:30 น - 11:45 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("11:45 น - 12:00 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            Toast.makeText(TeacherUpdateTime_Page.this, "อัพเดทเวลาเสร็จสิ้น", Toast.LENGTH_LONG).show();
        } else {
            view.setBackgroundResource(R.color.FreeTime);
            this.statefri3 = false;
            update.update("11:00 น - 11:15 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("11:15 น - 11:30 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("11:30 น - 11:45 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("11:45 น - 12:00 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            Toast.makeText(TeacherUpdateTime_Page.this, "อัพเดทเวลาเสร็จสิ้น", Toast.LENGTH_LONG).show();
        }
    }

    public void clickfri4(View view) {
        DocumentReference update = updatetime.document(nameTeacherCurrent + "_ศุกร์");
        if (statefri4 == null || statefri4 == false) {
            view.setBackgroundResource(R.color.NoFreeTime);
            this.statefri4 = true;
            update.update("12:00 น - 12:15 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("12:15 น - 12:30 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("12:30 น - 12:45 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("12:45 น - 13:00 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            Toast.makeText(TeacherUpdateTime_Page.this, "อัพเดทเวลาเสร็จสิ้น", Toast.LENGTH_LONG).show();
        } else {
            view.setBackgroundResource(R.color.FreeTime);
            this.statefri4 = false;
            update.update("12:00 น - 12:15 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("12:15 น - 12:30 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("12:30 น - 12:45 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("12:45 น - 13:00 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            Toast.makeText(TeacherUpdateTime_Page.this, "อัพเดทเวลาเสร็จสิ้น", Toast.LENGTH_LONG).show();
        }
    }

    public void clickfri5(View view) {
        DocumentReference update = updatetime.document(nameTeacherCurrent + "_ศุกร์");
        if (statefri5 == null || statefri5 == false) {
            view.setBackgroundResource(R.color.NoFreeTime);
            this.statefri5 = true;
            update.update("13:00 น - 13:15 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("13:15 น - 13:30 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("13:30 น - 13:45 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("13:45 น - 14:00 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            Toast.makeText(TeacherUpdateTime_Page.this, "อัพเดทเวลาเสร็จสิ้น", Toast.LENGTH_LONG).show();
        } else {
            view.setBackgroundResource(R.color.FreeTime);
            this.statefri5 = false;
            update.update("13:00 น - 13:15 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("13:15 น - 13:30 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("13:30 น - 13:45 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("13:45 น - 14:00 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            Toast.makeText(TeacherUpdateTime_Page.this, "อัพเดทเวลาเสร็จสิ้น", Toast.LENGTH_LONG).show();
        }
    }

    public void clickfri6(View view) {
        DocumentReference update = updatetime.document(nameTeacherCurrent + "_ศุกร์");
        if (statefri6 == null || statefri6 == false) {
            view.setBackgroundResource(R.color.NoFreeTime);
            this.statefri6 = true;
            update.update("14:00 น - 14:15 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("14:15 น - 14:30 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("14:30 น - 14:45 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("14:45 น - 15:00 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            Toast.makeText(TeacherUpdateTime_Page.this, "อัพเดทเวลาเสร็จสิ้น", Toast.LENGTH_LONG).show();
        } else {
            view.setBackgroundResource(R.color.FreeTime);
            this.statefri6 = false;
            update.update("14:00 น - 14:15 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("14:15 น - 14:30 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("14:30 น - 14:45 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("14:45 น - 15:00 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            Toast.makeText(TeacherUpdateTime_Page.this, "อัพเดทเวลาเสร็จสิ้น", Toast.LENGTH_LONG).show();
        }
    }

    public void clickfri7(View view) {
        DocumentReference update = updatetime.document(nameTeacherCurrent + "_ศุกร์");
        if (statefri7 == null || statefri7 == false) {
            view.setBackgroundResource(R.color.NoFreeTime);
            this.statefri7 = true;
            update.update("15:00 น - 15:15 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("15:15 น - 15:30 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("15:30 น - 15:45 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("15:45 น - 16:00 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            Toast.makeText(TeacherUpdateTime_Page.this, "อัพเดทเวลาเสร็จสิ้น", Toast.LENGTH_LONG).show();
        } else {
            view.setBackgroundResource(R.color.FreeTime);
            this.statefri7 = false;
            update.update("15:00 น - 15:15 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("15:15 น - 15:30 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("15:30 น - 15:45 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("15:45 น - 16:00 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            Toast.makeText(TeacherUpdateTime_Page.this, "อัพเดทเวลาเสร็จสิ้น", Toast.LENGTH_LONG).show();
        }
    }

    public void clickfri8(View view) {
        DocumentReference update = updatetime.document(nameTeacherCurrent + "_ศุกร์");
        if (statefri8 == null || statefri8 == false) {
            view.setBackgroundResource(R.color.NoFreeTime);
            this.statefri8 = true;
            update.update("16:00 น - 16:15 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("16:15 น - 16:30 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("16:30 น - 16:45 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("16:45 น - 17:00 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            Toast.makeText(TeacherUpdateTime_Page.this, "อัพเดทเวลาเสร็จสิ้น", Toast.LENGTH_LONG).show();
        } else {
            view.setBackgroundResource(R.color.FreeTime);
            this.statefri8 = false;
            update.update("16:00 น - 16:15 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("16:15 น - 16:30 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("16:30 น - 16:45 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("16:45 น - 17:00 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            Toast.makeText(TeacherUpdateTime_Page.this, "อัพเดทเวลาเสร็จสิ้น", Toast.LENGTH_LONG).show();
        }
    }

    public void clickfri9(View view) {
        DocumentReference update = updatetime.document(nameTeacherCurrent + "_ศุกร์");
        if (statefri9 == null || statefri9 == false) {
            view.setBackgroundResource(R.color.NoFreeTime);
            this.statefri9 = true;
            update.update("17:00 น - 17:15 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("17:15 น - 17:30 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("17:30 น - 17:45 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("17:45 น - 18:00 น", true).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            Toast.makeText(TeacherUpdateTime_Page.this, "อัพเดทเวลาเสร็จสิ้น", Toast.LENGTH_LONG).show();
        } else {
            view.setBackgroundResource(R.color.FreeTime);
            this.statefri9 = false;
            update.update("17:00 น - 17:15 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("17:15 น - 17:30 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("17:30 น - 17:45 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            update.update("17:45 น - 18:00 น", false).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.v("Test", "Update Time Successfully");
                }
            });
            Toast.makeText(TeacherUpdateTime_Page.this, "อัพเดทเวลาเสร็จสิ้น", Toast.LENGTH_LONG).show();
        }
    }
}
