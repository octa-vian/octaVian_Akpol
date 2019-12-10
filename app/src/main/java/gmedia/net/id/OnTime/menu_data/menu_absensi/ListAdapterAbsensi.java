package gmedia.net.id.OnTime.menu_data.menu_absensi;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import gmedia.net.id.OnTime.R;
import gmedia.net.id.OnTime.utils.ConvertDate;

public class ListAdapterAbsensi extends ArrayAdapter {
	private Context context;
	private List<ModelListAbsensi> absensi;

	public ListAdapterAbsensi(Context context, List<ModelListAbsensi> absensi) {
		super(context, R.layout.view_lv_data_absensi, absensi);
		this.context = context;
		this.absensi = absensi;
	}

	private static class ViewHolder {
		private TextView tglMasuk, jamMasuk, tglKeluar, jamKeluar, keterangan;
		private String status;
	}

	@Override
	public int getCount() {
		return super.getCount();
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}

	@Override
	public int getItemViewType(int position) {

		int hasil = 0;
		if (position % 2 == 1) {
			hasil = 0;
		} else {
			hasil = 1;
		}
		return hasil;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = new ViewHolder();
		int tipeViewList = getItemViewType(position);
		if (convertView == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
//            LayoutInflater inflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            /*LayoutInflater inflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);*/
			convertView = inflater.inflate(R.layout.view_lv_data_absensi, null);
			holder.tglMasuk = (TextView) convertView.findViewById(R.id.tglMasukAbsensi);
			holder.jamMasuk = (TextView) convertView.findViewById(R.id.jamMasukAbsensi);
			holder.tglKeluar = (TextView) convertView.findViewById(R.id.tglKeluarAbsensi);
			holder.jamKeluar = (TextView) convertView.findViewById(R.id.jamKeluarAbsensi);
			holder.keterangan = (TextView) convertView.findViewById(R.id.keteranganAbsensi);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		ModelListAbsensi modelListAbsensi = absensi.get(position);
		String tanggalMasuk = modelListAbsensi.getMasuk();
		if (tanggalMasuk.equals("0000-00-00 00:00:00")) {
			tanggalMasuk = "00-00-0000";
			holder.tglMasuk.setText(tanggalMasuk);
		} else {
			holder.tglMasuk.setText(ConvertDate.convert("yyyy-MM-dd HH:mm:ss", "dd-MM-yyyy", modelListAbsensi.getMasuk()));
		}
		holder.jamMasuk.setText(ConvertDate.convert("yyyy-MM-dd HH:mm:ss", "HH:mm", modelListAbsensi.getMasuk()));
		String tanggalKeluar = modelListAbsensi.getKeluar();
		if (tanggalKeluar.equals("0000-00-00 00:00:00")) {
			tanggalKeluar = "00-00-0000";
			holder.tglKeluar.setText(tanggalKeluar);
		} else {
			holder.tglKeluar.setText(ConvertDate.convert("yyyy-MM-dd HH:mm:ss", "dd-MM-yyyy", modelListAbsensi.getKeluar()));
		}
		holder.jamKeluar.setText(ConvertDate.convert("yyyy-MM-dd HH:mm:ss", "HH:mm", modelListAbsensi.getKeluar()));
//        holder.masuk.setText(GetDateAfterSpace.getDateAfterSpace(getScanMasuk));
//        holder.keluar.setText(GetDateAfterSpace.getDateAfterSpace(getScanKeluar));
		holder.keterangan.setText(modelListAbsensi.getKeterangan());
		holder.status = modelListAbsensi.getStatus();
		if (tipeViewList == 0) {
			LinearLayout layoutHistoryCuti = convertView.findViewById(R.id.layoutHistoryCuti);
			layoutHistoryCuti.setBackgroundColor(Color.parseColor("#F6F6F6"));
//            layoutHistoryCuti.setBackgroundColor(Color.parseColor("#FFE5E6E8"));
            /*RelativeLayout a = convertView.findViewById(R.id.layouttanggalabsen);
            RelativeLayout c = convertView.findViewById(R.id.layoutjamkeluarabsen);
            RelativeLayout d = convertView.findViewById(R.id.layoutjamtelat);
            a.setBackgroundColor(Color.parseColor("#FFE5E6E8"));
            c.setBackgroundColor(Color.parseColor("#FFE5E6E8"));
            d.setBackgroundColor(Color.parseColor("#FFE5E6E8"));*/
		}
		if (holder.status.equals("5")) {
			holder.tglMasuk.setTextColor(Color.parseColor("#FF0000"));
			holder.tglKeluar.setTextColor(Color.parseColor("#FF0000"));
			holder.keterangan.setTextColor(Color.parseColor("#FF0000"));
		} else if (!holder.status.equals("5")) {
			holder.tglMasuk.setTextColor(Color.parseColor("#000000"));
			holder.tglKeluar.setTextColor(Color.parseColor("#000000"));
			holder.keterangan.setTextColor(Color.parseColor("#000000"));
		}
        /*TextView texta = convertView.findViewById(R.id.tanggalabsen);
        String textb = texta.getText().toString();
        if (textb.contains("Sab")) {
            texta.setTextColor(Color.parseColor("#FF0000"));
        } else if (textb.contains("Min")) {
            texta.setTextColor(Color.parseColor("#FF0000"));
        } else {
            texta.setTextColor(context.getResources().getColor(R.color.textbiasa));
        }*/
		return convertView;
	}
}
