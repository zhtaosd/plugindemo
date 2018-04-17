package com.plugin.test;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.plugin.bridge.PayIntefaceService;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ProxyService extends Service {

    String serviceName;
    PayIntefaceService payIntefaceService;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        init(intent);
        return null;
    }

    private void init(Intent intent) {
        serviceName = intent.getStringExtra("serviceName");

        try {
            Class loadClass= PluginManager.getInstance().getDexClassLoader().loadClass(serviceName);
            Constructor<?> localConstructor = loadClass.getConstructor(new Class[]{});
            Object instance = localConstructor.newInstance(new Object[]{});
            payIntefaceService = (PayIntefaceService) instance;
            payIntefaceService.attach(this);
            Bundle bundle = new Bundle();
            bundle.putInt("form",1);
            payIntefaceService.onCreate();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(payIntefaceService==null){
            init(intent);
        }
        return payIntefaceService.onStartCommand(intent,flags,startId);
    }

    @Override
    public void onDestroy() {
        payIntefaceService.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        payIntefaceService.onLowMemory();
        super.onLowMemory();
    }


    @Override
    public boolean onUnbind(Intent intent) {
        payIntefaceService.onUnbind(intent);
        return super.onUnbind(intent);
    }

    @Override
    public void onRebind(Intent intent) {
        payIntefaceService.onRebind(intent);
        super.onRebind(intent);
    }
}
