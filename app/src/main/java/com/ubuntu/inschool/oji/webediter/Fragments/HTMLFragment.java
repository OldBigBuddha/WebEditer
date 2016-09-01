package com.ubuntu.inschool.oji.webediter.Fragments;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.ubuntu.inschool.oji.webediter.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HTMLFragment extends EditFragment {

    public HTMLFragment() {
        // Required empty public constructor
    }

    public static HTMLFragment newInstance() {
        HTMLFragment fragment = new HTMLFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (title == null) {
            title = "index";
        } else {
            titleInHTML = title.split("\\.")[0];
        }

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

        setCode(code);
        save();

    }

    @Override
    public void setHOnClick() {
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

    @Override
    public void setPOnClick() {
        setText   = "<p></p>";
        moveCount = 3;
        setText(setText);
    }

    @Override
    public void setDivOnClick() {
        setText   = "<div></div>";
        moveCount = 5;
        setText(setText);
    }
}
