package gmedia.net.id.OnTime.menu_gaji;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import gmedia.net.id.OnTime.R;

public class MenuDetailInfoGaji extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu_detail_info_gaji);
		ActionBar actionBar = getSupportActionBar();
		actionBar.setTitle("Info Detail Gaji");
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setElevation(0);
		initUI();
		initAction();
	}

	private void initUI() {

	}

	private void initAction() {

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				onBackPressed();
				return true;
		}
		return super.onOptionsItemSelected(item);

	}
}
