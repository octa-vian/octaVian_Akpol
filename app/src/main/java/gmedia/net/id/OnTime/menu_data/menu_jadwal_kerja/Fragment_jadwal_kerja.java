package gmedia.net.id.OnTime.menu_data.menu_jadwal_kerja;

import android.app.DatePickerDialog;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
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

public class Fragment_jadwal_kerja extends Fragment {
	private View view;
	private Context context;
	private TextView txtMulai, txtAkhir;
	private ImageView btnTglMulai, btnTglAkhir;
	private ListAdapterJadwalKerja adapter;
	private List<ModelListJadwalKerja> jadwalKerja;
	private List<ModelListJadwalKerja> moreJadwalKerja;
	private ListView listView;
	private String tanggal[] =
			{
					"02/11/2018",
					"08/04/2019",
					"02/11/2018",
					"08/04/2019",
					"02/11/2018"
			};
	private Button btnKirim;
	private Proses proses;
	private int startIndex = 0;
	private int count = 10;
	private boolean isLoading = false;
	private View footerList;
	private LinearLayout tableJadwalKerja;
	private String awal, akhir;
	private DialogGagal dialogGagal;
	private DialogDataTidakDitemukan dialogDataTidakDitemukan;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_jadwal_kerja, container, false);
		context = getContext();
		proses = new Proses(context);
		initUI();
		initAction();
		return view;
	}

	private void initUI() {
		btnTglMulai = (ImageView) view.findViewById(R.id.btnTglMulaiJadwalKerja);
		btnTglAkhir = (ImageView) view.findViewById(R.id.btnTglAkhirJadwalKerja);
		txtMulai = (TextView) view.findViewById(R.id.txtTglMulaiJadwalKerja);
		txtAkhir = (TextView) view.findViewById(R.id.txtTglAkhirJadwalKerja);
		listView = (ListView) view.findViewById(R.id.lv_jadwal_kerja);
		btnKirim = (Button) view.findViewById(R.id.btnKirimJadwalKerja);
		tableJadwalKerja = (LinearLayout) view.findViewById(R.id.tableJadwalKerja);
		LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		footerList = li.inflate(R.layout.footer_list, null);
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
//        jadwalKerja = prepareDataJadwalKerja();

		btnKirim.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (txtMulai.getText().toString().equals("")) {
					Toast.makeText(context, "Silahkan Isi Tanggal Awal Terlebih Dahulu", Toast.LENGTH_LONG).show();
					return;
				} else if (txtAkhir.getText().toString().equals("")) {
					Toast.makeText(context, "Silahkan Isi Tanggal Akhir Terlebih Dahulu", Toast.LENGTH_LONG).show();
					return;
				} else {
					prepareDataJadwalKerja();
				}
			}
		});
	}

	@Override
	public void onResume() {
		super.onResume();
		startIndex = 0;
	}

	private void prepareDataJadwalKerja() {
		isLoading = true;
		proses.ShowDialog();
		JSONObject jBody = new JSONObject();
		try {
			jBody.put("datestart", awal);
			jBody.put("dateend", akhir);
			jBody.put("start", String.valueOf(startIndex));
			jBody.put("count", String.valueOf(count));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		ApiVolley request = new ApiVolley(context, jBody, "POST", LinkURL.ListJadwalKerja, "", "", 0, new ApiVolley.VolleyCallback() {
			@Override
			public void onSuccess(String result) {
				isLoading = false;
				proses.DismissDialog();
				jadwalKerja = new ArrayList<>();
				try {
					JSONObject object = new JSONObject(result);
					String status = object.getJSONObject("metadata").getString("status");
					String message = object.getJSONObject("metadata").getString("message");
					if (status.equals("200")) {
						JSONArray response = object.getJSONArray("response");
						for (int i = 0; i < response.length(); i++) {
							JSONObject isi = response.getJSONObject(i);
							jadwalKerja.add(new ModelListJadwalKerja(
									isi.getString("tgl"),
									isi.getString("shift"),
									isi.getString("jam_masuk"),
									isi.getString("jam_pulang")
							));
						}
						listView.setAdapter(null);
						adapter = new ListAdapterJadwalKerja(context, jadwalKerja);
						listView.setAdapter(adapter);
						tableJadwalKerja.setVisibility(View.VISIBLE);
						listView.setOnScrollListener(new AbsListView.OnScrollListener() {
							@Override
							public void onScrollStateChanged(AbsListView absListView, int i) {

							}

							@Override
							public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
								if (view.getLastVisiblePosition() == totalItemCount - 1 && listView.getCount() > (count - 1) && !isLoading) {
									isLoading = true;
									listView.addFooterView(footerList);
									startIndex += count;
//                                        startIndex = 0;
									getMoreData();
									//Log.i(TAG, "onScroll: last ");
								}
							}
						});
					} else if (status.equals("404")) {
						tableJadwalKerja.setVisibility(View.GONE);
						dialogDataTidakDitemukan = new DialogDataTidakDitemukan(context);
						dialogDataTidakDitemukan.ShowDialog();
					} else {
//						Toast.makeText(context, message, Toast.LENGTH_LONG).show();
						tableJadwalKerja.setVisibility(View.GONE);
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
				isLoading = false;
				proses.DismissDialog();
				Toast.makeText(context, "Terjadi Kesalahan", Toast.LENGTH_LONG).show();
			}
		});
	}

	private void getMoreData() {
		isLoading = true;
		JSONObject jBody = new JSONObject();
		try {
			jBody.put("datestart", awal);
			jBody.put("dateend", akhir);
			jBody.put("start", String.valueOf(startIndex));
			jBody.put("count", String.valueOf(count));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		ApiVolley request = new ApiVolley(context, jBody, "POST", LinkURL.ListJadwalKerja, "", "", 0, new ApiVolley.VolleyCallback() {
			@Override
			public void onSuccess(String result) {
				proses.DismissDialog();
				moreJadwalKerja = new ArrayList<>();
				listView.removeFooterView(footerList);
				try {
					JSONObject object = new JSONObject(result);
					String status = object.getJSONObject("metadata").getString("status");
					if (status.equals("200")) {
						JSONArray response = object.getJSONArray("response");
						for (int i = 0; i < response.length(); i++) {
							JSONObject isi = response.getJSONObject(i);
							jadwalKerja.add(new ModelListJadwalKerja(
									isi.getString("tgl"),
									isi.getString("shift"),
									isi.getString("jam_masuk"),
									isi.getString("jam_pulang")
							));
						}
						isLoading = false;
						listView.removeFooterView(footerList);
						if (adapter != null) adapter.addMoreData(moreJadwalKerja);
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

    /*private List<ModelListJadwalKerja> prepareDataJadwalKerja() {
        ArrayList<ModelListJadwalKerja> rvData = new ArrayList<>();
        for (int i = 0; i < tanggal.length; i++) {
            ModelListJadwalKerja isi = new ModelListJadwalKerja();
            isi.setTanggalJadwalKerja(tanggal[i]);
            rvData.add(isi);
        }
        return rvData;
    }*/
}
