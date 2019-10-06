package com.example.estdieg_dandevi.appointments_estdieg_dandevi;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by electra on 26/12/18.
 */

public class DbHelper extends SQLiteOpenHelper {

    private static final String TAG = DbHelper.class.getSimpleName();

    public DbHelper(Context context) {
        super(context, EventContract.DB_NAME, null, EventContract.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = String.format("create table %s (%s int primary key,%s text, %s text, %s text)",
                EventContract.TABLE,
                EventContract.Column.ID,
                EventContract.Column.TIME,
                EventContract.Column.NOMBRE,
                EventContract.Column.DESCRIPCION);
        Log.d(TAG, "onCreate con consulta: " + sql);
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("drop table if exists " + EventContract.TABLE);

        onCreate(db);
        Log.d(TAG, "onUpgrade (modo asesino xD)");

    }
}
