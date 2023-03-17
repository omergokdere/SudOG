package bingyan.net.demonsudoku;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import bingyan.net.demonsudoku.db.SaveGameInfoOpenHelper;
import bingyan.net.demonsudoku.util.FileUtil;
import bingyan.net.demonsudoku.util.UseCount;

//选取游戏关卡
public class SelectActivity extends ActionBarActivity {
    private GridView gridView;
    private TextView textView;
    private int customPass;
    private int difficulty;
    private android.support.v7.app.ActionBar actionBar;
    private int limit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        actionBar = getSupportActionBar();
        actionBar.hide();

        @SuppressWarnings("deprecation")
        WindowManager windowManager= (WindowManager)
                getSystemService(Context.WINDOW_SERVICE);
        int height = windowManager.getDefaultDisplay().getHeight()/45;
        Intent intent = getIntent();

        difficulty = intent.getIntExtra(SaveGameInfoOpenHelper.DIFFICULTY,
                -1);
        textView = (TextView) findViewById(R.id.textView2);
        switch (difficulty){
            case 0:
                textView.setText("Kolay Seviyeli Oyunlar");
                limit = 8;
                break;
            case 1:
                textView.setText("Orta Seviyeli Oyunlar");
                limit = 12;
                break;
            case 2:
                textView.setText("Zor Seviyeli Oyunlar");
                limit = 10;
                break;
        }
        gridView = (GridView) findViewById(R.id.select_custom);
        gridView.setVerticalSpacing(height);
        refresh();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position <= customPass) {
                    Intent intent1 = new Intent(SelectActivity.this, DemonSudoku.class);
                    intent1.putExtra(SaveGameInfoOpenHelper.CUSTOM_PASS, position + 1);
                    intent1.putExtra(SaveGameInfoOpenHelper.DIFFICULTY, difficulty);
                    startActivity(intent1);
                } else {
                    Toast.makeText(SelectActivity.this,
                            "Bu seviyeyi açmanız için bir önceki seviyeyi tamamlamış olmanız gerekmektedir !!! ", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void refresh(){
        customPass = new UseCount().getPasses(this, FileUtil.FOLDER_NAME[difficulty]);
        List<Map<String,Object>> list = new ArrayList<>();
        for (int i = 0; i < limit; i++) {
            Map<String,Object> listItem = new HashMap<>();
            if (i<=customPass)
                listItem.put("background", R.drawable.btn_style_green);
            else listItem.put("background",R.drawable.btn_style_one_disabled);
            listItem.put("num", i+1+"");
            list.add(listItem);
        }
        SimpleAdapter adapter = new SimpleAdapter(this, list, R.layout.simple_gridview,
                new String[]{"background","num"}, new int[]{
                R.id.imageView,R.id.textView});
        gridView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();

    }
}
