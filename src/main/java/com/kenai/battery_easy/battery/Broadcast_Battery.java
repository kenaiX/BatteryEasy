package com.kenai.battery_easy.battery;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class Broadcast_Battery extends BroadcastReceiver {
	private int percent;
	private int temperature;
	private int status;
	private int plugged;
	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(Intent.ACTION_BATTERY_CHANGED)) {
			int current = intent.getExtras().getInt("level");// 获得当前电量
			int total = intent.getExtras().getInt("scale");// 获得总电量
			percent = current * 100 / total;
			current = intent.getExtras().getInt("temperature");
			temperature = current / 10;
			status = intent.getExtras().getInt("status");
			plugged = intent.getExtras().getInt("plugged");
		}
	}
	public final int getplugged() {
		return plugged;
	}
	public final int getstatus() {
		return status;
	}
	public final int getPercent() {
		return percent;
	}
	public final int getTemperature() {
		return temperature;
	}
}
