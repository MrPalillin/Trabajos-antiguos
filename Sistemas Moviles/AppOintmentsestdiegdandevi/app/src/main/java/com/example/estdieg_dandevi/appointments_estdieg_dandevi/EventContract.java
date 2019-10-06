package com.example.estdieg_dandevi.appointments_estdieg_dandevi;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by electra on 26/12/18.
 */

public class EventContract {

    public static final String DB_NAME = "event.db";
    public static final int DB_VERSION = 1;
    public static final String TABLE = "evento";
    public static final String DEFAULT_SORT = Column.TIME + " DESC";

    public static final String AUTHORITY = "com.example.estdieg_dandevi.Appointmentsestdiegdandevi";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE);
    public static final int STATUS_ITEM = 1;
    public static final int STATUS_DIR = 2;

    public class Column {
        public static final String ID = BaseColumns._ID;
        public static final String TIME = "time";
        public static final String NOMBRE = "nombre";
        public static final String DESCRIPCION = "descripcion";
    }
}
