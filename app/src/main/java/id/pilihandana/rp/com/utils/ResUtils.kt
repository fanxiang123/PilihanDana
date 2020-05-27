package id.pilihandana.rp.com.utils

import android.content.Context
import android.os.Build
import android.provider.Settings
import android.text.TextUtils
import android.util.Base64
import com.alibaba.fastjson.JSONException
import com.alibaba.fastjson.JSONObject
import id.pilihandana.rp.com.BuildConfig
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

class ResUtils {
    companion object {
        private val SKEY = "6c5299364488eca22059c22736dd8817"
        private val SIV = "14e4013163ace60b"


        fun createRequestData(
            map: MutableMap<String?, Any?>?,
            context: Context
        ): String? {
            if (null != map) {
                val storePreferences: StorePreferences = StorePreferences.getInstance(context)
                val driverInfo =
                    getSimpleDriverInfo(storePreferences.getGaid(), context)
                val timestamp = getTimeMillis()
                val guid: String = storePreferences.getGuid()
                val ref: String = storePreferences.getRef()
                var userId = ""
                if (UserUtlis.getUser(context) != null) userId =
                    UserUtlis.getUser(context)!!.user_id.toString()
                if (!TextUtils.isEmpty(storePreferences.getUserInfo())) {
                    try {
                        val jsonObject = org.json.JSONObject(storePreferences.getUserInfo())
                        userId = jsonObject.optString("user_id")
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }

//            map.put("appPackage", BuildConfig.APPLICATION_ID);
                map["appPackage"] = "id.pilihandana.rp.com"
                map["userId"] = if (TextUtils.isEmpty(userId)) "" else userId
                map["ref"] = if (TextUtils.isEmpty(ref)) "" else ref // 渠道添加
                map["appType"] = "android"
                map["position"] = "0,0"
                map["version"] = BuildConfig.VERSION_NAME
                map["guid"] = if (TextUtils.isEmpty(guid)) "" else guid
                map["appVersion"] = getAppVersionCode(context)
                map["channel"] = "App"
                map["deviceInfo"] = driverInfo ?: ""
                val sign = getRequestSign(map, timestamp)
                map["sign"] = sign
                map["timestamp"] = timestamp
                return getMapToString(map)
            }
            return ""
        }


        private fun getMapToString(map: Map<String?, Any?>): String? {
            return try {
                JSONObject.toJSONString(map)
            } catch (e: Exception) {
                e.printStackTrace()
                ""
            }
        }

        private fun getRequestSign(
            paraMap: Map<String?, Any?>,
            time: String
        ): String? {
            return try {
                val data = sortMapByKey(paraMap)
                val jsonData: String = JSONObject.toJSONString(data)
                val result = "$SKEY*|*$jsonData@!@$time"
                KeyUtlis.encrypt(KeyUtlis.encrypt(result))
            } catch (e: Exception) {
                e.printStackTrace()
                ""
            }
        }


        private fun getSimpleDriverInfo(
            gaid: String,
            context: Context
        ): Map<String, Any?>? {
            val mapClient: MutableMap<String, Any?> =
                HashMap()
            try {
                mapClient["andId"] = getAndroidID(context)
                mapClient["gaid"] = gaid
                mapClient["imei"] = getDriverIMIE(context)
                mapClient["mac"] = ""
                mapClient["sn"] = getSerialNumber()
                mapClient["model"] = getModel()
                mapClient["brand"] = getBrand()
                mapClient["release"] = getDriverOsVersion()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return mapClient
        }


        private fun getAndroidID(context: Context): String? {
            return Settings.Secure.getString(
                context.contentResolver,
                Settings.Secure.ANDROID_ID
            )
        }

        private fun getDriverIMIE(context: Context): String? {
            //        try {
//            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//            if (null != tm) {
//                result = "";
//            }
//        } catch (Throwable e) {
//            e.printStackTrace();
//        }
            return ""
        }


        private fun getSerialNumber(): String? {
            var result: String? = null
            try {
                result = Build.SERIAL
            } catch (e: Throwable) {
                e.printStackTrace()
            }
            return result
        }

        fun getModel(): String? {
            try {
                return Build.MODEL
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return ""
        }

        private fun getBrand(): String? {
            try {
                return Build.BRAND
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return ""
        }

        private fun getDriverOsVersion(): String? {
            try {
                return Build.VERSION.RELEASE
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return ""
        }


        private fun getAppVersionCode(context: Context?): Int {
            return if (null == context) {
                0
            } else try {
                val pm = context.packageManager
                val pi = pm.getPackageInfo(context.packageName, 0)
                pi.versionCode
            } catch (e: Exception) {
                e.printStackTrace()
                0
            }
        }

        private fun getTimeMillis(): String {
            val time = System.currentTimeMillis()
            return time.toString()
        }

        private fun sortMapByKey(map: Map<String?, Any?>?): Map<String?, Any?>? {
            if (map == null || map.isEmpty()) {
                return null
            }
            val sortMap: MutableMap<String?, Any?> =
                TreeMap(
                    MapKeyComparator()
                )
            sortMap.putAll(map)
            return sortMap
        }

        internal class MapKeyComparator : Comparator<String?> {
            override fun compare(str1: String?, str2: String?): Int {
                return str1!!.compareTo(str2.toString())
            }
        }


        fun encryptedStr(str: String): String? {
            try {
                val sKey = SKEY
                val sIv = SIV
                //java
                val raw = sKey.toByteArray()
                val skeySpec =
                    SecretKeySpec(raw, "AES")
                val cipher =
                    Cipher.getInstance("AES/CBC/PKCS5Padding") //aes-cbc-pkcs5(pkcs5与pkcs7通用)
                val iv =
                    IvParameterSpec(sIv.toByteArray()) //使用CBC模式，需要一个向量iv，可增加加密算法的强度
                cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv)
                val encrypted = cipher.doFinal(str.toByteArray(charset("UTF-8")))
                return Base64.encodeToString(encrypted, Base64.DEFAULT)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }

        infix fun decryptStr(str: String?): String? {
            try {
                val sKey = SKEY
                val sIv = SIV
                //java
                val raw = sKey.toByteArray()
                val skeySpec =
                    SecretKeySpec(raw, "AES")
                val cipher =
                    Cipher.getInstance("AES/CBC/PKCS5Padding") //aes-cbc-pkcs5(pkcs5与pkcs7通用)
                val iv =
                    IvParameterSpec(sIv.toByteArray()) //使用CBC模式，需要一个向量iv，可增加加密算法的强度
                cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv)
                val decrypt =
                    cipher.doFinal(Base64.decode(str, Base64.NO_WRAP))
                return String(decrypt)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }
    }
}