package com.ubuntu.inschool.oji.webediter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;

import com.ubuntu.inschool.oji.webediter.Fragments.EditFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Created by oji on 16/08/20.
 */
public class EditorAdapter extends FragmentPagerAdapter {

    private FragmentManager manager;
    private FragmentTransaction transaction;
//    private ArrayList<EditFragment> fragments;
    private HashMap<Integer,String> fragmentTags;
    private String fileName, code;
    private Context context;

    public EditorAdapter(FragmentManager fragmentManager, Context context) {
        super(fragmentManager);
//        fragments = new ArrayList<EditFragment>();
        this.manager = fragmentManager;
        this.context = context;
        fragmentTags = new HashMap<Integer, String>();
    }

    @Override
    public int getCount() {
        return fragmentTags.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {

        String title = manager.findFragmentByTag(getTag(position)).getArguments().getString("title");
        return title;
    }

    @Override
    public Fragment getItem(int position) {
        return Fragment.instantiate(context,EditActivity.class.getName());
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

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Object object = (EditFragment)super.instantiateItem(container, position);

        if (object instanceof EditFragment) {
            EditFragment fragment = (EditFragment)object;
            String fragmentTag = fragment.getTag();
            fragmentTags.put(position, fragmentTag);
        }

        return (EditFragment)object;
    }

    public boolean remove(ViewPager pager, final int position) {
        Object object = this.instantiateItem(pager, position);
        if (object != null) {
            destroyItem(pager, position, object);
            fragmentTags.remove(getTag(position));
            return true;
        }
        return false;
    }

    public EditFragment getFragment(final int position) {
        return (EditFragment) manager.findFragmentByTag(getTag(position));
    }

    protected void addFragment(String projectPath, String fileName, final int extension){

        setCode(fileName, extension);

        EditFragment fragment = EditFragment.newInstance();
        Bundle args = new Bundle();
        args.putString("title", this.fileName);
        args.putString("projectPath", projectPath);
        args.putString("code", code);
        fragment.setArguments(args);

        fragmentTags.put(fragmentTags.size() + 1, fragment.getTag());
    }

    protected HashMap<Integer,String> setFragments() {
        return this.fragmentTags;
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

    private String getTag(final int position) {
        String tag = fragmentTags.get(position);
        if (tag == null || tag.equals("")) {
            return null;
        }
        return tag;
    }
}
