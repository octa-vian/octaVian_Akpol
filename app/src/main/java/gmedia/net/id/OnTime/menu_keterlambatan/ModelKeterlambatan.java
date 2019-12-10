package gmedia.net.id.OnTime.menu_keterlambatan;

public class ModelKeterlambatan {
	private String tanggal, scanMasuk, jamMasuk, totalTerlambat;

	public ModelKeterlambatan(String tgl, String scan_masuk, String jam_masuk, String total_menit) {
		this.tanggal = tgl;
		this.scanMasuk = scan_masuk;
		this.jamMasuk = jam_masuk;
		this.totalTerlambat = total_menit;
	}

	public String getTanggal() {
		return tanggal;
	}

	public void setTanggal(String tanggal) {
		this.tanggal = tanggal;
	}

	public String getScanMasuk() {
		return scanMasuk;
	}

	public void setScanMasuk(String scanMasuk) {
		this.scanMasuk = scanMasuk;
	}

	public String getJamMasuk() {
		return jamMasuk;
	}

	public void setJamMasuk(String jamMasuk) {
		this.jamMasuk = jamMasuk;
	}

	public String getTotalTerlambat() {
		return totalTerlambat;
	}

	public void setTotalTerlambat(String totalTerlambat) {
		this.totalTerlambat = totalTerlambat;
	}
}
