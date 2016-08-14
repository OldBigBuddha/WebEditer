package com.ubuntu.inschool.oji.webediter;

import android.widget.EditText;

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
    private String fileName;
    private String code;

    public void FileManager(String projectPath, String fileName , String code) {

        path = new File(projectPath + "/" + fileName);
        this.code = code;
        this.fileName = fileName;

    }

    public void FileManager(){

    }

    private void savaCode() {

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(path);
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream,"UTF-8"));

            bufferedWriter.write(code);
            bufferedWriter.flush();
            bufferedWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
