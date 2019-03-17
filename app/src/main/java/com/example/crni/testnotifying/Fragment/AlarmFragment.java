package com.example.crni.testnotifying.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.crni.testnotifying.Activity.AlarmActivity;
import com.example.crni.testnotifying.Data.MyAlarm;
import com.example.crni.testnotifying.R;
import com.example.crni.testnotifying.Tools.MyAdapter;

import java.util.ArrayList;


public class AlarmFragment extends Fragment{

    private String title;
    TextView alarmTitle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_alarm, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        alarmTitle = getView().findViewById(R.id.tv_alarm_title);
        if (getArguments() != null){
            title = getArguments().getString("alarm");
            alarmTitle.setText(title);
        }
    }
}
