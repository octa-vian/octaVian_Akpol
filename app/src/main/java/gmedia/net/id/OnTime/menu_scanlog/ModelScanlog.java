package gmedia.net.id.OnTime.menu_scanlog;

public class ModelScanlog {
	private String scanDate, keterangan;

	public ModelScanlog(String scan_date, String keterangan) {
		this.scanDate = scan_date;
		this.keterangan = keterangan;
	}

	public String getScanDate() {
		return scanDate;
	}

	public void setScanDate(String scanDate) {
		this.scanDate = scanDate;
	}

	public String getKeterangan() {
		return keterangan;
	}

	public void setKeterangan(String keterangan) {
		this.keterangan = keterangan;
	}
}
