package com.example.appointment;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class RetrieveAcceptTeacherAdapter extends ArrayAdapter<RetrieveStatusUser> {

    private Activity context;
    private List<RetrieveStatusUser> ReStatus;

    public RetrieveAcceptTeacherAdapter(Activity context, List<RetrieveStatusUser> reStatus) {
        super(context, R.layout.listview_accept_teacher, reStatus);
        this.context = context;
        this.ReStatus = reStatus;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater infrater = context.getLayoutInflater();
        View listview = infrater.inflate(R.layout.listview_accept_teacher, null, true);

        TextView Topic = (TextView) listview.findViewById(R.id.textTopicTeacherStatus);
        TextView Name = (TextView) listview.findViewById(R.id.textNameStudentTeacherStatus);
        TextView ID = (TextView) listview.findViewById(R.id.textIdTeacherStatus);
        TextView Section = (TextView) listview.findViewById(R.id.textSectionTeacherStatus);
        TextView Advisor = (TextView) listview.findViewById(R.id.textAdvisorTeacherStatus);
        TextView Day = (TextView) listview.findViewById(R.id.textWanTeacherStatus);
        TextView Time = (TextView) listview.findViewById(R.id.textTimeTeacherStatus);
        TextView Detail = (TextView) listview.findViewById(R.id.textDetailTeacherStatus);
        TextView Date = (TextView) listview.findViewById(R.id.textDateTeacherStatus);

        final RetrieveStatusUser Restatus = ReStatus.get(position);

        Topic.setText(Restatus.getTopic());
        Name.setText(Restatus.getName());
        ID.setText(Restatus.getStudent_ID());
        Section.setText(Restatus.getSection());
        Advisor.setText(Restatus.getAdvisorName());
        Day.setText(Restatus.getDay());
        Time.setText(Restatus.getTime());
        Detail.setText(Restatus.getDetail());
        Date.setText(Restatus.getDate());

        return listview;
    }
}
