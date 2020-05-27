package id.pilihandana.rp.com.activity

import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.gson.Gson
import id.pilihandana.rp.com.BaseActivity
import id.pilihandana.rp.com.R
import id.pilihandana.rp.com.http.HttpUrl
import id.pilihandana.rp.com.modle.AppModle
import id.pilihandana.rp.com.utils.*
import kotlinx.android.synthetic.main.activity_app.*
import kotlinx.android.synthetic.main.app_title.*
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class AppActivity : BaseActivity() {

    private var id = 0
    private var packName : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app)
        val intent = intent
        id = Integer.valueOf(intent.action)
        setItile()
        loadApp()
        login.setOnClickListener {
            if (packName !== "" && packName != null){
                Utils.trackEventApp(
                    Utils.eventName + "_clickdown",
                    this@AppActivity
                )
                Utils.trackBranchApp(this@AppActivity, "_clickdown")
                Google.rateNow(this,packName!!)
            }
        }
    }

    private fun loadApp() {
        val list: List<String> =
            AppsBao().loadApps(this@AppActivity)
        val rmap: MutableMap<String?, Any?>? =
            HashMap()
        rmap?.set("pid", id)
        var map: String? = ResUtils.createRequestData(rmap, this)
        map = ResUtils.encryptedStr(map.toString())
        HttpUrl.HttpUrlOk()!!.appsInfo(
            RequestBody.create(MediaType.parse("text/plain"), map)
        )!!.enqueue(object : Callback<String?> {
            override fun onResponse(
                call: Call<String?>,
                response: Response<String?>
            ) {
                try {
                    val decrStr = ResUtils.decryptStr(response.body())
                    LogUtil.d("loadApp$decrStr")
                    val jsonObject = JSONObject(decrStr)
                    val data = jsonObject.optString("data")
                    val gs = Gson()
                    val appModle: AppModle =
                        gs.fromJson(data, AppModle::class.java)
                    min.text = "Rp " + appModle.getPrice_min()
                    max.text = "Rp " + appModle.getPrice_max()
                    money.text = "Rp " + appModle.getInterest_template()!!.loan_amount
                    time.text = "Time:" + appModle.getInterest_template()!!.cycle + "hari"
                    num1.text = "Rp " + appModle.getInterest_template()!!.repay_amount
                    num2.text = "Rp " + appModle.getInterest_template()!!.loan_amount
                    num3.text = "Rp " + appModle.getInterest_template()!!.interest_admin
                    num4.text = "Pinjam "+appModle.getInterest_template()!!.loan_amount+" mudah disetujui"
                    Glide.with(applicationContext).load(appModle!!.getIcon())
                        .asBitmap().fitCenter() //刷新后变形问题
                        .into(icon)
                    name.text =appModle.getProduct_name()
                    num.text =appModle.getTotal_score()
                    fen1.text ="Disetujui "+appModle.getPass_rate_score()
                    fen2.text ="Kecepatan "+appModle.getSpeed_score()
                    fen3.text ="Penagihan "+appModle.getDunning_score()
                    app_title.text = appModle.getProduct_name()
                    packName = appModle.getPackage_name()
                    Utils.trackEventApp(
                        Utils.eventName + "_detailview",
                        this@AppActivity
                    )

                    Utils.trackBranchApp(this@AppActivity, "_detailview")
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                call: Call<String?>,
                t: Throwable
            ) {
//                finish();
                Toast.makeText(this@AppActivity, t.message, Toast.LENGTH_SHORT).show()
                LogUtil.e(t)
            }
        })
    }

    private fun setItile() {
        back.setOnClickListener {
            finish()
        }
    }
}