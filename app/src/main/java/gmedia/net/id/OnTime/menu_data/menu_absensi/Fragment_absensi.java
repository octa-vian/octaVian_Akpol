package gmedia.net.id.OnTime.menu_data.menu_absensi;

import android.app.DatePickerDialog;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import gmedia.net.id.OnTime.utils.ApiVolley;
import gmedia.net.id.OnTime.utils.DialogDataTidakDitemukan;
import gmedia.net.id.OnTime.utils.DialogGagal;
import gmedia.net.id.OnTime.utils.LinkURL;
import gmedia.net.id.OnTime.utils.Proses;

public class Fragment_absensi extends Fragment {
	private View view;
	private Context context;
	private ImageView btnTglMulai, btnTglAkhir;
	private TextView txtMulai, txtAkhir;
	private Button btnKirim;
	private ListAdapterAbsensi adapter;
	private List<ModelListAbsensi> absensi;
	private ListView listView;
	private Proses proses;
	private String awal, akhir;
	private LinearLayout tableAbsensi;
	private DialogGagal dialogGagal;
	private DialogDataTidakDitemukan dialogDataTidakDitemukan;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_absensi, container, false);
		context = getContext();
		proses = new Proses(context);
		initUI();
		initAction();
		return view;
	}

	private void initUI() {
		btnTglMulai = (ImageView) view.findViewById(R.id.btnTglMulaiAbsensi);
		btnTglAkhir = (ImageView) view.findViewById(R.id.btnTglAkhirAbsensi);
		txtMulai = (TextView) view.findViewById(R.id.txtTglMulaiAbsensi);
		txtAkhir = (TextView) view.findViewById(R.id.txtTglAkhirAbsensi);
		btnKirim = (Button) view.findViewById(R.id.btnKirimAbsensi);
		listView = (ListView) view.findViewById(R.id.listViewAbsensi);
		tableAbsensi = (LinearLayout) view.findViewById(R.id.tableAbsensi);
	}

	private void initDefaultDate() {
		Date c = Calendar.getInstance().getTime();
		SimpleDateFormat df = new SimpleDateFormat("dd MMMM yyyy");
		String formattedDate = df.format(c);
		txtMulai.setText(formattedDate);
		txtAkhir.setText(formattedDate);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
		awal = sdf.format(c);
		akhir = sdf.format(c);
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
						txtMulai.setText(sdFormat.format(customDate.getTime()));
						txtMulai.setAlpha(1);
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
						awal = sdf.format(customDate.getTime());
					}
				};
				new DatePickerDialog(context, date, customDate.get(java.util.Calendar.YEAR), customDate.get(java.util.Calendar.MONTH), customDate.get(java.util.Calendar.DATE)).show();
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
						txtAkhir.setText(sdFormat.format(customDate.getTime()));
						txtAkhir.setAlpha(1);
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
						akhir = sdf.format(customDate.getTime());
					}
				};
				new DatePickerDialog(context, date, customDate.get(java.util.Calendar.YEAR), customDate.get(java.util.Calendar.MONTH), customDate.get(java.util.Calendar.DATE)).show();
			}
		});
		btnKirim.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (txtMulai.getText().toString().equals("")) {
					Toast.makeText(context, "Silahkan isi tanggal awal terlebih dahulu", Toast.LENGTH_LONG).show();
				} else if (txtAkhir.getText().toString().equals("")) {
					Toast.makeText(context, "Silahkan isi tanggal akhir terlebih dahulu", Toast.LENGTH_LONG).show();
				} else {
					prepareDataAbsensi();
				}
			}
		});
	}

	private void prepareDataAbsensi() {
		proses.ShowDialog();
		JSONObject jBody = new JSONObject();
		try {
			jBody.put("datestart", awal);
			jBody.put("dateend", akhir);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		ApiVolley request = new ApiVolley(context, jBody, "POST", LinkURL.viewAbsensi, "", "", 0, new ApiVolley.VolleyCallback() {
			@Override
			public void onSuccess(String result) {
				proses.DismissDialog();
				absensi = new ArrayList<>();
				try {
					JSONObject object = new JSONObject(result);
					String status = object.getJSONObject("metadata").getString("status");
					String message = object.getJSONObject("metadata").getString("message");
					if (status.equals("200")) {
						JSONArray response = object.getJSONArray("response");
						for (int i = 0; i < response.length(); i++) {
							JSONObject isi = response.getJSONObject(i);
							absensi.add(new ModelListAbsensi(
									isi.getString("scan_masuk"),
									isi.getString("scan_pulang"),
									isi.getString("keterangan"),
									isi.getString("status")
							));
						}
						listView.setAdapter(null);
						adapter = new ListAdapterAbsensi(context, absensi);
						listView.setAdapter(adapter);
						tableAbsensi.setVisibility(View.VISIBLE);
					} else if (status.equals("404")) {
						tableAbsensi.setVisibility(View.GONE);
						dialogDataTidakDitemukan = new DialogDataTidakDitemukan(context);
						dialogDataTidakDitemukan.ShowDialog();
					} else {
//						Toast.makeText(context, message, Toast.LENGTH_LONG).show();
						tableAbsensi.setVisibility(View.GONE);
						DialogGagal.message = message;
						dialogGagal = new DialogGagal(context);
						dialogGagal.ShowDialog();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onError(String result) {
				proses.DismissDialog();
				Toast.makeText(context, "Terjadi Kesalahan", Toast.LENGTH_LONG).show();
			}
		});
	}
}
