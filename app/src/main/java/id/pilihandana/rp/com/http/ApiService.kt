package id.pilihandana.rp.com.http

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    /**
     * 获取guid
     * @return
     */
    @POST("id/data-res/deviceinfo/get-guid")
    fun getGuId(@Body body: RequestBody?): Call<String?>?

    /**
     * 上传所有包名
     * @return
     */
    @POST("id/data-res/deviceinfo/usrpackages")
    fun upApps(@Body body: RequestBody?): Call<String?>?

    /**
     * 便利列表
     * @return
     */
    @POST("id/data-res/products/list2")
    fun listApps(@Body body: RequestBody?): Call<String?>?

    /**
     * 详情
     * @return
     */
    @POST("id/data-res/products/infos")
    fun appsInfo(@Body body: RequestBody?): Call<String?>?

    /**
     * 获取验证码
     * @return
     */
    @POST("id/data-res/user/get-regcode")
    fun getCode(@Body body: RequestBody?): Call<String?>?

    /**
     * 短信登录
     * @return
     */
    @POST("id/data-res/user/smslogin")
    fun login(@Body body: RequestBody?): Call<String?>?
}