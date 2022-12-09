package com.example.appointment;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import dmax.dialog.SpotsDialog;

public class ServicesFragment extends Fragment {

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

    private String textTopicService;
    private String textTimeService;
    private String textDateBor;
    private String textDayBack;
    private String textWanService;
    private String textTeacherService;
    private String textPurpose;
    private DatePickerDialog.OnDateSetListener dpDateBack;

    public ServicesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_services, container, false);

        // Datepicker
        final TextView tbTextDateBack = (TextView) rootView.findViewById(R.id.dateServiceBack);
        tbTextDateBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(getActivity(),
                        android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth,
                        dpDateBack, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        dpDateBack = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                Integer Intmonth = month + 1;
                String Month = Intmonth.toString();
                Integer Intday = day;
                String Day = Intday.toString();
                if (month < 9) {
                    Month = "0" + Intmonth;
                }
                if (day < 10) {
                    Day = "0" + day;
                }
                String dateBack = Day + "/" + Month + "/" + year;
                tbTextDateBack.setText(dateBack);
            }
        };

        final EditText tbPurPoser = (EditText) rootView.findViewById(R.id.tbPurposes);
        final EditText tbTool1 = (EditText) rootView.findViewById(R.id.tbTool1);
        final EditText tbToolNumber1 = (EditText) rootView.findViewById(R.id.tbNumTool1);
        final EditText tbTool2 = (EditText) rootView.findViewById(R.id.tbTool2);
        final EditText tbToolNumber2 = (EditText) rootView.findViewById(R.id.tbNumTool2);
        final EditText tbTool3 = (EditText) rootView.findViewById(R.id.tbTool3);
        final EditText tbToolNumber3 = (EditText) rootView.findViewById(R.id.tbNumTool3);
        final EditText tbTool4 = (EditText) rootView.findViewById(R.id.tbTool4);
        final EditText tbToolNumber4 = (EditText) rootView.findViewById(R.id.tbNumTool4);
        final EditText tbTool5 = (EditText) rootView.findViewById(R.id.tbTool5);
        final EditText tbToolNumber5 = (EditText) rootView.findViewById(R.id.tbNumTool5);

        final Spinner sptimeservice = (Spinner) rootView.findViewById(R.id.spTimeServices);
        String[] chosetimeservice = getResources().getStringArray(R.array.spTime);
        ArrayAdapter<String> adaptertimeservice = new ArrayAdapter<String>(getActivity(), R.layout.spinnertext, chosetimeservice);
        sptimeservice.setAdapter(adaptertimeservice);

        final Spinner spWanlayservice = (Spinner) rootView.findViewById(R.id.spChooseWanService);
        String[] chooseWanService = getResources().getStringArray(R.array.spWan);
        ArrayAdapter<String> adapterwanservice = new ArrayAdapter<String>(getActivity(), R.layout.spinnertext, chooseWanService);
        spWanlayservice.setAdapter(adapterwanservice);

        Button btAppService = (Button) rootView.findViewById(R.id.btSubmitAppService);
        final TextView textDate = (TextView) rootView.findViewById(R.id.dateServiceApp);

        spWanlayservice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

                switch (spWanlayservice.getSelectedItem().toString()) {
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

        //Tool List
        final ArrayList<String> textTool = new ArrayList<String>();
        final ArrayList<String> textToolNumber = new ArrayList<String>();

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

        btAppService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sptimeservice.getSelectedItem().toString().trim().equals("")) {
                    Toast.makeText(getActivity(), "กรุณากรอกข้อมูลให้ครบถ้วน", Toast.LENGTH_LONG).show();
                } else if (spWanlayservice.getSelectedItem().toString().trim().equals("")) {
                    Toast.makeText(getActivity(), "กรุณากรอกข้อมูลให้ครบถ้วน", Toast.LENGTH_LONG).show();
                } else if (textDate.getText().toString().equals("") || textDate.getText().toString().equals("วันที่นัดหมาย")) {
                    Toast.makeText(getActivity(), "กรุณากรอกข้อมูลให้ครบถ้วน", Toast.LENGTH_LONG).show();
                } else if (tbTextDateBack.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "กรุณาระบุวันที่คืน", Toast.LENGTH_LONG).show();
                } else if (tbPurPoser.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "กรุณาระบุกิจกรรมที่นำสิ่งของไปใช้", Toast.LENGTH_LONG).show();
                } else if (tbTool1.getText().toString().equals("") || tbToolNumber1.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "กรุณาระบุอุปกรณ์และจำนวนที่ต้องการยืม", Toast.LENGTH_LONG).show();
                } else {
                    final SpotsDialog spotDialog = (SpotsDialog) new SpotsDialog.Builder().setContext(getActivity()).setMessage("กำลังนัดหมายฝ่ายบริการ").setTheme(R.style.CustomSpotDialog).setCancelable(false).build();
                    spotDialog.show();

                    textTopicService = "การขอยืมอุปกรณ์";
                    textTimeService = sptimeservice.getSelectedItem().toString();
                    textDayBack = tbTextDateBack.getText().toString();
                    textWanService = spWanlayservice.getSelectedItem().toString();
                    textDateBor = textDate.getText().toString();
                    textTeacherService = "ฝ่ายบริการ";
                    textPurpose = tbPurPoser.getText().toString();

                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    Date date = new Date();
                    String dateNow = formatter.format(date);
                    Boolean _checktime = true;

                    if (textDate.getText().toString().equals(dateNow)) {
                        _checktime = checkTime(sptimeservice.getSelectedItem().toString());
                    }

                    final Long dateTimeMillis = convertDateToMillis(textDate.getText().toString(), sptimeservice.getSelectedItem().toString());

                    if (_checktime == true) {

                        DocumentReference checkTimeTeacher = UpdateTime.document("ฝ่ายบริการ_" + textWanService);

                        checkTimeTeacher.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if (documentSnapshot.exists()) {
                                    Boolean chkteacher = (Boolean) documentSnapshot.getBoolean(textTimeService);

                                    if (chkteacher == null || chkteacher == false) {

                                        textTool.add(tbTool1.getText().toString());
                                        textToolNumber.add(tbToolNumber1.getText().toString());
                                        textTool.add(tbTool2.getText().toString());
                                        textToolNumber.add(tbToolNumber2.getText().toString());
                                        textTool.add(tbTool3.getText().toString());
                                        textToolNumber.add(tbToolNumber3.getText().toString());
                                        textTool.add(tbTool4.getText().toString());
                                        textToolNumber.add(tbToolNumber4.getText().toString());
                                        textTool.add(tbTool5.getText().toString());
                                        textToolNumber.add(tbToolNumber5.getText().toString());

                                        AppointmentServiceModel serviceModel = new AppointmentServiceModel();
                                        serviceModel.setTopic(textTopicService);
                                        serviceModel.setTool(textTool);
                                        serviceModel.setToolNumber(textToolNumber);
                                        serviceModel.setTime(textTimeService);
                                        serviceModel.setDate(textDateBor);
                                        serviceModel.setDateBack(textDayBack);
                                        serviceModel.setStatus("รอการตอบรับ");
                                        serviceModel.setDay(textWanService);
                                        serviceModel.setTeacher(textTeacherService);
                                        serviceModel.setTeacherID("30");
                                        serviceModel.setName(NAME);
                                        serviceModel.setStudent_ID(ID);
                                        serviceModel.setSection(SECTION);
                                        serviceModel.setAdvisorID(ADVISORID);
                                        serviceModel.setAdvisorName(ADVISORNAME);
                                        serviceModel.setNote_ID(myFirestore.getId());
                                        serviceModel.setTimestamp(System.currentTimeMillis());
                                        serviceModel.setPurpose(textPurpose);
                                        serviceModel.setDateMillis(dateTimeMillis);

                                        myFirestore.set(serviceModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                            }
                                        });

                                        DocumentReference update = UpdateTime.document("ฝ่ายบริการ_" + textWanService);
                                        update.update(textTimeService, true).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {

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
