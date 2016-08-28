package com.ubuntu.inschool.oji.webediter.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;

import com.ubuntu.inschool.oji.webediter.EditActivity;
import com.ubuntu.inschool.oji.webediter.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PreveiwFragment extends Fragment {

    private WebView webView;
    private Button  updateButton;

    private String fileName;
    private String title;
    private final String urlHead = "file://";


    public PreveiwFragment() {
        // Required empty public constructor
    }

    public static PreveiwFragment newInstance() {
        PreveiwFragment fragment = new PreveiwFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_preveiw, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        webView         = (WebView)view.findViewById(R.id.webView);
        updateButton    = (Button)view.findViewById(R.id.updateButton);

        fileName = getArguments().getString("fileName");

        title = fileName.split("\\.")[0];

        View.OnClickListener updateOnClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        };

        updateButton.setOnClickListener(updateOnClick);

        update();

    }

    private void update() {
        String url = urlHead + EditActivity.projectPath  + "/" + fileName;
        webView.loadUrl(url);
    }
}
