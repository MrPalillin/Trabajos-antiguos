package com.example.estdieg_dandevi.appointments_estdieg_dandevi;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class EventsListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private SimpleCursorAdapter adapter;
    private static final String[] FROM = {EventContract.Column.TIME, EventContract.Column.NOMBRE,
    EventContract.Column.DESCRIPCION};
    private static final int[] TO = {R.id.list_item_time,
            R.id.list_item_name, R.id.list_item_description};
    private static final int LOADER_ID = 42;

    private static final String TAG = "dayfrag";


    int year;
    int month;
    int dayOfMonth;


    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle args = getActivity().getIntent().getExtras();
        year = args.getInt("year", 0);
        month = args.getInt("month", 0);
        dayOfMonth = args.getInt("dayOfMonth", 0);

        setEmptyText("No hay eventos para el dia: "+dayOfMonth+"/"+month+"/"+year);
        adapter = new SimpleCursorAdapter(getActivity(), R.layout.list_item, null, FROM, TO, 0);
        setListAdapter(adapter);
        getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (id != LOADER_ID)
            return null;
        String selection = String.format("%02d", dayOfMonth)+"/"+String.format("%02d", month)+"/"+String.valueOf(year);
        Date time = new Date();
        DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            time = sdf.parse(selection);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.d(TAG, selection+"turns into: "+time.toString());
        String regex = "TIME LIKE '" + time.toString().substring(0,10)+"%"+time.toString().substring(30) + "'";
        Log.d(TAG, regex);
        return new CursorLoader(getActivity(), EventContract.CONTENT_URI, null, regex,
                null, EventContract.DEFAULT_SORT);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
}
