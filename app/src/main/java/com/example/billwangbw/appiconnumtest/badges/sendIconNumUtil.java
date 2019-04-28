package com.example.billwangbw.appiconnumtest.badges;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

/**
 * 创建时间： 2019/3/12 0012.
 * 创建人：  yanbin
 * 功能：
 */

public class sendIconNumUtil {

    private static IconBadgeNumManager setIconBadgeNumManager = new IconBadgeNumManager();

    /**
     * 设置角标（不带出现通知栏）
     *
     * @param i        角标数字（为0时清空角标）
     * @param context  上下文
     * @param notIfyId 通知id
     */
    public static void sendIconNumNotification(int i, Application context, int notIfyId) {
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (nm == null) return;
        String notificationChannelId = null;

        Notification notification = null;
        try {
            notification = new NotificationCompat.Builder(context, notificationChannelId)
                    .setSmallIcon(context.getApplicationInfo().icon)
                    .setWhen(System.currentTimeMillis())
                    .setAutoCancel(true)
                    .build();
            notification = setIconBadgeNumManager.setIconBadgeNum(context, notification, i);
            nm.notify(notIfyId, notification);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置角标并且带通知栏
     *
     * @param i             角标数字（为0时清空角标）
     * @param context       上下文
     * @param notifyID      通知id
     * @param pendingIntent 跳转事件
     * @param title         标题
     * @param channelId     通知栏 id
     * @param SmallIcon     图标
     * @param Ticker
     * @param ContentText   通知内容
     * @param channelName   通知栏渠道划分（用来区分通知类型如果不传默认为其他分类）
     */
    public static void sendIconNumNotification(int i, Application context, int notifyID, PendingIntent pendingIntent, String title, String channelId, int SmallIcon
            , String Ticker, String ContentText, String channelName) {

        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (nm == null) return;
        Notification notification = null;
        String notificationChannelId = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = createNotificationChannel(channelId, channelName);
            nm.createNotificationChannel(notificationChannel);
            notificationChannelId = notificationChannel.getId();

        }
        notification = new NotificationCompat.Builder(context, notificationChannelId)
                .setSmallIcon(context.getApplicationInfo().icon)
                .setWhen(System.currentTimeMillis())
                .setTicker(Ticker)
                .setContentTitle(title)
                .setContentText(ContentText)
                .setSmallIcon(SmallIcon)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();
        try {
            notification = setIconBadgeNumManager.setIconBadgeNum(context, notification, i);
            nm.notify(notifyID, notification);
        } catch (Exception e) {
            if (e.toString().equals(""))
                Log.d("sendIconNumUtil", e.toString());
        }
    }

    /**
     * 设置角标并且带通知栏(不设置通知栏渠道 默认为渠道为：其他)
     *
     * @param i             角标数字（为0时清空角标）
     * @param context       上下文
     * @param notifyID      通知id
     * @param pendingIntent 跳转事件
     * @param title         标题
     * @param channelId     通知栏 id
     * @param SmallIcon     图标
     * @param Ticker
     * @param ContentText   通知内容
     */
    public static void sendIconNumNotification(int i, Application context, int notifyID, PendingIntent pendingIntent, String title, String channelId, int SmallIcon
            , String Ticker, String ContentText) {

        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (nm == null) return;
        Notification notification = null;
        String notificationChannelId = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = createNotificationChannel(channelId, "");
            nm.createNotificationChannel(notificationChannel);
            notificationChannelId = notificationChannel.getId();

        }
        notification = new NotificationCompat.Builder(context, notificationChannelId)
                .setSmallIcon(context.getApplicationInfo().icon)
                .setWhen(System.currentTimeMillis())
                .setTicker(Ticker)
                .setContentTitle(title)
                .setContentText(ContentText)
                .setSmallIcon(SmallIcon)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();
        try {
            notification = setIconBadgeNumManager.setIconBadgeNum(context, notification, i);
            nm.notify(notifyID, notification);
        } catch (Exception e) {
            if (e.toString().equals(""))
                Log.d("sendIconNumUtil", e.toString());
        }
    }

    /**
     * 使用自定义通知栏
     *
     * @param i            角标显示数量
     * @param context      上下文
     * @param notifyID     通知id
     * @param notification 自定义通知栏
     * @param nm           通知栏管理器
     */
    public static void sendIconNumNotification(int i, Application context, int notifyID, Notification notification, NotificationManager nm) {

        try {
            notification = setIconBadgeNumManager.setIconBadgeNum(context, notification, i);
            nm.notify(notifyID, notification);
        } catch (Exception e) {
            Log.d("sendIconNumUtil", e.toString());
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private static NotificationChannel createNotificationChannel(String channelId, String channelName) {
        String channelNames = channelName;
        NotificationChannel channel = null;
        channel = new NotificationChannel(channelId,
                (channelNames.equals("") ? "其他" : channelNames), NotificationManager.IMPORTANCE_DEFAULT);
        channel.canBypassDnd();//是否绕过勿扰模式
        channel.setBypassDnd(true);
        channel.enableLights(true); //是否在桌面icon右上角展示小红点
        channel.setLightColor(Color.RED); //小红点颜色
        channel.setShowBadge(true); //是否在久按桌面图标时显示此渠道的通知
        return channel;
    }


}
