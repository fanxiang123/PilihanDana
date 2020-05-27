package id.pilihandana.rp.com.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import id.pilihandana.rp.com.BaseActivity
import id.pilihandana.rp.com.R
import id.pilihandana.rp.com.adpter.MainAdpter
import id.pilihandana.rp.com.http.HttpUrl
import id.pilihandana.rp.com.modle.MainModle
import id.pilihandana.rp.com.utils.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.head_view.view.*
import kotlinx.android.synthetic.main.head_view.view.name
import kotlinx.android.synthetic.main.home_nav_header.view.*
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MainActivity : BaseActivity() {


    private val adapter: MainAdpter = MainAdpter()
    private lateinit var headView: View
    private lateinit var navHeadView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Utils.trackEventApp(Utils.eventName+ "_main", this)
        Utils.trackBranchApp(this, "_main")
        adapter.setActivity(this)
        // 设置布局
        rvList.setLayoutManager(GridLayoutManager(this,2))
        rvList.setAdapter(adapter)
        //demo 添加的 Header
        headView = layoutInflater.inflate(R.layout.head_view, null, false)
        //得到头部的布局，
        navHeadView = navigationView.getHeaderView(0)
        navHeadView.setting.setOnClickListener {
            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
        }

        navHeadView.id1.setOnClickListener {
            val intent = Intent(this, WebActivity::class.java)
            startActivity(intent)
        }
        login.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        adapter.addHeaderView(headView)
        headView.my.setOnClickListener {
            activity_na.openDrawer(navigationView)
        }
        headView.ll01.setOnClickListener {
            val intent = Intent(this, MainTwoActivity::class.java)
            intent.action = "0"
            startActivity(intent)
        }
        headView.ll02.setOnClickListener {
            val intent = Intent(this, MainTwoActivity::class.java)
            intent.action = "1"
            startActivity(intent)
        }

        swipe_ly.setRefreshing(true)

        //设置在listview上下拉刷新的监听
        swipe_ly.setOnRefreshListener(OnRefreshListener { loadNum() })
        loadNum()

    }

    private fun loadNum() {
        val rmap: MutableMap<String?, Any?>? =
            HashMap()
        rmap?.set("start", 0)
        rmap?.set("sort", "")
        rmap?.set("limit", 100)
        var map: String? = ResUtils.createRequestData(rmap, this)
        map = ResUtils.encryptedStr(map!!)
        HttpUrl.HttpUrlOk()!!.listApps(
            RequestBody.create(MediaType.parse("text/plain"), map)
        )!!.enqueue(object : Callback<String?> {
            override fun onResponse(
                call: Call<String?>,
                response: Response<String?>
            ) {
                val decrStr: String? = ResUtils.decryptStr(response.body())
                try {
                    LogUtil.d("loadNum$decrStr")
                    val jsonObject = JSONObject(decrStr)
                    val data = jsonObject.optString("data")
                    val gs = Gson()
                    var list : ArrayList<MainModle> = gs.fromJson(
                        data,
                        object : TypeToken<List<MainModle?>?>() {}.type
                    )
                    if(list.size <= 1){
                        headView.ll1.setVisibility(GONE)
                        headView.ll2.setVisibility(GONE)
                    }else{
                        var mm1 : MainModle  = list.get(0)
                        headView.name.setText(mm1.productName)
                        headView.num.setText(mm1.totalScore)
                        headView.money.setText("Rp "+mm1.priceMax)
                        headView.interest.setText(">="+mm1.interestRate+"%/hari")
                        headView.time.setText(mm1.loanDay+" hari")
                        Glide.with(this@MainActivity).load(mm1.icon)
                            .asBitmap().fitCenter() //刷新后变形问题
                            .placeholder(R.mipmap.ic_launcher)
                            .into(headView.icon)
                        var mm2 : MainModle  = list.get(1)
                        headView.name2.setText(mm2.productName)
                        headView.num2.setText(mm2.totalScore)
                        headView.money2.setText("Rp "+mm2.priceMax)
                        headView.interest2.setText(">="+mm2.interestRate+"%/hari")
                        headView.time2.setText(mm1.loanDay+" hari")
                        Glide.with(this@MainActivity).load(mm2.icon)
                            .asBitmap().fitCenter() //刷新后变形问题
                            .placeholder(R.mipmap.ic_launcher)
                            .into(headView.icon2)
                        list.removeAt(0)
                        list.removeAt(1)
                        headView.ll1.setOnClickListener {
                            val intent = Intent(this@MainActivity, AppActivity::class.java)
                            intent.setAction(mm1!!.id.toString() + "")
                            startActivity(intent)
                        }
                        headView.ll2.setOnClickListener {
                            val intent = Intent(this@MainActivity, AppActivity::class.java)
                            intent.setAction(mm2!!.id.toString() + "")
                            startActivity(intent)
                        }
                        headView.login.setOnClickListener {
                            val intent = Intent(this@MainActivity, AppActivity::class.java)
                            intent.setAction(mm1!!.id.toString() + "")
                            startActivity(intent)
                        }
                        headView.login2.setOnClickListener {
                            val intent = Intent(this@MainActivity, AppActivity::class.java)
                            intent.setAction(mm2!!.id.toString() + "")
                            startActivity(intent)
                        }
                    }
                    Utils.trackEventApp(Utils.eventName + "_homeview", this@MainActivity)
                    Utils.trackBranchApp(this@MainActivity, "_homeview")
                    adapter.setList(list)
                    swipe_ly.setRefreshing(false)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                call: Call<String?>,
                t: Throwable
            ) {
//                finish();
                Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_SHORT).show()
                LogUtil.e(t)
                swipe_ly.setRefreshing(false)
            }
        })
    }

    override fun onResume() {
        if (StorePreferences.getInstance(this@MainActivity).getUserInfo().isEmpty()) {
            login.visibility = View.VISIBLE
            navHeadView.login_tv.setText("Anda Belum Login~")
            navHeadView.name.setText("Hai!")
        } else {
            login.visibility = GONE
            navHeadView.login_tv.setText("")
            val mobile: String? = UserUtlis.getUser(this)!!.user_mobile
            val phonenum = mobile!!.substring(0, 3) +
                    "***" + mobile!!.substring(mobile.length - 5, mobile.length - 1)
            navHeadView.name.setText(phonenum)
        }
        super.onResume()
    }
}
