package com.zhansy.ordermanage.base;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import com.zhansy.ordermanage.MainActivity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;


/**
 * Created by ZHANSY
 * 邮箱：azhansy@hotmail.com
 *
 * 应用程序管理类，处理所有的Activity、应用程序的退出
 */
public class AppManager {

    private Stack<Activity> activityStack = new Stack<Activity>();

    /**
     * 单例模式
     *
     * @return
     */
    private static AppManager appManagerInstance;

    private AppManager() {
    }

    public static AppManager getAppManager() {
        if (appManagerInstance == null) {
            System.out.println("====AppManager====new instance...");
            synchronized (AppManager.class) {
                if (appManagerInstance == null) {
                    appManagerInstance = new AppManager();
                }
            }
        }
        return appManagerInstance;
    }

    /**
     * 添加当前Activity 到堆栈
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.push(activity);
    }

    public void removeActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity = null;
        }
    }

    /**
     * 获取当前的Activity （堆栈中的最后一个压入的）
     *
     * @return
     */
    public Activity getCurrentActivity() {
        if (activityStack.isEmpty()) {
            return null;
        }
        return activityStack.lastElement();
    }

    /**
     * 销毁当前的Activity
     */
    public void finishActivity() {
        if (!activityStack.isEmpty()) {
            finishActivity(activityStack.lastElement());
        }
    }

    /**
     * 销毁指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 销毁指定的Activity
     *
     * @param clazz
     */
    public void finishActivity(Class<?> clazz) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(clazz)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 销毁指定的Activity
     *
     * @param clazz
     */
    public Activity getActivity(Class<?> clazz) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(clazz)) {
                return activity;
            }
        }
        return null;
    }

    public void finishAllActivity() {

        Iterator<Activity> iterator = activityStack.iterator();

        while (iterator.hasNext()) {
            Activity activity = iterator.next();
            if (activity != null) {
                iterator.remove();
                activity.finish();
            }
        }

        activityStack.clear();
    }

    public void finishAllActivityWithoutMainActivity(Class<?> clazz) {
        Iterator<Activity> iterator = activityStack.iterator();
        List<Activity> removeActivities = new ArrayList<>();
        while (iterator.hasNext()) {
            Activity activity = iterator.next();
            if (activity != null && !activity.getClass().equals(clazz) && !activity.getClass().equals(MainActivity.class)) {
                iterator.remove();
                activity.finish();
                removeActivities.add(activity);
            }
        }
        activityStack.removeAll(removeActivities);
    }

    /**
     * 退出应用程序
     */
    @SuppressWarnings("deprecation")
    public void ExitApp(Context context) {
        finishAllActivity();
        ActivityManager activityMgr = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        activityMgr.restartPackage(context.getPackageName());
        System.exit(0);
    }

    public void printStack() {
        if (activityStack == null) {
            System.out.println("====AppManager====activityStack=null");
            return;
        }
        System.out.println("====AppManager====activityStack.size=" + activityStack.size());
        for (Activity activity : activityStack) {
            String name = activity.getClass().getName();
            System.out.println("====AppManager====foreach activityStack: " + name);
        }
    }

} 