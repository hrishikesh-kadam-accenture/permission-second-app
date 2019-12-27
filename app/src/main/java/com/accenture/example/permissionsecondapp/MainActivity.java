package com.accenture.example.permissionsecondapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    private static final int CALL_PHONE_REQUEST_CODE = 101;

    private TextView textViewCall1, textViewCall2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(LOG_TAG, "-> onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindViews();
    }

    private void bindViews() {
        Log.v(LOG_TAG, "-> bindViews");

        textViewCall1 = findViewById(R.id.textViewCall1);
        textViewCall2 = findViewById(R.id.textViewCall2);

        textViewCall1.setOnClickListener(this);
        textViewCall2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.textViewCall1:
                Log.v(LOG_TAG, "-> onClick -> textViewCall1");
                dialPhoneNumber("+91-98220112233");
                break;

            case R.id.textViewCall2:
                Log.v(LOG_TAG, "-> onClick -> textViewCall2");
                checkCallPhonePermission();
                break;
        }
    }

    public void dialPhoneNumber(String phoneNumber) {
        Log.v(LOG_TAG, "-> dialPhoneNumber");

        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @SuppressLint("MissingPermission")
    public void callPhoneNumber(String phoneNumber) {
        Log.v(LOG_TAG, "-> callPhoneNumber");

        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private void checkCallPhonePermission() {
        Log.v(LOG_TAG, "-> checkCallPhonePermission");

        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            Log.i(LOG_TAG, "-> checkCallPhonePermission -> CALL_PHONE permission not granted");

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    CALL_PHONE_REQUEST_CODE);
        } else {
            Log.i(LOG_TAG, "-> checkCallPhonePermission -> CALL_PHONE permission granted");
            callPhoneNumber("+91-98220445566");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.v(LOG_TAG, "-> onRequestPermissionsResult");

        switch (requestCode) {
            case CALL_PHONE_REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.i(LOG_TAG, "-> onRequestPermissionsResult -> CALL_PHONE permission granted");
                    callPhoneNumber("+91-98220445566");
                } else {
                    Log.i(LOG_TAG, "-> onRequestPermissionsResult -> CALL_PHONE permission denied");
                    Toast.makeText(this, "CALL_PHONE permission denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }
}
