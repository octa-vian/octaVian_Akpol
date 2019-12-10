package gmedia.net.id.OnTime.menu_pengajuan;

import android.content.res.Resources;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import java.lang.reflect.Field;

import gmedia.net.id.OnTime.R;

public class MenuPengajuan extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabLayoutViewPagerAdapterPengajuan adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengajuan);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Pengajuan");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setElevation(0);
        initUI();
        initAction();
    }

    private void initUI() {
        tabLayout = (TabLayout) findViewById(R.id.tabPengajuan);
        viewPager = (ViewPager) findViewById(R.id.viewPagerPengajuan);
    }

    private void initAction() {
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                setIndicator(tabLayout,50,50);
            }
        });

    }
    private void setupViewPager(final ViewPager viewPager) {
        adapter = new TabLayoutViewPagerAdapterPengajuan(getSupportFragmentManager());
        adapter.addFrag(new Fragment_cuti());
        adapter.addFrag(new Fragment_ijin());
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);

    }
    private void setIndicator (TabLayout tabs,int leftDip,int rightDip){
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }
}