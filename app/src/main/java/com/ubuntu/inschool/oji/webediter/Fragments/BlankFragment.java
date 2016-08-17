package com.ubuntu.inschool.oji.webediter.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.ubuntu.inschool.oji.webediter.EditActivity;
import com.ubuntu.inschool.oji.webediter.FileManager;
import com.ubuntu.inschool.oji.webediter.R;

public class BlankFragment extends Fragment {

    public static String title;
    private String extension;
    protected  String code;
    public static EditText editText;

    public BlankFragment() {
    }


    public static BlankFragment newInstance(String title, String extension) {
        BlankFragment fragment = new BlankFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("extension", extension);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View VIEW = inflater.inflate(R.layout.fragment_blank, container, false);
        return VIEW;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editText = (EditText) view.findViewById(R.id.editText);
        this.title = getArguments().getString("title");
        this.extension = getArguments().getString("extension");

        String fileName = EditActivity.fileName_user;

        if (fileName == null) {
            title = "index";
        } else {
            this.title = this.title.split("\\.")[0];
        }

        if (this.extension == null) {
            Log.d("error", "fileExtensionNull");
        } else switch (this.extension) {
            case "html":
                BlankFragment.this.code = "<html>\n" +
                        "\t<head>\n" +
                        "\t\t<title> " + title + "</title>\n" +
                        "\t\t<meta charset=\"utf-8\">\n" +
                        "\t\t<link rel=\"stylesheet\" type=\"text/css\" href=\"./index.css\">\n" +
                        "\t\t<script type=\"text/javascript\" src=\".index.js\"></script>\n" +
                        "\t</head>\n" +
                        "\t<body>\n" +
                        "\t\t<h1>HelloWorld</h1>\n" +
                        "\t</body>\n" +
                        "<html>\n";
                break;
            case "css":
                BlankFragment.this.code = "h1 {\n" +
                        "\tcolor: blue;\n" +
                        "}\n";
                break;
            case "js":
                BlankFragment.this.code = "alert(\"HelloWorld\")";
                break;
        }
        editText.setText(this.code);

    }


    public void save(String projectPath, String fileName) {
        String context = this.editText.getText().toString();
        FileManager fileManager = new FileManager(projectPath, fileName, context);
        fileManager.savaCode();
    }
}