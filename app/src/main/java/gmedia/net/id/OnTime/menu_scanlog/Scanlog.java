package gmedia.net.id.OnTime.menu_scanlog;

import android.app.DatePickerDialog;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import gmedia.net.id.OnTime.R;
import gmedia.net.id.OnTime.menu_gaji.MenuInfoGaji;
import gmedia.net.id.OnTime.utils.ApiVolley;
import gmedia.net.id.OnTime.utils.DialogDataTidakDitemukan;
import gmedia.net.id.OnTime.utils.DialogGagal;
import gmedia.net.id.OnTime.utils.LinkURL;
import gmedia.net.id.OnTime.utils.Proses;

public class Scanlog extends AppCompatActivity {
	private ListAdapterScanlog adapter;
	private List<ModelScanlog> list;
	private ListView listView;
	private Proses proses;
	private ImageView btnTgl;
	private TextView txtTanggal;
	private String tanggal = "";
	private Button btnProses;
	private LinearLayout tableScanlog;
	private DialogGagal dialogGagal;
	private DialogDataTidakDitemukan dialogDataTidakDitemukan;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scanlog);
		proses = new Proses(this);
		ActionBar actionBar = getSupportActionBar();
		actionBar.setTitle("Scanlog");
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setElevation(0);
		initUI();
		initAction();
	}

	private void initUI() {
		listView = (ListView) findViewById(R.id.lv_scanlog);
		btnTgl = (ImageView) findViewById(R.id.btnTanggalScanlog);
		txtTanggal = (TextView) findViewById(R.id.txtTanggalScanlog);
		btnProses = (Button) findViewById(R.id.btnProsesScanlog);
		tableScanlog = (LinearLayout) findViewById(R.id.tableScanlog);
	}

	private void initDefaultDate() {
		Date c = Calendar.getInstance().getTime();
		SimpleDateFormat df = new SimpleDateFormat("dd MMMM yyyy");
		String formattedDate = df.format(c);
		txtTanggal.setText(formattedDate);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
		tanggal = sdf.format(c);
	}

	private void initAction() {
		initDefaultDate();
		btnTgl.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				final java.util.Calendar customDate = java.util.Calendar.getInstance();
				DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
					@Override
					public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
						customDate.set(java.util.Calendar.YEAR, year);
						customDate.set(java.util.Calendar.MONTH, month);
						customDate.set(java.util.Calendar.DATE, dayOfMonth);
						SimpleDateFormat sdFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.US);
						txtTanggal.setText(sdFormat.format(customDate.getTime()));
//						txtMulai.setAlpha(1);
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
						tanggal = sdf.format(customDate.getTime());
					}
				};
				new DatePickerDialog(Scanlog.this, date, customDate.get(java.util.Calendar.YEAR), customDate.get(java.util.Calendar.MONTH), customDate.get(java.util.Calendar.DATE)).show();
			}
		});
		btnProses.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (txtTanggal.getText().equals("")) {
					Toast.makeText(Scanlog.this, "Silahkan Input Tanggal Terlebih Dahulu", Toast.LENGTH_LONG).show();
				} else {
					prepareDataScanlog();
				}
			}
		});
	}

	private void prepareDataScanlog() {
		proses.ShowDialog();
		JSONObject jBody = new JSONObject();
		try {
			jBody.put("date", tanggal);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		ApiVolley request = new ApiVolley(Scanlog.this, jBody, "POST", LinkURL.viewScanlog, "", "", 0, new ApiVolley.VolleyCallback() {
			@Override
			public void onSuccess(String result) {
				proses.DismissDialog();
				list = new ArrayList<>();
				try {
					JSONObject object = new JSONObject(result);
					String status = object.getJSONObject("metadata").getString("status");
					String message = object.getJSONObject("metadata").getString("message");
					if (status.equals("200")) {
						JSONArray response = object.getJSONArray("response");
						for (int i = 0; i < response.length(); i++) {
							JSONObject isi = response.getJSONObject(i);
							list.add(new ModelScanlog(
									isi.getString("scan_date"),
									isi.getString("keterangan")
							));
						}
						listView.setAdapter(null);
						adapter = new ListAdapterScanlog(Scanlog.this, list);
						listView.setAdapter(adapter);
						tableScanlog.setVisibility(View.VISIBLE);
					} else if (status.equals("404")) {
						tableScanlog.setVisibility(View.GONE);
						dialogDataTidakDitemukan = new DialogDataTidakDitemukan(Scanlog.this);
						dialogDataTidakDitemukan.ShowDialog();
					} else {
//						Toast.makeText(Scanlog.this, message, Toast.LENGTH_LONG).show();
						tableScanlog.setVisibility(View.GONE);
						DialogGagal.message = message;
						dialogGagal = new DialogGagal(Scanlog.this);
						dialogGagal.ShowDialog();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onError(String result) {
				proses.DismissDialog();
				Toast.makeText(Scanlog.this, "Terjadi Kesalahan", Toast.LENGTH_LONG).show();
			}
		});
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
}
