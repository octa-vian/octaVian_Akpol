package gmedia.net.id.OnTime.menu_keterlambatan;

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
import gmedia.net.id.OnTime.menu_scanlog.Scanlog;
import gmedia.net.id.OnTime.utils.ApiVolley;
import gmedia.net.id.OnTime.utils.DialogDataTidakDitemukan;
import gmedia.net.id.OnTime.utils.DialogGagal;
import gmedia.net.id.OnTime.utils.LinkURL;
import gmedia.net.id.OnTime.utils.Proses;

public class Keterlambatan extends AppCompatActivity {

	private Proses proses;
	private DialogGagal dialogGagal;
	private TextView txtTglMulai, txtTglAkhir;
	private ImageView btnTglMulai, btnTglAkhir;
	private Button btnProses;
	private String tglAwal = "", tglAkhir;
	private ListView listView;
	private ListAdapterKeterlambatan adapter;
	private List<ModelKeterlambatan> list;
	private LinearLayout tableKeterlambatan;
	private DialogDataTidakDitemukan dialogDataTidakDitemukan;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_keterlambatan);
		proses = new Proses(Keterlambatan.this);
		ActionBar actionBar = getSupportActionBar();
		actionBar.setTitle("Keterlambatan");
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setElevation(0);
		initUI();
		initAction();
	}

	private void initUI() {
		txtTglMulai = (TextView) findViewById(R.id.txtTglMulaiTerlambat);
		btnTglMulai = (ImageView) findViewById(R.id.btnTglMulaiTerlambat);
		txtTglAkhir = (TextView) findViewById(R.id.txtTglAkhirTerlambat);
		btnTglAkhir = (ImageView) findViewById(R.id.btnTglAkhirTerlambat);
		btnProses = (Button) findViewById(R.id.btnProsesKeterlambatan);
		tableKeterlambatan = (LinearLayout) findViewById(R.id.tableKeterlambatan);
		listView = (ListView) findViewById(R.id.lv_keterlambatan);
	}

	private void initDefaultDate() {
		Date c = Calendar.getInstance().getTime();
		SimpleDateFormat df = new SimpleDateFormat("dd MMMM yyyy");
		String formattedDate = df.format(c);
		txtTglMulai.setText(formattedDate);
		txtTglAkhir.setText(formattedDate);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
		tglAwal = sdf.format(c);
		tglAkhir = sdf.format(c);
	}

	private void initAction() {
		initDefaultDate();
		btnTglMulai.setOnClickListener(new View.OnClickListener() {
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
						txtTglMulai.setText(sdFormat.format(customDate.getTime()));
						txtTglMulai.setAlpha(1);
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
						tglAwal = sdf.format(customDate.getTime());
					}
				};
				new DatePickerDialog(Keterlambatan.this, date, customDate.get(java.util.Calendar.YEAR), customDate.get(java.util.Calendar.MONTH), customDate.get(java.util.Calendar.DATE)).show();
			}
		});
		btnTglAkhir.setOnClickListener(new View.OnClickListener() {
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
						txtTglAkhir.setText(sdFormat.format(customDate.getTime()));
						txtTglAkhir.setAlpha(1);
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
						tglAkhir = sdf.format(customDate.getTime());
					}
				};
				new DatePickerDialog(Keterlambatan.this, date, customDate.get(java.util.Calendar.YEAR), customDate.get(java.util.Calendar.MONTH), customDate.get(java.util.Calendar.DATE)).show();
			}
		});
		btnProses.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (txtTglMulai.getText().toString().equals("")) {
					Toast.makeText(Keterlambatan.this, "Silahkan Pilih Tanggal Mulai", Toast.LENGTH_LONG).show();
					return;
				} else if (txtTglAkhir.getText().toString().equals("")) {
					Toast.makeText(Keterlambatan.this, "Silahkan Pilih Tanggal Akhir", Toast.LENGTH_LONG).show();
					return;
				} else {
					prepareDataKeterlambatan();
				}
			}
		});
	}

	private void prepareDataKeterlambatan() {
		proses.ShowDialog();
		JSONObject jBody = new JSONObject();
		try {
			jBody.put("datestart", tglAwal);
			jBody.put("dateend", tglAkhir);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		ApiVolley request = new ApiVolley(Keterlambatan.this, jBody, "POST", LinkURL.viewTerlambat, "", "", 0, new ApiVolley.VolleyCallback() {
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
							list.add(new ModelKeterlambatan(
									isi.getString("tgl"),
									isi.getString("scan_masuk"),
									isi.getString("jam_masuk"),
									isi.getString("total_menit")
							));
						}
						listView.setAdapter(null);
						adapter = new ListAdapterKeterlambatan(Keterlambatan.this, list);
						listView.setAdapter(adapter);
						tableKeterlambatan.setVisibility(View.VISIBLE);
					} else if (status.equals("404")) {
						tableKeterlambatan.setVisibility(View.GONE);
						dialogDataTidakDitemukan = new DialogDataTidakDitemukan(Keterlambatan.this);
						dialogDataTidakDitemukan.ShowDialog();
					} else {
						tableKeterlambatan.setVisibility(View.GONE);
						DialogGagal.message = message;
						dialogGagal = new DialogGagal(Keterlambatan.this);
						dialogGagal.ShowDialog();
//						Toast.makeText(Keterlambatan.this, message, Toast.LENGTH_LONG).show();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onError(String result) {
				proses.DismissDialog();
				Toast.makeText(Keterlambatan.this, "Terjadi Kesalahan", Toast.LENGTH_LONG).show();
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