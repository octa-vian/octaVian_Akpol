package gmedia.net.id.OnTime.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import gmedia.net.id.OnTime.MainActivityBaru;

public class BootUpReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		Intent i = new Intent(context, MainActivityBaru.class);
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(i);
	}
}
