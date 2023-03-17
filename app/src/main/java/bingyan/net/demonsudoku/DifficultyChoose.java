package bingyan.net.demonsudoku;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import bingyan.net.demonsudoku.db.SaveGameInfoOpenHelper;

//选取难度
public class DifficultyChoose extends ActionBarActivity {
    private Button easy,medium,hard,back;
    private android.support.v7.app.ActionBar actionBar;

    private void findViews(){
        easy = (Button) findViewById(R.id.Easy);
        medium = (Button) findViewById(R.id.Medium);
        hard = (Button) findViewById(R.id.Hard);
        back = (Button) findViewById(R.id.Back);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diffculty__choose);
        actionBar = getSupportActionBar();
        actionBar.hide();
        findViews();

        easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DifficultyChoose.this,SelectActivity.class);
                intent.putExtra(SaveGameInfoOpenHelper.DIFFICULTY,0);
                startActivity(intent);
            }
        });

        medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DifficultyChoose.this,SelectActivity.class);
                intent.putExtra(SaveGameInfoOpenHelper.DIFFICULTY,1);
                startActivity(intent);
            }
        });

        hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DifficultyChoose.this,SelectActivity.class);
                intent.putExtra(SaveGameInfoOpenHelper.DIFFICULTY,2);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
