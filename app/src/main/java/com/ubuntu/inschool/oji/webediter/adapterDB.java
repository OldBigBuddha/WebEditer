package com.ubuntu.inschool.oji.webediter;

import android.support.v4.app.FragmentPagerAdapter;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by oji on 16/08/28.
 */
@Table(name = "adapter_table")
public class adapterDB {

    @Column(name = "adapter")
    public FragmentPagerAdapter adapter;

    public adapterDB () {
        super();
    }

    public adapterDB (FragmentPagerAdapter fragmentPagerAdapter) {
        this.adapter = fragmentPagerAdapter;
    }
}
