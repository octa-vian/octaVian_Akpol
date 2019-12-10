package gmedia.net.id.OnTime.menu_approval_cuti;

public class ModelApprovalCuti {
	private String nama, tglAwal, tglAkhir, alasan, id;

	public ModelApprovalCuti(String nama, String awal, String akhir, String alasan, String id) {
		this.nama = nama;
		this.tglAwal = awal;
		this.tglAkhir = akhir;
		this.alasan = alasan;
		this.id = id;
	}

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public String getTglAwal() {
		return tglAwal;
	}

	public void setTglAwal(String tglAwal) {
		this.tglAwal = tglAwal;
	}

	public String getTglAkhir() {
		return tglAkhir;
	}

	public void setTglAkhir(String tglAkhir) {
		this.tglAkhir = tglAkhir;
	}

	public String getAlasan() {
		return alasan;
	}

	public void setAlasan(String alasan) {
		this.alasan = alasan;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
