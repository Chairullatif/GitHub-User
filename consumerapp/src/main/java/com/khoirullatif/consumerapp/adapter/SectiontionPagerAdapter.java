package com.khoirullatif.consumerapp.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.khoirullatif.consumerapp.R;
import com.khoirullatif.consumerapp.fragment.FollowerFragment;
import com.khoirullatif.consumerapp.fragment.FollowingFragment;

public class SectiontionPagerAdapter extends FragmentPagerAdapter {

    private final Context mContext;
    public String usernameUser;

    public SectiontionPagerAdapter(Context context, FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mContext = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = FollowerFragment.newInstance(usernameUser);
                break;
            case 1:
                fragment = FollowingFragment.newInstance(usernameUser);
                break;
        }
        assert fragment != null;
        return fragment;
    }

    @StringRes
    private final int[] TAB_TITLES = new int[]{
            R.string.tab_1,
            R.string.tab_2
    };

    @NonNull
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return 2;
    }
}
