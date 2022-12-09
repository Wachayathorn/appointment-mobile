package com.example.appointment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import dmax.dialog.SpotsDialog;

class User {

    public String Name;
    public String Name_prefix;
    public String uID;

    public User() {

    }

    public User(String Name, String Name_prefix, String uID) {
        this.Name = Name;
        this.Name_prefix = Name_prefix;
        this.uID = uID;
    }

}

public class TeacherFragment extends Fragment {

    private ArrayList<String> mylist;
    private ArrayList<String> allTeacherID;

    private String userID;
    private FirebaseDatabase db;
    private FirebaseAuth FireAuth;
    private DatabaseReference myRef;
    private DocumentReference myFirestore;
    private CollectionReference UpdateTime;
    private String NAME;
    private String ID;
    private String SECTION;
    private String ADVISORID;
    private String ADVISORNAME;

    private String textTeacherID;
    private String textNameTeacher;
    private String textTopicTeacher;
    private String textTimeTeacher;
    private String textWanTeacher;
    private String textDateTeacher;
    private String textDetailTeacher;

    public TeacherFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_teacher, container, false);

        allTeacherID = new ArrayList<String>();
        // จำเป็นต้องใส่ add ไว้ก่อน
        mylist = new ArrayList<String>();
        mylist.add("");

        db = FirebaseDatabase.getInstance();
        DatabaseReference refUser = db.getReference("Users-Teacher");

        refUser.orderByChild("Role").equalTo("อาจารย์").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                User teacherName = dataSnapshot.getValue(User.class);
                mylist.add(teacherName.Name_prefix + teacherName.Name);
                allTeacherID.add(teacherName.uID);
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

        final Spinner spteacherlay = (Spinner) rootView.findViewById(R.id.spChooseTeacher);
        ArrayAdapter<String> adapterteacher = new ArrayAdapter<String>(getActivity(), R.layout.spinnertext, mylist);
        spteacherlay.setAdapter(adapterteacher);

        final Spinner sptopiclay = (Spinner) rootView.findViewById(R.id.spChooseTopic);
        String[] chosetopic = getResources().getStringArray(R.array.spTopic);
        ArrayAdapter<String> adaptertopic = new ArrayAdapter<String>(getActivity(), R.layout.spinnertext, chosetopic);
        sptopiclay.setAdapter(adaptertopic);


        final Spinner sptimelay = (Spinner) rootView.findViewById(R.id.spChooseTime);
        String[] chosetime = getResources().getStringArray(R.array.spTime);
        ArrayAdapter<String> adaptertime = new ArrayAdapter<String>(getActivity(), R.layout.spinnertext, chosetime);
        sptimelay.setAdapter(adaptertime);

        final Spinner spWanlay = (Spinner) rootView.findViewById(R.id.spChooseWan);
        String[] chooseWan = getResources().getStringArray(R.array.spWan);
        ArrayAdapter<String> adapterwan = new ArrayAdapter<String>(getActivity(), R.layout.spinnertext, chooseWan);
        spWanlay.setAdapter(adapterwan);

        Button btAppTeacher = (Button) rootView.findViewById(R.id.btSubmitAppTeacher);
        final EditText edDetailTeacher = (EditText) rootView.findViewById(R.id.tbDetailAppTeacher);
        final TextView textDate = (TextView) rootView.findViewById(R.id.dateTeacherApp);

        spWanlay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                SimpleDateFormat form = new SimpleDateFormat("dd/MM/yyyy");
                Calendar now = Calendar.getInstance();

                Integer dayOfWeek = now.get(Calendar.DAY_OF_WEEK);

                String[] days = new String[7];
                int delta = -now.get(GregorianCalendar.DAY_OF_WEEK) + 1;
                now.add(Calendar.DAY_OF_MONTH, delta);
                for (int i = 0; i < 7; i++) {
                    days[i] = form.format(now.getTime());
                    now.add(Calendar.DAY_OF_MONTH, 1);
                }

                switch (spWanlay.getSelectedItem().toString()) {
                    case "จันทร์":
                        Integer mon = 2;
                        if (mon >= dayOfWeek) {
                            textDate.setText(days[1]);
                        } else {
                            Toast.makeText(getActivity(), "ไม่สามารถนัดหมายวันที่ผ่านมาแล้วได้", Toast.LENGTH_LONG).show();
                            textDate.setText("วันที่นัดหมาย");
                        }
                        break;

                    case "อังคาร":
                        Integer tue = 3;
                        if (tue >= dayOfWeek) {
                            textDate.setText(days[2]);
                        } else {
                            Toast.makeText(getActivity(), "ไม่สามารถนัดหมายวันที่ผ่านมาแล้วได้", Toast.LENGTH_LONG).show();
                            textDate.setText("วันที่นัดหมาย");
                        }
                        break;

                    case "พุธ":
                        Integer wed = 4;
                        if (wed >= dayOfWeek) {
                            textDate.setText(days[3]);
                        } else {
                            Toast.makeText(getActivity(), "ไม่สามารถนัดหมายวันที่ผ่านมาแล้วได้", Toast.LENGTH_LONG).show();
                            textDate.setText("วันที่นัดหมาย");
                        }
                        break;

                    case "พฤหัสบดี":
                        Integer thu = 5;
                        if (thu >= dayOfWeek) {
                            textDate.setText(days[4]);
                        } else {
                            Toast.makeText(getActivity(), "ไม่สามารถนัดหมายวันที่ผ่านมาแล้วได้", Toast.LENGTH_LONG).show();
                            textDate.setText("วันที่นัดหมาย");
                        }
                        break;

                    case "ศุกร์":
                        Integer fri = 6;
                        if (fri >= dayOfWeek) {
                            textDate.setText(days[5]);
                        } else {
                            Toast.makeText(getActivity(), "ไม่สามารถนัดหมายวันที่ผ่านมาแล้วได้", Toast.LENGTH_LONG).show();
                            textDate.setText("วันที่นัดหมาย");
                        }
                        break;

                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        FireAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        myRef = db.getReference("Users-Student");
        FirebaseUser userCur = FireAuth.getCurrentUser();
        userID = userCur.getUid();
        String userEmail = userCur.getEmail();
        String findID = userEmail.substring(0, 14);

        final FirebaseFirestore dbFirestore = FirebaseFirestore.getInstance();
        myFirestore = dbFirestore.collection("Appointment").document();
        UpdateTime = dbFirestore.collection("Timetable");

        if (userID != null) {
            myRef.child(findID).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    ID = dataSnapshot.child("Student_ID").getValue().toString();
                    NAME = dataSnapshot.child("Name").getValue().toString();
                    ADVISORID = dataSnapshot.child("AdvisorID").getValue().toString();

                    final CollectionReference myFirestore = dbFirestore.collection("Section");

                    myFirestore.document(dataSnapshot.child("Section_ID").getValue().toString()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    SECTION = document.getData().get("section").toString();
                                }
                            }
                        }
                    });

                    db.getReference("Users-Teacher").child(ADVISORID).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            ADVISORNAME = dataSnapshot.child("Name_prefix").getValue().toString() + dataSnapshot.child("Name").getValue().toString();
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
        }

        btAppTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spteacherlay.getSelectedItem().toString().trim().equals("")) {
                    Toast.makeText(getActivity(), "กรุณากรอกข้อมูลให้ครบถ้วน", Toast.LENGTH_LONG).show();
                } else if (sptopiclay.getSelectedItem().toString().trim().equals("")) {
                    Toast.makeText(getActivity(), "กรุณากรอกข้อมูลให้ครบถ้วน", Toast.LENGTH_LONG).show();
                } else if (sptimelay.getSelectedItem().toString().trim().equals("")) {
                    Toast.makeText(getActivity(), "กรุณากรอกข้อมูลให้ครบถ้วน", Toast.LENGTH_LONG).show();
                } else if (spWanlay.getSelectedItem().toString().trim().equals("")) {
                    Toast.makeText(getActivity(), "กรุณากรอกข้อมูลให้ครบถ้วน", Toast.LENGTH_LONG).show();
                } else if (textDate.getText().toString().equals("") || textDate.getText().toString().equals("วันที่นัดหมาย")) {
                    Toast.makeText(getActivity(), "กรุณากรอกข้อมูลให้ครบถ้วน", Toast.LENGTH_LONG).show();
                } else {
                    final SpotsDialog spotDialog = (SpotsDialog) new SpotsDialog.Builder().setContext(getActivity()).setMessage("กำลังนัดหมายอาจารย์").setTheme(R.style.CustomSpotDialog).setCancelable(false).build();
                    spotDialog.show();

                    Integer positionSP = spteacherlay.getSelectedItemPosition();
                    textNameTeacher = spteacherlay.getSelectedItem().toString();
                    textTeacherID = allTeacherID.get(positionSP - 1);
                    textTopicTeacher = sptopiclay.getSelectedItem().toString();
                    textTimeTeacher = sptimelay.getSelectedItem().toString();
                    textWanTeacher = spWanlay.getSelectedItem().toString();
                    textDateTeacher = textDate.getText().toString();
                    textDetailTeacher = edDetailTeacher.getText().toString();

                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    Date date = new Date();
                    String dateNow = formatter.format(date);
                    Boolean checktime = true;
                    if (textDate.getText().toString().equals(dateNow)) {
                        checktime = checkTime(sptimelay.getSelectedItem().toString());
                    }

                    final Long dateTimeMillis = convertDateToMillis(textDate.getText().toString(), sptimelay.getSelectedItem().toString());

                    if (checktime == true) {

                        DocumentReference checkTimeTeacher = UpdateTime.document(textTeacherID + "_" + textWanTeacher);

                        checkTimeTeacher.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if (documentSnapshot.exists()) {
                                    Boolean chkteacher = (Boolean) documentSnapshot.getBoolean(textTimeTeacher);

                                    if (chkteacher == null || chkteacher == false) {
                                        AppointmentTeacherModel teacherModel = new AppointmentTeacherModel();
                                        teacherModel.setStudent_ID(ID);
                                        teacherModel.setName(NAME);
                                        teacherModel.setSection(SECTION);
                                        teacherModel.setAdvisorID(ADVISORID);
                                        teacherModel.setAdvisorName(ADVISORNAME);
                                        teacherModel.setDate(textDateTeacher);
                                        teacherModel.setTime(textTimeTeacher);
                                        teacherModel.setDetail(textDetailTeacher);
                                        teacherModel.setTopic(textTopicTeacher);
                                        teacherModel.setTeacher(textNameTeacher);
                                        teacherModel.setTeacherID(textTeacherID);
                                        teacherModel.setStatus("รอการตอบรับ");
                                        teacherModel.setDay(textWanTeacher);
                                        teacherModel.setNote_ID(myFirestore.getId());
                                        teacherModel.setTimestamp(System.currentTimeMillis());
                                        teacherModel.setDateMillis(dateTimeMillis);

                                        myFirestore.set(teacherModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Log.v("Test", "Successfully");
                                            }
                                        });

                                        DocumentReference update = UpdateTime.document(textTeacherID + "_" + textWanTeacher);
                                        update.update(textTimeTeacher, true).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.v("Test", "Update Time Successfully");
                                            }
                                        });

                                        Toast.makeText(getActivity(), "นัดหมายเสร็จสิ้น", Toast.LENGTH_LONG).show();
                                        Intent finish = new Intent(getActivity(), HomeUser_Page.class);
                                        startActivity(finish);
                                    } else {
                                        spotDialog.dismiss();
                                        Toast.makeText(getActivity(), "วันเวลาในสัปดาห์นี้ไม่สามารถนัดหมายได้", Toast.LENGTH_LONG).show();
                                    }
                                }

                            }
                        });
                    } else {
                        spotDialog.dismiss();
                        Toast.makeText(getActivity(), "ไม่สามารถนัดหมายในช่วงเวลาที่เลยมาแล้วได้", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        return rootView;
    }

    public Boolean checkTime(String time) {
        Boolean value = null;
        String timeApp = time.substring(0, 5);
        if (time.substring(0, 1).equals("0")) {
            timeApp = "0" + time.substring(0, 4);
        }

        SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        String now = parser.format(date);
        try {
            Date timeAppParser = parser.parse(timeApp);
            Date timeNow = parser.parse(now);

            if (timeNow.before(timeAppParser)) {
                value = true;
            } else {
                value = false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return value;
    }

    public Long convertDateToMillis(String date, String time) {
        long millis = 1;
        String timeApp = time.substring(0, 5);
        if (time.substring(0, 1).equals("0")) {
            timeApp = "0" + time.substring(0, 4);
        }
        String fmd = date + " " + timeApp.trim() + ":00";

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        try {
            Date _date = sdf.parse(fmd);
            millis = _date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return millis;
    }
}
