package com.example.crni.testnotifying.Activity;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;

import com.example.crni.testnotifying.Fragment.InfoFragment;
import com.example.crni.testnotifying.Fragment.SettingsFragment;
import com.example.crni.testnotifying.Tools.DBHandler;
import com.example.crni.testnotifying.Tools.MyAdapter;
import com.example.crni.testnotifying.Data.MyAlarm;
import com.example.crni.testnotifying.Data.MyNotification;
import com.example.crni.testnotifying.R;

import java.util.ArrayList;

public class AlarmActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private DBHandler dbHandler = new DBHandler(this, null, null, 1);;
    private MyNotification notification = new MyNotification();
    private ArrayList<MyAlarm> alarms = new ArrayList<MyAlarm>();
    private DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarms_list);

        //Set the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }

        //Get data from DB and insert in recyclerView
        buildRecyclerView();
        getAlarmsFromNotification();
        showAlarms();

        //Set Navdrawer listener
        drawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new InfoFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_info);
        }
    }

    //Dodaje settings u app bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_items, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_delete:
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle("Delete entire list");
                alert.setMessage("Are you sure you want to delete?");
                alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbHandler.deleteAlarms();
                        showAlarms();
                    }
                });
                alert.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alert.show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SettingsFragment()).commit();
                break;
            case R.id.nav_info:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new InfoFragment()).commit();
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void buildRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView_alarms);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        if (adapter == null){
            adapter = new MyAdapter(alarms);
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
        if (getIntent().getExtras() != null){
            Bundle notify = getIntent().getBundleExtra("NOTIFY");
            notification.set_notificationTitle(notify.getString("TITLE"));
            notification.set_notificationBody(notify.getString("MESSAGE"));
            dbHandler.addnotification(notification);
            getIntent().removeExtra("NOTIFY");
            showAlarms();
        }
    }

    public void showAlarms(){
        ArrayList<MyNotification> notifications = dbHandler.notificationResults();
        if (!notifications.isEmpty() || notifications.size() != alarms.size()){
            alarms.clear();
            for (MyNotification n : notifications) {
                alarms.add(new MyAlarm(n.get_notificationID(), R.drawable.ic_action_delete, n.get_notificationTitle(), n.get_notificationBody()));
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

}
