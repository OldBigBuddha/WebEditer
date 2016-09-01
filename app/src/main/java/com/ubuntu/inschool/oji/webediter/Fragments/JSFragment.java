package com.ubuntu.inschool.oji.webediter.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ubuntu.inschool.oji.webediter.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class JSFragment extends EditFragment {


    public JSFragment() {
        // Required empty public constructor
    }

    public static JSFragment newInstance() {
        return new JSFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        code = "alert(\"HelloWorld\")";

        setCode(code);
        save();
    }


    @Override
    public void setHOnClick() {

    }

    @Override
    public void setPOnClick() {

    }

    @Override
    public void setDivOnClick() {

    }

}
