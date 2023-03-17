package bingyan.net.demonsudoku.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import bingyan.net.demonsudoku.model.SaveInfo;

/**
 * Created by Demon on 2015/2/15.
 *
 */
public class SaveGameInfo {
    public static final int VERSION = 1;
    private static SaveGameInfo saveGameInfo;
    private SQLiteDatabase db;

    private SaveGameInfo(Context context) {
        SaveGameInfoOpenHelper saveGameInfoOpenHelper = new SaveGameInfoOpenHelper(
                context, SaveGameInfoOpenHelper.TABLE_NAME, null, VERSION
        );
        db = saveGameInfoOpenHelper.getWritableDatabase();
    }

    public synchronized static SaveGameInfo getInstance(Context context) {
        if (null == saveGameInfo) {
            saveGameInfo = new SaveGameInfo(context);
        }
        return saveGameInfo;
    }

    //插入记录到数据库中
    public Boolean insert(SaveInfo saveInfo) {
        Boolean bool = false;
        if (null != saveInfo) {
            ContentValues values = new ContentValues();
            values.put(SaveGameInfoOpenHelper.DIFFICULTY, saveInfo.getDifficulty());
            values.put(SaveGameInfoOpenHelper.CUSTOM_PASS, saveInfo.getCustomPass());
            values.put(SaveGameInfoOpenHelper.RECORD, saveInfo.getRecord());
            values.put(SaveGameInfoOpenHelper.USED_TIME, saveInfo.getUsedTime());
            values.put(SaveGameInfoOpenHelper.CURRENT_TIME, saveInfo.getCurrentTime());
            if (-1 != db.insert(SaveGameInfoOpenHelper.TABLE_NAME, null, values)) {
                bool = true;
            }
        }
        return bool;
    }

    //从数据库中读取记录
    public List<SaveInfo> loadGames() {
        List<SaveInfo> saveInfos = null;
        Cursor cursor = db.query(SaveGameInfoOpenHelper.TABLE_NAME,
                null, null, null, null, null, null
        );
        if (cursor.moveToFirst()) {
            saveInfos = new ArrayList<>();
            do {
                SaveInfo saveInfo = new SaveInfo();
                saveInfo.setId(cursor.getInt(0));
                saveInfo.setDifficulty(cursor.getInt(1));
                saveInfo.setCustomPass(cursor.getInt(2));
                saveInfo.setUsedTime(cursor.getLong(3));
                saveInfo.setRecord(cursor.getString(4));
                saveInfo.setCurrentTime(cursor.getString(5));
                saveInfos.add(saveInfo);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return saveInfos;
    }

    //从数据库中读取某项记录
    public SaveInfo loadGame(int position) {
        SaveInfo saveInfo = null;
        Cursor cursor = db.query(SaveGameInfoOpenHelper.TABLE_NAME,
                null, null, null, null, null, null
        );
        if (cursor.moveToPosition(position)) {
            saveInfo = new SaveInfo();
            saveInfo.setDifficulty(cursor.getInt(1));
            saveInfo.setCustomPass(cursor.getInt(2));
            saveInfo.setUsedTime(cursor.getLong(3));
            saveInfo.setRecord(cursor.getString(4));
            saveInfo.setCurrentTime(cursor.getString(5));
        }
        return saveInfo;
    }

    //从数据库中删除某项记录
    public boolean delete(int id) {
        int i = db.delete(SaveGameInfoOpenHelper.TABLE_NAME,
                SaveGameInfoOpenHelper.ID + "=?",
                new String[]{id + ""});
        return i > 0;
    }


}
