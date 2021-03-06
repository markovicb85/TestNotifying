package com.example.crni.testnotifying;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button button;
    EditText device_name;
    EditText server_name;

    String app_server_url = "http://%1$s.ngrok.io/fcmtest/fcm_insert.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getBoolean("isFirstRun", true);

        if (!isFirstRun) {
            startActivity(new Intent(MainActivity.this, AlarmActivity.class));

        } else {
            setContentView(R.layout.activity_main);
            button = (Button) findViewById(R.id.button);
            device_name = findViewById(R.id.txtDeviceName);
            server_name = findViewById(R.id.txtServerName);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.FCM_PREF), Context.MODE_PRIVATE);
                    final String token = sharedPreferences.getString(getString(R.string.FCM_TOKEN), "");
                    final String deviceName = device_name.getText().toString();
                    final String serverName = server_name.getText().toString();
                    app_server_url = String.format(app_server_url, server_name.getText().toString());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, app_server_url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("fcm_token", token);
                            params.put("deviceName", deviceName);

                            return params;
                        }
                    };
                    getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                            .putString("deviceName", device_name.getText().toString()).apply();
                    MySingleton.getmInstance(MainActivity.this).addToRequestque(stringRequest);
                    startActivity(new Intent(MainActivity.this, AlarmActivity.class));
                }
            });
        }

        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                .putBoolean("isFirstRun", false).apply();

    }
}
