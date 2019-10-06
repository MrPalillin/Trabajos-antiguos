package com.example.estdieg_dandevi.appointments_estdieg_dandevi;

import android.content.Context;
import android.content.Intent;
import android.util.Log;


/**
 * Created by electra on 7/01/19.
 */

public class AlarmReceiver extends android.content.BroadcastReceiver{

    private static String TAG = "AlarmReceiver";


    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "Alarma");

        String nombre = intent.getExtras().getString("name");
        Log.d("AlarmReceiver", "NAME :"+nombre);

        Intent alarmIntent = new Intent(context, AlarmActivity.class);
        alarmIntent.putExtras(intent.getExtras());
        context.startActivity(alarmIntent);
        
    }
}
