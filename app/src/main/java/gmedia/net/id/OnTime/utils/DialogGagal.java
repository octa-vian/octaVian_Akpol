package gmedia.net.id.OnTime.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import gmedia.net.id.OnTime.R;

public class DialogGagal {
	private Context context;
	private Dialog dialog;
	public static String message = "";

	public DialogGagal(Context context) {
		this.context = context;
		dialog = new Dialog(context);
		dialog.setContentView(R.layout.popup_gagal_custom);
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		TextView messageError = (TextView) dialog.findViewById(R.id.txtMessageError);
		messageError.setText(message);
		RelativeLayout btnUlangi = (RelativeLayout) dialog.findViewById(R.id.btnUlangiLagiPopupGagalCustom);
		btnUlangi.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				dialog.dismiss();
			}
		});
		dialog.setCanceledOnTouchOutside(false);
	}

	public void ShowDialog() {
		dialog.show();
		message = "";
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
