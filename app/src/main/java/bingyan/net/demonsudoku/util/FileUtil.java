package bingyan.net.demonsudoku.util;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Demon on 2015/2/12.
 * 文件存储工具类
 */
public class FileUtil {
    //包名
    public final static String PACKAGE_NAME = "bingyan.net.demonsudoku";

    //手机内部存储路径
    public final static String PATH = Environment.getDataDirectory() + File.separator + "data" +
            File.separator + PACKAGE_NAME + File.separator;

    //文件夹名
    public final static String[] FOLDER_NAME = {
            "easy", "normal", "hard"
    };

    //文件名
    public final static String[] FILE_NAME = {
            "sudoku_easy", "sudoku_normal", "sudoku_hard"
    };

    //创建文件夹
    public static boolean createNewFolder(String folderName) {
        Boolean bool = false;
        File file = new File(PATH + folderName);
        if (!file.exists())
            bool = file.mkdir();
        return bool;
    }

    //创建保存游戏关卡的文件夹
    public static boolean createFolders(){
        Boolean bool = true;
        for (int i = 0; i < FOLDER_NAME.length; i++) {

            if (!createNewFolder(FileUtil.FOLDER_NAME[i])) {
                bool = false;
                break;
            }
        }
        return bool;
    }

    //创建文件
    public static boolean createNewFile(File file) {
        boolean bool = false;
        if (!file.exists()) {
            try {
                bool = file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bool;
    }

    //读取关卡文件
    //一个文件内一行的数据代表一个关卡
    //返回该关卡所在行数据
    public static String readFile(String folderName,
                                  String fileName,int sLevel) {
        File file = new File(PATH + folderName, fileName);
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            String tmp;
            int line = 1;
            while ((tmp = bufferedReader.readLine()) != null) {
                if (line ==  sLevel) return tmp;
                line++;
            }
            bufferedReader.close();
        } catch (IOException e) {
            //Log.d("TestDebug", e.toString());
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    //Log.d("TestDebug", e.toString());
                }
            }
        }
        return null;
    }

    //将资源文件写入手机内部存储文件
    public static void writeFile(String folderName,
                                 String fileName,Context context,int resourceID) {
        File file = new File(PATH + folderName, fileName);
        createNewFile(file);
        InputStream is = context.getResources().openRawResource
                (resourceID);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            byte[] buffer = new byte[400000];
            int count;
            while ((count = is.read(buffer)) > 0) {
                fos.write(buffer, 0, count);
            }
            fos.close();
            is.close();
        } catch (IOException e) {
            new UseCount().setFlag(context, false);
            Toast.makeText(context, "aaaaaaaaaaaaaaaaaaaaaa",
                    Toast.LENGTH_LONG).show();
        }
        new UseCount().setFlag(context,true);
    }

}
