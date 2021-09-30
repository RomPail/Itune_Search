package fr.cl.musicsearch.search.manager

import android.content.Context
import fr.cl.musicsearch.core.networking.ApiInterfaceSearch
import fr.cl.musicsearch.core.networking.WebserviceCallbackListener
import fr.cl.musicsearch.core.networking.response.GetSearch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchWebServiceManager {
    companion object{

        /**
         * WS allowing to get several information
         * */
        fun fetchMusicInformation(webserviceCallbackListener: WebserviceCallbackListener, context: Context, searchText : String, media : String) {

            val workListApiClient = ApiInterfaceSearch.ApiUtilsSearch.apiInterface

            val call = workListApiClient.getResearch(searchText, media)

            call.enqueue(
                object : Callback<GetSearch> {
                    override fun onFailure(call: Call<GetSearch>, t: Throwable) {
                        webserviceCallbackListener.onFailure(call,t)
                    }
                    override fun onResponse(call: Call<GetSearch>, response: Response<GetSearch>) {
                        webserviceCallbackListener.onResponse(call,response)
                    }
                }
            )
        }

    }
}