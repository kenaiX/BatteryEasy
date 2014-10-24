package com.kenai.battery_easy;

import com.kenai.function.setting.XSetting;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AutoStart extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		String locIntent = intent.getAction();
		if (Intent.ACTION_BOOT_COMPLETED.equals(locIntent)) {
			if (XSetting
					.xget_boolean(context, R.string.main_boolean_auto_start)) {
				context.startService(new Intent(context,
						ServiceforBattery.class));
			}
		}

	}
}
