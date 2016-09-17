package com.ubuntu.inschool.oji.webediter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ubuntu.inschool.oji.webediter.Fragments.EditFragment;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

/**
* アプリ甲子園提出用アプリ 【はとまる】
*/

public class MainActivity extends AppCompatActivity {

    private TextView createProject, setting;
    private ListView listView;
    protected static File filePath;
    private ArrayList<String> fileList;
    private Intent intent;
    private boolean isLoad = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView      = (ListView)findViewById(R.id.projectList);
        createProject = (TextView)findViewById(R.id.createProject);
        setting       = (TextView)findViewById(R.id.setting);
        intent = new Intent(MainActivity.this, EditActivity.class);

        filePath = getFilesDir();
        fileList = new ArrayList<>(Arrays.asList(filePath.list()));

        listView.setAdapter(
                setProjectList(getApplicationContext())
        );


        createProject.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View v) {
                    intent.putExtra("isLoad", false);
                startActivity(intent);
            }
        });

        listView.setAdapter(setProjectList(getApplicationContext()));
    }

    public ArrayAdapter setProjectList(final Context context) {
        filePath = getFilesDir();
        fileList = new ArrayList<>(Arrays.asList(filePath.list()));
        fileList.remove(fileList.indexOf("instant-run"));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String projectName = fileList.get(position);
                isLoad = true;
                intent.putExtra("loadProjectName",projectName);
                intent.putExtra("loadProjectPath", filePath.toString() + "/" + projectName);
                intent.putExtra("isLoad", isLoad);
                startActivity(intent);
            }
        });

        ArrayAdapter arrayAdapter = new ArrayAdapter(context, android.R.layout.simple_expandable_list_item_1, fileList);
//        listView.setAdapter(arrayAdapter);
        return arrayAdapter;
    }
}
