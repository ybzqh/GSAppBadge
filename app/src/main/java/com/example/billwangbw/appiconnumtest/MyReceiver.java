package com.example.billwangbw.appiconnumtest;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;

import com.tot.badges.sendIconNumUtil;

import java.net.URISyntaxException;

import cn.jpush.android.api.JPushInterface;

import static android.content.Context.NOTIFICATION_SERVICE;
import static com.example.billwangbw.appiconnumtest.MainActivity.i;

/**
 * 创建时间： 2019/3/14 0014.
 * 创建人：  yanbin
 * 功能：
 */

public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "JIGUANG";
    private NotificationManager notificationManager;
    public static String regId;
    String extra_json;

    @Override
    public void onReceive(Context context, Intent intent) {

        try {
            Bundle bundle = intent.getExtras();

            if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
                regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
                Log.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);

                //send the Registration Id to your server...

            } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
                Log.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息(内容为): " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
                processCustomMessage(context, bundle);
                i++;
                // 自定义消息不是通知，默认不会被SDK展示到通知栏上，极光推送仅负责透传给SDK。其内容和展示形式完全由开发者自己定义。
                // 自定义消息主要用于应用的内部业务逻辑和特殊展示需求
            } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
                Log.d(TAG, "[MyReceiver] 接收到推送下来的通知");
                i++;
                extra_json = bundle.getString(JPushInterface.EXTRA_EXTRA);
                if (!TextUtils.isEmpty(extra_json))
                    Log.d(TAG, "[MyReceiver] 接收到推送下来的通知附加字段" + extra_json);

                // 可以利用附加字段来区别Notication,指定不同的动作,extra_json是个json字符串
                // 通知（Notification），指在手机的通知栏（状态栏）上会显示的一条通知信息
            } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {

                // 在这里根据 JPushInterface.EXTRA_EXTRA(附加字段) 的内容处理代码，
                // 比如打开新的Activity， 打开一个网页等..

            } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
                Log.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
                //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..
                i++;
            } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
                boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
                Log.w(TAG, "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
            } else {
                Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
            }

            sendIconNumUtil.sendIconNumNotification(i, (Application) context.getApplicationContext(),222);
        } catch (Exception e) {

        }

    }


    private void processCustomMessage(Context context, Bundle bundle) {

        String channelID = "1";
        String channelName = "channel_name";
        String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        // 跳转的Activity
        Intent intent = new Intent(context, Main2Activity.class);
        intent.putExtra("test", message);

        Log.d(TAG, message);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        // 获得系统推送的自定义消息


        notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);

        //适配安卓8.0的消息渠道
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }


        NotificationCompat.Builder notification = new NotificationCompat.Builder(context, channelID);

        notification.setAutoCancel(true)
                .setContentText(message)
                .setContentTitle("我是Title")
                .setTicker("托儿所")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setDefaults(Notification.DEFAULT_ALL)
                .setContentIntent(pendingIntent);

        notificationManager.notify((int) (System.currentTimeMillis() / 1000), notification.build());

        // managers.cancel(R.mipmap.ic_launcher);

    }
}
