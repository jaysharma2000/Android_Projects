package com.example.vehicleinsuranceclaimapp.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.example.vehicleinsuranceclaimapp.database.AppDatabase;
import com.example.vehicleinsuranceclaimapp.model.Claim;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class ClaimUpdateService extends Service {

    private final Handler handler = new Handler();
    private AppDatabase database;

    @Override
    public void onCreate(){
        super.onCreate();
        database  = AppDatabase.getInstance(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        simulateRealTimeUpdates();
        return START_STICKY;
    }

    private void simulateRealTimeUpdates() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String[] statuses = {"Pending", "Approved", "Rejected"};

                new Thread(() -> {

                    List<Claim> claims = database.claimDao().getAllClaims();

                    for (Claim claim : claims) {
                        String newStatus = statuses[new Random().nextInt(statuses.length)];

                        if (!claim.getStatus().equals(newStatus)) {
                            claim.setStatus(newStatus);


                            database.claimDao().updateClaim(claim);
                        }
                    }
                }).start();

                handler.postDelayed(this, 5000);
            }
        }, 5000);
    }


    public void onDistroy(){
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
