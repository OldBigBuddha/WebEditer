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

import java.io.File;

public class EditFragment extends Fragment {

    public static String title;
//    public int extension;
    public String code;
    public EditText editText;
//    private String titleInHTML;
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
//        container.removeAllViews();
        final View VIEW = inflater.inflate(R.layout.fragment_blank, container, false);
        return VIEW;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editText = (EditText) view.findViewById(R.id.editText);
        projectPath = getArguments().getString("projectPath");
        title = getArguments().getString("title");
        code = getArguments().getString("code");

        filePath = projectPath + "/" + title;

        editText.setText(code);
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

}