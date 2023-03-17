package bingyan.net.demonsudoku;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import bingyan.net.demonsudoku.util.FileUtil;
import bingyan.net.demonsudoku.util.UseCount;

//游戏入口
public class MainActivity extends ActionBarActivity {
    private Button newGame,loadGame,gameHelper,exitGame;
    private android.support.v7.app.ActionBar actionBar;

    private void findViews(){
        newGame = (Button) findViewById(R.id.Easy);
        loadGame =(Button) findViewById(R.id.Medium);
        gameHelper = (Button) findViewById(R.id.Hard);
        exitGame = (Button) findViewById(R.id.ExitGame);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        actionBar = getSupportActionBar();
        actionBar.hide();
        findViews();
        //如果程序首次启动或者没有导入成功,需要创建文件夹并且将文件资源导入文件夹
        if(!new UseCount().isImportSucceed(this)) {
            FileUtil.createFolders();
            FileUtil.writeFile(FileUtil.FOLDER_NAME[0],
                    FileUtil.FILE_NAME[0],this,R.raw.sudoku_easy);
            FileUtil.writeFile(FileUtil.FOLDER_NAME[0],
                    FileUtil.FILE_NAME[0]+"_answer",this,R.raw.sudoku_easy_answer);

            FileUtil.writeFile(FileUtil.FOLDER_NAME[1],
                    FileUtil.FILE_NAME[1],this,R.raw.sudoku_normal);
            FileUtil.writeFile(FileUtil.FOLDER_NAME[1],
                    FileUtil.FILE_NAME[1]+"_answer",this,R.raw.sudoku_normal_answer);

            FileUtil.writeFile(FileUtil.FOLDER_NAME[2],
                    FileUtil.FILE_NAME[2],this,R.raw.sudoku_hard);
            FileUtil.writeFile(FileUtil.FOLDER_NAME[2],
                    FileUtil.FILE_NAME[2]+"_answer",this,R.raw.sudoku_hard_answer);
        }

        newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,DifficultyChoose.class);
                startActivity(intent);
            }
        });

        loadGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoadGame.class);
                startActivity(intent);
            }
        });

        gameHelper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GameInfo.class);
                startActivity(intent);
            }
        });

        exitGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
        
}
