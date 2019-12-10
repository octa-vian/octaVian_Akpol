package gmedia.net.id.OnTime.menu_data.menu_absensi;

public class ModelListAbsensi {
    private String masuk, keluar, keterangan, status;

    public ModelListAbsensi(String scan_masuk, String scan_pulang, String keterangan, String status) {
        this.masuk = scan_masuk;
        this.keluar = scan_pulang;
        this.keterangan = keterangan;
        this.status=status;
    }

    public String getMasuk() {
        return masuk;
    }

    public void setMasuk(String masuk) {
        this.masuk = masuk;
    }

    public String getKeluar() {
        return keluar;
    }

    public void setKeluar(String keluar) {
        this.keluar = keluar;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
