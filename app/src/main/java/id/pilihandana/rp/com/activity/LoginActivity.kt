package id.pilihandana.rp.com.activity

import android.graphics.Paint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import id.pilihandana.rp.com.BaseActivity
import id.pilihandana.rp.com.R
import id.pilihandana.rp.com.dialog.LoadDialog
import id.pilihandana.rp.com.http.HttpUrl
import id.pilihandana.rp.com.utils.LogUtil
import id.pilihandana.rp.com.utils.ResUtils
import id.pilihandana.rp.com.utils.StorePreferences
import id.pilihandana.rp.com.utils.Utils
import kotlinx.android.synthetic.main.app_title.*
import kotlinx.android.synthetic.main.login_activity.*
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class LoginActivity : BaseActivity()  {


    private var loadDialog: LoadDialog? = null
    private var timer: Timer? = null
    private val time = 60000
    private var timerTask: TimerTask? = null
    private var timess = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
        setItile()
        btn_code.setOnClickListener {
            if (phone.text.toString() == ""){
                Toast.makeText(this, "phone Error", Toast.LENGTH_SHORT).show()
            }else{
                countDown()
                getAppCode()
            }
        }
        login.isEnabled = false
        login.setBackgroundResource(R.drawable.btn02)
        code.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                charSequence: CharSequence,
                i: Int,
                i1: Int,
                i2: Int
            ) {
            }

            override fun onTextChanged(
                charSequence: CharSequence,
                i: Int,
                i1: Int,
                i2: Int
            ) {
            }

            override fun afterTextChanged(editable: Editable) {
                if (code.text.toString() == "") {
                    login.isEnabled = false
                    login.setBackgroundResource(R.drawable.btn02)
                } else {
                    login.isEnabled = true
                    login.setBackgroundResource(R.drawable.btn01)
                }
            }
        })
        loadDialog = LoadDialog(this, "Loading……")
        loadDialog!!.setCanceledOnTouchOutside(false)
        login.setOnClickListener {
            loadDialog!!.show()
            login()
        }
        tv.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG) //下划线
        tv.getPaint().setAntiAlias(true) //抗锯齿

    }


    private fun getAppCode() {
        val rmap: MutableMap<String?, Any?>? =
            HashMap()
        rmap?.set("mobile", phone.text.toString())
        var map: String? = ResUtils.createRequestData(rmap, this)
        map = ResUtils.encryptedStr(map!!)
        HttpUrl.HttpUrlOk()!!.getCode(
            RequestBody.create(MediaType.parse("text/plain"), map)
        )!!.enqueue(object : Callback<String?> {
            override fun onResponse(
                call: Call<String?>,
                response: Response<String?>
            ) {
                try {
                    val decrStr: String? = ResUtils.decryptStr(response.body())
                    LogUtil.d("decrStr:$decrStr")
                    val jsonObject = JSONObject(decrStr)
                    val code = jsonObject.optInt("code")
                    if (code != 0) {
                        Toast.makeText(this@LoginActivity, "code Error", Toast.LENGTH_SHORT)
                            .show()
                        stopTimer()
                    } else if (code == 0) {
//                        Utils.trackEventApp(
//                            Utils.eventName.toString() + "_getcode",
//                            this@RegisterActivity
//                        )
                        Utils.trackEventApp(
                            Utils.eventName+ "_getcode",
                            this@LoginActivity
                        )
                        Utils.trackBranchApp(this@LoginActivity, "_getcode")
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    stopTimer()
                    Toast.makeText(this@LoginActivity, "code Error", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(
                call: Call<String?>,
                t: Throwable
            ) {
//                finish();
                Toast.makeText(this@LoginActivity, t.message, Toast.LENGTH_SHORT).show()
                LogUtil.e(t)
            }
        })
    }

    private fun setItile() {
        back.setOnClickListener {
            finish()
        }
    }

    private fun login() {
        val rmap: MutableMap<String?, Any?> =
            HashMap()
        rmap["mobile"] = phone.text.toString()
        rmap["code"] = code.text.toString()
        var map = ResUtils.createRequestData(rmap, this)
        map = ResUtils.encryptedStr(map!!)
        HttpUrl.HttpUrlOk()!!.login(
            RequestBody.create(MediaType.parse("text/plain"), map)
        )!!.enqueue(object : Callback<String?> {
            override fun onResponse(
                call: Call<String?>,
                response: Response<String?>
            ) {
                loadDialog!!.dismiss()
                try {
                    val decrStr = ResUtils.decryptStr(response.body())
                    val jsonObject = JSONObject(decrStr)
                    LogUtil.d("login$decrStr")
                    val code = jsonObject.optInt("code")
                    if (code == 0) {
//                        Utils.trackEventApp(
//                            Utils.eventName.toString() + "_login",
//                            this@LoginActivity
//                        )
                        StorePreferences.getInstance(this@LoginActivity)
                            .setUserInfo(jsonObject.optString("data"))
                        Utils.trackEventApp(
                            Utils.eventName + "_login",
                            this@LoginActivity
                        )
                        Utils.trackBranchApp(this@LoginActivity, "_login")
                        finish()
                    } else Toast.makeText(this@LoginActivity, "LognError", Toast.LENGTH_SHORT).show()
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                call: Call<String?>,
                t: Throwable
            ) {
                loadDialog!!.dismiss()
                //                finish();
                Toast.makeText(this@LoginActivity, t.message, Toast.LENGTH_SHORT).show()
                LogUtil.e(t)
            }
        })
    }

    private fun countDown() {
        btn_code.setEnabled(false)
        timess = time / 1000
        btn_code.setText(timess.toString() + "S")
        if (timerTask == null) {
            timerTask = object : TimerTask() {
                override fun run() {
                    runOnUiThread(Runnable {
                        timess--
                        btn_code.setText(timess.toString() + "S")
                        if (timess <= 0) {
                            stopTimer()
                            return@Runnable
                        }
                    })
                }
            }
        }
        if (timer == null) {
            timer = Timer()
        }
        timer!!.schedule(timerTask, 1000, 1000)
    }

    private fun stopTimer() {
        if (timer != null) {
            timer!!.cancel()
            timer = null
        }
        if (timerTask != null) {
            timerTask!!.cancel()
            timerTask = null
        }
        btn_code.setText("Kirim ulang code")
        btn_code.setEnabled(true)
    }


}