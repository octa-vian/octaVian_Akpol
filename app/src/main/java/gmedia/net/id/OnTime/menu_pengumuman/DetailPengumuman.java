package gmedia.net.id.OnTime.menu_pengumuman;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import gmedia.net.id.OnTime.R;
import gmedia.net.id.OnTime.utils.ApiVolley;
import gmedia.net.id.OnTime.utils.DialogGagal;
import gmedia.net.id.OnTime.utils.LinkURL;
import gmedia.net.id.OnTime.utils.Proses;

public class DetailPengumuman extends Fragment {
	private View view;
	private Context context;
	private String id = "";
	private TextView judul, tanggal, isiBerita;
	private ImageView isiGambarBerita;
	private Proses proses;
	private DialogGagal dialogGagal;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.detail_pengumuman, container, false);
		context = getContext();
		proses = new Proses(context);
		Bundle bundle = this.getArguments();
		if (bundle != null) {
			id = bundle.getString("id", "");
		}
//        Toast.makeText(context, id, Toast.LENGTH_LONG).show();
		initUI();
		initAction();
		return view;
	}

	private void initUI() {
		judul = (TextView) view.findViewById(R.id.txtJudul);
		tanggal = (TextView) view.findViewById(R.id.txtTanggal);
		isiBerita = (TextView) view.findViewById(R.id.txtBerita);
		isiGambarBerita = (ImageView) view.findViewById(R.id.picDetailNews);
	}

	private void initAction() {
		proses.ShowDialog();
		JSONObject jBody = new JSONObject();
		try {
			jBody.put("id", id);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		ApiVolley request = new ApiVolley(context, jBody, "POST", LinkURL.listPengumuman, "", "", 0, new ApiVolley.VolleyCallback() {
			@Override
			public void onSuccess(String result) {
				proses.DismissDialog();
				try {
					JSONObject object = new JSONObject(result);
					String status = object.getJSONObject("metadata").getString("status");
					String message = object.getJSONObject("metadata").getString("message");
					if (status.equals("200")) {
						JSONObject response = object.getJSONObject("response");
						judul.setText(response.getString("judul"));
						tanggal.setText(response.getString("tgl"));
						isiBerita.setText(response.getString("teks"));
						String gambar = response.getString("gambar");
						if (!gambar.equals("")) {
							Picasso.get().load(gambar).into(isiGambarBerita);
						}
					} else {
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
