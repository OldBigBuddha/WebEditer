package com.ubuntu.inschool.oji.webediter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;

import com.ubuntu.inschool.oji.webediter.Fragments.EditFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oji on 16/08/20.
 */
public class EditorAdapter extends FragmentPagerAdapter {

    private FragmentManager manager;
    private FragmentTransaction transaction;
    private ArrayList<EditFragment> fragments;
    private String fileName, code;

    public EditorAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        fragments = new ArrayList<EditFragment>();
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = fragments.get(position).getArguments().getString("title");
        return title;
    }

    @Override
    public Fragment getItem(int position) {
        return this.fragments.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container,final int position, Object object) {
        super.destroyItem(container, position, object);

        if (position <= getCount()) {
            FragmentManager fm = ((Fragment) object).getFragmentManager();
            FragmentTransaction fTransaction = fm.beginTransaction();
            fTransaction.remove((Fragment) object);
            fTransaction.commit();
        }
    }

    public void remove(ViewPager pager, final int position) {
        Object object = this.instantiateItem(pager, position);
        if (object != null) {
            destroyItem(pager, position, object);
        }
    }

    protected void addFragment(String projectPath, String fileName, final int extension){

        setCode(fileName, extension);

        EditFragment fragment = EditFragment.newInstance();
        Bundle args = new Bundle();
        args.putString("title", this.fileName);
        args.putString("projectPath", projectPath);
        args.putString("code", code);
        fragment.setArguments(args);

        fragments.add(fragment);
    }

    protected ArrayList<EditFragment> setFragments() {
        return this.fragments;
    }

    private void setCode(String fileName, final int extension) {

        String code = "";

        switch (extension) {
            case EditActivity.TYPE_HTML:
                code = "<html>\n" +
                        "\t<head>\n" +
                        "\t\t<title> " + fileName + "</title>\n" +
                        "\t\t<meta charset=\"utf-8\">\n" +
                        "\t\t<link rel=\"stylesheet\" type=\"text/css\" href=\"./index.css\">\n" +
                        "\t\t<script type=\"text/javascript\" src=\".index.js\"></script>\n" +
                        "\t</head>\n" +
                        "\t<body>\n" +
                        "\t\t<h1>HelloWorld</h1>\n" +
                        "\t</body>\n" +
                        "<html>\n";
                fileName = fileName + ".html";
                break;
            case EditActivity.TYPE_CSS:
                code = "h1 {\n" +
                        "\tcolor: blue;\n" +
                        "}\n";
                fileName = fileName + ".css";
                break;
            case EditActivity.TYPE_JS:
                code = "alert(\"HelloWorld\")";
                fileName = fileName + ".js";
                break;
            default:
                code = "";
                fileName = fileName + ".txt";
        }
        this.code = code;
        this.fileName = fileName;
    }
}
