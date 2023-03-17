package bingyan.net.demonsudoku;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import bingyan.net.demonsudoku.db.SaveGameInfo;
import bingyan.net.demonsudoku.db.SaveGameInfoOpenHelper;
import bingyan.net.demonsudoku.model.SaveInfo;
import bingyan.net.demonsudoku.util.FileUtil;
import bingyan.net.demonsudoku.util.UseCount;

/**
 * Created by Demon on 2015/2/10.
 *  游戏运行界面
 */

public class DemonSudoku extends ActionBarActivity {

    private android.support.v7.app.ActionBar actionBar;

    //存放81个Spinner
    private Spinner [] mSpinners = new Spinner[81];

    //
    private Chronometer mChronometer;

    //
    private Button saveGame,showAnswer;

    //存放81个Spinner的ID
    private final int[] mIDs = new int[]{
            R.id.spinner11, R.id.spinner12, R.id.spinner13, R.id.spinner14, R.id.spinner15,
            R.id.spinner16, R.id.spinner17, R.id.spinner18, R.id.spinner19,
            R.id.spinner21, R.id.spinner22, R.id.spinner23, R.id.spinner24, R.id.spinner25,
            R.id.spinner26, R.id.spinner27, R.id.spinner28, R.id.spinner29,
            R.id.spinner31, R.id.spinner32, R.id.spinner33, R.id.spinner34, R.id.spinner35,
            R.id.spinner36, R.id.spinner37, R.id.spinner38, R.id.spinner39,
            R.id.spinner41, R.id.spinner42, R.id.spinner43, R.id.spinner44, R.id.spinner45,
            R.id.spinner46, R.id.spinner47, R.id.spinner48, R.id.spinner49,
            R.id.spinner51, R.id.spinner52, R.id.spinner53, R.id.spinner54, R.id.spinner55,
            R.id.spinner56, R.id.spinner57, R.id.spinner58, R.id.spinner59,
            R.id.spinner61, R.id.spinner62, R.id.spinner63, R.id.spinner64, R.id.spinner65,
            R.id.spinner66, R.id.spinner67, R.id.spinner68, R.id.spinner69,
            R.id.spinner71, R.id.spinner72, R.id.spinner73, R.id.spinner74, R.id.spinner75,
            R.id.spinner76, R.id.spinner77, R.id.spinner78, R.id.spinner79,
            R.id.spinner81, R.id.spinner82, R.id.spinner83, R.id.spinner84, R.id.spinner85,
            R.id.spinner86, R.id.spinner87, R.id.spinner88, R.id.spinner89,
            R.id.spinner91, R.id.spinner92, R.id.spinner93, R.id.spinner94,
            R.id.spinner95, R.id.spinner96, R.id.spinner97, R.id.spinner98, R.id.spinner99};

    //Spinner候选项
    private final String[] numbs = new String[]{
            "","1","2","3","4","5","6","7","8","9"
    };

    //记录初始局面,及给出谜题局面
    private String[] initRecords = new String[81];
    //记录当前局面
    private String[] mRecords = new String[81];
    //游戏难度
    private int difficulty;
    //游戏关卡数
    private int customPass;
    //读取的数据
    private String mLoadScene;
    //数据库实例
    private SaveGameInfo saveGameInfo;
    //已用时间
    private long mUsedTime;

    //初始化当前局面,设置Spinner的默认选择
    //特别注意mRecords[i].charAt(0)的值为ASIIC码值
    private void startGame() {
        for (int i = 0; i < mRecords.length; i++) {
            //设置初始值
            if (!mRecords[i].equals("")) {
                mSpinners[i].setSelection(mRecords[i].charAt(0) - 48);
            }
            else mSpinners[i].setSelection(0);
        }
    }

    //设置Spinner是否可点击
    //游戏谜题给出的数字所在Spinner不可点击
    private void spinnerCanClick() {
        for (int i = 0; i < initRecords.length; i++) {
            if (initRecords[i].equals("")) {
                mSpinners[i].setEnabled(true);
            }
            else mSpinners[i].setEnabled(false);
        }
    }


    //FindViews
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void findViews() {
        WindowManager windowManager= (WindowManager)
                getSystemService(Context.WINDOW_SERVICE);
        actionBar = getSupportActionBar();
        actionBar.hide();

        mChronometer = (Chronometer) findViewById(R.id.chronometer);
        saveGame = (Button) findViewById(R.id.Save);
        showAnswer = (Button) findViewById(R.id.ShowAnswer);
        @SuppressWarnings("deprecation")
        //设置Adapter展开后的宽度
        int dropDownWidth = windowManager.
                getDefaultDisplay().getWidth()/5;
        for (int i = 0; i < mIDs.length; i++) {
            mSpinners[i] = (Spinner) findViewById(mIDs[i]);
            mSpinners[i].setDropDownWidth(dropDownWidth);
        }
    }

