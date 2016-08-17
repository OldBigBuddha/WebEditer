package com.ubuntu.inschool.oji.webediter.Fragments;

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

public class EditFragment extends Fragment {

    public static String title;
    public int extension;
    public String code;
    public EditText editText;
    private String titleInHTML;


    public EditFragment() {
    }


    public static EditFragment newInstance(String title, final int extension) {
        EditFragment fragment = new EditFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putInt("extension", extension);
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
        this.extension = getArguments().getInt("extension");

        String fileName = EditActivity.fileName_user;

        if (fileName == null) {
            title = "index";
        } else {
            titleInHTML = this.title.split("\\.")[0];
        }

        switch (this.extension) {
            case EditActivity.TYPE_HTML:
                EditFragment.this.code = "<html>\n" +
                        "\t<head>\n" +
                        "\t\t<title> " + titleInHTML + "</title>\n" +
                        "\t\t<meta charset=\"utf-8\">\n" +
                        "\t\t<link rel=\"stylesheet\" type=\"text/css\" href=\"./index.css\">\n" +
                        "\t\t<script type=\"text/javascript\" src=\".index.js\"></script>\n" +
                        "\t</head>\n" +
                        "\t<body>\n" +
                        "\t\t<h1>HelloWorld</h1>\n" +
                        "\t</body>\n" +
                        "<html>\n";
                break;
            case EditActivity.TYPE_CSS:
                EditFragment.this.code = "h1 {\n" +
                        "\tcolor: blue;\n" +
                        "}\n";
                break;
            case EditActivity.TYPE_JS:
                EditFragment.this.code = "alert(\"HelloWorld\")";
                break;
        }
        editText.setText(this.code);
        save(this.title);

    }


    public void save(String fileName) {
        String context = this.editText.getText().toString();
        FileManager fileManager = new FileManager(EditActivity.projectPath, fileName, context);
        fileManager.savaCode();
    }
}