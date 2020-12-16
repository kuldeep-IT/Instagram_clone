package com.example.instagramversion2;

import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

class TabAdapter extends FragmentPagerAdapter {
    public TabAdapter(@NonNull FragmentManager fm)
    {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int tabPosition) {
        switch(tabPosition)
        {
            case 0:
                ProfileFragment pf = new ProfileFragment();
                return pf;
            case 1:
                UserFragment up = new UserFragment();
                return up;
            case 2:
                return new SharePictureFragment();

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        switch (position)
        {
            case 0:
                return "Profile";
            case 1:
                return "User";
            case 2:
                return "SharePicture";

            default:
                return null;
        }
    }
}
