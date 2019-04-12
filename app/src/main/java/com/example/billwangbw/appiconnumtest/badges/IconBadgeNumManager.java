package com.example.billwangbw.appiconnumtest.badges;

import android.app.Application;
import android.app.Notification;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

public class IconBadgeNumManager implements IconBadgeNumModel {
    private static final String NOT_SUPPORT_PHONE = "not support your phone [ Build.MANUFACTURER is null ]";
    private static final String NOT_SUPPORT_MANUFACTURER_ = "not support ";
    private static final String NOT_SUPPORT_LAUNCHER = "not support your launcher [ default launcher is null ]";
    private static final String NOT_SUPPORT_LAUNCHER_ = "not support ";
    private Map<String, IconBadgeNumModel> iconBadgeNumModelMap;
    private LauncherHelper launcherHelper;

    public IconBadgeNumManager() {
        this.launcherHelper = new LauncherHelper();
        iconBadgeNumModelMap = new HashMap<>();
    }

    @Override
    public Notification setIconBadgeNum(@NonNull Application context, Notification notification, int count) throws Exception {
        IconBadgeNumModel iconBadgeNumModel = getSetIconBadgeNumModel(context);
        return iconBadgeNumModel.setIconBadgeNum(context, notification, count);
    }

    /**
     * 根据手机当前launcher获取相应Model
     */
    @NonNull
    private IconBadgeNumModel getIconBadgeNumModelByLauncher(@NonNull String launcherType) throws Exception {
        switch (launcherType) {
            case LauncherHelper.HUAWEI:
                return new HuaWeiModelImpl();
            case LauncherHelper.XIAOMI:
                return new XiaoMiModelImpl();
            case LauncherHelper.VIVO:
                return new VIVOModelImpl();
            case LauncherHelper.OPPO:
                return new OPPOModelImpl();
            case LauncherHelper.SAMSUNG:
                return new SamsungModelImpl();
            case LauncherHelper.MEIZU:
                return new MeizuModelImpl();
            case LauncherHelper.GOOGLE:
                return new GoogleModelImpl();
            default:
                throw new Exception(NOT_SUPPORT_LAUNCHER_ + launcherType);
        }
    }

    @NonNull
    private IconBadgeNumModel getSetIconBadgeNumModel(@NonNull Application context) throws Exception {
        String launcherName = launcherHelper.getLauncherPackageName(context);
        if (TextUtils.isEmpty(launcherName)) {
            throw new Exception(NOT_SUPPORT_LAUNCHER);
        }
        String launcherType = launcherHelper.getLauncherTypeByName(launcherName);
        if (TextUtils.isEmpty(launcherType)) {
            throw new Exception(NOT_SUPPORT_LAUNCHER_ + launcherName);
        }
        if (iconBadgeNumModelMap.containsKey(launcherType)) {
            return iconBadgeNumModelMap.get(launcherType);
        }
        IconBadgeNumModel iconBadgeNumModel = getIconBadgeNumModelByLauncher(launcherType);
        iconBadgeNumModelMap.put(launcherType, iconBadgeNumModel);
        return iconBadgeNumModel;
    }


}
