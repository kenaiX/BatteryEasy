package com.kenai.battery_easy;

import com.kenai.battery_easy.battery.Broadcast_Battery;
import com.kenai.function.meizu.MeizuNotification;
import com.kenai.function.setting.XSetting;
import com.kenai.function.state.XState;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.BatteryManager;
import android.os.IBinder;

public class ServiceforBattery extends Service implements
		OnSharedPreferenceChangeListener {
	Context context;

	@Override
	public void onCreate() {
		super.onCreate();
		context = getBaseContext();
		XSetting.getSharedPreferences(context)
				.registerOnSharedPreferenceChangeListener(this);
		load_canshu();
		mybind();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		XSetting.getSharedPreferences(context)
				.unregisterOnSharedPreferenceChangeListener(this);
		myunbind();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);
		myNotification();
		return START_STICKY;
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	private final int ID_NOTI = R.drawable.battery_01;

	// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 用于电量获取的参数
	public static int precent;
	private boolean is_update = false;
	private int plugged;
	private int plugged_last;
	private int precent_last;
	private int status;
	private int status_last;
	private int temperature;
	private int temperature_last;

	// 主控制台
	private Broadcast_Battery myBroadcast_main = new Broadcast_Battery() {
		@Override
		public void onReceive(Context context, Intent intent) {
			super.onReceive(context, intent);
			if (intent.getAction().equals(Intent.ACTION_BATTERY_CHANGED)) {
				precent = getPercent();
				temperature = getTemperature();
				plugged = getplugged();
				status = getstatus();
				if (precent_last != precent) {
					precent_last = precent;
					is_update = true;
				}
				if (temperature_last != temperature) {
					temperature_last = temperature;
					is_update = true;
				}
				if (plugged_last != plugged) {
					plugged_last = plugged;
					is_update = true;
				}
				if (status_last != status) {
					status_last = status;
					is_update = true;
				}
				if (is_update) {
					myNotification();
					is_update = false;
				}
			}

		}
	};

	// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 主方法
	private final void mybind() {
		IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
		context.registerReceiver(myBroadcast_main, filter);
	}

	private final void myunbind() {
		context.unregisterReceiver(myBroadcast_main);
	}

	private final void myNotification() {
		Notification notification;
		String ss = "";
		if (plugged == BatteryManager.BATTERY_PLUGGED_AC)
			ss = "交流充电中";
		else if (plugged == BatteryManager.BATTERY_PLUGGED_USB)
			ss = "usb充电中";
		else
			ss = "充电器未连接";

		Intent localIntent = new Intent("");
		localIntent.setComponent(new ComponentName("com.android.settings",
				"com.android.settings.fuelgauge.PowerUsageSummary"));
		localIntent.setAction("android.intent.action.VIEW");
		localIntent.addFlags(268435456);
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
				localIntent, 0);
		if (!XState.get_issdk14())
			notification = new Notification(R.drawable.mylog, "kenai提醒您" + " "
					+ ss, System.currentTimeMillis());
		else
			notification = new Notification(R.drawable.mylog_for4, "kenai提醒您"
					+ " " + ss, System.currentTimeMillis());

		String s = "";
		String sss = "";
		switch (status) {
		case BatteryManager.BATTERY_STATUS_CHARGING:
			sss = "充电中";
			break;
		case BatteryManager.BATTERY_STATUS_DISCHARGING:
			sss = "放电中";
			break;
		case BatteryManager.BATTERY_STATUS_FULL:
			sss = "充满电";
			break;
		case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
			sss = "伪充电";
			break;
		}
		String locs = "";
		if (xianshi_wendu)
			locs = "电量：" + precent + "% ";
		else
			locs = "温度：" + temperature + "°C";
		notification.icon = set_index();
		notification.setLatestEventInfo(context, locs, "状态：" + s + "  " + sss,
				pendingIntent);
		notification.flags |= Notification.FLAG_ONGOING_EVENT; // 放置在"正在运行"栏目中
//		notification.internalApp = 1;
        MeizuNotification.internalApp(notification);
		startForeground(ID_NOTI, notification);
	}

	/**
	 * 设置显示的ico
	 * 
	 * @return
	 */
	private int set_index() {
		int shiwei = 0;// 换算
		int index = 0;// 图标id
		boolean is_wendu = false;
		if (xianshi_wendu)
			is_wendu = true;
		else
			is_wendu = false;

		// 温度显示部分
		if (is_wendu) {
			shiwei = (temperature - 1);
			if (shiwei < 0)
				shiwei = 0;
			else if (shiwei > 99)
				shiwei = 99;
			if (!XState.get_issdk14())
				index = R.drawable.battery_01 + shiwei ;
			else
				index = R.drawable.battery_meizustyle_01 + shiwei ;

		}
		// 电量显示部分
		else {
			shiwei = (precent - 1);
			if (shiwei < 0)
				shiwei = 0;
			else if (shiwei > 99)
				shiwei = 99;
			if (!XState.get_issdk14())
				index = R.drawable.battery_01 + shiwei;
			else
				index = R.drawable.battery_meizustyle_01 + shiwei;
		}
		return index;
	}

	private boolean xianshi_wendu;

	private void load_canshu() {
		xianshi_wendu = XSetting.xget_boolean(context,
				R.string.main_boolean_xianshi_wendu);
	}

	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		if (key.equals(context.getString(R.string.main_boolean_xianshi_wendu))) {
			xianshi_wendu = XSetting.xget_boolean(context,
					R.string.main_boolean_xianshi_wendu);
			myNotification();
		}

	}

}
