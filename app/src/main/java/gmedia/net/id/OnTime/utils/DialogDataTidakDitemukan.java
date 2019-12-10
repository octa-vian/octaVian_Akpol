package gmedia.net.id.OnTime.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;

import gmedia.net.id.OnTime.R;

public class DialogDataTidakDitemukan {
	private Context context;
	private Dialog dialog;

	public DialogDataTidakDitemukan(Context context) {
		this.context = context;
		dialog = new Dialog(context);
		dialog.setContentView(R.layout.popup_data_tidak_ditemukan);
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		RelativeLayout btnClose = (RelativeLayout) dialog.findViewById(R.id.closePopupDataTidakDitemukan);
		btnClose.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				dialog.dismiss();
			}
		});
		dialog.setCanceledOnTouchOutside(false);
	}

	public void ShowDialog() {
		dialog.show();
		final Handler handler = new Handler();
		final Runnable runnable = new Runnable() {
			@Override
			public void run() {
				if (dialog.isShowing()) {
					dialog.dismiss();
				}
			}
		};

		dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
			@Override
			public void onDismiss(DialogInterface dialog) {
				handler.removeCallbacks(runnable);
			}
		});
		handler.postDelayed(runnable, 3000);
	}

	public void DismissDialog() {
		dialog.dismiss();
	}
}
