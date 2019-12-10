package gmedia.net.id.OnTime.menu_approval_ijin;

public class ModelApprovalIjin {
	private String nama, tgl, alasan, id;

	public ModelApprovalIjin(String nama, String tgl, String alasan, String id) {
		this.nama = nama;
		this.tgl = tgl;
		this.alasan = alasan;
		this.id = id;
	}

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
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

	public String getTgl() {
		return tgl;
	}

	public void setTgl(String tgl) {
		this.tgl = tgl;
	}
}
