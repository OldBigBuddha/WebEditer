package com.ubuntu.inschool.oji.webediter.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.ubuntu.inschool.oji.webediter.EditActivity;
import com.ubuntu.inschool.oji.webediter.FileManager;
import com.ubuntu.inschool.oji.webediter.R;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;

public class EditFragment extends Fragment {

    private String title;
//    private String fileName;
    public int extension;
    public String code;
    public EditText editText;
    private String titleInHTML;
    private String projectPath;
    private String filePath;

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

        editText    = (EditText) view.findViewById(R.id.editText);
        projectPath = EditActivity.projectPath;
        title       = getArguments().getString("title");
        extension   = getArguments().getInt("extension");

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
}