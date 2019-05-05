package com.example.billwangbw.appiconnumtest;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import com.tot.badges.IconBadgeNumManager;
import com.tot.badges.LauncherHelper;
import com.tot.badges.SendIconNumUtil;

import cn.jpush.android.api.JPushInterface;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    IconBadgeNumManager setIconBadgeNumManager = new IconBadgeNumManager();
    public static int i = 0;
    NotificationManager nm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e(TAG, Build.MANUFACTURER);
        Log.e(TAG, new LauncherHelper().getLauncherPackageName(this));
        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                JPushInterface.clearAllNotifications(MainActivity.this);
            SendIconNumUtil.clearAllIconNumNotifucation();
            }
        });
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String notificationChannelId = null;
                NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    //8.0以上需设置消息通道id
                    NotificationChannel notificationChannel = createNotificationChannel();
                    nm.createNotificationChannel(notificationChannel);
                    notificationChannelId = notificationChannel.getId();
                }
                Notification notification = null;
                RemoteViews remoteViews = new RemoteViews(getApplicationContext().getPackageName(), R.layout.image_item);
                remoteViews.setTextViewText(R.id.mTv, "wohahahahahahah");
                notification = new NotificationCompat.Builder(MainActivity.this, notificationChannelId)
                        .setSmallIcon(getApplicationInfo().icon)
                        .setWhen(System.currentTimeMillis())
                        .setAutoCancel(true)
                        .setCustomContentView(remoteViews)
                        .build();
                SendIconNumUtil.sendIconNumNotification(5, getApplication(), 666, notification, nm);

                //点击导航栏的跳转事件（系统默认导航栏）
//                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
//                //从导航传递的数据
//                intent.putExtra("test", "我是测试从通知栏传过来的数据");
//                i++;
//                String chann = "test" + i;
//                PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent,
//                        PendingIntent.FLAG_ONE_SHOT);
//                SendIconNumUtil.sendIconNumNotification(1, getApplication(), i, pendingIntent,
//                        "testTitle", "tests", R.mipmap.ic_launcher,
//                        "我是ticker", i + "", "测试专用渠道2");
                // sendIconNumNotification(5);
            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {
                //自定义导航栏
                String notificationChannelId = null;
                 nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    //8.0以上需设置消息通道id
                    NotificationChannel notificationChannel = createNotificationChannel();
                    nm.createNotificationChannel(notificationChannel);
                    notificationChannelId = notificationChannel.getId();
                }
                Notification notification = null;
                RemoteViews remoteViews = new RemoteViews(getApplicationContext().getPackageName(), R.layout.image_item);
                remoteViews.setTextViewText(R.id.mTv, "wohahahahahahah");
                notification = new NotificationCompat.Builder(MainActivity.this, notificationChannelId)
                        .setSmallIcon(getApplicationInfo().icon)
                        .setWhen(System.currentTimeMillis())
                        .setAutoCancel(true)
                        .setCustomContentView(remoteViews)
                        .build();
                SendIconNumUtil.sendIconNumNotification(5, getApplication(), 555, notification, nm);
            }
        });

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private static NotificationChannel createNotificationChannel() {
        String channelId = "test";
        NotificationChannel channel = null;
        channel = new NotificationChannel(channelId,
                "第四个权限", NotificationManager.IMPORTANCE_HIGH);
        channel.enableLights(true); //是否在桌面icon右上角展示小红点
        channel.setLightColor(Color.BLACK); //小红点颜色
        channel.setShowBadge(true); //是否在久按桌面图标时显示此渠道的通知
        return channel;
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void addNotificationChannel() {
        //点击后进入的界面
        Intent intent = new Intent(this, Main2Activity.class);
        intent.putExtra("test", "我是测试从通知栏传过来的数据");
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);


        Notification notification = new Notification.Builder(this)
                .setTicker("托儿所")
                .setContentTitle("喜之郎")
                .setContentText("儿童节")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)
                .build();

        NotificationManager manager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(666, notification);
    }

}
