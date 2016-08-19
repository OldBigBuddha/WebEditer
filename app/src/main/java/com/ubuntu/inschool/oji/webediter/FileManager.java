package com.ubuntu.inschool.oji.webediter;

import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by oji on 16/08/14.
 * 主にファイルの入出力をするためのクラス
 */
public class FileManager {

    private File path;
    protected String fileName;
    private String code;

    public FileManager(String projectPath, String fileName , String code) {

        this.path = new File(projectPath + "/" + fileName);
        this.code = code;
        this.fileName = fileName;


    }

    public boolean createFile() {
        try {
            return this.path.createNewFile();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void savaCode() {

        if (this.path.exists()) {
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(this.path);
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream, "UTF-8"));

                bufferedWriter.write(code);
                bufferedWriter.flush();
                bufferedWriter.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Log.d("[error_saveCode]", "Couldn't found file");
    }
}
