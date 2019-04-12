package com.example.billwangbw.appiconnumtest;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * 创建时间： 2019/3/11 0011.
 * 创建人：  yanbin
 * 功能：监听通知栏消失
 */

public class NotificationBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        int type = intent.getIntExtra("type",1);
        if (type == -1){
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancel(type);
         if (action.equals("notification_cancelled")){
             Log.d("LogTestNotification","h删除通知栏所有成功");
         }
        }
    }
}
