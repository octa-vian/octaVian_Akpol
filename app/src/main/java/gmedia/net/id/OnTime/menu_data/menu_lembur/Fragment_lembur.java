package gmedia.net.id.OnTime.menu_data.menu_lembur;

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

public class Fragment_lembur extends Fragment {
	private View view;
	private Context context;
	private ImageView btnTglAwal, btnTglAkhir;
	private TextView txtMulai, txtAkhir;
	private Button btnKirim;
	private String awal, akhir;
	private ListAdapterLembur adapter;
	private List<ModelListLembur> lembur;
	private ListView listView;
	private Proses proses;
	private LinearLayout tableLembur;
	private DialogGagal dialogGagal;
	private DialogDataTidakDitemukan dialogDataTidakDitemukan;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_lembur, container, false);
		context = getContext();
		proses = new Proses(context);
		initUI();
		initAction();
		return view;
	}

	private void initUI() {
		btnTglAwal = (ImageView) view.findViewById(R.id.btnTglMulaiLembur);
		btnTglAkhir = (ImageView) view.findViewById(R.id.btnTglAkhirLembur);
		txtMulai = (TextView) view.findViewById(R.id.txtTglMulaiLembur);
		txtAkhir = (TextView) view.findViewById(R.id.txtTglAkhirLembur);
		btnKirim = (Button) view.findViewById(R.id.btnKirimLembur);
		tableLembur = (LinearLayout) view.findViewById(R.id.tableLembur);
		listView = (ListView) view.findViewById(R.id.listViewLembur);
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
		btnTglAwal.setOnClickListener(new View.OnClickListener() {
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
					prepareDataLembur();
				}
			}
		});
	}

	private void prepareDataLembur() {
		proses.ShowDialog();
		JSONObject jBody = new JSONObject();
		try {
			jBody.put("datestart", awal);
			jBody.put("dateend", akhir);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		ApiVolley request = new ApiVolley(context, jBody, "POST", LinkURL.viewLembur, "", "", 0, new ApiVolley.VolleyCallback() {
			@Override
			public void onSuccess(String result) {
				proses.DismissDialog();
				lembur = new ArrayList<>();
				try {
					JSONObject object = new JSONObject(result);
					String status = object.getJSONObject("metadata").getString("status");
					String message = object.getJSONObject("metadata").getString("message");
					if (status.equals("200")) {
						JSONArray response = object.getJSONArray("response");
						for (int i = 0; i < response.length(); i++) {
							JSONObject isi = response.getJSONObject(i);
							lembur.add(new ModelListLembur(
									isi.getString("awal"),
									isi.getString("akhir"),
									isi.getString("keterangan")
							));
						}
						listView.setAdapter(null);
						adapter = new ListAdapterLembur(context, lembur);
						listView.setAdapter(adapter);
						tableLembur.setVisibility(View.VISIBLE);
					} else if (status.equals("404")) {
						tableLembur.setVisibility(View.GONE);
						dialogDataTidakDitemukan = new DialogDataTidakDitemukan(context);
						dialogDataTidakDitemukan.ShowDialog();
					} else {
//						Toast.makeText(context, message, Toast.LENGTH_LONG).show();
						tableLembur.setVisibility(View.GONE);
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
