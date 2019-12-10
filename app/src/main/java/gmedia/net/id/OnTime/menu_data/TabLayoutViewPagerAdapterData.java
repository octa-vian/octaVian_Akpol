package gmedia.net.id.OnTime.menu_data;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class TabLayoutViewPagerAdapterData extends FragmentPagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();

    public TabLayoutViewPagerAdapterData(FragmentManager fm) {
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
                return "Jadwal Kerja";
            case 1:
                return "Absensi";
            case 2:
                return "Lembur";
        }

        return null;
    }
}
