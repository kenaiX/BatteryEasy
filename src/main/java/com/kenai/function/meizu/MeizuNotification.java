package com.kenai.function.meizu;

import java.lang.reflect.Field;

public class MeizuNotification {
	public static void internalApp(Object notification) {
		try {
			Field field = notification.getClass().getDeclaredField(
					"internalApp");
			try {
				field.set(notification, 1);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
