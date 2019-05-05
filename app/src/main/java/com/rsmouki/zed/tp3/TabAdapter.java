package com.rsmouki.zed.tp3;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by abdellahrs on 31/10/18.
 */
public class TabAdapter extends FragmentPagerAdapter {
    int numoftabs;

    public TabAdapter(FragmentManager fm, int mnumoftabs) {
        super(fm);
        this.numoftabs = mnumoftabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                fragments.welcomepage tab1 = new fragments.welcomepage();
                return tab1;

            case 1:
                fragments.homepage tab2 = new fragments.homepage();
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numoftabs;
    }
}

