package com.example.appointment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class RetrieveStatusUserAdapter extends ArrayAdapter<RetrieveStatusUser> {

    Activity context;
    List<RetrieveStatusUser> ReStatus;

    public RetrieveStatusUserAdapter(Activity context, List<RetrieveStatusUser> reStatus) {
        super(context, R.layout.listview_status_user, reStatus);
        this.context = context;
        this.ReStatus = reStatus;
    }

    @SuppressLint("ResourceAsColor")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater infrater = context.getLayoutInflater();
        View listview = infrater.inflate(R.layout.listview_status_user, null, true);

        TextView topic = (TextView) listview.findViewById(R.id.textTopic);
        TextView teacher = (TextView) listview.findViewById(R.id.textteacher);
        TextView Day = (TextView) listview.findViewById(R.id.textDay);
        TextView Date = (TextView) listview.findViewById(R.id.textDate);
        TextView status = (TextView) listview.findViewById(R.id.textStatus);
        TextView Time = (TextView) listview.findViewById(R.id.textTime);
        ImageView imageStatus = (ImageView) listview.findViewById(R.id.ImageStatus);
        ImageView imageTeacher = (ImageView) listview.findViewById(R.id.ImagePic);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference UpdateStatus = db.collection("Appointment");

        RetrieveStatusUser Restatus = ReStatus.get(position);

        topic.setText(Restatus.getTopic());
        teacher.setText(Restatus.getTeacher());
        Day.setText(Restatus.getDay());
        Date.setText(Restatus.getDate());
        Time.setText(Restatus.getTime());

        String checkImage = Restatus.getStatus();
        String checkImTeacher = teacher.getText().toString();

        if (checkImage.equals("รอการตอบรับ")) {
            status.setText(Restatus.getStatus());
            imageStatus.setImageResource(R.drawable.wait1);
        } else if (checkImage.equals("อนุมัติ")) {
            status.setText(Restatus.getStatus());
            imageStatus.setImageResource(R.drawable.accept);
            status.setTextColor(Color.parseColor("#32CD32"));
        } else if (checkImage.equals("ปฏิเสธ")) {
            DocumentReference updateSta = UpdateStatus.document(Restatus.getNote_ID());
            updateSta.update("status", "ปฏิเสธ(เสร็จสิ้น)");
            status.setText(Restatus.getStatus() + " / " + Restatus.getDeclineReason());
            imageStatus.setImageResource(R.drawable.decline);
            status.setTextColor(Color.parseColor("#FF0000"));
        } else if (checkImage.equals("กำลังดำเนินเอกสาร")) {
            status.setText(Restatus.getStatus() + " / " + Restatus.getBarcode());
            imageStatus.setImageResource(R.drawable.improcess);
            status.setTextColor(Color.parseColor("#FF9933"));
        } else if (checkImage.equals("ดำเนินเอกสารเสร็จสิ้น")) {
            DocumentReference updateSta = UpdateStatus.document(Restatus.getNote_ID());
            updateSta.update("barcode", "เสร็จสิ้น");
            status.setText(Restatus.getStatus());
            imageStatus.setImageResource(R.drawable.doc_complete);
            status.setTextColor(Color.parseColor("#FF9900"));
        } else if (checkImage.equals("กำลังยืมอุปกรณ์")) {
            status.setText("กำลังยืม / วันที่คืน : " + Restatus.getDateBack());
            imageStatus.setImageResource(R.drawable.borrow);
            status.setTextColor(Color.parseColor("#FF9933"));
        } else {
            imageStatus.setImageResource(R.drawable.checked);
        }

        if (checkImTeacher.equals("ฝ่ายธุรการ")) {
            imageTeacher.setImageResource(R.drawable.immanage);
        } else if (checkImTeacher.equals("ฝ่ายบริการ")) {
            imageTeacher.setImageResource(R.drawable.imservices);
        } else {
            imageTeacher.setImageResource(R.drawable.imteacher);
        }

        return listview;
    }
}


