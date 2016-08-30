package com.ubuntu.inschool.oji.webediter.Fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.ubuntu.inschool.oji.webediter.EditActivity;
import com.ubuntu.inschool.oji.webediter.FileManager;
import com.ubuntu.inschool.oji.webediter.R;

import java.io.File;
/**
* アプリ甲子園提出用アプリ 【はとまる】
*/

public class EditFragment extends Fragment {

    private String title;
    public int extension;
    public String code;
    private EditText editText;
    private Button tabButton, pButton, divButton, hButton;
    private String titleInHTML;
    private String projectPath;
    private String filePath;

    private int start, end;
    private int moveCount = 0;
    private String setText;
    private Editable editable;

    public EditFragment() {
    }

    public static EditFragment newInstance() {
        EditFragment fragment = new EditFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View VIEW = inflater.inflate(R.layout.fragment_edit, container, false);
        return VIEW;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editText    = (EditText)view.findViewById(R.id.editText);
        tabButton   = (Button)  view.findViewById(R.id.tabButton);
        pButton     = (Button)  view.findViewById(R.id.pButton);
        divButton   = (Button)  view.findViewById(R.id.divButton);
        hButton     = (Button)  view.findViewById(R.id.hButton);
        projectPath = EditActivity.projectPath;
        title       = getArguments().getString("title");
        extension   = getArguments().getInt("extension");

        tabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setText = "\t";
                setText(setText);
            }
        });

        pButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setText   = "<p></p>";
                moveCount = 3;
                setText(setText);
            }
        });

        divButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setText   = "<div></div>";
                moveCount = 5;
                setText(setText);
            }
        });

        hButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListView listView = new ListView(getContext());
                AlertDialog alertDialog = null;
                AlertDialog.Builder ADBuilder = new AlertDialog.Builder(getContext());

                final CharSequence[] ITEMS = {"1","2","3","4","5","6"};
                ADBuilder.setTitle("選択してください")
                        .setView(listView)
                        .setItems(ITEMS, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int hSize = which + 1;
                                setText = "<h" + hSize + "></h" + hSize + ">";
                                moveCount = 4;
                                setText(setText);
                            }
                        });
                ADBuilder.setNegativeButton("キャンセル", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                alertDialog = ADBuilder.create();
                alertDialog.show();

            }
        });


        filePath = projectPath + "/" + title;


        if (this.title == null) {
            this.title = "index";
        } else {
            titleInHTML = title.split("\\.")[0];
        }

        switch (this.extension) {
            case EditActivity.TYPE_HTML:
                code = "<html>\n" +
                        "\t<head>\n" +
                        "\t\t<title>" + titleInHTML + "</title>\n" +
                        "\t\t<meta charset=\"utf-8\">\n" +
                        "\t\t<link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\">\n" +
                        "\t\t<script type=\"text/javascript\" src=\".index.js\"></script>\n" +
                        "\t</head>\n" +
                        "\t<body>\n" +
                        "\t\t<h1>HelloWorld</h1>\n" +
                        "\t</body>\n" +
                        "<html>\n";
                break;
            case EditActivity.TYPE_CSS:
                code = "h1 {\n" +
                        "\tcolor: blue;\n" +
                        "}\n";
                break;
            case EditActivity.TYPE_JS:
                code = "alert(\"HelloWorld\")";
                break;
        }
        setCode(code);
        save();

    }

    @Override
    public void onPause() {
        super.onPause();
        code = editText.getText().toString();
        save();
    }

    public void save() {
        String fileCode = editText.getText().toString();
        File writePath = new File(filePath);
        FileManager fileManager = new FileManager(projectPath, title);
        if (!writePath.exists()) {
            fileManager.createFile();
        }
        fileManager.setFileCode(fileCode);
        fileManager.savaCode();
    }

    public void setCode(String code) {
        editText.setText(code);
    }

    private void setText(String setText) {

        start = editText.getSelectionStart();
        end   = editText.getSelectionEnd();
        editable = editText.getText();


        editable.replace(Math.min(start, end), Math.max(start, end), setText);

        if (moveCount > 0) {
            int position = start + moveCount;
            editText.setSelection(position);
        }

        moveCount = 0;
    }
}