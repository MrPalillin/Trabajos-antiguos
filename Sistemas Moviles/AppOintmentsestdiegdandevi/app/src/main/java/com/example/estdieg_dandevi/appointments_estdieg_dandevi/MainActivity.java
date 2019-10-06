package com.example.estdieg_dandevi.appointments_estdieg_dandevi;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            CalendarFragment fragment = new CalendarFragment();
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(android.R.id.content,fragment,fragment.getClass().getSimpleName());
            transaction.commit();
        }
    }
}
