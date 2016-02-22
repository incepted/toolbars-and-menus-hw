package com.example.envy.toolbars_and_menus_hw;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Envy on 2/22/2016.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumofTabs) {
        super(fm);
        this.mNumOfTabs = NumofTabs;
    }

    public Fragment getItem(int position){

       switch(position) {
           case 0:
               AboutFragment tab1 = new AboutFragment();
               return tab1;

           case 1:
               NextFragment tab2 = new NextFragment();
               return tab2;

           case 2:
               RelatedFragment tab3 = new RelatedFragment();
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
