package id.pilihandana.rp.com.utils

import android.content.Context
import android.content.Intent
import android.content.pm.ResolveInfo
import java.util.*

class AppsBao {

    /**
     * 查看手机所有包名
     * @param context
     */
    fun loadApps(context: Context): List<String> {
        var apps: List<ResolveInfo> = ArrayList()
        val appload: MutableList<String> =
            ArrayList()
        val intent = Intent(Intent.ACTION_MAIN, null)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        apps = context.packageManager.queryIntentActivities(intent, 0)
        //for循环遍历ResolveInfo对象获取包名和类名
        for (i in apps.indices) {
            val info = apps[i]
            appload.add(info.activityInfo.packageName)
        }
        return appload
    }
}