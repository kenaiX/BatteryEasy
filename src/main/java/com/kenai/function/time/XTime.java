package com.kenai.function.time;
import android.text.format.Time;

public class XTime {
	
	/**
	 * 1:year 2:month 3:monthday 4:hour 5:minute 6:second
	 */
	public final static int gettime_partly(int part) {
		Time t=new Time(); // or Time t=new Time("GMT+8"); 加上Time Zone资料。
		t.setToNow(); // 取得系统时间。
		int time_partly=0;
		switch(part){
		case 1:
			time_partly= t.year;
			break;
		case 2:
			time_partly= t.month;
			break;
		case 3:
			time_partly = t.monthDay;
			break;
		case 4:
			time_partly = t.hour; // 0-23
			break;
		case 5:
			time_partly = t.minute;
			break;
		case 6:
			time_partly = t.second;
			break;
		}
		return time_partly;
	}
	/**
	 * 1:year 2:month 3:monthday  4:hour 5:minutes 
	 * eg 21 00 02
	 */
	public final static String gettime_partly_String(int part) {
		Time t = new Time(); // or Time t=new Time("GMT+8"); 加上Time Zone资料。
		t.setToNow(); // 取得系统时间。
		String time_partly = "";
		switch (part) {
		case 1:
			time_partly = String.valueOf(t.year);
			break;
		case 2:
			if (t.month > 9)
				time_partly = String.valueOf(t.month);
			else
				time_partly = "0" + String.valueOf(t.month);
			break;
		case 3:
			if (t.monthDay > 9)
				time_partly = String.valueOf(t.monthDay);
			else
				time_partly = "0" + String.valueOf(t.monthDay);
			break;

		case 4:
			if (t.hour > 9)
				time_partly = String.valueOf(t.hour);
			else
				time_partly = "0" + String.valueOf(t.hour);
			break;

		case 5:
			if (t.minute > 9)
				time_partly = String.valueOf(t.minute);
			else
				time_partly = "0" + String.valueOf(t.minute);
			break;
		}
		return time_partly;
	}

	
	
	
	
}
