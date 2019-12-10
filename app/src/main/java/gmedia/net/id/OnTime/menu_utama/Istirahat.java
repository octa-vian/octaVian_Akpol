package gmedia.net.id.OnTime.menu_utama;

import android.app.Activity;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

import gmedia.net.id.OnTime.DashboardBaru;
import gmedia.net.id.OnTime.R;
import gmedia.net.id.OnTime.ScanAbsen;
import gmedia.net.id.OnTime.utils.GetLocation;

public class Istirahat extends Activity {
    private View view;
//    private Context context;
    private TextView tanggal, jam, menit;
    private RelativeLayout btnCheckIn;
    private GetLocation getLocation;
    private String tipe_scan = "3";

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.istirahat);
		/*if (DashboardBaru.latitude.equals("") || DashboardBaru.longitude.equals("")) {
			getLocation = new GetLocation();
			getLocation.GetLocation(Istirahat.this);
		}*/
		initUI();
		initAction();
	}

	/*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.istirahat, viewGroup, false);
        context = getContext();
        if (DashboardBaru.latitude.equals("") || DashboardBaru.longitude.equals("")) {
            getLocation = new GetLocation();
            getLocation.GetLocation(context);
        }
        initUI();
        initAction();
        return view;
    }*/

    private void initUI() {
        tanggal = (TextView) findViewById(R.id.txtDinoTanggalIstirahat);
        jam = (TextView) findViewById(R.id.txtJamIstirahat);
        menit = (TextView) findViewById(R.id.txtMenitIstirahat);
        btnCheckIn = (RelativeLayout) findViewById(R.id.tombolCheckInIstirahat);
    }

    private void initAction() {
		final Handler handler = new Handler();
		final Runnable r = new Runnable() {
			public void run() {
				long date = System.currentTimeMillis();
				SimpleDateFormat formatTgl = new SimpleDateFormat("dd MMMM yyyy");
				SimpleDateFormat formatJam = new SimpleDateFormat("HH");
				SimpleDateFormat formatMenit = new SimpleDateFormat("mm");
				tanggal.setText(formatTgl.format(date));
				jam.setText(formatJam.format(date));
				menit.setText(formatMenit.format(date));
				handler.postDelayed(this, 500);
			}
		};
		handler.postDelayed(r, 300);
        btnCheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
				ScanAbsen scanAbsen = new ScanAbsen(Istirahat.this,tipe_scan);
                /*if (DashboardBaru.latitude.equals("") || DashboardBaru.longitude.equals("")) {
                    getLocation = new GetLocation();
                    getLocation.GetLocation(Istirahat.this);
                    Toast.makeText(Istirahat.this, "please try again", Toast.LENGTH_LONG).show();
                } else {
                    ScanAbsen scanAbsen = new ScanAbsen(Istirahat.this,tipe_scan);
//                    Toast.makeText(context, "" + DashboardBaru.latitude + " & " + DashboardBaru.longitude, Toast.LENGTH_LONG).show();
//                    Toast.makeText(context, "Berhasil", Toast.LENGTH_LONG).show();
                }*/
            }
        });
    }
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.no_move, R.anim.fade_out_animation);
	}
}
