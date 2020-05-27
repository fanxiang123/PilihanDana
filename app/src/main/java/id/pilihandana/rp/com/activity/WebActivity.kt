package id.pilihandana.rp.com.activity

import android.app.Activity
import android.os.Bundle
import android.view.View
import id.pilihandana.rp.com.BaseActivity
import id.pilihandana.rp.com.R
import kotlinx.android.synthetic.main.activity_web_view.*
import kotlinx.android.synthetic.main.app_two_title.*

class WebActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        setItile()
        setAndroidNativeLightStatusBar(this,true)

        web.loadUrl("https://www.pilihandana.com/policyagreement.html")
        //声明WebSettings子类
        val webSettings = web!!.settings

        //设置自适应屏幕，两者合用
        webSettings.useWideViewPort = true //将图片调整到适合webview的大小
        webSettings.loadWithOverviewMode = true // 缩放至屏幕的大小

    }

    private fun setItile() {
        back.setOnClickListener {
            finish()
        }
        app_title.setText("Tengaturan")
    }

    //修改状态栏颜色
    private fun setAndroidNativeLightStatusBar(
        activity: Activity,
        dark: Boolean
    ) {
        val decor: View = activity.window.decorView
        if (dark) {
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
        } else {
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
        }
    }
}