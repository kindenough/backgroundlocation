package com.Utils;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import org.kymjs.kjframe.KJHttp;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.http.HttpParams;
import org.kymjs.kjframe.ui.ViewInject;
import org.kymjs.kjframe.utils.KJLoger;

import java.io.File;
import java.security.PublicKey;
import java.util.Locale;

import static android.support.v4.content.ContextCompat.checkSelfPermission;

/**
 * Created by hsx on 2017/12/15.
 */

public class Utils {
    public static void post1(){
        // 网络请求
        KJHttp kjh = new KJHttp();
        //HttpParams params = new HttpParams();
        //params.put("teamid", "0");
        //kjh.post("http://www.maomx.cn/position/post/0", params,
                kjh.get("http://www.maomx.cn/position/get/1",
                new HttpCallBack() {
                    @Override
                    public void onPreStart() {
                        super.onPreStart();
                        KJLoger.debug("即将开始http请求");
                        // et.setText("即将开始http请求");
                    }

                    @Override
                    public void onSuccess(String t) {
                        super.onSuccess(t);
                        ViewInject.longToast("请求成功");
                        KJLoger.debug("请求成功:" + t.toString());
                        // et.setText("请求成功:" + t.toString());
                    }

                    @Override
                    public void onFailure(int errorNo, String strMsg) {
                        super.onFailure(errorNo, strMsg);
                        KJLoger.debug("出现异常:" + strMsg);
                        // et.setText("出现异常:" + strMsg);
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        KJLoger.debug("请求完成，不管成功还是失败");
                    }
                });
    }
    /**
     * 检查是否有网络
     */
    public static boolean isNetworkAvailable(Context context)
    {

        NetworkInfo info = getNetworkInfo(context);
        return info != null && info.isAvailable();
    }

    /**
     * 检查是否是WIFI
     */
    public static boolean isWifi(Context context)
    {

        NetworkInfo info = getNetworkInfo(context);
        if (info != null)
        {
            if (info.getType() == ConnectivityManager.TYPE_WIFI)
                return true;
        }
        return false;
    }

    /**
     * 检查是否是移动网络
     */
    public static boolean isMobile(Context context)
    {

        NetworkInfo info = getNetworkInfo(context);
        if (info != null)
        {
            if (info.getType() == ConnectivityManager.TYPE_MOBILE)
                return true;
        }
        return false;
    }

    private static NetworkInfo getNetworkInfo(Context context)
    {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }

    /**
     * 检查SD卡是否存在
     */
    private static boolean checkSdCard()
    {

        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }


    /**
     * 获取手机SD卡总空间
     *
     * @return
     */
    public static long getSDcardTotalSize()
    {

        if (checkSdCard())
        {
            File path = Environment.getExternalStorageDirectory();
            StatFs mStatFs = new StatFs(path.getPath());
            long blockSizeLong = mStatFs.getBlockSizeLong();
            long blockCountLong = mStatFs.getBlockCountLong();

            return blockSizeLong * blockCountLong;
        } else
        {
            return 0;
        }
    }

    /**
     * 获取SDka可用空间
     *
     * @return
     */
    public static long getSDcardAvailableSize()
    {

        if (checkSdCard())
        {
            File path = Environment.getExternalStorageDirectory();
            StatFs mStatFs = new StatFs(path.getPath());
            long blockSizeLong = mStatFs.getBlockSizeLong();
            long availableBlocksLong = mStatFs.getAvailableBlocksLong();
            return blockSizeLong * availableBlocksLong;
        } else
            return 0;
    }


    /**
     * 获取手机内部存储总空间
     *
     * @return
     */
    public static long getPhoneTotalSize()
    {

        if (!checkSdCard())
        {
            File path = Environment.getDataDirectory();
            StatFs mStatFs = new StatFs(path.getPath());
            long blockSizeLong = mStatFs.getBlockSizeLong();
            long blockCountLong = mStatFs.getBlockCountLong();
            return blockSizeLong * blockCountLong;
        } else
            return getSDcardTotalSize();
    }
    /**
     * 获取手机内存存储可用空间
     *
     * @return
     */
    public static long getPhoneAvailableSize()
    {

        if (!checkSdCard())
        {
            File path = Environment.getDataDirectory();
            StatFs mStatFs = new StatFs(path.getPath());
            long blockSizeLong = mStatFs.getBlockSizeLong();
            long availableBlocksLong = mStatFs.getAvailableBlocksLong();
            return blockSizeLong * availableBlocksLong;
        } else
            return getSDcardAvailableSize();
    }

    public static String getImsi(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);

         if (checkSelfPermission(context,Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED) {

                String deviceid = tm.getDeviceId();// 获取智能设备唯一编号
            String te1 = tm.getLine1Number();// 获取本机号码
            String imei = tm.getSimSerialNumber();// 获得SIM卡的序号
            String imsi = tm.getSubscriberId();// 得到用户Id
            if (imsi != null && !imsi.equals("")) {
                return "获取智能设备唯一编号====Deviceid" + deviceid + "\nteleNum" + te1
                        + "\nSimNUM" + imei + "\nUserId" + imsi;
            }
        }
        return "未知";
    }
    //判读语言
//    private boolean isZh() {
//        Locale locale = getResources().getConfiguration().locale;
//        String language = locale.getLanguage();
//        if (language.endsWith("zh"))
//            return true;
//        else
//            return false;
//    }

    /**
     * 获得手机型号,系统版本,App版本号等信息
     */
    public static String getHandSetInfo(Context context){
        String handSetInfo=
                "手机型号:" + android.os.Build.MODEL +
                        ",SDK版本:" + android.os.Build.VERSION.SDK +
                        ",系统版本:" + android.os.Build.VERSION.RELEASE+
                        ",软件版本:"+getAppVersionName(context);
        return handSetInfo;
    }


    //获取当前版本号
    public static String getAppVersionName(Context context) {
        String versionName = "";
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo("com.home.quhong.test01.Test11Server", 0);
            versionName = packageInfo.versionName;
            if (TextUtils.isEmpty(versionName)) {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionName;
    }
}
