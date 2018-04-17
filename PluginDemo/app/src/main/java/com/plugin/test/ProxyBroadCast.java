package com.plugin.test;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.plugin.bridge.PayInterfaceBroadcast;

import java.lang.reflect.Constructor;

public class ProxyBroadCast extends BroadcastReceiver {
    private String className;
    PayInterfaceBroadcast payInterfaceBroadcast;

    public ProxyBroadCast(String className, Context context) {
        this.className = className;
        try {
            Class loadClass = PluginManager.getInstance().getDexClassLoader().loadClass(className);
            Constructor<?> localConstructor = loadClass.getConstructor(new Class[]{});
            Object instance = localConstructor.newInstance(new Object[]{});
            payInterfaceBroadcast = (PayInterfaceBroadcast) instance;
            payInterfaceBroadcast.attach(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        payInterfaceBroadcast.onReceive(context, intent);
    }
}
