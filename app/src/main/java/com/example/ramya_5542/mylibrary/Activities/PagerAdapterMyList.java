package com.example.ramya_5542.mylibrary.Activities;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapterMyList extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapterMyList(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
               FavoritesFragment tab1 = new FavoritesFragment();
                return tab1;
            case 1:
                ReadingFragment  tab2 = new ReadingFragment();
                return tab2;
            case 2:
                WishListFragment  tab3 = new WishListFragment();
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