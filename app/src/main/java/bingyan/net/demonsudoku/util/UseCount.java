package bingyan.net.demonsudoku.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Demon on 2015/2/5.
 * 该类方法用于记录程序的一些数据
 */
public class UseCount {

    //是否导入成功
    public boolean isImportSucceed(Context context){
        String KEY = "isImportSucceed";
        SharedPreferences preferences = context.getSharedPreferences(KEY,
                Context.MODE_PRIVATE);
        return preferences.getBoolean(KEY,false);
    }


    //是否导入成功参数的修改
    public void setFlag(Context context,Boolean b){
        String KEY = "isImportSucceed";
        SharedPreferences preferences = context.getSharedPreferences(KEY,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(KEY,b);
        editor.apply();
    }


    //记录通关的次数
    public void setPasses(Context context,String difficulty){
        SharedPreferences preferences = context.getSharedPreferences(difficulty,
                Context.MODE_PRIVATE);
        int count = preferences.getInt(difficulty,0);
        SharedPreferences.Editor editor = preferences.edit();
        //存入数据
        editor.putInt(difficulty,++count);
        //提交修改
        editor.apply();
    }


    //获取通关次数
    public int getPasses(Context context,String difficulty) {
        SharedPreferences preferences = context.getSharedPreferences(difficulty,
                Context.MODE_PRIVATE);
        return preferences.getInt(difficulty,0);
    }

}
