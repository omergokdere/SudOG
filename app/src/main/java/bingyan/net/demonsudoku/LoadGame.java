package bingyan.net.demonsudoku;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import bingyan.net.demonsudoku.db.SaveGameInfo;
import bingyan.net.demonsudoku.db.SaveGameInfoOpenHelper;
import bingyan.net.demonsudoku.model.SaveInfo;

//读取游戏进度
public class LoadGame extends ActionBarActivity {
    private ListView listView;
    private List<SaveInfo> saveGameInfoList;
    private SaveGameInfo saveGameInfo;
    private android.support.v7.app.ActionBar actionBar;

    //根据难度系数显示不同信息
    private String setTextForD(int difficulty) {
        switch (difficulty) {
            case 0:
                return "Kolay Oyun : ";
            case 1:
                return "Orta Oyun : ";
            case 2:
                return "Zor Oyun : ";
        }
        return "Kayıtlı oyun yok !!! ";
    }

    private String setTextForC(int customPass) {
        return "" + customPass + ". Seviye";
    }


    //把毫秒转化为日期
    private String transferLongToDate(String dateFormat,Long millSec){
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date date= new Date(millSec);
        return sdf.format(date);
    }
    //刷新
    private void refresh(){
        listView = (ListView) findViewById(R.id.List_load_game);
        saveGameInfoList = saveGameInfo.loadGames();
        if (saveGameInfoList != null) {
            List<Map<String, Object>> list = new ArrayList<>();
            for (int i = 0; i < saveGameInfoList.size(); i++) {
                Map<String, Object> map = new HashMap<>();
                map.put("gameInfo", setTextForD(saveGameInfoList.get(i).getDifficulty())
                        + "    " + setTextForC(saveGameInfoList.get(i).getCustomPass()));
                map.put("current_time", saveGameInfoList.get(i).getCurrentTime());
                map.put("used_time", "Geçen zaman : " + transferLongToDate
                        ("mm:ss",saveGameInfoList.get(i).getUsedTime()));
                list.add(map);
            }
            SimpleAdapter simpleAdapter = new SimpleAdapter(this, list, R.layout.list_item,
                    new String[]{"gameInfo", "current_time", "used_time"},
                    new int[]{R.id.custom_pass, R.id.current_time, R.id.used_time});
            listView.setAdapter(simpleAdapter);
        }
        else Toast.makeText(this, "Kayıtlı Oyun Bulunmamaktadır !", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_game);
        actionBar = getSupportActionBar();
        actionBar.hide();
        saveGameInfo = SaveGameInfo.getInstance(this);
        refresh();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SaveInfo saveInfo = saveGameInfo.loadGame(position);
                Intent intent = new Intent(LoadGame.this, DemonSudoku.class);
                intent.putExtra(SaveGameInfoOpenHelper.DIFFICULTY, saveInfo.getDifficulty());
                intent.putExtra(SaveGameInfoOpenHelper.CUSTOM_PASS, saveInfo.getCustomPass());
                intent.putExtra("test", saveInfo.getRecord());
                intent.putExtra("used_time", saveInfo.getUsedTime());
                startActivity(intent);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                //Log.d("test", position + "");
                AlertDialog.Builder dialog = new AlertDialog.Builder
                        (LoadGame.this);
                dialog.setTitle("Oyunu Sil");
                dialog.setMessage("Silmek istediğinizden emin misiniz !?");
                dialog.setCancelable(true);
                dialog.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Log.d("test", saveGameInfoList.get(position).getCurrentTime());
                        if (saveGameInfo.delete
                                (saveGameInfoList.get(position).getId())) {
                            Toast.makeText(LoadGame.this,
                                    "Kayıtlı Oyun Silindi", Toast.LENGTH_SHORT).show();
                        }
                        else Toast.makeText(LoadGame.this, "Kayıtlı Oyun Silinemedi !", Toast.LENGTH_SHORT).show();
                        if (saveGameInfo.loadGames() == null) {
                            finish();
                        } else refresh();

                    }
                });
                dialog.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.show();
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }


}
