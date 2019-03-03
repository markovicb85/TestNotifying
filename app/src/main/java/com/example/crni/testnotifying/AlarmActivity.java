package com.example.crni.testnotifying;

import android.content.DialogInterface;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;

public class AlarmActivity extends AppCompatActivity {

    ListView listView;
    DBHandler dbHandler;
    Notification notification = new Notification();
    ArrayList<String> alarms = new ArrayList<String>();
    private DrawerLayout mDrawerLayout;

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

        //Get data from DB and insert in listView
        listView = findViewById(R.id.listView_alarms);
        dbHandler = new DBHandler(this, null, null, 1);

        getAlarms();

        //Set Navdrawer listener
        mDrawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        return true;
                    }
                });
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
                mDrawerLayout.openDrawer(GravityCompat.START);
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
    protected void onResume() {
        super.onResume();
    }

    public void getAlarms(){
        if (getIntent().getExtras() != null){
            Bundle notify = getIntent().getBundleExtra("NOTIFY");
            notification.set_notificationTitle(notify.getString("TITLE"));
            notification.set_notificationBody(notify.getString("MESSAGE"));
            dbHandler.addnotification(notification);
        }
        showAlarms();
    }

    public void showAlarms(){
        ArrayList<Notification> notifications = dbHandler.notificationResults();
        if (!notifications.isEmpty()){
            for (Notification n : notifications) {
                alarms.add(n.get_notificationTitle());
            }
        }else {
            alarms.clear();
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, alarms);
        listView.setAdapter(arrayAdapter);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }


}
