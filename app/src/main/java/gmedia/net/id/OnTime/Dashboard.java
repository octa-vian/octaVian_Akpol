package gmedia.net.id.OnTime;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class Dashboard extends Fragment {
    private Context context;
    private View view;
    private RelativeLayout menuAbsenMasuk, menuAbsenKeluar, menuIstirahat, menuMulaiKerja, menuMulaiLembur, menuBerhentiLembur, menuSampaiTujuan, menuPindahTempat, menuReimbusement;


    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dashboard, viewGroup, false);
        context = getContext();
        initUI();
        initAction();

        return view;
    }

    private void initUI() {
        menuAbsenMasuk = (RelativeLayout) view.findViewById(R.id.menuAbsenMasuk);
        menuAbsenKeluar = (RelativeLayout) view.findViewById(R.id.menuAbsenKeluar);
        menuIstirahat = (RelativeLayout) view.findViewById(R.id.menuIstirahat);
        menuMulaiKerja = (RelativeLayout) view.findViewById(R.id.menuMulaiKerja);
        menuMulaiLembur = (RelativeLayout) view.findViewById(R.id.menuMulaiLembur);
        menuBerhentiLembur = (RelativeLayout) view.findViewById(R.id.menuBerhentiLembur);
        menuSampaiTujuan = (RelativeLayout) view.findViewById(R.id.menuSampaiTujuan);
        menuPindahTempat = (RelativeLayout) view.findViewById(R.id.menuPindahTempat);
        menuReimbusement = (RelativeLayout) view.findViewById(R.id.menuReimbusement);
    }

    private void initAction() {
        menuAbsenMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Open_front_camera.class);
                intent.putExtra("absen", "masuk");
                ((Activity) context).startActivity(intent);
            }
        });
        menuAbsenKeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Open_front_camera.class);
                intent.putExtra("absen", "keluar");
                ((Activity) context).startActivity(intent);
            }
        });
        menuIstirahat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Open_front_camera.class);
                intent.putExtra("absen", "istirahat");
                ((Activity) context).startActivity(intent);
            }
        });
        menuMulaiKerja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Open_front_camera.class);
                intent.putExtra("absen", "mulai kerja");
                ((Activity) context).startActivity(intent);
            }
        });
        menuMulaiLembur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Open_front_camera.class);
                intent.putExtra("absen", "mulai lembur");
                ((Activity) context).startActivity(intent);
            }
        });
        menuBerhentiLembur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Open_front_camera.class);
                intent.putExtra("absen", "berhenti lembur");
                ((Activity) context).startActivity(intent);
            }
        });
        menuSampaiTujuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Open_front_camera.class);
                intent.putExtra("absen", "sampai tujuan");
                ((Activity) context).startActivity(intent);
            }
        });
        menuPindahTempat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Open_front_camera.class);
                intent.putExtra("absen", "pindah tempat");
                ((Activity) context).startActivity(intent);
            }
        });
        menuReimbusement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Open_front_camera.class);
                intent.putExtra("absen", "reimbusement");
                ((Activity) context).startActivity(intent);
            }
        });
    }
}
