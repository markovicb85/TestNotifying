package com.example.crni.testnotifying.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.crni.testnotifying.Data.MyAlarm;
import com.example.crni.testnotifying.Data.MyNotification;
import com.example.crni.testnotifying.R;
import com.example.crni.testnotifying.Tools.DBHandler;
import com.example.crni.testnotifying.Tools.MyAdapter;

import java.util.ArrayList;


public class AlarmListFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private View view;


    private DBHandler dbHandler;
    private MyNotification notification = new MyNotification();
    private ArrayList<MyAlarm> alarms = new ArrayList<MyAlarm>();
    private ArrayList<MyNotification> notificationList = new ArrayList<MyNotification>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_alarm_list, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        dbHandler = new DBHandler(this.getActivity(), null, null, 1);

        //Get data from DB and insert in recyclerView
        buildRecyclerView();
        getAlarmsFromNotification();
        showAlarms();
    }

    public void buildRecyclerView() {
        recyclerView = view.findViewById(R.id.recyclerView_alarms);
        layoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(layoutManager);
        if (adapter == null){
            adapter = new MyAdapter(this.getActivity(), alarms);
        }
        recyclerView.setAdapter(adapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                String position = viewHolder.itemView.getTag().toString();
                dbHandler.deleteOneAlarm(position);
                alarms.remove(viewHolder.getAdapterPosition());
                adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                adapter.notifyItemRangeChanged(viewHolder.getAdapterPosition(), adapter.getItemCount());
            }
        }).attachToRecyclerView(recyclerView);
    }


    public void getAlarmsFromNotification(){
        if (this.getActivity().getIntent().getExtras() != null){
            Bundle notify = this.getActivity().getIntent().getBundleExtra("NOTIFY");
            notification.set_notificationTitle(notify.getString("TITLE"));
            notification.set_notificationBody(notify.getString("MESSAGE"));
            dbHandler.addnotification(notification);
            this.getActivity().getIntent().removeExtra("NOTIFY");
            showAlarms();
        }
    }

    public void showAlarms(){
        if (notificationList.isEmpty() || notificationList.size() != alarms.size()){
            alarms.clear();
            notificationList.clear();
            notificationList = dbHandler.notificationResults();
            for (MyNotification n : notificationList) {
                alarms.add(new MyAlarm(n.get_notificationID(), R.drawable.ic_action_delete, n.get_notificationTitle(), n.get_notificationBody()));
            }
        }
        adapter.notifyDataSetChanged();
    }
}
