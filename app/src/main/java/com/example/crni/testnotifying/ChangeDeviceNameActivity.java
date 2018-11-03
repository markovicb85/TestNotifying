package com.example.crni.testnotifying;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChangeDeviceNameActivity extends AppCompatActivity {

    EditText oldName;
    EditText newName;
    Button changeName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_device_name);

        oldName = findViewById(R.id.txtDeviceName);
        newName = findViewById(R.id.txtNewDeviceName);
        String newDeviceName = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getString("deviceName", null);
        oldName.setText(newDeviceName);

    }

    public void setName(View view) {
        String name = newName.getText().toString();
        if(name.matches("")){
            Toast.makeText(this, "Please enter a new name", Toast.LENGTH_SHORT).show();
        }else{
            getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                    .putString("deviceName", newName.getText().toString()).apply();
            startActivity(new Intent(ChangeDeviceNameActivity.this, AlarmActivity.class));
        }
    }
}
