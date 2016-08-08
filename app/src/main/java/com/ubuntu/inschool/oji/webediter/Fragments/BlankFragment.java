package com.ubuntu.inschool.oji.webediter.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.ubuntu.inschool.oji.webediter.R;

public class BlankFragment extends Fragment {

    public String title;

    public BlankFragment() {
        // Required empty public constructor
    }

    public static BlankFragment newInstance() {
        BlankFragment fragment = new BlankFragment();
        Bundle args = new Bundle();
//        args.putInt(ARG_PARAM, page);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        int page = getArguments().getInt(ARG_PARAM, 0);
        View view = inflater.inflate(R.layout.fragment_blank, container, false);
        EditText editText = (EditText) view.findViewById(R.id.editText);
//        TextView textView = (TextView)view.findViewById(R.id.editText);
//        textView.setText("Page" + page);
//        editText.setText("Page" + (page + 1));
        editText.setText("test");

        return view;
    }
}