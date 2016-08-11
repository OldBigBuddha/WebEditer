package com.ubuntu.inschool.oji.webediter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ubuntu.inschool.oji.webediter.Fragments.BlankFragment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener{

    AlertDialog alertDialog;
    AlertDialog.Builder ADBuilder;

    String projectName;
    ArrayList<Fragment> fragmentArray = new ArrayList<>();

    TabLayout tabLayout;
    ViewPager viewPager;
    Toolbar toolbar;

    File dateFilePath;
    String filePath;
    String projectPath;

    FragmentPagerAdapter adapter;

    String fileName;
    public static String fileName_user;

    ArrayList<String> arrayList;
    ArrayAdapter<String> arrayadapter;
    ListView listView;


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

                        setNavigationFiletree();

                    }
                });

        alertDialog = ADBuilder.create();

        alertDialog.show();


        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        BlankFragment fragmentHTML  = BlankFragment.newInstance("index.html","html");
        BlankFragment fragmentCSS   = BlankFragment.newInstance("style.css","css");
        BlankFragment fragmentJS    = BlankFragment.newInstance("index.js","js");

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

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.new_tab) {

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

//                    Toast.makeText(MainActivity.this,"search click!!",Toast.LENGTH_LONG).show();
                    return true;
                }

                return true;
            }
        });

//        return true;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        final int ID = item.getItemId();

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

                fileName_user = editText_FileName.getText().toString();

                Log.d("FileName", fileName_user);
                fileName_user = fileName_user.split("\\.")[0];
                Toast.makeText(EditActivity.this, fileName_user,Toast.LENGTH_LONG).show();


                fileName = fileName_user + "." + extension;

                Fragment fragment = BlankFragment.newInstance(fileName, extension);
                fragmentArray.add(fragment);

                tabLayout.addTab(tabLayout.newTab().setText(fileName));
                viewPager.setAdapter(adapter);

                makeFile(fileName);

                int ArrySize = fragmentArray.size();
                TabLayout.Tab tab = tabLayout.getTabAt(ArrySize - 1);
                tab.select();

                setNavigationFiletree();
            }
        });
        nameDig.setNegativeButton("キャンセル", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        nameDig.create().show();

    }

    private void makeFile(String fileName) {
        File newFile = new File(projectPath + "/" + fileName );
        try {
            if (!newFile.exists()) {
                newFile.createNewFile();
                EditText editText = (EditText)findViewById(R.id.editText);
                saveCode(fileName, editText.getText().toString());
            }
        }catch (IOException e) {
            Log.d("MakeNewFile", e + "");
        }

    }

    private void setNavigationFiletree() {
        listView = (ListView)findViewById(R.id.treelist);
        TextView textView = (TextView)findViewById(R.id.navigation_textView);
        textView.setText(projectName);
        arrayList = new ArrayList(Arrays.asList(dateFilePath.list()));

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                ListView pointingList = (ListView)parent;
                String item = (String)pointingList.getItemAtPosition(position);
                arrayadapter = (ArrayAdapter<String>)listView.getAdapter();


                String positingFileName = arrayList.get(position);
                File deleteFile = new File(dateFilePath.toString() + "/" + positingFileName);
                deleteFile.delete();

                fragmentArray.remove(position);
                adapter.notifyDataSetChanged();
//                tabLayout.removeTabAt(position);
                arrayList.remove(position);
                arrayadapter.notifyDataSetChanged();

                return false;
            }
        });

        arrayadapter = new ArrayAdapter(this,android.R.layout.simple_expandable_list_item_1,arrayList);
        listView.setAdapter(arrayadapter);

    }

    private void saveCode(String fileName, String text) {
        try {

            FileOutputStream fos = new FileOutputStream(new File(projectPath + "/" + fileName));
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));

            //追記する
            bw.write(text);
            bw.flush();
            bw.close();
        } catch (IOException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }

    }

}

