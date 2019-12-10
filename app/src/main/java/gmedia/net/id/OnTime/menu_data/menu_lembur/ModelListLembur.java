package gmedia.net.id.OnTime.menu_data.menu_lembur;

public class ModelListLembur {
    private String mulai, selesai, keterangan;

    public ModelListLembur(String mulai, String selesai, String keterangan) {
        this.mulai = mulai;
        this.selesai = selesai;
        this.keterangan = keterangan;
    }

    public String getMulai() {
        return mulai;
    }

    public void setMulai(String mulai) {
        this.mulai = mulai;
    }

    public String getSelesai() {
        return selesai;
    }

    public void setSelesai(String selesai) {
        this.selesai = selesai;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
}
