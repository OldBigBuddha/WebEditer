package com.ubuntu.inschool.oji.webediter;

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
public class EditorFragmentAdapter extends FragmentPagerAdapter {

    private ArrayList<EditFragment> fragments = new ArrayList<>();

    public EditorFragmentAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
//        this.fragments = fragments;
    }

    @Override
    public int getCount() {
        getFragments();
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        getFragments();
        String title = fragments.get(position).getArguments().getString("title");
        return title;
    }

    @Override
    public Fragment getItem(int position) {
        getFragments();
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

    private void getFragments() {
        this.fragments = EditActivity.fragmentArray;
    }
}
