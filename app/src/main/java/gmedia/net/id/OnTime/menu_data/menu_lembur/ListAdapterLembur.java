package gmedia.net.id.OnTime.menu_data.menu_lembur;

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

public class ListAdapterLembur extends ArrayAdapter {
	private Context context;
	private List<ModelListLembur> lembur;

	public ListAdapterLembur(Context context, List<ModelListLembur> lembur) {
		super(context, R.layout.view_lv_data_lembur, lembur);
		this.context = context;
		this.lembur = lembur;
	}

	private static class ViewHolder {
		private TextView tglMulai, jamMulai, tglSelesai, jamSelesai, keterangan;
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
			convertView = inflater.inflate(R.layout.view_lv_data_lembur, null);
			holder.tglMulai = (TextView) convertView.findViewById(R.id.tglMulaiLembur);
			holder.jamMulai = (TextView) convertView.findViewById(R.id.jamMulaiLembur);
			holder.tglSelesai = (TextView) convertView.findViewById(R.id.tglSelesaiLembur);
			holder.jamSelesai = (TextView) convertView.findViewById(R.id.jamSelesaiLembur);
			holder.keterangan = (TextView) convertView.findViewById(R.id.keteranganLembur);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		ModelListLembur modelListLembur = lembur.get(position);
		holder.tglMulai.setText(ConvertDate.convert("yyyy-MM-dd HH:mm:ss", "dd-MM-yyyy", modelListLembur.getMulai()));
		holder.jamMulai.setText(ConvertDate.convert("yyyy-MM-dd HH:mm:ss", "HH:mm", modelListLembur.getMulai()));
		holder.tglSelesai.setText(ConvertDate.convert("yyyy-MM-dd HH:mm:ss", "dd-MM-yyyy", modelListLembur.getSelesai()));
		holder.jamSelesai.setText(ConvertDate.convert("yyyy-MM-dd HH:mm:ss", "HH:mm", modelListLembur.getSelesai()));
//        holder.mulai.setText(GetDateAfterSpace.getDateAfterSpace(getScanMasuk));
//        holder.tglSelesai.setText(GetDateAfterSpace.getDateAfterSpace(getScanKeluar));
		holder.keterangan.setText(modelListLembur.getKeterangan());
		if (tipeViewList == 0) {
			LinearLayout layoutUtama = convertView.findViewById(R.id.layoutLembur);
			layoutUtama.setBackgroundColor(Color.parseColor("#F6F6F6"));
//            layoutHistoryCuti.setBackgroundColor(Color.parseColor("#FFE5E6E8"));
            /*RelativeLayout a = convertView.findViewById(R.id.layouttanggalabsen);
            RelativeLayout c = convertView.findViewById(R.id.layoutjamkeluarabsen);
            RelativeLayout d = convertView.findViewById(R.id.layoutjamtelat);
            a.setBackgroundColor(Color.parseColor("#FFE5E6E8"));
            c.setBackgroundColor(Color.parseColor("#FFE5E6E8"));
            d.setBackgroundColor(Color.parseColor("#FFE5E6E8"));*/
		}
        /*if (holder.status.equals("5")) {
//            TextView textView = convertView.findViewById(R.id.);
        }*/
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
