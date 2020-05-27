package id.pilihandana.rp.com.utils;

import android.content.Context;
import android.text.TextUtils;

import com.facebook.appevents.AppEventsLogger;

import io.branch.referral.util.BranchEvent;


public class Utils {

    public static final String eventName="PilihanDana";


    public static boolean isLocalApp(Context context, String appPkgName) {
        assert (context != null);
        if (TextUtils.isEmpty(appPkgName))
            return false;

        boolean flag = false;
        try {
            if (context.getPackageManager().getLaunchIntentForPackage(appPkgName) != null) {
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    public static void trackEventApp(String value,Context context) {
        AppEventsLogger.newLogger(context).logEvent(value);
    }

    public static void trackBranchApp(Context context,String Key){
        new BranchEvent(eventName+Key)
//                .setDescription(eventName+Key)
                .logEvent(context);
        LogUtil.d("Branch埋点事件:"+Key);
    }

}
