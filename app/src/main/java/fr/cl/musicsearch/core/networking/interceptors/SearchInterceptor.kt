package fr.cl.musicsearch.core.networking.interceptors

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import java.io.IOException

fun getInterceptor(): Interceptor?{

   /* /**
     * get error 500 msg by WS Request
     */
    override fun getError500Msg(response: Response):Pair<Boolean, String>{

        // depending on the request, get the error msg
        val wsRequest = getWSRequestName(response.request)

        when(wsRequest){
            // get Worklist
            "worklist" -> return Pair(true, context.resources.getString(R.string.worklist_error_msg_get_worklist))
        }
        return Pair(false, "")
    }*/
    return SearchInterceptor()

}

internal class SearchInterceptor : Interceptor {
    //Code pasted from okHttp webSite itself
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val response = chain.proceed(request)
        val body = response.body

        val wrappedBody =  body!!.string()
        //Log.d("Interceptor Sample",String.format("response : %s",wrappedBody))

        return response.newBuilder()
            .body(ResponseBody.create(body.contentType(), wrappedBody))
            .build()

    }
}
