package id.pilihandana.rp.com.http

import id.pilihandana.rp.com.utils.LogUtil
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HttpUrl {

    companion object {

        private val SERVICE_URL = "https://datares.pilihandana.com/"
        private var service: ApiService? = null
        private var retrofit: Retrofit? = null
        fun HttpUrlOk(): ApiService? {
            if (retrofit == null) {
                LogUtil.d("重新配置 retrofit")
                val okHttpClient = OkHttpClient.Builder()
                    //添加统一的请求头
                    .addInterceptor { chain -> // 以拦截到的请求为基础创建一个新的请求对象，然后插入Header
                        val request = chain.request().newBuilder()
                            .addHeader("Content-Type", "text/plain")
                            .build()
                        // 开始请求
                        chain.proceed(request)
                    }
                    .build()
                retrofit = Retrofit.Builder()
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create()) //解析方法
                    .baseUrl(SERVICE_URL) //主机地址
                    .build()
            } else {
                LogUtil.d("不需要配置 retrofit")
            }
            //2.创建访问API的请求
            if (service == null) service =
                retrofit?.create(
                    ApiService::class.java
                )
            return service
        }
    }
}