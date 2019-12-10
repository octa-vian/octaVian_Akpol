package gmedia.net.id.OnTime.menu_pengajuan;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class TabLayoutViewPagerAdapterPengajuan extends FragmentPagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();

    public TabLayoutViewPagerAdapterPengajuan(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFrag(Fragment fragment) {
        mFragmentList.add(fragment);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Cuti";
            case 1:
                return "Ijin";
        }
        return null;
    }
}
