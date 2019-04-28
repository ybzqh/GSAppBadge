华为手机需要添加两个权限
 <!--华为手机角标权限-->：
   <uses-permission android:name="android.permission.INTERNET" />
   <uses-permission android:name="com.huawei.android.launcher.permission.CHANGE_BADGE" />
最好在加上闪光灯以及震动权限：
 <!--闪光的灯以及震动权限-->
    <uses-permission android:name="android.permission.VIBRATE" />
    <uses-permission android:name="android.permission.FLASHLIGHT" />
注意事项：
  1.源生系统不支持（应该只会显示一个红色圆点不会显示具体数字 具体待测试）
  2.魅族不支持该功能
  3.oppo需要申请（一般的小应用就别考虑的需要日活跃高于一定量）
  4.vivo需要与厂商合作
