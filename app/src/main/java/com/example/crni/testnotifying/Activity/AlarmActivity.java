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

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.crni.testnotifying.Fragment.AlarmListFragment;
import com.example.crni.testnotifying.Fragment.AlarmFragment;
import com.example.crni.testnotifying.Fragment.InfoFragment;
import com.example.crni.testnotifying.Fragment.SettingsFragment;
import com.example.crni.testnotifying.Tools.DBHandler;
import com.example.crni.testnotifying.Data.MyAlarm;
import com.example.crni.testnotifying.R;
import com.example.crni.testnotifying.Tools.MyAdapter;

import java.util.ArrayList;

public class AlarmActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        MyAdapter.ItemClicked {

    private DBHandler dbHandler = new DBHandler(this, null, null, 1);
    private ArrayList<MyAlarm> alarms = new ArrayList<MyAlarm>();
    private DrawerLayout drawerLayout;

    private TextView alarmTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarms_list);

        alarmTitle = findViewById(R.id.tv_alarm_title);

        //Set the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }

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
                    new AlarmListFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_alarm_list);
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
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                new AlarmListFragment())
                                .addToBackStack(null)
                                .commit();
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
            case R.id.nav_alarm_list:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AlarmListFragment())
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.nav_settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SettingsFragment())
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.nav_info:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new InfoFragment())
                        .addToBackStack(null)
                        .commit();
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count != 0) {
            getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    public void onItemClicked(MyAlarm alarmData) {
        Bundle bundle = new Bundle();
        AlarmFragment alarm = new AlarmFragment();

        bundle.putString("alarm", alarmData.getText2());
        alarm.setArguments(bundle);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                alarm)
                .addToBackStack(null)
                .commit();
    }
}
