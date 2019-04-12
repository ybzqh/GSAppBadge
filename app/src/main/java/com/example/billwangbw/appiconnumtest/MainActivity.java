package com.example.billwangbw.appiconnumtest;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.tot.badges.IconBadgeNumManager;
import com.tot.badges.LauncherHelper;
import com.tot.badges.sendIconNumUtil;

import java.util.List;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    IconBadgeNumManager setIconBadgeNumManager = new IconBadgeNumManager();
    public static int i = 0;
    private TextView mTvOne, mTvTwo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //sendIconNumNotification(0);
        // sendIconNumUtil.sendIconNumNotification(0, getApplication());

        Log.e(TAG, Build.MANUFACTURER);
        Log.e(TAG, new LauncherHelper().getLauncherPackageName(this));
//        mTvOne = findViewById(R.id.button1)
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//               Intent i = new Intent(MainActivity.this, MyService.class);
//                startService(i);
//            //    i++;
//                (int i, Application context, int notifyID, String title, PendingIntent pendingIntent, String channelId, int SmallIcon
//            , String Ticker, String ContentText)
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                intent.putExtra("test", "我是测试从通知栏传过来的数据");
                i++;
                String chann = "test"+ i;
                PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
                sendIconNumUtil.sendIconNumNotification(i, getApplication(), 666, pendingIntent, "testTitle", chann, R.mipmap.ic_launcher, "我是ticker", "我是Contentext");
                // sendIconNumNotification(5);
            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {
                String notificationChannelId = null;
                NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel notificationChannel = createNotificationChannel();

                    nm.createNotificationChannel(notificationChannel);
                    notificationChannelId = notificationChannel.getId();


                }
                Notification notification = null;
                RemoteViews remoteViews = new RemoteViews(getApplicationContext().getPackageName(),R.layout.image_item);
                remoteViews.setTextViewText(R.id.mTv,"wohahahahahahah");
                notification = new NotificationCompat.Builder(MainActivity.this, notificationChannelId)
                        .setSmallIcon(getApplicationInfo().icon)
                        .setWhen(System.currentTimeMillis())
//                        .setContentTitle("2019年3月14日")
//                        .setContentText("天气晴，心情困")
//                        .setContentTitle("title")
//                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setAutoCancel(true)
                        .setCustomContentView(remoteViews)
                       .build();
                sendIconNumUtil.sendIconNumNotification(5,getApplication(),555,notification,nm);
                //     Intent i = new Intent(MainActivity.this, MyService.class);
//                stopService(i);
            }
        });

        Intent intentCancel = new Intent(getApplicationContext(), NotificationBroadcastReceiver.class);
        PendingIntent pendingIntentCancel = PendingIntent.getBroadcast(getApplicationContext(), 0,
                intentCancel, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(MainActivity.this);


        mBuilder.setDeleteIntent(pendingIntentCancel);
        //取消消息回调
    }

    private void sendIconNumNotification(int i) {
        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (nm == null) return;
        String notificationChannelId = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = createNotificationChannel();

            nm.createNotificationChannel(notificationChannel);
            notificationChannelId = notificationChannel.getId();


        } else {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                addNotificationChannel();
//            }
        }
        Intent intent = new Intent(this, Main2Activity.class);
        intent.putExtra("test", "我是测试从通知栏传过来的数据");
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        Notification notification = null;
        try {
            notification = new NotificationCompat.Builder(this, notificationChannelId)
                    .setSmallIcon(getApplicationInfo().icon)
                    .setWhen(System.currentTimeMillis())
                    .setContentTitle("2019年3月14日")
                    .setContentText("天气晴，心情困")
                    .setContentTitle("title")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .build();

            notification = setIconBadgeNumManager.setIconBadgeNum(getApplication(), notification, 5);
            nm.notify(32154, notification);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private static NotificationChannel createNotificationChannel() {
        String channelId = "test";
        NotificationChannel channel = null;
        channel = new NotificationChannel(channelId,
                "Channel1", NotificationManager.IMPORTANCE_DEFAULT);
        channel.canBypassDnd();//是否绕过勿扰模式
        channel.setBypassDnd(true);
        channel.enableLights(true); //是否在桌面icon右上角展示小红点
        channel.setLightColor(Color.RED); //小红点颜色
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
