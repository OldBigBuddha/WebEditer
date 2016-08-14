package com.ubuntu.inschool.oji.webediter.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.ubuntu.inschool.oji.webediter.EditActivity;
import com.ubuntu.inschool.oji.webediter.R;

public class BlankFragment extends Fragment {

    public static String title;
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
        View view = inflater.inflate(R.layout.fragment_blank, container, false);
        String extension;
        this.title = getArguments().getString("title");
        extension = getArguments().getString("extension");
        editText = (EditText) view.findViewById(R.id.editText);

        String fileName = EditActivity.fileName_user;

        if (fileName == null) {
            title = "index";
        } else {
            title = title.split("\\.")[0];
        }

        if (extension == null) {
            Log.d("error", "fileExtensionNull");
        } else switch (extension) {
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
        editText.setText(code);

        return view;
    }
}