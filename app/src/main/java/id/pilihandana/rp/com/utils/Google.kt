package id.pilihandana.rp.com.utils

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri

class Google {
    companion object {
    fun rateNow(context: Context, packageName: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("market://details?id=$packageName")
            intent.setPackage("com.android.vending") //这里对应的是谷歌商店，跳转别的商店改成对应的即可
            if (intent.resolveActivity(context.packageManager) != null) {
                context.startActivity(intent)
            } else { //没有应用市场，通过浏览器跳转到Google Play
                val intent2 = Intent(Intent.ACTION_VIEW)
                intent2.data =
                    Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
                if (intent2.resolveActivity(context.packageManager) != null) {
                    context.startActivity(intent2)
                } else {
                    //没有Google Play 也没有浏览器
                }
            }
        } catch (activityNotFoundException1: ActivityNotFoundException) {
            LogUtil.e("GoogleMarket Intent not found")
        }
    }
    }
}