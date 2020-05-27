package id.pilihandana.rp.com.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.google.gson.Gson
import id.pilihandana.rp.com.BaseActivity
import id.pilihandana.rp.com.R
import id.pilihandana.rp.com.http.HttpUrl
import id.pilihandana.rp.com.modle.GuId
import id.pilihandana.rp.com.utils.*
import io.branch.referral.Branch
import io.branch.referral.Branch.BranchReferralInitListener
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class SplashActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        SetGaid()
        loadMum()

    }

    private fun SetGaid() {
        if (StorePreferences.getInstance(this@SplashActivity).gaid.isEmpty()) {
            try {
                val adInfo: AdUtils.AdInfo = AdUtils.getAdvertisingIdInfo(this@SplashActivity)
                    ?: return
                val advertisingId: String = adInfo.getId()
                StorePreferences.getInstance(this@SplashActivity).gaid = advertisingId
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun loadMum() {
        if (StorePreferences.getInstance(this@SplashActivity).getGuid().isEmpty()) {
            val rmap: MutableMap<String?, Any?>? =
                HashMap()
            var map: String? = ResUtils.createRequestData(rmap, this)
            map = ResUtils.encryptedStr(map.toString())
            HttpUrl.HttpUrlOk()?.getGuId(
                RequestBody.create(MediaType.parse("text/plain"), map)
            )!!.enqueue(object : Callback<String?> {
                override fun onResponse(
                    call: Call<String?>,
                    response: Response<String?>
                ) {
                    try {
                        val decrStr: String? = ResUtils.decryptStr (response.body())
                        val jsonObject = JSONObject(decrStr)
                        val data = jsonObject.optString("data")
                        val gs = Gson()
                        LogUtil.d("loadApps$decrStr")
                        val guId: GuId = gs.fromJson(data, GuId::class.java)
                        StorePreferences.getInstance(this@SplashActivity).setGuid(guId.getGuid())
                        loadApps()
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }

                override fun onFailure(
                    call: Call<String?>,
                    t: Throwable
                ) {
//                finish();
                    Toast.makeText(this@SplashActivity, t.message, Toast.LENGTH_SHORT).show()
                    LogUtil.e(t)
                }
            })
        } else {
            loadApps()
        }
    }

    private fun loadApps() {
        val list: List<String> =
            AppsBao().loadApps(this@SplashActivity)
        val rmap: MutableMap<String?, Any?>? =
            HashMap()
        rmap?.set("packageList", list)
        var map: String? = ResUtils.createRequestData(rmap, this)
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
                val intent = Intent(this@SplashActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }

            override fun onFailure(
                call: Call<String?>,
                t: Throwable
            ) {
//                finish();
                Toast.makeText(this@SplashActivity, t.message, Toast.LENGTH_SHORT).show()
                LogUtil.e(t)
            }
        })
    }




    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        // if activity is in foreground (or in backstack but partially visible) launching the same
        // activity will skip onStart, handle this case with reInitSession
        Branch.sessionBuilder(this).withCallback(KotlinUtlis.branchReferralInitListener)
            .reInit()
    }

    override fun onStart() {

        super.onStart()
        KotlinUtlis.getSplash(this)
    }


}