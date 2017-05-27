package com.example.kabhishek1.musicv1;

/**
 * Created by r.vivek on 3/29/2017.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

class PagerAdapter extends FragmentStatePagerAdapter {
   int mNumOfTabs;

   public PagerAdapter(FragmentManager fm, int NumOfTabs) {
       super(fm);
       this.mNumOfTabs = NumOfTabs;
   }

   @Override
   public Fragment getItem(int position) {

       switch (position) {
           case 0:
               musicTab1 tab1 = new musicTab1();
               return tab1;
           case 1:
              musicTab2 tab2 = new musicTab2();
               return tab2;
           case 2:
               musicTab3 tab3 = new musicTab3();
               return tab3;

           default:
               return null;
       }
   }

   @Override
   public int getCount() {
       return mNumOfTabs;
   }
}
