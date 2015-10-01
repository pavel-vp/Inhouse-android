package com.mobileme.photolocator.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.mobileme.photolocator.api.HSPhoto;
import com.mobileme.photolocator.dao.HSSettings;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 23.09.2015.
 */
public class HSPhotoDBHelper extends SQLiteOpenHelper {

    private static final String TABLE_NAME = "hsphotolist";

    private static final String NAME = "name";
    private static final String CREATEDATE = "createdate";
    private static final String IMAGE = "image";
    private static final String IMAGEMINI = "imagemini";
    private static final String LATITUDE = "latitude";
    private static final String LONGITUDE = "longitude";
    private static final String STATUS = "status";
    private static final String STATUSDATE = "statusdate";
    private static final String STATUSMSG = "statusmsg";

    private static final String DROP_TABLE = "drop table " + TABLE_NAME;
    private static final String CREATE_TABLE = "create table " + TABLE_NAME + " ("
            + CREATEDATE + " long, "
            + NAME + " text, "
            + IMAGE + " BLOB, "
            + IMAGEMINI + " BLOB, "
            + LATITUDE + " double, "
            + LONGITUDE + " double, "
            + STATUS + " integer, "
            + STATUSDATE + " long, "
            + STATUSMSG + " text)";

    public HSPhotoDBHelper(Context context) {
        super(context, HSSettings.DB_NAME, null, HSSettings.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void addPhoto(HSPhoto p) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(CREATEDATE		, p.getCreatedate());
            cv.put(NAME		, p.getName());
            cv.put(IMAGE		, p.getImage());
            cv.put(IMAGEMINI		, p.getImagemini());
            cv.put(LATITUDE	, p.getLatitude());
            cv.put(LONGITUDE	, p.getLongitude());
            cv.put(STATUS		, p.getStatus());
            cv.put(STATUSDATE		, p.getStatusdate());
            cv.put(STATUSMSG		, p.getStatusMsg());
            db.insert(TABLE_NAME, null, cv);
            //db.close();
        } catch (Exception e)
        {
            Log.e("HS", "addHSPhoto ", e);
        }
    }

    public void updatePhoto(HSPhoto p) {
            SQLiteDatabase db = this.getWritableDatabase();
            try {

                ContentValues cv = new ContentValues();
                cv.put(STATUS		, p.getStatus());
                cv.put(STATUSDATE, p.getStatusdate());
                cv.put(STATUSMSG, p.getStatusMsg());

                db.update(TABLE_NAME, cv, CREATEDATE + "= ?", new String[]{Long.toString(p.getCreatedate())});

            } catch (Exception e) {
                Log.e("HS", "updatePhoto ", e);
            }
    }

    public void deletePhoto(HSPhoto p) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {

            db.delete(TABLE_NAME, CREATEDATE + "= ?", new String[]{Long.toString(p.getCreatedate())});

        } catch (Exception e) {
            Log.e("HS", "deletePhoto ", e);
        }
    }

    public List<HSPhoto> getPhotoList() {
        List<HSPhoto> res = new ArrayList<HSPhoto>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor;
        try {
            cursor = db.query(TABLE_NAME, new String[] {NAME, CREATEDATE, IMAGE, IMAGEMINI, LATITUDE, LONGITUDE, STATUS, STATUSDATE, STATUSMSG}
                    , null, null, null, null,  CREATEDATE + " desc", null);

        } catch (Exception e)
        {
            e.printStackTrace();
            cursor = null;
            db.execSQL(DROP_TABLE);
            db.execSQL(CREATE_TABLE);
        }
        if (cursor == null)
            return res;

        if (cursor.moveToFirst()) {
            do {
                HSPhoto p = new HSPhoto();
                p.setCreatedate(cursor.getLong(cursor.getColumnIndex(CREATEDATE)));
                p.setName(cursor.getString(cursor.getColumnIndex(NAME)));
                p.setImage(cursor.getBlob(cursor.getColumnIndex(IMAGE)));
                p.setImagemini(cursor.getBlob(cursor.getColumnIndex(IMAGEMINI)));
                p.setLongitude(cursor.getDouble(cursor.getColumnIndex(LONGITUDE)));
                p.setLatitude(cursor.getDouble(cursor.getColumnIndex(LATITUDE)));
                p.setStatus(cursor.getInt(cursor.getColumnIndex(STATUS)));
                p.setStatusdate(cursor.getLong(cursor.getColumnIndex(STATUSDATE)));
                p.setStatusMsg(cursor.getString(cursor.getColumnIndex(STATUSMSG)));

                res.add(p);
                Log.d("HS","selectPhoto: " + p.toString());
            } while (cursor.moveToNext());
        }
        if ((cursor != null) && !(cursor.isClosed()))
            cursor.close();

        return res;
    }
}
