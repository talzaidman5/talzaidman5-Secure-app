package com.example.a21a10243_project1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity implements SensorEventListener  {
    private Button activity_main_BTN_permissions;
    private SensorManager mSensorManager;
    private Sensor mSensorProximity;
    private float currentValue;
    private int checkAirplaneMode=0,isBatteryCharging,checkBluetoothMode=0;
    private boolean hasFlash =false, timeLeft=false;
    Date currentTime;
    int count=0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        currentTime = Calendar.getInstance().getTime();
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mSensorProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        activity_main_BTN_permissions = findViewById(R.id.activity_main_BTN_permissions);

        activity_main_BTN_permissions.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    checkAirplaneMode = Settings.System.getInt(MainActivity.this.getContentResolver(), Settings.Global.AIRPLANE_MODE_ON, 0);
                    checkBluetoothMode = Settings.System.getInt(MainActivity.this.getContentResolver(), Settings.Global.BLUETOOTH_ON, 0);
                    final Intent batteryIntent = MainActivity.this.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
                    isBatteryCharging = batteryIntent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
                    Date currentTimeEnd = Calendar.getInstance().getTime();
                    float change =  (currentTimeEnd.getTime()-currentTime.getTime())/1000;
                    if (checkAirplaneMode == 0 &&checkBluetoothMode ==0 && currentValue < 8 && isBatteryCharging == BatteryManager.BATTERY_STATUS_CHARGING &&change >=15)
                        openGreatJob();
                    else {
                        Toast toast = Toast.makeText(getApplicationContext(), "Eror", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
            });

        }

    private void openGreatJob() {
        Intent intent = new Intent(this, GreatJob.class);
        startActivity(intent);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
         currentValue = sensorEvent.values[0];
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mSensorProximity != null) {
            mSensorManager.registerListener(this, mSensorProximity,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        mSensorManager.unregisterListener(this);
    }

}

