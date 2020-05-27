package id.pilihandana.rp.com.activity

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import id.pilihandana.rp.com.BaseActivity
import id.pilihandana.rp.com.R
import id.pilihandana.rp.com.dialog.DialogOne
import id.pilihandana.rp.com.utils.CacheUtils
import id.pilihandana.rp.com.utils.DisplayUtil
import id.pilihandana.rp.com.utils.StorePreferences
import id.pilihandana.rp.com.utils.UserUtlis
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.activity_setting.login
import kotlinx.android.synthetic.main.app_title.*
import kotlinx.android.synthetic.main.head_view.view.*
import kotlinx.android.synthetic.main.home_nav_header.view.*


class SettingActivity : BaseActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        setItile()
        ll01.setOnClickListener {
            CacheUtils.clearAllCache(this)
            getCacheSize()
        }
        login.setOnClickListener {
            val settingDialog = DialogOne(this)
            val window: Window = settingDialog.getWindow()
            val lp = window.attributes
            //将宽度值dp转px
            //将宽度值dp转px
            lp.width = WindowManager.LayoutParams.MATCH_PARENT
            //高度自适应(也可设置为固定值,同宽度设置方法)
            //高度自适应(也可设置为固定值,同宽度设置方法)
            lp.height = DisplayUtil.dip2px(this, 250F)
            settingDialog.getWindow().setAttributes(lp)
            settingDialog.show()
        }
        setAndroidNativeLightStatusBar(this,true)

        if (StorePreferences.getInstance(this).getUserInfo().isEmpty()) {
            login.visibility = View.GONE
        } else {
            login.visibility = View.VISIBLE
        }

    }

    private fun setItile() {
        back.setOnClickListener {
            finish()
        }
    }

    private fun getCacheSize() {
        var cashSzize = ""
        try {
            cashSzize = CacheUtils.getTotalCacheSize(this)
            btn1.text = cashSzize
        } catch (e: Exception) {
            e.printStackTrace()
        }
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