package com.ubuntu.inschool.oji.webediter.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ubuntu.inschool.oji.webediter.R;

/**
 * Created by oji on 16/08/12.
 */
public class WebFragment implements Fragment {

    public WebFragment () {

    }

    public static WebFragment newInstance() {
        WebFragment webFragment = WebFragment();
        return webFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_web,container,false);

        return view;
    }


}
