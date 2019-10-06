package com.example.estdieg_dandevi.appointments_estdieg_dandevi;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class CalendarFragment extends Fragment implements  View.OnClickListener {

    Button botonViewAll;
    ImageButton botonAdd;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        CalendarView calendar = (CalendarView) view.findViewById(R.id.calendario);
        botonAdd = (ImageButton) view.findViewById(R.id.create_event);
        botonViewAll = (Button) view.findViewById(R.id.view_all);
        ListView lw = (ListView) view.findViewById(R.id.drawer_layout);//se puede eliminar (creo)


        botonAdd.setOnClickListener(this);
        botonViewAll.setOnClickListener(this);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                /*EventsListFragment newFragment = new EventsListFragment();
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(android.R.id.content,newFragment,newFragment.getClass().getSimpleName());*/
                Intent newIntent = new Intent(getContext(), EventsListActivity.class);
                //startActivity(new Intent(getContext(), EventsListActivity.class));
                //return true;
                Bundle date = new Bundle();
                date.putInt("year", year);
                date.putInt("month", month+1);
                date.putInt("dayOfMonth", dayOfMonth);
                newIntent.putExtras(date);
                startActivity(newIntent);
                /*newFragment.setArguments(date);
                transaction.commit();*/
//                Toast.makeText(getContext(), dayOfMonth + "/" + month + "/" + year, Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.create_event:
                NewEventFragment newFragment1 = new NewEventFragment();
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(android.R.id.content,newFragment1,newFragment1.getClass().getSimpleName());
                transaction.commit();
                break;
            case R.id.view_all:
                Intent newIntent = new Intent(getContext(), AllEventsActivity.class);
                startActivity(newIntent);
                break;
        }

    }

}
