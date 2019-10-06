package com.example.estdieg_dandevi.appointments_estdieg_dandevi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class NewEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            NewEventFragment fragment = new NewEventFragment();
            getFragmentManager().beginTransaction().add(android.R.id.content, fragment, fragment.getClass().getSimpleName()).commit();
        }
    }
}
