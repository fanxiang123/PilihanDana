package id.pilihandana.rp.com

import android.content.Context
import android.content.IntentFilter
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import id.pilihandana.rp.com.utils.BootReceiver
import id.pilihandana.rp.com.utils.StatusBarUtil

open class BaseActivity : AppCompatActivity(){

    private var installedReceiver: BootReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (installedReceiver == null) {
            installedReceiver = BootReceiver()
            val filter = IntentFilter()
            filter.addAction("android.intent.action.PACKAGE_ADDED")
            filter.addAction("android.intent.action.PACKAGE_REMOVED")
            filter.addDataScheme("package")
            this.registerReceiver(installedReceiver, filter)
        }
        setStatusBar()
    }
    protected open fun setStatusBar() {
        //这里做了两件事情，1.使状态栏透明并使contentView填充到状态栏 2.预留出状态栏的位置，防止界面上的控件离顶部靠的太近。这样就可以实现开头说的第二种情况的沉浸式状态栏了
        StatusBarUtil.setTransparent(this)
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {
        // TODO Auto-generated method stub
        //点击其他地方软键盘小时方法
        val imm =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (event.action == MotionEvent.ACTION_DOWN) {
            if (this.currentFocus != null) {
                if (this.currentFocus.windowToken != null) {
                    imm.hideSoftInputFromWindow(
                        this.currentFocus.windowToken,
                        InputMethodManager.HIDE_NOT_ALWAYS
                    )
                }
            }
        }
        return super.onTouchEvent(event)
    }
}