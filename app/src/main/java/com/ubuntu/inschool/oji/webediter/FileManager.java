package com.ubuntu.inschool.oji.webediter;

import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by oji on 16/08/14.
 * 主にファイルの入出力をするためのクラス
 *
 * アプリ甲子園提出用アプリ 【はとまる】
 */

public class FileManager {

    private File path;
    private String fileCode;
    protected String fileName;
    private String projectPath;
    private String code;

    public FileManager(String projectPath, String fileName) {
        this.path = new File(projectPath + "/" + fileName);
    }

    public String read() {

        if (path.isFile() && path.canRead()) {
            try {
                FileReader fileReader = new FileReader(path);
                BufferedReader bufferedReader = new BufferedReader(fileReader);

                fileCode = bufferedReader.readLine();
                while (fileCode != null) {
                    fileCode = fileCode + "\n" + bufferedReader.readLine();
                }
                bufferedReader.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return fileCode;
        }
        return null;
    }

    public String getFileCode() {
        return fileCode;
    }

    public void setFileCode(String code) {
        this.fileCode = code;
    }


    public boolean createFile() {
        try {
            return path.createNewFile();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteFile() {
        if (this.path.exists()) {
            return this.path.delete();
        }
        return  false;
    }

    public void savaCode() {

        if (this.path.exists()) {
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(this.path);
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream, "UTF-8"));

                bufferedWriter.write(fileCode);
                bufferedWriter.flush();
                bufferedWriter.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Log.d("[error_saveCode]", "Couldn't found file");
    }
}
