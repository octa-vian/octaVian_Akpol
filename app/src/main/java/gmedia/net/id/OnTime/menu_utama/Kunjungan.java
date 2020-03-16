package gmedia.net.id.OnTime.menu_utama;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

import gmedia.net.id.OnTime.DashboardBaru;
import gmedia.net.id.OnTime.R;
import gmedia.net.id.OnTime.ScanAbsen;
import gmedia.net.id.OnTime.ScanAbsenKunjungan;
import gmedia.net.id.OnTime.utils.GetLocation;
import gmedia.net.id.OnTime.utils.Proses;

public class Kunjungan extends Activity {
	private View view;
	private Context context;
	private TextView tanggal, jam, menit;
	private RelativeLayout btnCheckIn, cekin, cekout;
	private GetLocation getLocation;
	private String tipe_scan1 = "7";
	private String tipe_scan2 = "8";
	private Proses proses;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.absen_kunjungan_baru);
		proses = new Proses(Kunjungan.this);
		/*if (DashboardBaru.latitude.equals("") || DashboardBaru.longitude.equals("")) {
			getLocation = new GetLocation();
			getLocation.GetLocation(AbsenMasuk.this);
		}*/
		if (DashboardBaru.latitude.equals("") || DashboardBaru.longitude.equals("")) {
			getLocation = new GetLocation();
			getLocation.GetLocation(Kunjungan.this);
		}
		initUI();
		initAction();

		cekin = (RelativeLayout) findViewById(R.id.cekin);
		cekout = (RelativeLayout) findViewById(R.id.cekout);

		cekin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
			/*	Intent intent = new Intent(Kunjungan.this, AbsenCekIn.class);
				startActivity(intent);*/
				final Dialog dialog = new Dialog(Kunjungan.this);
				dialog.setContentView(R.layout.popup_absen_kunjungan);
				dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
				final Button btn_msk, btn_cancel;
				btn_msk = dialog.findViewById(R.id.btn_masuk);
				btn_cancel = dialog.findViewById(R.id.btn_cancel);
				btn_msk.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						ScanAbsen scanAbsen = new ScanAbsen(Kunjungan.this, tipe_scan1);
						if (DashboardBaru.latitude.equals("") || DashboardBaru.longitude.equals("")) {
							getLocation = new GetLocation();
							getLocation.GetLocation(Kunjungan.this);
							Toast.makeText(context, "please try again", Toast.LENGTH_LONG).show();
						} else {
//                   prepareAbsenMasuk();
							ScanAbsenKunjungan scanAbsenKunjungan = new ScanAbsenKunjungan(Kunjungan.this, tipe_scan1);
//                   Toast.makeText(context, "" + DashboardBaru.latitude + " & " + DashboardBaru.longitude, Toast.LENGTH_LONG).show();
							//                   Toast.makeText(context, "Berhasil", Toast.LENGTH_LONG).show();
						}
					}
				});

				btn_cancel.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});
				dialog.show();
			}
		});

		cekout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				/*Intent intent = new Intent(Kunjungan.this, AbsenCekOut.class);
				startActivity(intent);*/
				final Dialog dialog = new Dialog(Kunjungan.this);
				dialog.setContentView(R.layout.popup_absen_kunjungan);
				dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
				final Button btn_msk,btn_cancel;
				btn_cancel = dialog.findViewById(R.id.btn_cancel);
				btn_msk = dialog.findViewById(R.id.btn_masuk);
				btn_msk.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						ScanAbsen scanAbsen = new ScanAbsen(Kunjungan.this, tipe_scan2);
						if (DashboardBaru.latitude.equals("") || DashboardBaru.longitude.equals("")) {
							getLocation = new GetLocation();
							getLocation.GetLocation(Kunjungan.this);
							Toast.makeText(context, "please try again", Toast.LENGTH_LONG).show();
						} else {

//                   prepareAbsenMasuk();
							ScanAbsenKunjungan scanAbsenKunjungan = new ScanAbsenKunjungan(Kunjungan.this, tipe_scan2);
//                   Toast.makeText(context, "" + DashboardBaru.latitude + " & " + DashboardBaru.longitude, Toast.LENGTH_LONG).show();
							//                   Toast.makeText(context, "Berhasil", Toast.LENGTH_LONG).show();
						}
					}

				});

				btn_cancel.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});

				dialog.show();

			}
		});

	}

	/*@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.absen_masuk, viewGroup, false);
		context = getApplicationContext();
		proses = new Proses(context);
		if (DashboardBaru.latitude.equals("") || DashboardBaru.longitude.equals("")) {
			getLocation = new GetLocation();
			getLocation.GetLocation(context);
		}
		initUI();
		initAction();
		return view;
	}*/

	private void initUI() {
		tanggal = (TextView) findViewById(R.id.txtDinoTanggalMasuk);
		jam = (TextView) findViewById(R.id.txtJamMasuk);
		menit = (TextView) findViewById(R.id.txtMenitMasuk);

	}

	@SuppressLint("ClickableViewAccessibility")
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


	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.no_move, R.anim.fade_out_animation);
	}
}