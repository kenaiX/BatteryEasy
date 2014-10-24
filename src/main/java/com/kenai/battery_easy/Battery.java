package com.kenai.battery_easy;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;

import com.kenai.function.state.XState;

import cc.kenai.common.ad.KenaiTuiguang;
import cc.kenai.common.ad.LoadDialog;
import cc.kenai.common.program.Question;
import cc.kenai.common.stores.StoreUtil;

public class Battery extends PreferenceActivity {
    Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        /**
         * 适配4.0的主题
         */
        if (XState.get_issdk14())
            setTheme(16974123);
        super.onCreate(savedInstanceState);
        context = getBaseContext();
        /**
         * 配置初始化
         */
        if (XState.get_is_need_first_reset(context)) {
        }
        /**
         * 版本更新
         */
        if (XState.get_isfirst(context)) {
            LoadDialog.showDialog(Battery.this);
        }
        /**
         * 载入配置
         */
        addPreferencesFromResource(R.xml.settings_battery);
        load_button();
        /**
         * 启动服务
         */
        startService(new Intent(Battery.this, ServiceforBattery.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    private final void load_button() {
        load_button_tuijian();
        PreferenceScreen esc = (PreferenceScreen) findPreference("Esc");
        esc.setOnPreferenceClickListener(new OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                Intent it = new Intent(Battery.this, ServiceforBattery.class);
                stopService(it);
                finish();
                System.exit(0);
                return true;
            }
        });
        PreferenceScreen call = (PreferenceScreen) findPreference("call");
        call.setOnPreferenceClickListener(new OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                Question.NotificationAndDialog(Battery.this);
                return false;
            }
        });
    }

    private final void load_button_tuijian() {
        StoreUtil.bindClick(Battery.this, findPreference("update"), "d208014990f942cc94fbbe184d0a6d87");

        StoreUtil.bindClick(Battery.this, findPreference("zhichi"), "e4f6ea40cd134c57878623089096fa99");

        findPreference("tuiguang").setOnPreferenceClickListener(new OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                KenaiTuiguang.show(getBaseContext());
                return true;
            }
        });

    }
}
