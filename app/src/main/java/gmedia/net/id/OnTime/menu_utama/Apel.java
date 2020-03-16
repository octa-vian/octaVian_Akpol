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

import java.text.SimpleDateFormat;

import gmedia.net.id.OnTime.R;
import gmedia.net.id.OnTime.ScanAbsen;
import gmedia.net.id.OnTime.utils.GetLocation;
import gmedia.net.id.OnTime.utils.Proses;

public class Apel extends Activity {
	private View view;
	private Context context;
	private TextView tanggal, jam, menit;
	private RelativeLayout btnCheckIn, cekin, cekout;
	private String tipe_scan1 = "9";
	private String tipe_scan2 = "10";
	private Proses proses;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.absen_apel_baru);
		proses = new Proses(Apel.this);
		/*if (DashboardBaru.latitude.equals("") || DashboardBaru.longitude.equals("")) {
			getLocation = new GetLocation();
			getLocation.GetLocation(AbsenMasuk.this);
		}*/

		cekin = (RelativeLayout) findViewById(R.id.cekin);
		cekin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				final Dialog dialog = new Dialog(Apel.this);
				dialog.setContentView(R.layout.popup_absen_masuk_apel);
				dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
				final Button btn_msk, btn_cancel;
				btn_msk = dialog.findViewById(R.id.btn_masuk);
				btn_cancel = dialog.findViewById(R.id.btn_cancel);
				btn_msk.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						ScanAbsen scanAbsen = new ScanAbsen(Apel.this, tipe_scan1);
					}
				});

				btn_cancel.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});
				dialog.show();
				/*Intent intent = new Intent(Apel.this, AbsenMasuk.class);
				startActivity(intent);*/
			}
		});

		cekout = (RelativeLayout) findViewById(R.id.cekout);
		cekout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				final Dialog dialog = new Dialog(Apel.this);
				dialog.setContentView(R.layout.popup_absen_kelua_apel);
				dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
				final Button btn_klr, btn_cancel;
				btn_cancel = dialog.findViewById(R.id.btn_cancel);
				btn_klr = dialog.findViewById(R.id.btn_keluar);
				btn_klr.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						ScanAbsen scanAbsen = new ScanAbsen(Apel.this, tipe_scan2);
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

		initUI();
		initAction();
	}

	private void initUI() {
		tanggal = (TextView) findViewById(R.id.txtDinoTanggalMasuk);
		jam = (TextView) findViewById(R.id.txtJamMasuk);
		menit = (TextView) findViewById(R.id.txtMenitMasuk);
		//cekin = (RelativeLayout) findViewById(R.id.cekin);

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



		/*cekout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Apel.this, AbsenPulang.class);
				startActivity(intent);
			}
		});*/
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.no_move, R.anim.fade_out_animation);
	}
}