package gmedia.net.id.OnTime.menu_data.menu_jadwal_kerja;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import gmedia.net.id.OnTime.R;
import gmedia.net.id.OnTime.utils.ConvertDate;

public class ListAdapterJadwalKerja extends ArrayAdapter {
    private Context context;
    private List<ModelListJadwalKerja> jadwalKerja;

    public ListAdapterJadwalKerja(Context context, List<ModelListJadwalKerja> jadwalKerja) {
        super(context, R.layout.view_lv_jadwal_kerja, jadwalKerja);
        this.context = context;
        this.jadwalKerja = jadwalKerja;
    }

    private static class ViewHolder {
        private TextView tanggal, shift, jamMasuk, jamKeluar;
    }
    public void addMoreData(List<ModelListJadwalKerja>moreData){
        jadwalKerja.addAll(moreData);
        notifyDataSetChanged();
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
            convertView = inflater.inflate(R.layout.view_lv_jadwal_kerja, null);
            holder.tanggal = (TextView) convertView.findViewById(R.id.tanggalJadwalKerja);
            holder.shift = (TextView) convertView.findViewById(R.id.shiftJamKerja);
            holder.jamMasuk = (TextView) convertView.findViewById(R.id.jamMasukKerja);
            holder.jamKeluar = (TextView) convertView.findViewById(R.id.jamKeluarKerja);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ModelListJadwalKerja modelListJadwalKerja = jadwalKerja.get(position);
        String date = ConvertDate.convert("yyyy-MM-dd","dd-MM-yyyy",modelListJadwalKerja.getTanggalJadwalKerja());
        holder.tanggal.setText(date);
        holder.shift.setText(modelListJadwalKerja.getShiftJadwalKerja());
        holder.jamMasuk.setText(modelListJadwalKerja.getJamMasukJadwalKerja());
        holder.jamKeluar.setText(modelListJadwalKerja.getJamKeluarJadwalKerja());
        return convertView;
    }
}
