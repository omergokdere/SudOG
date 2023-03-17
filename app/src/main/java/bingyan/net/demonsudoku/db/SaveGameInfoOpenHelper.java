package bingyan.net.demonsudoku.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Demon on 2015/2/15.
 *
 */
public class SaveGameInfoOpenHelper extends SQLiteOpenHelper {
    //ID
    public static final String ID = "id";
    //游戏难度
    public static final String DIFFICULTY = "difficulty";
    //游戏关卡
    public static final String CUSTOM_PASS = "custom_pass";
    //游戏所用时间
    public static final String USED_TIME = "used_time";
    //游戏当时记录
    public static final String RECORD = "record";
    //游戏储存时时间
    public static final String CURRENT_TIME = "current_time";
    //表名
    public static final String TABLE_NAME = "saveGameInfo";
    //建表语句,特别需要注意空格
    public static final String CREATE_INFO = "create table "
            + TABLE_NAME + "("
            + ID + " integer primary key autoincrement,"
            + DIFFICULTY + " integer,"
            + CUSTOM_PASS + " integer,"
            + USED_TIME + " long,"
            + RECORD + " text,"
            + CURRENT_TIME + " text)";

    public SaveGameInfoOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_INFO);
        //Log.d("CREATE", CREATE_INFO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
