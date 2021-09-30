package fr.cl.musicsearch.core.manager

import fr.cl.musicsearch.core.networking.ApiInterfaceSearch
import fr.cl.musicsearch.core.networking.interceptors.getInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {
    val authentificationInterceptor = getInterceptor()

    var retrofit: Retrofit? = null
    var retrofitXml: Retrofit? = null
    var client: OkHttpClient? = null


    fun getSearch(baseUrl: String): Retrofit? {
        if (retrofit == null) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = authentificationInterceptor?.let {
                OkHttpClient.Builder()
                    .addInterceptor(Interceptor { chain ->
                        val original = chain.request()
                        //header
                        val request = original.newBuilder()
                            .header("Content-Type", "application/json")
                            .method(original.method, original.body)
                            .build()
                        return@Interceptor chain.proceed(request)
                    })
                    .addInterceptor(it)
                    .connectTimeout(1000, TimeUnit.SECONDS)
                    .readTimeout(1000, TimeUnit.SECONDS)
                    .build()
            }
            retrofit = Retrofit.Builder()
                .baseUrl(ApiInterfaceSearch.ApiUtilsSearch.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit
    }

}