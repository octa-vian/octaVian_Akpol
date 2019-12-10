package gmedia.net.id.OnTime.menu_history_ijin;

public class ModelHistoryIjin {
    private String tanggal, jam, alasan, status;

    public ModelHistoryIjin(String tgl, String jam, String alasan, String keterangan) {
        this.tanggal = tgl;
        this.jam = jam;
        this.alasan = alasan;
        this.status = keterangan;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getJam() {
        return jam;
    }

    public void setJam(String jam) {
        this.jam = jam;
    }

    public String getAlasan() {
        return alasan;
    }

    public void setAlasan(String alasan) {
        this.alasan = alasan;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
