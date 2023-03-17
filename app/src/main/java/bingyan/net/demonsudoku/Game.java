package bingyan.net.demonsudoku;

import java.util.ArrayList;
import java.util.List;

import bingyan.net.demonsudoku.util.FileUtil;

/**
 * Created by Demon on 2015/2/11.
 *
 */
public class Game {


    //拟设定的谜题
    private String mPuzzle;

    //用于记录读取存储数据的变量
    private String mLoadScene;

    /*difficulty------游戏的难度,决定去哪个文件夹中读取文件
     *customPass------目前关卡,决定读取哪个谜题
     */
    //该构造方法用于创建新游戏
    public Game(int difficulty,int customPass) {
        String branch = FileUtil.FOLDER_NAME[difficulty];
        mPuzzle = FileUtil.readFile(branch,
                FileUtil.FILE_NAME[difficulty], customPass);
        mLoadScene = mPuzzle;
    }
    //该构造方法用于读取游戏
    public Game(int difficulty,int customPass,String mLoadScene){
        String branch = FileUtil.FOLDER_NAME[difficulty];
        mPuzzle = FileUtil.readFile(branch,
                FileUtil.FILE_NAME[difficulty], customPass);
        this.mLoadScene = mLoadScene;
    }


    //初始化九宫格
    public String[] initMRecords(String resource) {
        String[] result = new String[81];
        for (int i = 0; i < resource.length(); i++) {
            //Log.d("test", mPuzzle.charAt(i) + "");
            if (resource.charAt(i) == 48) {
                result[i] = "";
                continue;
            }
            result[i] = resource.charAt(i) + "";
        }
        //Log.d("result", Arrays.toString(result));
        return result;
    }

    //

    //检查列是否符合要求
    public static List<Integer> checkRow(String[] currentRecords,int position) {
        List<Integer> list = new ArrayList<>(2);
        int line = position / 9;
        int row = position % 9;
        //Log.e("checkRow", line + " " + row);
        CharSequence target = currentRecords[position];
        for (int i = 0; i < 9; i++) {
            if (i == line) continue;
            if (currentRecords[row + 9 * i] == target) {
                list.add(row + 9 * i);
                //Log.e("position", row + 9 * i + " ");
            }
        }
        return list;
    }

    //检查行是否符合要求
    public static  List<Integer> checkLine(String[] currentRecords,int positon) {
        List<Integer> list = new ArrayList<>(2);
        int line = positon / 9;
        int row = positon % 9;
        //Log.e("checkLine", line + " " + row);
        CharSequence target = currentRecords[positon];
        for (int i = 0; i < 9; i++) {
            if (i == row) continue;

            if (currentRecords[9 * line + i] == target) {
                list.add(i + 9 * line);
                //Log.e("position", 9 * line + i + "");
            }
        }
        return list;
    }

    //检查3*3区域是否符合要求
    public static List<Integer> checkNine(String[] currentRecords,int positon) {
        List<Integer> list = new ArrayList<>(2);
        int line = positon / 9;
        int row = positon % 9;

        //获取左上角坐标位置
        int j = line / 3 * 3;
        int k = row / 3 * 3;
        //Log.e("checkNine", j + " " + k);
        CharSequence target = currentRecords[positon];
        //开始循环比较
        for (int i = j; i < j + 3; i++) {
            for (int m = k; m < k + 3; m++) {
                if (positon == 9 * i + m) continue;
                if (currentRecords[9 * i + m] == target) {
                    list.add(9 * i + m);
                    //Log.e("position", 9 * i + m + "");
                }
            }
        }
        return list;
    }

    //检查是否填写正确
    public static List<Integer> isCorrect(String[] currentRecords, int position) {

        List<Integer> list = new ArrayList<>(6);
        list.addAll(checkRow(currentRecords, position));
        list.addAll(checkLine(currentRecords, position));
        list.addAll(checkNine(currentRecords, position));
        return list;

    }

    //判断九宫格是否填满
    public static int checkWin(String[] mRecords){

            int numb = 0;
            for (String mRecord : mRecords) {
                if (!mRecord.isEmpty()) numb++;
            }
            return  numb;
    }

    public String getMLoadScene() {
        return mLoadScene;
    }

    public String getMPuzzle() {
        return mPuzzle;
    }
}
