package com.ubuntu.inschool.oji.webediter;

import android.content.DialogInterface;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
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
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;

public class EditActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener{

    //ツールバー
    private Toolbar toolbar;

    //プロジェクト名
    private String projectName;
    //Fragmentを管理するためのArrayList
    private ArrayList<BlankFragment> fragmentArray = new ArrayList<>();
    //フラグメントアダプター
    private FragmentPagerAdapter adapter;


    //Tab関連
    private TabLayout tabLayout;
    private ViewPager viewPager;

    //アプリの保存場所(/data/data/com.ubuntu.inschool.oji.webediter/files)パス
    private String filePath;
    //ファイルの保存場所(filePath + / + プロジェクト名)パス
    private String projectPath;
    //projectPathのFile型
    private File dateFilePath;

    //新規作成時のファイル名
    private String fileName;
    public static String fileName_user;

    //NavigationViewにファイル一覧を表示させるための変数
    //ファイル名一覧
    private ArrayList<String> fileNameList;
    //listViewにfileNameListをセットするためのAdapter
    private ArrayAdapter<String> arrayAdapter;
    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        //関連付け
        toolbar = (Toolbar)findViewById(R.id.tool_bar);
        tabLayout = (TabLayout)findViewById(R.id.tabs);
        viewPager = (ViewPager)findViewById(R.id.pager);

        //ToolbarをActionbarの代わりにセット
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //プロジェクト名取得用ダイアログを生成
        makeDialog_newProject();

        //初期ファイルの編集画面を作成
        BlankFragment fragmentHTML  = BlankFragment.newInstance("index.html","html");
        BlankFragment fragmentCSS   = BlankFragment.newInstance("style.css","css");

        //画面追加を記録
        fragmentArray.add(fragmentHTML);
        fragmentArray.add(fragmentCSS);

        //FragmentAdapterの初期化
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

        //viewPagerにadapterをセット
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(this);

