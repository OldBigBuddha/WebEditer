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

    public String title;
    public int extension;
    public String code;
    public EditText editText;
    public Button btTab, btH, btP, btDiv;
    public String titleInHTML;
    public String projectPath;
    public String filePath;

    public int start, end;
    public int moveCount = 0;
    public String setText;
    public Editable editable;

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

        editText = (EditText)view.findViewById(R.id.editText);
        btTab    = (Button)  view.findViewById(R.id.tabButton);
        btDiv    = (Button)  view.findViewById(R.id.divButton);
        btP      = (Button)  view.findViewById(R.id.pButton);
        btH      = (Button)  view.findViewById(R.id.hButton);
        editText    = (EditText)view.findViewById(R.id.editText);
        projectPath = EditActivity.projectPath;
        title       = getArguments().getString("title");
        extension   = getArguments().getInt("extension");

        btTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTabOnClick();
            }
        });
        btH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setHOnClick();
            }
        });
        btP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPOnClick();
            }
        });
        btDiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDivOnClick();
            }
        });


        filePath = projectPath + "/" + title;
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

    public void setText(String setText) {

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

    public void setTabOnClick() {
        setText = "\t";
        setText(setText);
    }

    public void setHOnClick() {}

    public void setPOnClick() {}

    public void setDivOnClick() {}

}