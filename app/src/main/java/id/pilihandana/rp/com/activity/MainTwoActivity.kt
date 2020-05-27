package id.pilihandana.rp.com.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import id.pilihandana.rp.com.BaseActivity
import id.pilihandana.rp.com.R
import id.pilihandana.rp.com.adpter.MainTwoAdpter
import id.pilihandana.rp.com.http.HttpUrl
import id.pilihandana.rp.com.modle.MainModle
import id.pilihandana.rp.com.utils.LogUtil
import id.pilihandana.rp.com.utils.ResUtils
import id.pilihandana.rp.com.utils.Utils
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_title.*
import kotlinx.android.synthetic.main.head_two_view.view.*
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class MainTwoActivity : BaseActivity() {

    private var type = 0

    private val adapter: MainTwoAdpter = MainTwoAdpter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_two_activity)
        adapter.setActivity(this)

        val intent = intent
        val action = intent.action
        type = if (action == "0") 0 else 1
        val headView = layoutInflater.inflate(R.layout.head_two_view, null, false)
        adapter.addHeaderView(headView)
        setItile(headView)
        // 设置布局
        rvList.setLayoutManager(LinearLayoutManager(this))
        rvList.setAdapter(adapter)
        swipe_ly.setRefreshing(true)

        //设置在listview上下拉刷新的监听
        swipe_ly.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener { loadTwoNum() })
        loadTwoNum()
    }

    private fun setItile(headView: View) {
        back.setOnClickListener {
            finish()
        }
        if (type != 0) {
            app_title.setText("Produkterbaru")
            headView.banner.setBackgroundResource(R.mipmap.img15)
        } else {
            app_title.setText("Mudahdisetujui")
            headView.banner.setBackgroundResource(R.mipmap.img17)
        }
    }

    private fun loadTwoNum() {
        val rmap: MutableMap<String?, Any?>? =
            HashMap()
        rmap?.set("start", 0)
        if (type == 0) rmap!!["sort"] = "fast_sort" else rmap!!["sort"] = "new_sort"
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
                    LogUtil.d("loadTwoNum$decrStr")
                    val jsonObject = JSONObject(decrStr)
                    val data = jsonObject.optString("data")
                    val gs = Gson()
                    var list : ArrayList<MainModle> = gs.fromJson(
                        data,
                        object : TypeToken<List<MainModle?>?>() {}.type
                    )
                    if (type == 0) {
                        Utils.trackEventApp(
                            Utils.eventName + "_fastview",
                            this@MainTwoActivity
                        )

                        Utils.trackBranchApp(this@MainTwoActivity, "_fastview")
                    } else {
                        Utils.trackEventApp(
                            Utils.eventName + "_newview",
                            this@MainTwoActivity
                        )
                        Utils.trackBranchApp(this@MainTwoActivity, "_newview")
                    }
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
                Toast.makeText(this@MainTwoActivity, t.message, Toast.LENGTH_SHORT).show()
                LogUtil.e(t)
                swipe_ly.setRefreshing(false)
            }
        })
    }

}