        //FragmentをTabLayoutで表示
        tabLayout.setupWithViewPager(viewPager);
    }

    //Toolbarのメニュー定義
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_menu, menu);

        //メニューの中身を設定
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();

                //Tabの追加及びファイルの新規作成
                if (id == R.id.new_tab) {

                    //新規作成ダイアログの作成
                    final CharSequence[] ITEMS = {"HTML","CSS","JavaScript"};
                    AlertDialog.Builder addDig = new AlertDialog.Builder(EditActivity.this);
                    addDig.setTitle("ファイル形式を選択してください");
                    addDig.setItems(ITEMS, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            //作成するファイルの種類を選択
                            //種類選別用変数・拡張子が入る
                            String extension;
                            switch (which) {
                                //HTMLファイル
                                case 0:
                                    extension = "html";
                                    EditActivity.this.makeDialog_newFile(extension);
                                    break;
                                //CSSファイル
                                case 1:
                                    extension = "css";
                                    EditActivity.this.makeDialog_newFile(extension);
                                    break;
                                //JavaScriptファイル
                                case 2:
                                    extension = "js";
                                    EditActivity.this.makeDialog_newFile(extension);
                                    break;
                            }

                        }
                    });

                    addDig.create().show();
                    return true;

                } else
                //保存
                if (id == R.id.save_tab) {

                    for (int i = 0; i < fragmentArray.size(); i++) {
                        saveCode(fragmentArray.get(i).title);

                    }

                }

                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    /*  ↓ViewPager.OnPageChangeListenerをimplementsしたため必要なメソッド群↓   */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
    /*  ↑ViewPager.OnPageChangeListenerをimplementsしたため必要なメソッド群↑   */

    //ファイル新規作成にファイル名及び種類を尋ねるダイアログの作成
    //引数:extension
    //ファイルの種類選別用・拡張子が入る
    private void makeDialog_newFile(final String extension) {

        //ダイアログの生成
        AlertDialog.Builder nameDig = new AlertDialog.Builder(EditActivity.this);
        //ダイアログ上でのファイル名入力欄
        final EditText editText_FileName = new EditText(EditActivity.this);
        editText_FileName.setSingleLine(true);
        nameDig.setTitle("ファイル名を入力してください（拡張子不要）");
        nameDig.setView(editText_FileName);
        //MakeFileが押された際の動作
        nameDig.setPositiveButton("MakeFile", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //ファイル名の取得
                fileName_user = editText_FileName.getText().toString();

                //取得したユーザ希望のファイル名に拡張子が入っていた場合除去
                //なおこの操作によりファイル名に"."を入れるとそれ以降は全てカットされる
                fileName_user = fileName_user.split("\\.")[0];
                Toast.makeText(EditActivity.this, fileName_user,Toast.LENGTH_LONG).show();

                //カット済みの取得名に拡張をつけ、ファイル名に
                fileName = fileName_user + "." + extension;

                //Fragmentの生成
                BlankFragment fragment = BlankFragment.newInstance(fileName, extension);
                fragmentArray.add(fragment);

                //Tabの生成
                tabLayout.addTab(tabLayout.newTab().setText(fileName));
                viewPager.setAdapter(adapter);

                //新規ファイルの作成
                makeFile(fileName);

                //新しく生成したタブを選択にする
                int selectTabPosition = fragmentArray.size() - 1;
                TabLayout.Tab tab = tabLayout.getTabAt(selectTabPosition);
                tab.select();

                //新規ファイルをNavigationViewのファイルツリーに反映
                setFileTreeOnNavigatinView();
            }
        });

        //キャンセルを押された時の動作
        nameDig.setNegativeButton("キャンセル", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        nameDig.create().show();

    }

    //新規プロジェクト名を尋ねるダイアログの生成
    private void makeDialog_newProject() {

        AlertDialog alertDialog;
        AlertDialog.Builder ADBuilder = new AlertDialog.Builder(EditActivity.this);
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
                        }else {

                        }
                        setFileTreeOnNavigatinView();
                    }
                });

        alertDialog = ADBuilder.create();

        alertDialog.show();
    }

    //ファイル新規作成
    //別クラスに移行予定
    private void makeFile(String fileName) {
        //作成ファイルのパスの取得
        File newFile = new File(projectPath + "/" + fileName );
        try {
            //取得したファイル名が存在しているか検証
            if (!newFile.exists()) {
                //ファイル新規作成
                newFile.createNewFile();
//                saveCode(fileName);
            }else {
//                saveCode(fileName);
            }
        }catch (IOException e) {
            Log.d("MakeNewFile", e + "");
        }

    }

    //NavigationViewのtreeListにdataFilePath下のファイル一覧をセット
    private void setFileTreeOnNavigatinView() {
        listView = (ListView)findViewById(R.id.treelist);
        TextView textView = (TextView)findViewById(R.id.navigation_textView);
        textView.setText(projectName);
        fileNameList = new ArrayList(Arrays.asList(dateFilePath.list()));

        //要素長押しで選択したファイルを削除
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                //Adapterの取得
                arrayAdapter = (ArrayAdapter<String>)listView.getAdapter();

                //削除するファイル名及びフルパス取得
                String positingFileName = fileNameList.get(position);
                File deleteFile = new File(dateFilePath.toString() + "/" + positingFileName);
                //ファイル削除
                deleteFile.delete();

                //削除したファイルに関連するFragment及びTabの削除及び更新
                fragmentArray.remove(position);
                adapter.notifyDataSetChanged();
                fileNameList.remove(position);
                arrayAdapter.notifyDataSetChanged();

                return false;
            }
        });

        //ファイルリストをセット
        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_expandable_list_item_1,fileNameList);
        listView.setAdapter(arrayAdapter);

    }

    //中身の保存
    //別クラスに移行予定
    private void saveCode(String fileName) {

        try {

            for (int i = 0; i < fragmentArray.size(); i++) {
                //保存するファイルのフルパス取得
                File makePath = new File(projectPath + "/" + fileName);

                //ファイルの描きだし関係初期化
                FileOutputStream fos = new FileOutputStream(makePath);
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));

                //Fragment内のeditTextのText取得
                EditText editText = fragmentArray.get(i).editText;
                String code = editText.getText().toString();

                //ファイルの書き出し
                bw.write(code);
                bw.flush();
                bw.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

