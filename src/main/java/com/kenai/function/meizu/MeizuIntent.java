package com.kenai.function.meizu;

public class MeizuIntent {
	public static final String NEW_MESSAGE="com.kenai.action.new_message";
	/**
	 * 发送此广播可以锁屏
	 * 不需要申请权限
	 */
	public static final String LOCK_SCREEN="android.intent.action.MEIZU_LOCK_SCREEN_FOR_LAUNCHER";
	/**
	 * 发送此广播，可以调用系统任务管理器
	 */
	public static final String TASK_MANAGER="meizu.intent.double_home_key";
	
	/**
	 * flyme系统切换主页显示会发送此广播
	 * 参数："shown" false为不在主页
	 */
	public static final String CHANGE_HOME="meizu.intent.action.HOME_SHOWN";
	
	
}
