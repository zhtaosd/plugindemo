package com.plugin.test;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.plugin.bridge.PayInterfaceActivity;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * 加载目的应用的壳
 */
public class ProxyActivity extends Activity {
    private String className;
    PayInterfaceActivity payInterfaceActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        className = getIntent().getStringExtra("className");
        try {
            Class activityClass = getClassLoader().loadClass(className);
            Constructor constructor = activityClass.getConstructor(new Class[]{});
            Object instance = constructor.newInstance(new Object[]{});
            payInterfaceActivity = (PayInterfaceActivity) instance;
            payInterfaceActivity.attach(this);
            Bundle bundle = new Bundle();
            payInterfaceActivity.onCreate(bundle);
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
    public void startActivity(Intent intent) {
        String className1 = intent.getStringExtra("className");
        Intent intent1 = new Intent(this,ProxyActivity.class);
        intent1.putExtra("calssName",className1);
        super.startActivity(intent);
    }

    @Override
    public ClassLoader getClassLoader() {
        return PluginManager.getInstance().getDexClassLoader();
    }

    @Override
    public Resources getResources() {
        return PluginManager.getInstance().getResources();
    }

    @Override
    protected void onStart() {
        super.onStart();
        payInterfaceActivity.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        payInterfaceActivity.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        payInterfaceActivity.onPause();
    }
}
