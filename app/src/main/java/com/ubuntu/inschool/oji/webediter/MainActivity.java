package com.ubuntu.inschool.oji.webediter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

/**
* アプリ甲子園提出用アプリ 【はとまる】
*/

public class MainActivity extends AppCompatActivity {

    private File filePath;
    private ArrayList<String> fileList;
    private Button createButton, loadButton;
    private Intent intent;
    private boolean isLoad = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createButton = (Button)findViewById(R.id.newCreateButton);
        loadButton   = (Button)findViewById(R.id.loadProjectsButton);

        intent = new Intent(MainActivity.this, EditActivity.class);

        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ListView listView = new ListView(MainActivity.this);
                setProjectList(MainActivity.this, listView);
                AlertDialog alertDialog = null;
                AlertDialog.Builder ADBuilder = new AlertDialog.Builder(MainActivity.this);

                ADBuilder.setTitle("選択してください")
                        .setView(listView);
                ADBuilder.setNegativeButton("キャンセル", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog = ADBuilder.create();
                alertDialog.show();

            }
        });

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("isLoad", isLoad);
                startActivity(intent);
            }
        });

    }

    private void setProjectList(final Context context, final ListView listView) {
        filePath = getFilesDir();
        fileList = new ArrayList<>(Arrays.asList(filePath.list()));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String projectName = fileList.get(position);
                isLoad = true;
                intent.putExtra("loadProjectName", projectName);
                intent.putExtra("isLoad", isLoad);
                startActivity(intent);
            }
        });

        ArrayAdapter arrayAdapter = new ArrayAdapter(context, android.R.layout.simple_expandable_list_item_1, fileList);
        listView.setAdapter(arrayAdapter);
    }
}
