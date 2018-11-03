package com.example.crni.testnotifying;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static com.example.crni.testnotifying.R.xml.rounded_textview_red;

public class AlarmActivity extends AppCompatActivity {

    public static String NOTIFICATION;

     Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        button = (Button) findViewById(R.id.alarm);
        Intent intent = getIntent();

        if (intent.getBooleanExtra("notification", false)) {
            button.setEnabled(true);
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.back:
                startActivity(new Intent(AlarmActivity.this, ChangeDeviceNameActivity.class));
        }

        return (super.onOptionsItemSelected(item));
    }

    public void changeColor(View view) {
        button.setEnabled(false);
    }
}
