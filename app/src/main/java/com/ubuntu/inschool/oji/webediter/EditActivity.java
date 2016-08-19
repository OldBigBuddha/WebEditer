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

import com.ubuntu.inschool.oji.webediter.Fragments.EditFragment;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class EditActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener{

    //ツールバー
    private Toolbar toolbar;

    //プロジェクト名
    private String projectName;
    //Fragmentを管理するためのArrayList
    private ArrayList<EditFragment> fragmentArray = new ArrayList<>();
    private ArrayList<String> fragmentIdArray = new ArrayList<>();
    //フラグメントアダプター
    private FragmentPagerAdapter adapter;


    //Tab関連
    private TabLayout tabLayout;
    private ViewPager viewPager;

    //アプリの保存場所(/data/data/com.ubuntu.inschool.oji.webediter/files)パス
    private String filePath;
    //ファイルの保存場所(filePath + / + プロジェクト名)パス
    public static String projectPath;
    //projectPathのFile型
    private File dateFilePath;

    //新規作成時のファイル名
    private String fileName;
    public static String fileName_user;

    //拡張子
    public static final int TYPE_HTML = 0;
    public static final int TYPE_CSS  = 1;
    public static final int TYPE_JS   = 2;

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

//        //初期ファイルの編集画面を作成
//        EditFragment fragmentHTML  = EditFragment.newInstance("index.html",TYPE_HTML);
//        EditFragment fragmentCSS   = EditFragment.newInstance("style.css",TYPE_CSS);
//
//        //画面追加を記録
//        fragmentArray.add(fragmentHTML);
////        fragmentHTML.save(projectPath,"index.html");
//        fragmentArray.add(fragmentCSS);
////        fragmentCSS.save(projectPath,"style.css");

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
                int size = fragmentArray.size();
//                Toast.makeText(EditActivity.this, size + "", Toast.LENGTH_LONG).show();
                return fragmentArray.size();
            }
        };


        //プロジェクト名取得用ダイアログを生成
        makeDialog_newProject();

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
                            switch (which) {
                                //HTMLファイル
                                case 0:
                                    EditActivity.this.makeDialog_newFile(TYPE_HTML);
                                    break;
                                //CSSファイル
                                case 1:
                                    EditActivity.this.makeDialog_newFile(TYPE_CSS);
                                    break;
                                //JavaScriptファイル
                                case 2:
                                    EditActivity.this.makeDialog_newFile(TYPE_JS);
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
                            makeFile("index", TYPE_HTML);
                            makeFile("style", TYPE_CSS);
                        }else {

                        }
                        setFileTreeOnNavigatinView();
                    }
                });

        alertDialog = ADBuilder.create();

        alertDialog.show();
    }

    //ファイル新規作成にファイル名及び種類を尋ねるダイアログの作成
    //引数:extension
    //ファイルの種類選別用・拡張子が入る
    private void makeDialog_newFile(final int extension) {

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
//                fileName = fileName_user.split("\\.")[0];
                Toast.makeText(EditActivity.this, fileName_user,Toast.LENGTH_LONG).show();

                switch (extension) {
                    case TYPE_HTML:
                        makeFile(fileName, TYPE_HTML);
                        break;

                    case TYPE_CSS:
                        makeFile(fileName, TYPE_CSS);
                        break;

                    case TYPE_JS:
                        makeFile(fileName, TYPE_JS);
                        break;
                }
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

    //ファイル新規作成
    //別クラスに移行予定
    private boolean makeFile(String fileName, final int extension) {

            //作成ファイルのパスの取得
//            File newFile = null;
//            String filePath = projectPath + "/" + fileName;
//            String filePath = null;
            String type = null;


            switch (extension) {
                case TYPE_HTML: {
                    type = ".html";
                    fileName = fileName + type;
//                    filePath = projectPath + "/" + fileName;
//                    newFile = new File(filePath);
                    break;
                }

                case TYPE_CSS: {
                    type = ".css";
                    fileName = fileName + type;
//                    filePath = projectPath + "/" + fileName;
//                    newFile = new File(filePath);
                    break;
                }

                case TYPE_JS: {
                    type = ".js";
                    fileName = fileName + type;
//                    filePath = projectPath + "/" + fileName;
//                    newFile = new File(filePath);
                    break;
                }
            }

            //Fragmentの生成
            EditFragment fragment = EditFragment.newInstance(projectPath,fileName, extension);
            fragmentArray.add(fragment);

            //Tabの生成
            adapter.notifyDataSetChanged();
            tabLayout.addTab(tabLayout.newTab().setText(fileName));
            viewPager.setAdapter(adapter);

            //新しく生成したタブを選択にする
            int selectTabPosition = fragmentArray.size() - 1;
            TabLayout.Tab tab = tabLayout.getTabAt(selectTabPosition);
            tab.select();

            //新規ファイルをNavigationViewのファイルツリーに反映
//            setFileTreeOnNavigatinView();

            //ファイル新規作成
//            return newFile.createNewFile();

//        return false;
    }

    //NavigationViewのtreeListにdataFilePath下のファイル一覧をセット
    private void setFileTreeOnNavigatinView() {
        listView = (ListView)findViewById(R.id.treeList);
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
    //TODO 別クラスに移行予定
//    private void saveCode(String fileName) {
//
//        try {
//
//            for (int i = 0; i < fragmentArray.size(); i++) {
//                //保存するファイルのフルパス取得
//                File makePath = new File(projectPath + "/" + fileName);
//
//                //ファイルの描きだし関係初期化
//                FileOutputStream fos = new FileOutputStream(makePath);
//                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));
//
//                //Fragment内のeditTextのText取得
//                EditText editText = fragmentArray.get(i).editText;
//                String code = editText.getText().toString();
//
//                //ファイルの書き出し
//                bw.write(code);
//                bw.flush();
//                bw.close();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}

