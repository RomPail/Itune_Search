package fr.cl.musicsearch.core.networking.response

import com.google.gson.annotations.SerializedName

class InformationList {
    @SerializedName("artistName")
    var artistName: String? = null

    @SerializedName("trackName")
    var trackName: String? = null

    @SerializedName("previewUrl")
    var previewUrl: String? = null

    @SerializedName("artworkUrl100")
    var artworkUrl100: String? = null
}