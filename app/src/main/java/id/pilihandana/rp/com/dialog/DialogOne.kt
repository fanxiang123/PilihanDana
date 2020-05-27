package id.pilihandana.rp.com.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.view.View
import id.pilihandana.rp.com.R
import id.pilihandana.rp.com.utils.StorePreferences
import id.pilihandana.rp.com.utils.UserUtlis
import kotlinx.android.synthetic.main.dialog_one.*

class DialogOne(context: Context) : Dialog(context) {

    init {
        val view =
            View.inflate(context, R.layout.dialog_one, null)
        setContentView(view)
        batal.setOnClickListener(View.OnClickListener { dismiss() })
        yakin.setOnClickListener(View.OnClickListener {
            StorePreferences.getInstance(context).setUserInfo(null)
            UserUtlis.logout(context)
            val activity = context as Activity
            activity.finish()
            dismiss()
        })
    }

}

