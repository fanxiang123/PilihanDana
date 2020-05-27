package id.pilihandana.rp.com.utils

import android.content.Context
import com.google.gson.Gson
import id.pilihandana.rp.com.modle.User

class UserUtlis{

    companion object {

        private var user: User? = null


        fun getUser(context: Context?): User? {
            if (StorePreferences.getInstance(context).getUserInfo().isEmpty()) return null
            if (user == null) {
                val userInfo: String = StorePreferences.getInstance(context).getUserInfo()
                val gs = Gson()
                val userModle1: User = gs.fromJson(userInfo, User::class.java)
                user = userModle1
            }
            return user
        }

        fun logout(context: Context?) {
            StorePreferences.getInstance(context).setUserInfo(null)
            user = null
        }
    }
}