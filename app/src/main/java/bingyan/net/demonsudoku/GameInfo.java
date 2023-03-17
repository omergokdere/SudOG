package bingyan.net.demonsudoku;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

//游戏帮助
public class GameInfo extends ActionBarActivity {
    private TextView textView;
    private android.support.v7.app.ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_info);
        actionBar = getSupportActionBar();
        actionBar.hide();

        textView = (TextView) findViewById(R.id.GameInfo);
        textView.setText("Sudoku standart olarak 9x9 boyutların da bir diyagramda çözülen ve her satır, her sütun ve her 3x3'lük karede 1'den 9'a kadar rakamların birer kez yer alması gereken bir zeka oyunu türüdür.        Bu uygulama Ömer Gökdere tarafından geliştirilmiştir."+"\n");
    }


}
