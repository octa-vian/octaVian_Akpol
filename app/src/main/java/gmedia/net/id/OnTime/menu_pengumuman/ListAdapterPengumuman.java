package gmedia.net.id.OnTime.menu_pengumuman;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import gmedia.net.id.OnTime.R;

public class ListAdapterPengumuman extends ArrayAdapter {
    private Context context;
    private List<ModelPengumuman> dataPengumuman;
    private Fragment fragment;


    public ListAdapterPengumuman(Context context, List<ModelPengumuman> dataPengumuman) {
        super(context, R.layout.lv_pengumuman, dataPengumuman);
        this.context = context;
        this.dataPengumuman = dataPengumuman;
    }

    private static class ViewHolder {
        private Boolean baru;
        private TextView tanggal, judul;
        private RelativeLayout background;
//        private String flag_libur;
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
            convertView = inflater.inflate(R.layout.lv_pengumuman, null);
            holder.tanggal = (TextView) convertView.findViewById(R.id.tanggalInfoGaji);
            holder.judul = (TextView) convertView.findViewById(R.id.judulInfoGaji);
            holder.background = (RelativeLayout) convertView.findViewById(R.id.bcg_lv_pengumuman);
        } else {
            holder = (ListAdapterPengumuman.ViewHolder) convertView.getTag();
        }
        final ModelPengumuman model = dataPengumuman.get(position);
        holder.tanggal.setText(model.getTanggal());
        holder.judul.setText(model.getJudul());
        holder.background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("id",model.getId());
//                MenuPengumuman.id = model.getId();
                fragment = new DetailPengumuman();
                fragment.setArguments(bundle);
                callFragment(fragment);
            }
        });
        return convertView;
    }

    private void callFragment(Fragment fragment) {
        ((FragmentActivity) context).getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.zoom_in_detail_pengumuman, R.anim.no_move)
                .replace(R.id.replaceLayout, fragment, fragment.getClass().getSimpleName())
                .addToBackStack(null)
                .commit();
    }
}
