package com.plugin.test;


import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

/**
 * 单例模式 运用最简单的单例模式
 */
public class PluginManager {

    private PackageInfo packageInfo;
    private Resources resources;
    private Context context;
    private DexClassLoader dexClassLoader;

    private static final PluginManager instance = new PluginManager();

    private PluginManager() {
    }

    public PackageInfo getPackageInfo() {
        return packageInfo;
    }

    public Resources getResources() {
        return resources;
    }

    public DexClassLoader getDexClassLoader() {
        return dexClassLoader;
    }

    public static PluginManager getInstance() {
        return instance;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void loadPath(Context context) {
        File fileDir = context.getDir("plugin", Context.MODE_PRIVATE);
        String name = "pluginb.apk";
        String path = new File(fileDir, name).getAbsolutePath();
        PackageManager packageManager = context.getPackageManager();
        packageInfo = packageManager.getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES);

        File dexOutFile = context.getDir("dex", Context.MODE_PRIVATE);
        dexClassLoader = new DexClassLoader(path, dexOutFile.getAbsolutePath(), null, context.getClassLoader());

        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssestPath = AssetManager.class.getMethod("addAssetPath",String.class);
            addAssestPath.invoke(assetManager,path);
            resources = new Resources(assetManager,context.getResources().getDisplayMetrics(),context.getResources().getConfiguration());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
