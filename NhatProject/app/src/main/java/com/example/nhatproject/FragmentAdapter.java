package com.example.nhatproject;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class FragmentAdapter extends FragmentStatePagerAdapter {
    int num = 2;

    public FragmentAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new ChampionsLeagueFragment();
            case 1: return new EuropaLeagueFragment();
            default: return new ChampionsLeagueFragment();
        }
    }

    @Override
    public int getCount() {
        return num;
    }
}
