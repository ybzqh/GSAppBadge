package com.tot.badges;

import android.app.Application;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static com.tot.badges.Utils.UNABLE_TO_RESOLVE_INTENT_ERROR_;

public class IconBadgeNumManager {
    private static final String NOTIFICATION_XiaoMi_ERROR = "Xiaomi phones must send notification";
    private static final String NOTIFICATION_GOOGLe_ERROR = "google not support before API O";
    private static final String NOT_SUPPORT_LAUNCHER = "not support your launcher [ default launcher is null ]";
    private static final String NOT_SUPPORT_LAUNCHER_ = "not support ";
    private static final String NOTIFICATION_VIVO_ERROR = "not support : vivo";
    private static final String NOTIFICATION_OPPO_ERROR = "not support : oppo";
    private static final String NOTIFICATION_MEIZU_ERROR = "not support : meizu";
    private Map<String, Object> iconBadgeNumModelMap;
    private LauncherHelper launcherHelper;

    public IconBadgeNumManager() {
        this.launcherHelper = new LauncherHelper();
        iconBadgeNumModelMap = new HashMap<>();
    }


    public Notification setIconBadgeNum(@NonNull Application context, Notification notification, int count) throws Exception {
        switch (getSetIconBadgeNumModel(context)) {
            case LauncherHelper.HUAWEI:
                return setIconHuaWeiBadgeNum(context, notification, count);
            case LauncherHelper.XIAOMI:
                return setXiaoMiIconBadgeNum(context, notification, count);
            case LauncherHelper.VIVO:
                return setVIVOIconBadgeNum(context, notification, count);
            case LauncherHelper.OPPO:
                return setOPPOIconBadgeNum(context, notification, count);
            case LauncherHelper.SAMSUNG:
                return setSamsungIconBadgeNum(context, notification, count);
            case LauncherHelper.MEIZU:
                return setMeiZuIconBadgeNum(context, notification, count);
            case LauncherHelper.GOOGLE:
                return setGoogleIconBadgeNum(context, notification, count);
            default:
                throw new Exception(NOT_SUPPORT_LAUNCHER_ + getSetIconBadgeNumModel(context));
        }

//        IconBadgeNumModel iconBadgeNumModel = getSetIconBadgeNumModel(context);
//        return iconBadgeNumModel.setIconBadgeNum(context, notification, count);

    }

    private String getSetIconBadgeNumModel(@NonNull Application application) throws Exception {
        String launcherName = launcherHelper.getLauncherPackageName(application);
        if (TextUtils.isEmpty(launcherName)) {
            throw new Exception(NOT_SUPPORT_LAUNCHER);
        }
        String launcherType = launcherHelper.getLauncherTypeByName(launcherName);
        if (TextUtils.isEmpty(launcherType)) {
            throw new Exception(NOT_SUPPORT_LAUNCHER_ + launcherName);
        }
        if (iconBadgeNumModelMap.containsKey(launcherType)) {
            return launcherType;
        }
        iconBadgeNumModelMap.put(launcherType, launcherType);
        return launcherName;
    }


    /**
     * https://developer.huawei.com/consumer/cn/devservice/doc/30802
     * <p>华为角标生成方法
     * max:99
     */
    public Notification setIconHuaWeiBadgeNum(@NonNull Application context, Notification notification, int count) throws Exception {
        Bundle bunlde = new Bundle();
        bunlde.putString("package", context.getPackageName()); // com.test.badge is your package name
        bunlde.putString("class", Utils.getInstance().getLaunchIntentForPackage(context)); // com.test. badge.MainActivity is your apk main activity
        bunlde.putInt("badgenumber", count);
        context.getContentResolver().call(Uri.parse("content://com.huawei.android.launcher.settings/badge/"), "change_badge", null, bunlde);
        return notification;
    }


