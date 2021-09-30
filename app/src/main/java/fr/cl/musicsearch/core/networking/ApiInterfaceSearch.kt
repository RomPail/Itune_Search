package fr.cl.musicsearch.core.networking

import fr.cl.musicsearch.core.manager.ApiClient
import fr.cl.musicsearch.core.networking.response.GetSearch
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

class ApiInterfaceSearch {

    interface APIInterfaceSearch {

        @GET("search")

        fun getResearch(@Query("term") term : String, @Query("media") media : String) : Call<GetSearch>
    }

    object ApiUtilsSearch{

        val BASE_URL = "https://itunes.apple.com/"

        val apiInterface: APIInterfaceSearch

            get() = ApiClient.getSearch(BASE_URL)!!.create(APIInterfaceSearch::class.java)
    }
}