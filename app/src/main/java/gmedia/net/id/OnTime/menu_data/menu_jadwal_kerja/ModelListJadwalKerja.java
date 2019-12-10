package gmedia.net.id.OnTime.menu_data.menu_jadwal_kerja;

public class ModelListJadwalKerja {
    private String tanggalJadwalKerja, shiftJadwalKerja, jamMasukJadwalKerja, jamKeluarJadwalKerja;

    public ModelListJadwalKerja(String tgl, String shift, String jam_masuk, String jam_pulang) {
        this.tanggalJadwalKerja = tgl;
        this.shiftJadwalKerja = shift;
        this.jamMasukJadwalKerja = jam_masuk;
        this.jamKeluarJadwalKerja = jam_pulang;
    }

    public String getTanggalJadwalKerja() {
        return tanggalJadwalKerja;
    }

    public void setTanggalJadwalKerja(String tanggalJadwalKerja) {
        this.tanggalJadwalKerja = tanggalJadwalKerja;
    }

    public String getShiftJadwalKerja() {
        return shiftJadwalKerja;
    }

    public void setShiftJadwalKerja(String shiftJadwalKerja) {
        this.shiftJadwalKerja = shiftJadwalKerja;
    }

    public String getJamMasukJadwalKerja() {
        return jamMasukJadwalKerja;
    }

    public void setJamMasukJadwalKerja(String jamMasukJadwalKerja) {
        this.jamMasukJadwalKerja = jamMasukJadwalKerja;
    }

    public String getJamKeluarJadwalKerja() {
        return jamKeluarJadwalKerja;
    }

    public void setJamKeluarJadwalKerja(String jamKeluarJadwalKerja) {
        this.jamKeluarJadwalKerja = jamKeluarJadwalKerja;
    }
}
