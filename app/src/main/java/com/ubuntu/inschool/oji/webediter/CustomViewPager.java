package com.ubuntu.inschool.oji.webediter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ubuntu.inschool.oji.webediter.Fragments.BlankFragment;

/**
 * Created by oji on 16/08/11.
 */
public class CustomViewPager extends FragmentPagerAdapter {

    public CustomViewPager (FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        BlankFragment fragment = new BlankFragment();

        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }
}
