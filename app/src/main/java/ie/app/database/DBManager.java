package ie.app.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ie.app.activities.Base;
import ie.app.models.donation;


public class DBManager {

    private SQLiteDatabase database;
    private DBDesigner dbHelper;

    public DBManager(Context context) {
        dbHelper = new DBDesigner(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        database.close();
    }

    public void add(donation d) {
        ContentValues values = new ContentValues();
        values.put("amount", d.amount);
        values.put("method", d.method);

        database.insert("donations", null, values);
    }

    public List<donation> getAll() {
        List<donation> donations = new ArrayList<donation>();
        Cursor cursor = database.rawQuery("SELECT * FROM donations", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            donation d = toDonation(cursor);
            donations.add(d);
            cursor.moveToNext();
        }
        cursor.close();
        return donations;
    }

    private donation toDonation(Cursor cursor) {
        donation pojo = new donation();
        pojo.id = cursor.getInt(0);
        pojo.amount = cursor.getInt(1);
        pojo.method = cursor.getString(2);
        return pojo;
    }

    public void setTotalDonated(Base base) {
        Cursor c = database.rawQuery("SELECT SUM(amount) FROM donations", null);
        c.moveToFirst();
        if (!c.isAfterLast())
            base.app.totalDonated = c.getInt(0);
    }

    public void reset() {
        database.delete("donations", null, null);
    }
}