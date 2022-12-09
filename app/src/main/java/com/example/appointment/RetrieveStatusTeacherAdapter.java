package com.example.appointment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.ResourceBundle;

class RetrieveStatusTeacherAdapter extends ArrayAdapter<RetrieveStatusUser> {

    private Activity context;
    private List<RetrieveStatusUser> ReStatus;

    private CollectionReference UpdateStatus;
    private CollectionReference UpdateTimePath;

    private AlertDialog.Builder mAlert;
    private AlertDialog dialogDecline;

    public RetrieveStatusTeacherAdapter(Activity context, List<RetrieveStatusUser> reStatus) {
        super(context, R.layout.listview_status_teacher, reStatus);
        this.context = context;
        this.ReStatus = reStatus;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final LayoutInflater infrater = context.getLayoutInflater();
        View listview = infrater.inflate(R.layout.listview_status_teacher, null, true);

        final View alertPopup = infrater.inflate(R.layout.dialog_decline_reason, null, true);

        final Spinner sp_reason = (Spinner) alertPopup.findViewById(R.id.sp_reason);
        LinearLayout btn_confirm = (LinearLayout) alertPopup.findViewById(R.id.bt_confirm_decline_reason);
        LinearLayout btn_no_confirm = (LinearLayout) alertPopup.findViewById(R.id.bt_no_confirm_decline_reason);

        String[] listReason = alertPopup.getResources().getStringArray(R.array.spDeclineReason);
        ArrayAdapter<String> adapterReason = new ArrayAdapter<String>(getContext(), R.layout.spinnertext, listReason);
        sp_reason.setAdapter(adapterReason);

        TextView Topic = (TextView) listview.findViewById(R.id.textTopicTeacherStatus);
        TextView Name = (TextView) listview.findViewById(R.id.textNameStudentTeacherStatus);
        TextView ID = (TextView) listview.findViewById(R.id.textIdTeacherStatus);
        TextView Section = (TextView) listview.findViewById(R.id.textSectionTeacherStatus);
        TextView Advisor = (TextView) listview.findViewById(R.id.textAdvisorTeacherStatus);
        TextView Day = (TextView) listview.findViewById(R.id.textWanTeacherStatus);
        TextView Time = (TextView) listview.findViewById(R.id.textTimeTeacherStatus);
        TextView Date = (TextView) listview.findViewById(R.id.textDateTeacherStatus);
        TextView Detail = (TextView) listview.findViewById(R.id.textDetailTeacherStatus);
        final ImageView Accept = (ImageView) listview.findViewById(R.id.ImClickAcceptStatusTeacher);
        final ImageView Decline = (ImageView) listview.findViewById(R.id.ImClickDeclineStatusTeacher);

        final RetrieveStatusUser Restatus = ReStatus.get(position);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        UpdateStatus = db.collection("Appointment");
        UpdateTimePath = db.collection("Timetable");

        Topic.setText(Restatus.getTopic());
        Name.setText(Restatus.getName());
        ID.setText(Restatus.getStudent_ID());
        Section.setText(Restatus.getSection());
        Advisor.setText(Restatus.getAdvisorName());
        Day.setText(Restatus.getDay());
        Time.setText(Restatus.getTime());
        Date.setText(Restatus.getDate());
        Detail.setText(Restatus.getDetail());

        if (Restatus.getStatus().equals("รอการตอบรับ")) {
            Accept.setBackgroundResource(R.color.Default);
            Decline.setBackgroundResource(R.color.Default);
        } else if (Restatus.getStatus().equals("อนุมัติ") || Restatus.getStatus().equals("อนุมัติ(เสร็จสิ้น)")) {
            Accept.setBackgroundResource(R.color.Accept);
            Decline.setBackgroundResource(R.color.Default);
        } else if (Restatus.getStatus().equals("ปฏิเสธ") || Restatus.getStatus().equals("ปฏิเสธ(เสร็จสิ้น)")) {
            Accept.setBackgroundResource(R.color.Default);
            Decline.setBackgroundResource(R.color.Decline);
        }

        Accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Restatus.setStatus("อนุมัติ");
                Decline.setBackgroundResource(R.color.Default);
                v.setBackgroundResource(R.color.Accept);

                DocumentReference updateSta = UpdateStatus.document(Restatus.getNote_ID());
                updateSta.update("status", "อนุมัติ", "declineReason", null);

                String teacherID = Restatus.getTeacherID();
                String day = Restatus.getDay();
                String time = Restatus.getTime();
                DocumentReference updateTime = UpdateTimePath.document(teacherID + "_" + day);
                updateTime.update(time, true);
                Toast.makeText(getContext(), "อนุมัติเรียบร้อยแล้ว", Toast.LENGTH_LONG).show();
            }
        });

        Decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Restatus.getStatus() != "รอการตอบรับ") {
                    DocumentReference updateSta = UpdateStatus.document(Restatus.getNote_ID());
                    updateSta.update("status", "รอการตอบรับ");

                    String teacherID = Restatus.getTeacherID();
                    String day = Restatus.getDay();
                    String time = Restatus.getTime();
                    DocumentReference updateTime = UpdateTimePath.document(teacherID + "_" + day);
                    updateTime.update(time, true);

                    Restatus.setStatus("รอการตอบรับ");
                    Accept.setBackgroundResource(R.color.Default);
                }

                v.setBackgroundResource(R.color.Decline);

                if (alertPopup.getParent() != null) {
                    ((ViewGroup) alertPopup.getParent()).removeView(alertPopup);
                }

                mAlert = new AlertDialog.Builder(getContext());
                mAlert.setView(alertPopup);
                dialogDecline = mAlert.create();
                dialogDecline.show();
            }
        });

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(sp_reason.getSelectedItem().toString())) {
                    Toast.makeText(getContext(), "กรุณาใส่เหตุผลที่ปฏิเสธ", Toast.LENGTH_LONG).show();
                } else {
                    Restatus.setStatus("ปฏิเสธ");
                    DocumentReference updateSta = UpdateStatus.document(Restatus.getNote_ID());
                    updateSta.update("status", "ปฏิเสธ", "declineReason", sp_reason.getSelectedItem().toString());

                    String teacherID = Restatus.getTeacherID();
                    String day = Restatus.getDay();
                    String time = Restatus.getTime();
                    DocumentReference updateTime = UpdateTimePath.document(teacherID + "_" + day);
                    updateTime.update(time, false);
                    Toast.makeText(getContext(), "ปฏิเสธเรียบร้อยแล้ว", Toast.LENGTH_LONG).show();
                    dialogDecline.dismiss();
                }
            }
        });

        btn_no_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Decline.setBackgroundResource(R.color.Default);
                dialogDecline.dismiss();
            }
        });

        return listview;
    }
}
