package com.example.estdieg_dandevi.appointments_estdieg_dandevi;

import android.app.usage.UsageEvents;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by electra on 4/01/19.
 */

public class EventProvider extends ContentProvider {

    private static final String TAG = "EventProvider";
    private DbHelper dbHelper;
    private static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(EventContract.AUTHORITY, EventContract.TABLE,
                EventContract.STATUS_DIR);
        uriMatcher.addURI(EventContract.AUTHORITY, EventContract.TABLE + "/#",
                EventContract.STATUS_ITEM);
    }

    @Override
    public boolean onCreate() {
        dbHelper = new DbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        String where;
        switch (uriMatcher.match(uri)) {
            case EventContract.STATUS_DIR:
                where = selection;
                break;
            case EventContract.STATUS_ITEM:
                long id = ContentUris.parseId(uri);
                where = EventContract.Column.ID
                        + "="
                        + id
                        + (TextUtils.isEmpty(selection) ? "" : " and ( " + selection + " )");
                break;
            default:
                throw new IllegalArgumentException("uri incorrecta: " + uri);
        }
        String orderBy = (TextUtils.isEmpty(sortOrder)) ? EventContract.DEFAULT_SORT : sortOrder;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(EventContract.TABLE, projection, where, selectionArgs, null, null,
                orderBy);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        Log.d(TAG, "registros recuperados: " + cursor.getCount());
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)) {
            case EventContract.STATUS_DIR:
                Log.d(TAG, "gotType: vnd.android.cursor.dir/vnd.com.example.estdieg_dandevi.appointments_estdieg_dandevi.provider.event");
                return "vnd.android.cursor.dir/vnd.com.example.estdieg_dandevi.appointments_estdieg_dandevi.provider.event";
            case EventContract.STATUS_ITEM:
                Log.d(TAG, "gotType: vnd.android.cursor.item/vnd.com.example.estdieg_dandevi.appointments_estdieg_dandevi.provider.event");
                return "vnd.android.cursor.item/vnd.com.example.estdieg_dandevi.appointments_estdieg_dandevi.provider.event";
            default:
                throw new IllegalArgumentException("uri incorrecta: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        Uri ret = null;

        if (uriMatcher.match(uri) != EventContract.STATUS_DIR) {
            throw new IllegalArgumentException("uri incorrecta: " + uri);
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long rowId = db.insertWithOnConflict(EventContract.TABLE, null,
                values, SQLiteDatabase.CONFLICT_IGNORE);
        if (rowId != -1) {
            long id = values.getAsLong(EventContract.Column.ID);
            ret = ContentUris.withAppendedId(uri, id);
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return ret;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        String where;
        switch (uriMatcher.match(uri)) {
            case EventContract.STATUS_DIR:
                where = selection;
                break;
            case EventContract.STATUS_ITEM:
                long id = ContentUris.parseId(uri);
                where = EventContract.Column.ID
                        + "="
                        + id
                        + (TextUtils.isEmpty(selection) ? "" : " and ( " + selection + " )");
                break;
            default:
                throw new IllegalArgumentException("uri incorrecta: " + uri);
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int ret = db.delete(EventContract.TABLE, where, selectionArgs);
        if (ret > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        Log.d(TAG, "registros borrados: " + ret);
        return ret;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        String where;
        switch (uriMatcher.match(uri)) {
            case EventContract.STATUS_DIR:
                where = selection;
                break;
            case EventContract.STATUS_ITEM:
                long id = ContentUris.parseId(uri);
                where = EventContract.Column.ID
                        + "="
                        + id
                        + (TextUtils.isEmpty(selection) ? "" : " and ( " + selection + " )");
                break;
            default:
                throw new IllegalArgumentException("uri incorrecta: " + uri);
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int ret = db.update(EventContract.TABLE, values, where, selectionArgs);
        if (ret > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        Log.d(TAG, "registros actualizados: " + ret);
        return ret;
    }
}
