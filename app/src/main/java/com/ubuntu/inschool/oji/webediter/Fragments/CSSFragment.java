package com.ubuntu.inschool.oji.webediter.Fragments;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.menu.ActionMenuItemView;
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
public class CSSFragment extends EditFragment {

    private EditText  editText;

    public CSSFragment() {
        // Required empty public constructor
    }

    public static CSSFragment newInstance() {
        return new CSSFragment();
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
        code = "h1 {\n" +
                "\tcolor: blue;\n" +
                "}\n";

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
                        setText = "h" + hSize + "{\n" +
                                "\t\n" +
                                "}\n";
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
        setText = "p {\n" +
                "\t\n" +
                "}\n";
        setText(setText);
    }

    @Override
    public void setDivOnClick() {
        setText = "div {\n" +
                "\t\n" +
                "}\n";
        setText(setText);
    }


}
