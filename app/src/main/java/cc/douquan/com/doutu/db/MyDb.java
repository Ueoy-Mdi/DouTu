package cc.douquan.com.doutu.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by feq on 2016/11/2.
 */

public class MyDb extends SQLiteOpenHelper {
    public static final String CREATE_TABLE =
            "create table Person(id integer primary key autoincrement," +
                    "name text," +
                    "age integer," +
                    "flag boolean)";

    public MyDb(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
