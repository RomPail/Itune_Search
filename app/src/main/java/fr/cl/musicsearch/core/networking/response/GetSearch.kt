package fr.cl.musicsearch.core.networking.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class GetSearch {
    @SerializedName("resultCount")
    var resultCount: Int? = null
    @SerializedName("results")
    var results:  ArrayList<InformationList>? = null




}