    //初始化时设置DropDown的Adapter
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setAdapter() {

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,
                R.layout.spinner_dropdown, numbs);
        for (int i = 0; i < mIDs.length; i++) {
            mSpinners[i].setAdapter(arrayAdapter);
        }
    }

    //初始化监听器
    private void setListener() {
        for (int i = 0; i < mSpinners.length; i++) {
            //i记录当前position
            final int finalI = i;
            //触摸时显示绿色
            mSpinners[i].setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    mSpinners[finalI].setBackgroundColor(Color.GREEN);
                    return false;
                }
            });
            //选择某个候选数字后
            mSpinners[i].setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @SuppressWarnings("deprecation")
                @Override
                public void onItemSelected(AdapterView<?> parent,
                                           View view, int position, long id) {
                    //先清除九宫格
                    clearBackground();
                    //设置字体参数
                    TextView tv = (TextView) view;
                    //给定的初始条件不可改变，字体设为黑色
                    if (!initRecords[finalI].equals(""))
                        tv.setTextColor(Color.BLACK);
                    //获取当前九宫格的数字信息,并更新mRecords
                    mRecords[finalI] = (String) parent.
                            getItemAtPosition(position);
                    //当九宫格未填时不考虑
                    if (!mRecords[finalI].equals("")) {

                        List a = Game.isCorrect(mRecords, finalI);
                        //如果存在有冲突的格子话
                        if (a.size() != 0) {
                            for (int i = 0; i < a.size(); i++) {
                                //冲突格子设置成黄色
                                mSpinners[(int) a.get(i)].
                                        setBackgroundColor(Color.YELLOW);

                                //屏蔽其他Spinner,知道修改其他可行值时才可操作
                                setEnabled(finalI, false);
                            }
                            //当前格子设置成红色，表示错误
                            mSpinners[finalI].
                                    setBackgroundColor(Color.RED);
                        } else {
                            //刷新Spinner是否可点击
                            spinnerCanClick();
                            //判断游戏胜利
                            if (Game.checkWin(mRecords) == 81) gameWin();
                            //Log.d("numbers",Game.checkWin(mRecords)+"");
                        }
                    }
                    //刷新Spinner是否可点击
                    else spinnerCanClick();
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    //未选择
                }
            });

            saveGame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //保存游戏
                    saveGame();
                }
            });

            showAnswer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //显示答案
                    showAnswer();
                }
            });

        }
    }

    private void gameWin() {
        //若当前关卡为最高关卡，更新个人记录,解锁下一个关卡
        if ((customPass-1) == new UseCount().getPasses(this,
                FileUtil.FOLDER_NAME[difficulty])) {
            new UseCount().setPasses
                    (this, FileUtil.FOLDER_NAME[difficulty]);
        }
        //计时器停止
        mChronometer.stop();
        //储存游戏不可用
        saveGame.setEnabled(false);
        //显示提示框，不屏蔽back键，可通过Back查看答案
        AlertDialog.Builder dialog = new AlertDialog.Builder
                (DemonSudoku.this);
        dialog.setTitle("Oyun Bitti . Tebrikler !!!");
        dialog.setMessage("Yeni Oyuna başlansın mı ?");
        dialog.setCancelable(true);
        dialog.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(DemonSudoku.this, SelectActivity.class);
                intent.putExtra(SaveGameInfoOpenHelper.DIFFICULTY, difficulty);
                startActivity(intent);
            }
        });
        dialog.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(DemonSudoku.this, MainActivity.class);
                startActivity(intent);

            }
        });

        dialog.show();
    }

    //清除背景色
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void clearBackground() {
        for (Spinner mSpinner : mSpinners) {
            mSpinner.setBackground(null);
        }
    }

    //是否屏蔽其他组件
    private void setEnabled(int position,Boolean canTouch) {
        for (int i = 0; i < mSpinners.length; i++) {
            if (i == position) continue;
            mSpinners[i].setEnabled(canTouch);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_table);
        findViews();
        //获取用户选择的困难度,关卡,游戏进行时局面以及所用时间
        Intent intent =getIntent();
        difficulty = intent.getIntExtra(SaveGameInfoOpenHelper.DIFFICULTY, -1);
        customPass = intent.getIntExtra(SaveGameInfoOpenHelper.CUSTOM_PASS, -1);
        mLoadScene = intent.getStringExtra("test");
        mUsedTime = intent.getLongExtra("used_time", 0);
        //setAdapter需先在startGame之前
        Game game;
        //如果直接进入游戏，则mLoadScene为null
        if (mLoadScene == null) {
            game = new Game(difficulty, customPass);
        }
        else {
            game = new Game(difficulty, customPass, mLoadScene);
        }
        //初始化
        mRecords = game.initMRecords(game.getMLoadScene());
        initRecords = game.initMRecords(game.getMPuzzle());
        spinnerCanClick();
        setAdapter();
        //startGame时，每个格子都被填上初始的值
        //OnItemSelected中的方法被调用一次
        //当OnItemSelected中的方法被调用时，mRecords中的记录被更新
        startGame();
        //从储存游戏前的时间断点开始计时
        if (mUsedTime != 0) {
            mChronometer.setBase(SystemClock.elapsedRealtime() - mUsedTime);
        }

        mChronometer.start();
        setListener();
    }


    //储存游戏进度
    private void saveGame(){
        saveGameInfo = SaveGameInfo.getInstance(DemonSudoku.this);
        Date date = new Date();
        DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd hh:mm EE");
        df2.format(date);
        SaveInfo saveInfo = new SaveInfo(difficulty, customPass,
                SystemClock.elapsedRealtime()-mChronometer.getBase(),
                df2.format(date), toStringData(mRecords));
        if (saveGameInfo.insert(saveInfo)) {
            Toast.makeText(this, "Oyun Kaydedildi ！", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(this,"Oyun Kaydedilemedi !",Toast.LENGTH_SHORT).show();
    }

    private String toStringData(String[] strings) {
        StringBuilder sb = new StringBuilder("");
        for (String string : strings) {
            if (string.equals("")) sb.append("0");
            else sb.append(string);
        }
        return sb.toString();
    }

    //显示答案
    private void showAnswer() {
        String s = FileUtil.readFile(FileUtil.FOLDER_NAME[difficulty],
                FileUtil.FILE_NAME[difficulty] + "_answer", customPass);
        for (int i = 0; i < s.length(); i++) {
            mSpinners[i].setSelection(s.charAt(i)-48);
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        mChronometer.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mChronometer.start();
    }
}
