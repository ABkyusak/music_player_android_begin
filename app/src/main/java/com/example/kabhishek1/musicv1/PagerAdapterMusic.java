package com.example.kabhishek1.musicv1;

/**
 * Created by k.abhishek1 on 19-05-2017.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by r.vivek on 3/29/2017.
 */

class PagerAdapterMusic extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapterMusic(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                musicPlayerTab1 tab1 = new musicPlayerTab1();
                return tab1;
            case 1:
                musicPlayerTab2 tab2 = new musicPlayerTab2();
                return tab2;


            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
