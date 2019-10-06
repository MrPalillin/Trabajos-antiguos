package com.example.estdieg_dandevi.appointments_estdieg_dandevi;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class AlarmFragment extends Fragment implements View.OnClickListener{

    TextView name;
    Button off;
    Ringtone tono;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alarm, container, false);

        name = (TextView) view.findViewById(R.id.alarm_name);
        off = (Button) view.findViewById(R.id.off_button);

        Bundle extras = getActivity().getIntent().getExtras();
        name.setText(extras.getString("name"));
        off.setOnClickListener(this);

        Uri alarmaUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        tono = RingtoneManager.getRingtone(getContext(), alarmaUri);
        tono.play();

        return view;
    }

    @Override
    public void onClick(View v) {
        tono.stop();
        CalendarFragment newFragment = new CalendarFragment();
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(android.R.id.content,newFragment,newFragment.getClass().getSimpleName());
        transaction.commit();
    }
}
