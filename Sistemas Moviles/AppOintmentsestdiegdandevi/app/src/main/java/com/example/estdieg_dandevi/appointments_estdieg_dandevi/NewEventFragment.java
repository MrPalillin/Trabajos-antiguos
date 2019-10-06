package com.example.estdieg_dandevi.appointments_estdieg_dandevi;


import android.app.AlarmManager;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class NewEventFragment extends Fragment implements View.OnClickListener, TextWatcher {

    private static final String TAG = "NewActivity";
    Button newButton;
    EditText nombre, fecha, hora, descripcion;

    DbHelper dbHelper;
    SQLiteDatabase db;

    private static final int ALARM_REQUEST_CODE = 1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_event, container, false);

        newButton = (Button) view.findViewById(R.id.buttonCrear);
        nombre = (EditText) view.findViewById(R.id.nombre);
        fecha = (EditText) view.findViewById(R.id.fecha);
        hora = (EditText) view.findViewById(R.id.hora);
        descripcion = (EditText) view.findViewById(R.id.comentario);

        dbHelper = new DbHelper(this.getContext());

        newButton.setOnClickListener(this);
        nombre.addTextChangedListener(this);
        fecha.addTextChangedListener(this);
        hora.addTextChangedListener(this);
        descripcion.addTextChangedListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {

        //Log.d(TAG,"click");
        String nombreE = nombre.getText().toString();
        String fechaE = fecha.getText().toString();
        String horaE = hora.getText().toString();
        String descE = descripcion.getText().toString();
        Log.d(TAG, "nombre: " + nombreE + " fecha: " + fechaE + " hora: " + horaE + " desc: " + descE);
        String dateString = fechaE + " " + horaE;
        Log.d(TAG, "time: "+dateString);
        Date tiempo = new Date();
        try {
            DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            tiempo = sdf.parse(dateString);
            Log.d(TAG, "Date registrado" + tiempo.toString());
            //tiempo = new SimpleDateFormat("dd-MM-yyyy-HH:mm").parse(fechaE + "-" + horaE);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.clear();
            values.put(EventContract.Column.NOMBRE, nombreE);
            Log.d(TAG, "values tiempo: "+tiempo);
            values.put(EventContract.Column.TIME, tiempo.toString());
            values.put(EventContract.Column.DESCRIPCION, descE);
            db.insertWithOnConflict(EventContract.TABLE, null, values, SQLiteDatabase.CONFLICT_IGNORE);
            db.close();
            //Añadir aqui la logica de alarmas
            setAlarma(nombreE, tiempo);
        }catch (SQLException e){
            Toast.makeText(getContext(),"Fallo en la creación del evento",Toast.LENGTH_LONG).show();
        }

        try {

            Toast.makeText(getContext(), "Evento creado exitosamente", Toast.LENGTH_LONG).show();
            TimeUnit.MILLISECONDS.sleep(500);
            FragmentManager manager = getFragmentManager();
            manager.popBackStackImmediate();

        }catch(InterruptedException e){
            e.printStackTrace();
        }

    }

    private void setAlarma(String nombreE, Date tiempo) {
        AlarmManager manager = (AlarmManager) this.getActivity().getSystemService(Context.ALARM_SERVICE);

        Intent intent  = new Intent(this.getContext(), AlarmReceiver.class);
        Bundle args = new Bundle();
        args.putString("name", nombreE);
        intent.putExtras(args);
        PendingIntent pIntent = PendingIntent.getBroadcast(this.getContext(), ALARM_REQUEST_CODE, intent,  PendingIntent.FLAG_CANCEL_CURRENT);

        manager.set(AlarmManager.RTC_WAKEUP, tiempo.getTime(), pIntent);
        Log.d(TAG, "establecido alarma para: "+tiempo.getTime());
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