    //Google 生成方法
    public Notification setGoogleIconBadgeNum(@NonNull Application context, Notification notification, int count) throws Exception {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.O) {
            throw new Exception(NOTIFICATION_GOOGLe_ERROR);
        }
        Intent intent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
        intent.putExtra("badge_count", count);
        intent.putExtra("badge_count_package_name", context.getPackageName());
        intent.putExtra("badge_count_class_name", Utils.getInstance().getLaunchIntentForPackage(context)); // com.test. badge.MainActivity is your apk main activity
        context.sendBroadcast(intent);
        return notification;
    }


    //魅族手机（暂不支持消息角标）
    public Notification setMeiZuIconBadgeNum(@NonNull Application context, Notification notification, int count) throws Exception {
        if (true) {
            throw new Exception(NOTIFICATION_MEIZU_ERROR);
        }
        return null;
    }


    /**
     * 以下是oppo客服原文（其中没有说明3.3中日活量和总下载量的标准）
     * <p>
     * 亲，您可以通过邮件的形式提交申请，邮件形式如下：
     * <p>
     * 1.主题：“申请开放OPPO手机应用角标权限——（应用名称）”
     * <p>
     * 2.收件人：devtec@oppo.com
     * <p>
     * 3.正文：应用角标申请所需材料
     * 以下为应用角标申请所需材料：
     * 1.应用方申请人基本信息（姓名、邮箱、手机、微信）；
     * 2.应用方厂商介绍（不少于200字）；
     * 3.申请应用近一个月内IOS与安卓系统的平均日活量及总下载量介绍；
     * 4.申请应用产品主要功能介绍（不少于100字）；
     * 5.申请应用期望角标出现的全部场景。（即哪些类的消息需要出现角标，需全部列出）
     * <p>
     * 您这边以邮件的形式申请接入角标后，OPPO方审核人员将在接收日期10个工作日内给出审核结果。感谢您对OPPO开放平台的关注与信任，还
     * 请您耐心等待的哦~
     */
    public Notification setOPPOIconBadgeNum(@NonNull Application context, Notification notification, int count) throws Exception {
        if (true) {
            throw new Exception(NOTIFICATION_OPPO_ERROR);
        }
        return null;
    }

    /**
     * 没有找到官方文档说明，只有网上的方法
     * <p>
     * Galaxy S8/SM-G9500/android 8.0
     * Galaxy Galaxy Note8/SM-N9500/android 8.0
     */
    public Notification setSamsungIconBadgeNum(@NonNull Application context, Notification notification, int count) throws Exception {
        Intent intent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
        intent.putExtra("badge_count", count);
        intent.putExtra("badge_count_package_name", context.getPackageName());
        intent.putExtra("badge_count_class_name", Utils.getInstance().getLaunchIntentForPackage(context));

        if (Utils.getInstance().canResolveBroadcast(context, intent)) {
            context.sendBroadcast(intent);
        } else {
            throw new Exception(UNABLE_TO_RESOLVE_INTENT_ERROR_ + intent.toString());
        }
        return notification;
    }


    /**
     * 网上查到了此段代码，但在vivo X21A[android-8.1.0/Funtouch OS_4.0]真机上并未测试成功。
     * 在vivo开发者平台上与人工客服联系后，对方回复暂时没有公开的方法可以设置，也没有渠道可以申请，只有vivo特别指定的应用可以实现（微信、微博等）
     */
    public Notification setVIVOIconBadgeNum(@NonNull Application context, Notification notification, int count) throws Exception {
        if (true) {
            throw new Exception(NOTIFICATION_VIVO_ERROR);
        }


        return null;
    }

    /**
     * https://dev.mi.com/console/doc/detail?pId=939
     * <p>小米生成的方法
     * 必须发送通知
     */
    public Notification setXiaoMiIconBadgeNum(@NonNull Application context, Notification notification, int count) throws Exception {
        if (notification == null) {
            throw new Exception(NOTIFICATION_XiaoMi_ERROR);
        }
        Field field = notification.getClass().getDeclaredField("extraNotification");
        Object extraNotification = field.get(notification);
        Method method = extraNotification.getClass().getDeclaredMethod("setMessageCount", int.class);
        method.invoke(extraNotification, count);
        return notification;
    }


}
