package com.example.crni.testnotifying.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.session.MediaSession;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.crni.testnotifying.Data.TokenRegistration;
import com.example.crni.testnotifying.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button button;
    EditText device_name;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Integer currentValue;
    TokenRegistration tokenRegistration;
    String newNode;
    long childNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button);
        device_name = findViewById(R.id.txtDeviceName);

        Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getBoolean("isFirstRun", true);

        if (!isFirstRun) {
            startActivity(new Intent(MainActivity.this, AlarmActivity.class));
        } else {
            //Initialization of firebase
            FirebaseApp.initializeApp(MainActivity.this);
            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference = firebaseDatabase.getReference("tokens");

            //Set new values for SharedPreferences
            getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putString("deviceName", device_name.getText().toString()).apply();
            getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putBoolean("isFirstRun", false).apply();
        }
    }

    public void sendToken(View view) {
        if(!TextUtils.isEmpty(device_name.getText().toString())){
            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.FCM_PREF), Context.MODE_PRIVATE);
            tokenRegistration = new TokenRegistration(
            device_name.getText().toString(),
            sharedPreferences.getString(getString(R.string.FCM_TOKEN), ""));

            databaseReference.runTransaction(new Transaction.Handler() {
                @Override
                public Transaction.Result doTransaction(@NonNull MutableData mutableData) {
                    long value = 0;
                    if(mutableData.getValue() != null) {
                        value = mutableData.getChildrenCount();
                    }
                    value++;
                    newNode = Long.toHexString(value);
                    return Transaction.success(mutableData);
                }

                @Override
                public void onComplete(@Nullable DatabaseError databaseError, boolean b, @Nullable DataSnapshot dataSnapshot) {
                    //Map <String, Object> newData = new HashMap<String, Object>();
                    if (databaseError != null){
                        System.out.println("Transaction error: " + databaseError.getDetails());
                    }else{
                        addNode();
                        Toast.makeText(MainActivity.this, "Device name is added", Toast.LENGTH_SHORT).show();
                    }
                }
            });


            startActivity(new Intent(MainActivity.this, AlarmActivity.class));
        }
        else{
            Toast.makeText(this, "Please enter a device name", Toast.LENGTH_SHORT).show();
        }
    }

    private void addNode() {
        databaseReference.child(newNode).setValue(tokenRegistration);
    }
}
