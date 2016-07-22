package com.ubuntu.inschool.oji.webediter.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.ubuntu.inschool.oji.webediter.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HTMLFragment extends Fragment {


    public HTMLFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_html, container, false);
        EditText editText = (EditText)view.findViewById(R.id.HTMLeditText);

        return view;
    }

}
