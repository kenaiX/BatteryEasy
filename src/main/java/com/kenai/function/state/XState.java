package com.kenai.function.state;

import com.kenai.function.setting.XSetting;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;

public class XState {
	private static boolean issdk14 = false;

	//

	// //////////////////////////////////////////////////////////gggggggggggggggeeeeeeeeeeeeeeeeeettttttttttttttttt

	private static int issdk14hasdone = 0;

	public static boolean get_issdk14() {
		if (issdk14hasdone == 0) {
			issdk14hasdone = 1;
			String shouji = android.os.Build.VERSION.SDK;
			int sdk = Integer.parseInt(shouji);
			if (sdk < 12)
				issdk14 = false;
			else
				issdk14 = true;
		}
		return issdk14;
	}

	/**
	 * 用于检测是否属于版本升级
	 * 
	 * @param context
	 * @return
	 */
	public static boolean get_isfirst(Context context) {
		int ver1 = 0;
		try {
			PackageInfo info = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
			ver1 = info.versionCode;
		} catch (NameNotFoundException e) {

		}
		int ver2 = XSetting.xget_int(context, "ver_kenai");
		if (ver1 == ver2) {
			return false;
		} else {
			XSetting.xset_string_int(context, "ver_kenai", String.valueOf(ver1));
			return true;
		}
	}

	/**
	 * 用于检测是否属于初次安装
	 * 
	 * @param context
	 * @return
	 */
	public static boolean get_is_need_first_reset(Context context) {
		int ver = XSetting.xget_int(context, "is_need_first_reset");
		if (ver != 0) {
			return false;
		} else {
			XSetting.xset_string_int(context, "is_need_first_reset", "1");
			return true;
		}
	}

	// //////////////////////////////////////////////////////////sssssssssssseeeeeeeeeeeeeeeeeettttttttttttttttt

}
