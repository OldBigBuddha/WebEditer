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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.ubuntu.inschool.oji.webediter.Fragments.BlankFragment;

import java.io.File;
import java.util.ArrayList;

public class EditActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener{


    AlertDialog alertDialog;
    AlertDialog.Builder ADBuilder;

    String projectName;

    TabLayout tabLayout;
    ViewPager viewPager;
    final String[] PAGE_TITLE = {"index.html", "style.css", "index.js"};
    ArrayList<String> page_title = new ArrayList<String>();
    Toolbar toolbar;

    File dateFilePath;

    FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
        @Override
        public Fragment getItem(int position) {
            return BlankFragment.newInstance(position + 1);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            CharSequence page_char = "Page" + position;
//                return PAGE_TITLE[position];
            return page_title.get(position);
        }

        @Override
        public int getCount() {
            return page_title.size();
        }
    };


    public EditActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        toolbar = (Toolbar)findViewById(R.id.tool_bar);
        tabLayout = (TabLayout)findViewById(R.id.tabs);
        viewPager = (ViewPager)findViewById(R.id.pager);

//        dateFilePath = new Context.getFilesDir();

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final View VIEW = EditActivity.this.getLayoutInflater().inflate(R.layout.context_main, null);


        ADBuilder = new AlertDialog.Builder(EditActivity.this);

        ADBuilder.setTitle("Project's Name")
                .setView(VIEW)
                .setPositiveButton("GO!!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText editText_ProjectName = (EditText)VIEW.findViewById(R.id.editText_ProjectName);
                        EditActivity.this.projectName = editText_ProjectName.getText().toString();
//                        super.onClick();
                    }
                });

        alertDialog = ADBuilder.create();
        alertDialog.show();

        tabLayout.addTab(tabLayout.newTab().setText("Tab 1"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 2"));
        tabLayout.addTab(tabLayout.newTab().setText("Tab 3"));

        page_title.add("index.html");
        page_title.add("style.css");
        page_title.add("index.js");

        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(this);

        tabLayout.setupWithViewPager(viewPager);

//        final String DPATH = "/storage/sdcard1/com.ubuntu.inschool.oji.webedite/";
//        final String FPATH = DPATH + projectName;
//        File newDir = new File(FPATH);
//
//        File htmlFile = new File(FPATH + "/index.html");
//        try {
//            if (htmlFile.createNewFile()) {
//                Log.d("NewFileLog", "OK");
//            } else {
//                Log.d("NewFileLog", "NOT");
//            }
//
//        }catch (IOException e) {
//            Log.d("IOException", e + "");
//        }
//
//        File cssFile = new File(FPATH + "/style.css");
//        try {
//            if (cssFile.createNewFile()) {
//                Log.d("NewFileLog", "OK");
//            } else {
//                Log.d("NewFileLog", "NOT");
//
//            }
//        }catch (IOException e) {
//            Log.d("IOException", e + "");
//        }


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

        final FragmentPagerAdapter adapter_addTab = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return BlankFragment.newInstance(position);
            }

            @Override
            public int getCount() {
                return 0;
            }
        };

        final CharSequence[] ITEMS = {"HTML","CSS","JavaScript"};
        AlertDialog.Builder addDig = new AlertDialog.Builder(EditActivity.this);
        addDig.setTitle("ファイル形式を選択してください");
        addDig.setItems(ITEMS, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (which) {
                    case 0:
                        EditActivity.this.makeDialog();

//                        tabLayout.addTab(tabLayout.newTab().setText("HTMLFile") );
//
//                        viewPager.setAdapter(adapter);
//                        viewPager.addOnPageChangeListener(EditActivity.this);
//                        tabLayout.setupWithViewPager(viewPager);
                        break;
                    case 1:
                        tabLayout.addTab(tabLayout.newTab().setText("CSSFile"));
                        break;
                    case 2:
                        tabLayout.addTab(tabLayout.newTab().setText("JavaScriptFile"));
                        break;
                }



//
//                tabLayout.addTab(tabLayout.newTab().setText("TAB_NEW"));

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

    public void makeDialog() {
        AlertDialog.Builder nameDig = new AlertDialog.Builder(EditActivity.this);
        final EditText editText_FileName = new EditText(EditActivity.this);
        nameDig.setTitle("ファイル名を入力してください（拡張子必須）");
        nameDig.setView(editText_FileName);
        nameDig.setPositiveButton("MakeFile", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String fileName;
                fileName = editText_FileName.getText().toString();
                page_title.add(fileName);

                tabLayout.addTab(tabLayout.newTab().setText(fileName) );
                viewPager.setAdapter(adapter);
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
}
