package gmedia.net.id.OnTime;

import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import gmedia.net.id.OnTime.menu_drawer.ListAdapterMenuDrawer;
import gmedia.net.id.OnTime.menu_drawer.SetGetMenuDrawer;
import gmedia.net.id.OnTime.utils.RuntimePermissionsActivity;

public class MainActivity extends RuntimePermissionsActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final int REQUEST_PERMISSIONS = 20;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private ListView lvMenuDrawer;
    private ListAdapterMenuDrawer adapter;
    private RelativeLayout buttonDrawer;
    private ArrayList<SetGetMenuDrawer> menuDrawer;
    private Integer icon[] =
            {
                    R.drawable.home,
                    R.drawable.pengumuman,
                    R.drawable.cuti,
                    R.drawable.cuti,
                    R.drawable.rekap,
                    R.drawable.logout
            };
    private String textMenu[] =
            {
                    "Home",
                    "Pengumuman",
                    "Ijin",
                    "Cuti",
                    "Rekap Absensi",
                    "Logout"
            };
    private Fragment fragment;
    public static Boolean isAbsenMasuk = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            super.requestAppPermissions(new String[]{
                            Manifest.permission.CAMERA,
                            Manifest.permission.INTERNET,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION
                    },
                    R.string.runtime_permissions_txt, REQUEST_PERMISSIONS);
        }
        initUI();
        initAction();
            /*if (fragment == null) {
                fragment = new Dashboard();
                callFragment(fragment);
            }*/

        /*ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();*/

    }

    @Override
    public void onPermissionsGranted(int requestCode) {

    }

    private void initUI() {
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        buttonDrawer = (RelativeLayout) findViewById(R.id.drawer_button);
        lvMenuDrawer = (ListView) findViewById(R.id.listmenu);
    }

    private void initAction() {
        setSupportActionBar(toolbar);
//        navigationView.setNavigationItemSelectedListener(this);
        buttonDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                drawer.openDrawer(GravityCompat.START);
                final Dialog dialogMenu = new Dialog(MainActivity.this);
                dialogMenu.setContentView(R.layout.popup_menu);
                dialogMenu.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialogMenu.setCanceledOnTouchOutside(false);
                dialogMenu.show();
                Window window = dialogMenu.getWindow();
                window.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            }
        });
        menuDrawer = isiMenuDrawer();
        adapter = new ListAdapterMenuDrawer(this, menuDrawer);
        lvMenuDrawer.setAdapter(adapter);
        lvMenuDrawer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                switch (position) {
                    case 0:
                        fragment = new Dashboard();
                        callFragment(fragment);
                        break;
                }
            }
        });
    }

    private ArrayList<SetGetMenuDrawer> isiMenuDrawer() {
        ArrayList<SetGetMenuDrawer> rvData = new ArrayList<>();
        for (int i = 0; i < icon.length; i++) {
            SetGetMenuDrawer custom = new SetGetMenuDrawer();
            custom.setIconMenu(icon[i]);
            custom.setTextMenu(textMenu[i]);
            rvData.add(custom);
        }
        return rvData;
    }

    @Override
    protected void onResume() {
        super.onResume();
        fragment = new Dashboard();
        callFragment(fragment);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            preparePopUpExit();
        }
    }

    private void preparePopUpExit() {
        final Dialog dialogExit = new Dialog(MainActivity.this);
        dialogExit.setContentView(R.layout.popup_exit);
        dialogExit.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogExit.setCanceledOnTouchOutside(false);
        Button ya = (Button) dialogExit.findViewById(R.id.btnYa);
        Button tidak = (Button) dialogExit.findViewById(R.id.btnTidak);
        ya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                System.exit(0);
            }
        });
        tidak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogExit.dismiss();
                fragment = new Dashboard();
                callFragment(fragment);
            }
        });
        dialogExit.show();
    }

    private void callFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainLayout, fragment, fragment.getClass().getSimpleName())
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .addToBackStack(null)
                .commit();
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }*/

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        /*int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);*/
        return true;
    }
}
