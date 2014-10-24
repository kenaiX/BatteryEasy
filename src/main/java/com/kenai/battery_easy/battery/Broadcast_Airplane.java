package com.kenai.battery_easy.battery;

import com.kenai.battery_easy.R;
import com.kenai.battery_easy.ServiceforBattery;
import com.kenai.function.message.XToast;
import com.kenai.function.setting.XSetting;
import com.kenai.function.state.XState;
import com.kenai.function.time.XTime;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class Broadcast_Airplane extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		String locIntent = intent.getAction();
		if (Intent.ACTION_AIRPLANE_MODE_CHANGED.equals(locIntent)) {
			// 关闭或打开飞行模式时的广播
			try {
				boolean state =
				// intent.getBooleanExtra("state", true);
				intent.getExtras().getBoolean("state");
				if (state) {
					feixingmoshi_on(context);
				} else {
					feixingmoshi_off(context);
				}
			} catch (Exception e) {
				XToast.xToast_byID(context, R.string.error_feixingmoshi);
			}

		}

	}

	// 飞行模式 电量统计模块//飞行模式 电量统计模块//飞行模式 电量统计模块//飞行模式 电量统计模块//飞行模式 电量统计模块//飞行模式
	// 电量统计模块//飞行模式 电量统计模块//飞行模式 电量统计模块

	private final void feixingmoshi_on(Context context) {
		if (XSetting
				.xget_boolean(context, R.string.main_boolean_feixing_sunhao)) {
			XToast.xToast(context, "飞行损耗 已开启");
			set_battery(context);
			XSetting.xset_string_int(
					context,
					"battery_feixing_time",
					XTime.gettime_partly_String(4) + ":"
							+ XTime.gettime_partly_String(5));
		}
	}

	private final void feixingmoshi_off(Context context) {
		if (XSetting
				.xget_boolean(context, R.string.main_boolean_feixing_sunhao)) {
			myNotification(context, "082e2554efb948bf88bf2d3db55f3b00");
		}
	}

	private final void myNotification(Context context, String name) {
		Intent intent = new Intent("com.kenai.nothing");
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
				intent, 0);

		Notification notification;
		if (!XState.get_issdk14()) {
			notification = new Notification(R.drawable.mylog, "飞行损耗提醒",
					System.currentTimeMillis());
			notification.icon = R.drawable.ic_statebar_feixingmoshi;
		} else {
			notification = new Notification(R.drawable.mylog_for4, "飞行损耗提醒",
					System.currentTimeMillis());
			notification.icon = R.drawable.ic_statebar_feixingmoshi_flyme;
		}

		notification.setLatestEventInfo(
				context,
				XSetting.xget_string(context, "battery_feixing_time") + "~"
						+ XTime.gettime_partly_String(4) + ":"
						+ XTime.gettime_partly_String(5),
				print_battery(context), pendingIntent);

		com.kenai.function.meizu.MeizuNotification.internalApp(notification);
		notification.flags |= Notification.FLAG_AUTO_CANCEL;

		NotificationManager manager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		if (ServiceforBattery.precent != 0)
			manager.notify(R.drawable.ic_statebar_feixingmoshi, notification);
		else
			XToast.xToast(context, "飞行损耗 意料之内的错误！");
	}

	private void set_battery(Context context) {
		XSetting.xset_string_int(context, "last_battery", ""
				+ ServiceforBattery.precent);
	}

	private int get_battery(Context context) {
		return XSetting.xget_int(context, "last_battery");
	}

	private String print_battery(Context context) {
		String s = "";
		int b1;
		int b2;
		b1 = get_battery(context);
		set_battery(context);
		b2 = get_battery(context);

		if (b2 - b1 > 0)
			s = "飞行模式期间补充电量：" + (b2 - b1) + "%";
		else if (b2 - b1 == 0)
			s = "飞行模式期间消耗不足1%";
		else
			s = "飞行模式期间消耗电量：" + (b1 - b2) + "%";
		return s;
	}

}
