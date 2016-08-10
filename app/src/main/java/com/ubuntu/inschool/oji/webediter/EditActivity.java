package com.ubuntu.inschool.oji.webediter;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Environment;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ubuntu.inschool.oji.webediter.Fragments.BlankFragment;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener{

    AlertDialog alertDialog;
    AlertDialog.Builder ADBuilder;

    String projectName;
    Fragment fragmentPage;
    ArrayList<Fragment> fragmentArray = new ArrayList<>();

    TabLayout tabLayout;
    ViewPager viewPager;
    Toolbar toolbar;

    File dateFilePath;
    String filePath;
    String projectPath;

    FragmentPagerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        toolbar = (Toolbar)findViewById(R.id.tool_bar);
        tabLayout = (TabLayout)findViewById(R.id.tabs);
        viewPager = (ViewPager)findViewById(R.id.pager);


        ADBuilder = new AlertDialog.Builder(EditActivity.this);
        final View VIEW = EditActivity.this.getLayoutInflater().inflate(R.layout.context_main, null);

        ADBuilder.setTitle("Project's Name")
                .setView(VIEW)
                .setPositiveButton("GO!!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText editText_ProjectName = (EditText)VIEW.findViewById(R.id.editText_ProjectName);
                        EditActivity.this.projectName = editText_ProjectName.getText().toString();

                        filePath = getFilesDir().toString();
                        projectPath = filePath + "/" + projectName;
                        dateFilePath = new File(projectPath);
                        Toast.makeText(EditActivity.this,dateFilePath.toString(),Toast.LENGTH_LONG).show();

                        if (!dateFilePath.exists()) {
                            dateFilePath.mkdir();

                            makeFile("index.html");
                            makeFile("style.css");
                            makeFile("index.js");

                        }else {

                        }

                    }
                });

        alertDialog = ADBuilder.create();

        alertDialog.show();


        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        BlankFragment fragmentHTML  = BlankFragment.newInstance("index.html");
        BlankFragment fragmentCSS   = BlankFragment.newInstance("style.css");
        BlankFragment fragmentJS    = BlankFragment.newInstance("index.js");

        fragmentArray.add(fragmentHTML);
        fragmentArray.add(fragmentCSS);
        fragmentArray.add(fragmentJS);

        adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragmentArray.get(position);
            }

            @Override
            public CharSequence getPageTitle(int position) {
                String title = fragmentArray.get(position).getArguments().getString("title");
                return title;
            }

            @Override
            public int getCount() {
                return fragmentArray.size();
            }
        };

        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(this);

        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_menu, menu);
//        return true;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        final int ID = item.getItemId();

        final CharSequence[] ITEMS = {"HTML","CSS","JavaScript"};
        AlertDialog.Builder addDig = new AlertDialog.Builder(EditActivity.this);
        addDig.setTitle("ファイル形式を選択してください");
        addDig.setItems(ITEMS, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String extension;
                switch (which) {
                    case 0:
                        extension = "html";
                        EditActivity.this.makeDialog(extension);
                        break;
                    case 1:
                        extension = "css";
                        EditActivity.this.makeDialog(extension);
                        break;
                    case 2:
                        extension = "js";
                        EditActivity.this.makeDialog(extension);
                        break;
                }

            }
        });


        addDig.create().show();

        return super.onOptionsItemSelected(item);

    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void makeDialog(final String extension) {
        AlertDialog.Builder nameDig = new AlertDialog.Builder(EditActivity.this);
        final EditText editText_FileName = new EditText(EditActivity.this);
        editText_FileName.setSingleLine(true);
        nameDig.setTitle("ファイル名を入力してください（拡張子不要）");
        nameDig.setView(editText_FileName);
        nameDig.setPositiveButton("MakeFile", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String fileName;
                String fileName_user;

                fileName_user = editText_FileName.getText().toString();

//                String regex = "/.";
//                fileName_user = fileName_user.replaceAll(regex, "");
                Log.d("FileName", fileName_user);
                fileName_user = fileName_user.split("\\.")[0];
                Toast.makeText(EditActivity.this, fileName_user,Toast.LENGTH_LONG).show();


                fileName = fileName_user + "." + extension;

                Fragment fragment = BlankFragment.newInstance(fileName);
                fragmentArray.add(fragment);

                tabLayout.addTab(tabLayout.newTab().setText(fileName));
                viewPager.setAdapter(adapter);

                makeFile(fileName);

                int ArrySize = fragmentArray.size();
                TabLayout.Tab tab = tabLayout.getTabAt(ArrySize - 1);
                tab.select();
            }
        });
        nameDig.setNegativeButton("キャンセル", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        nameDig.create().show();

    }

    private void mkdir () {
        File mkdirPathName = getFilesDir();

    }

    private void makeFile(String fileName) {
        File newFile = new File(projectPath + "/" + fileName );
        try {
            if (!newFile.exists()) {
                newFile.createNewFile();
            }

        }catch (IOException e) {
            Log.d("MakeNewFile", e + "");
        }

    }
}

