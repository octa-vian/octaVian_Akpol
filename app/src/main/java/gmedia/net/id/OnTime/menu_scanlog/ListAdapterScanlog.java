package gmedia.net.id.OnTime.menu_scanlog;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import gmedia.net.id.OnTime.R;
import gmedia.net.id.OnTime.menu_pengumuman.ListAdapterPengumuman;
import gmedia.net.id.OnTime.utils.ConvertDate;

public class ListAdapterScanlog extends ArrayAdapter {
	private Context context;
	private List<ModelScanlog> list;

	public ListAdapterScanlog(Context context, List<ModelScanlog> list) {
		super(context, R.layout.view_lv_scanlog, list);
		this.context = context;
		this.list = list;
	}

	private static class ViewHolder {
		private TextView tanggal, jam, keterangan;
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
			convertView = inflater.inflate(R.layout.view_lv_scanlog, null);
			holder.tanggal = (TextView) convertView.findViewById(R.id.tglScanlog);
			holder.jam = (TextView) convertView.findViewById(R.id.jamScanlog);
			holder.keterangan = (TextView) convertView.findViewById(R.id.keteranganScanlog);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		ModelScanlog model = list.get(position);
		holder.tanggal.setText(ConvertDate.convert("yyyy-MM-dd HH:mm:ss", "dd-MM-yyyy", model.getScanDate()));
		holder.jam.setText(ConvertDate.convert("yyyy-MM-dd HH:mm:ss", "HH:mm", model.getScanDate()));
		holder.keterangan.setText(model.getKeterangan());
		return convertView;
	}
}
