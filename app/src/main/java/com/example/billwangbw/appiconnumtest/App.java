package com.example.billwangbw.appiconnumtest;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;

import java.util.List;

/**
 * 创建时间： 2019/3/11 0011.
 * 创建人：  yanbin
 * 功能：
 */

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.tot.badges.sendIconNumUtil;

import java.util.concurrent.TimeUnit;

import cn.jpush.android.api.JPushInterface;

public class App extends Application {
    private ActivityManager activityManager;
    private String packageName;
    private boolean stop = false;
    private int mFinalCount = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        packageName = this.getPackageName();
     JPushInterface.setDebugMode(true);
      JPushInterface.init(this);

        //设置每次进入app时清空角标  如果需自定义则在application的onCreate方法调用
        // registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks()
        sendIconNumUtil.init(this);
//
//        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
//
//            @Override
//            public void onActivityCreated(Activity activity, Bundle bundle) {
//                Log.d("test==", "我是onActivityCreated");
//            }
//
//            @Override
//            public void onActivityStarted(Activity activity) {
//
//                    Log.d("test==", "回到了前台了");
//                    sendIconNumUtil.sendIconNumNotification(0, (Application) getApplicationContext(), 222);
//                //测试修改
//            }
//
//            @Override
//            public void onActivityResumed(Activity activity) {
//                Log.d("test==", "我是onActivityResumed");
//            }
//
//            @Override
//            public void onActivityPaused(Activity activity) {
//                Log.d("test==", "我是onActivityPaused");
//            }
//
//            @Override
//            public void onActivityStopped(Activity activity) {
//
//                    Log.d("test==", "滚后台去了");
//
//            }
//
//            @Override
//            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
//                Log.d("test==", "我是onActivitySaveInstanceState");
//            }
//
//            @Override
//            public void onActivityDestroyed(Activity activity) {
//                Log.d("test==", "我是onActivityDestroyed");
//            }
//        });
    }


//    private class AppStatus implements Runnable {
//        @Override
//        public void run() {
//            stop = false;
//
//            while (!stop) {
//                try {
//                    if (appOnForeground()) {
//                        System.out.println("当前App处于前台");
//                    } else {
//                        System.out.println("当前App处于后台");
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//                try {
//                    TimeUnit.SECONDS.sleep(1);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//    private boolean appOnForeground() {
//        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
//
//        if (appProcesses == null)
//            return false;
//
//        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
//            if (appProcess.processName.equals(packageName) && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
//                return true;
//            }
//        }
//
//        return false;
//    }
//
//    private void processCustomMessage(Context context, Bundle bundle) {
//
//        String channelID = "1";
//        String channelName = "channel_name";
//
//        // 跳转的Activity
//        Intent intent = new Intent(context, Main2Activity.class);
//        intent.putExtras(bundle);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
//
//        // 获得系统推送的自定义消息
//        String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
//
//        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
//
//        //适配安卓8.0的消息渠道
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);
//            notificationManager.createNotificationChannel(channel);
//        }
//
//        NotificationCompat.Builder notification =
//                new NotificationCompat.Builder(context, channelID);
//
//        notification.setAutoCancel(true)
//                .setContentText(message)
//                .setContentTitle("我是Title")
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setDefaults(Notification.DEFAULT_ALL)
//                .setContentIntent(pendingIntent);
//
//        notificationManager.notify((int) (System.currentTimeMillis() / 1000), notification.build());
//    }
}