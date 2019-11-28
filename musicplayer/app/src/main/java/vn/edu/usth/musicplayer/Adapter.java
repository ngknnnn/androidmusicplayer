package vn.edu.usth.musicplayer;

import android.os.Bundle;
import android.view.Menu;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class Adapter extends FragmentPagerAdapter{
    private final int page_count =2;

    public Adapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public int getCount() {
        return page_count;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment frag = new MenuFragment();
        switch (position){
            case 0:
                frag = new MenuFragment();
                break;
            case 1:
                frag = new MusicPlayerFragment();
                break;
        }
        return frag;
    }

}
