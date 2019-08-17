package rf.ryanandri.basictools;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import rf.ryanandri.basictools.fragments.About;
import rf.ryanandri.basictools.fragments.DeviceInfo;
import rf.ryanandri.basictools.fragments.DisplayCalibration;
import rf.ryanandri.basictools.fragments.Miscellaneous;
import rf.ryanandri.basictools.utils.RootUtils;

public class MainActivity extends AppCompatActivity {
    private List<Fragment> mFragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = findViewById(R.id.view_pager);
        TabLayout tabs = findViewById(R.id.tabs);

        setFragments();

        SectionsPagerAdapter sectionsPagerAdapter =
                new SectionsPagerAdapter(getSupportFragmentManager());
        viewPager.setOffscreenPageLimit(mFragmentList.size());
        viewPager.setAdapter(sectionsPagerAdapter);
        tabs.setupWithViewPager(viewPager);
    }

    private void setFragments() {
        mFragmentList.add(new DeviceInfo());
        mFragmentList.add(new Miscellaneous());
        mFragmentList.add(new DisplayCalibration());
        mFragmentList.add(new About());
    }

    private class SectionsPagerAdapter extends FragmentPagerAdapter {

        @StringRes
        private final int[] tabs_tittle = new int[]{
                R.string.tab_device_info, R.string.tab_miscellaneous,
                R.string.tab_display, R.string.tab_about,};

        private SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return getResources().getString(tabs_tittle[position]);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }
    }

    @Override
    public void finish() {
        super.finish();
        RootUtils.closeSU();
    }
}