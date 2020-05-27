package id.pilihandana.rp.com.adpter


import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import id.pilihandana.rp.com.R
import id.pilihandana.rp.com.activity.AppActivity
import id.pilihandana.rp.com.modle.MainModle


class MainAdpter : BaseQuickAdapter<MainModle?, BaseViewHolder>(R.layout.main_item) {

    private var mActivity : Activity? = null

    override fun convert(helper: BaseViewHolder, item: MainModle?) {
        (helper.getView<View>(R.id.app)).setOnClickListener {
            val intent = Intent(mActivity, AppActivity::class.java)
            intent.setAction(item!!.id.toString() + "")
            mActivity?.startActivity(intent)
        }
        (helper.getView<View>(R.id.login)).setOnClickListener {
            val intent = Intent(mActivity, AppActivity::class.java)
            intent.setAction(item!!.id.toString() + "")
            mActivity?.startActivity(intent)
        }
        (helper.getView<TextView>(R.id.name)).setText(item!!.productName)
        (helper.getView<TextView>(R.id.num)).setText(item!!.totalScore)
        (helper.getView<TextView>(R.id.money)).setText("Rp "+item!!.priceMax)
        (helper.getView<TextView>(R.id.interest)).setText(">="+item!!.interestRate+"%/hari")
        (helper.getView<TextView>(R.id.time)).setText(item!!.loanDay+" hari")
        Glide.with(mActivity).load(item.icon)
            .asBitmap().fitCenter() //刷新后变形问题
            .placeholder(R.mipmap.ic_launcher)
            .into((helper.getView<ImageView>(R.id.icon)))
    }

    fun  setActivity(activity: Activity){
        mActivity = activity
    }

}