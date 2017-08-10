package team.hex.wallex.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alireza on 6/30/17.
 */

public class PagerAdapterCreator {

    private List<Fragment> fragments = new ArrayList<>();
    private FragmentManager fm;

    private PagerAdapterCreator(FragmentManager fm) {
        this.fm = fm;
    }

    public static PagerAdapterCreator init(FragmentManager fragmentManager) {
        return new PagerAdapterCreator(fragmentManager);
    }


    public PagerAdapterCreator addFragment(Fragment fragment) {
        fragments.add(fragment);
        return this;
    }


    public PagerAdapter create() {
        return new PagerAdapter(fm, fragments);
    }


    public class PagerAdapter extends FragmentStatePagerAdapter {

        private List<Fragment> fragments;

        public PagerAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }


        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

    }
}