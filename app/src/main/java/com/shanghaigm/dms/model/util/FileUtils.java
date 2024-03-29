package com.shanghaigm.dms.model.util;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import com.shanghaigm.dms.model.entity.as.PathInfo;
import com.shanghaigm.dms.model.entity.mm.PaperInfo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Tom on 2017/9/7.
 */

public class FileUtils {
    private static String SDPATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getPath() + "/report_cp";
    private String cpPath;

    //储存日报压缩图片
    public void saveCpBitmap(Bitmap bm, String picName, SavePic savePic, String path) {
        try {
            if (!isFileExist("")) {
                File tempf = createSDDir("");
            }
            File f = new File(SDPATH, picName);
            cpPath = f.getAbsolutePath();
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.JPEG, 40, out);
            out.flush();
            out.close();
            Log.i("cpPath", "saveCpBitmap: " + cpPath);
            savePic.onExchangeFinish(cpPath, picName, path);              //接口
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static File createSDDir(String dirName) throws IOException {
        File dir = new File(SDPATH + dirName);
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {

            System.out.println("createSDDir:" + dir.getAbsolutePath());
            System.out.println("createSDDir:" + dir.mkdir());
        }
        return dir;
    }

    public static boolean isFileExist(String fileName) {
        File file = new File(SDPATH + fileName);
        file.isFile();
        return file.exists();
    }

    public static void delFile(String fileName) {
        File file = new File(SDPATH + fileName);
        if (file.isFile()) {
            file.delete();
        }
        file.exists();
    }

    //清空压缩图片文件夹
    public static void deleteCpDir(String dirPath) {
        int c = 0;
        File dir = new File(dirPath);
        if (dir == null || !dir.exists() || !dir.isDirectory())
            return;
        Log.i("listFiles", "deleteCpDir:listFiles        " + dir.listFiles().length);
        for (File file : dir.listFiles()) {
            if (file.isFile()) {
                if (file.delete()) {
                    c++;
                }
            }
        }
        Log.i("listFiles", "deleteCpDir:c        "+c);
        dir.delete();
    }

    public static boolean fileIsExists(String path) {
        try {
            File f = new File(path);
            if (!f.exists()) {
                return false;
            }
        } catch (Exception e) {

            return false;
        }
        return true;
    }

    public interface SavePic {
        void onExchangeFinish(String cpPath, String name, String path);
    }
//
//    public void setOnSavePic(SavePic savePic) {
//        this.savePic = savePic;
//    }

}
