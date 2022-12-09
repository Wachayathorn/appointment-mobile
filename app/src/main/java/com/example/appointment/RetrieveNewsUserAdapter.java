package com.example.appointment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appointment.R;
import com.example.appointment.RetrieveNewsUser;
import com.example.appointment.RetrieveStatusUser;


import java.util.List;

public class RetrieveNewsUserAdapter extends ArrayAdapter<RetrieveNewsUser> {
    Activity context;
    List<RetrieveNewsUser> ReNews;

    public RetrieveNewsUserAdapter(Activity context, List<RetrieveNewsUser> reStatus) {
        super(context, R.layout.listview_news_user, reStatus);
        this.context = context;
        this.ReNews = reStatus;
    }

    @SuppressLint("ResourceAsColor")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater infrater = context.getLayoutInflater();
        View listview = infrater.inflate(R.layout.listview_news_user, null, true);

        ImageView image = (ImageView) listview.findViewById(R.id.imageTeacher);
        TextView name = (TextView) listview.findViewById(R.id.text_nameTeacher);
        TextView title = (TextView) listview.findViewById(R.id.text_title);
        TextView news = (TextView) listview.findViewById(R.id.text_news);
        TextView startdate = (TextView) listview.findViewById(R.id.text_startdate);
        TextView enddate = (TextView) listview.findViewById(R.id.text_enddate);

        RetrieveNewsUser Renews = ReNews.get(position);
        String role = Renews.getTeacherRole();
        if (role.equals("อาจารย์")) {
            image.setImageResource(R.drawable.imteacher);
            name.setText(Renews.getTeacherName());
            name.setTextColor(Color.parseColor("#32CD32"));
        } else {
            image.setImageResource(R.drawable.immanage);
            name.setText(role);
            name.setTextColor(Color.parseColor("#FF9933"));
        }
        title.setText(Renews.getTitle());
        news.setText(Renews.getNews());
        startdate.setText(Renews.getStartdate());
        enddate.setText(Renews.getEnddate());
        return listview;
    }
}
