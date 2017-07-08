package com.example.xunhu.xunchat.Model.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by xunhu on 6/21/2017.
 */

public class XunChatDatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String CREATE_USER = "create table user ("+"user_id integer primary key autoincrement, "+
            "username, "+
            "password, "+
            "isActive)";
    private static final String CREATE_FRIEND_REQUEST = "create table request ("+"request_id integer primary key autoincrement, "+
            "sender_id, "+
            "sender, "+
            "isRead, "+
            "extras, "+
            "isAgreed, "+
            "time, "+
            "url, "+
            "username,"+
            "FOREIGN KEY (username) REFERENCES user (username))";

    public XunChatDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory, 1);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER);
        db.execSQL(CREATE_FRIEND_REQUEST);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (oldVersion){
            case 0:
                db.execSQL(CREATE_USER);
                db.execSQL(CREATE_FRIEND_REQUEST);
        }
    }
}
