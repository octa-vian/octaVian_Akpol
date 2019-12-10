package gmedia.net.id.OnTime.menu_keterlambatan;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import gmedia.net.id.OnTime.R;
import gmedia.net.id.OnTime.menu_history_cuti.ListAdapterHistoryCuti;
import gmedia.net.id.OnTime.utils.ConvertDate;

public class ListAdapterKeterlambatan extends ArrayAdapter {
	private Context context;
	private List<ModelKeterlambatan> list;

	public ListAdapterKeterlambatan(Context context, List<ModelKeterlambatan> list) {
		super(context, R.layout.view_lv_keterlambatan, list);
		this.context = context;
		this.list = list;
	}

	private static class ViewHolder {
		private TextView tanggal, scanMasuk, jamMasuk, totalMenit;
	}

	@Override
	public int getCount() {
		return super.getCount();
	}

	@NonNull
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = new ViewHolder();
		if (convertView == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
//            LayoutInflater inflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            /*LayoutInflater inflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);*/
			convertView = inflater.inflate(R.layout.view_lv_keterlambatan, null);
			holder.tanggal = (TextView) convertView.findViewById(R.id.tglKeterlambatan);
			holder.scanMasuk = (TextView) convertView.findViewById(R.id.scanMasukKeterlambatan);
			holder.jamMasuk = (TextView) convertView.findViewById(R.id.jamMasukKeterlambatan);
			holder.totalMenit = (TextView) convertView.findViewById(R.id.totalMenitKeterlambatan);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		ModelKeterlambatan model = list.get(position);
		String isiTgl = ConvertDate.convert("yyyy-MM-dd", "dd-MM-yyyy", model.getTanggal());
		String isiScanMasuk = ConvertDate.convert("HH:mm:ss", "HH:mm", model.getScanMasuk());
		String isiJamMasuk = ConvertDate.convert("HH:mm:ss", "HH:mm", model.getJamMasuk());
		holder.tanggal.setText(isiTgl);
		holder.scanMasuk.setText(isiScanMasuk);
		holder.jamMasuk.setText(isiJamMasuk);
		holder.totalMenit.setText(model.getTotalTerlambat()+" menit");
		return convertView;
	}
}
