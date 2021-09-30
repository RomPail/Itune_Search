package fr.cl.musicsearch.search.manager

class SearchGetInfo(artistName:String, trackName:String, previewURL:String,artWork:String, indice : Int) {
    private var artistName: String? = artistName
    private var trackName: String? = trackName
    private var previewURL:String? = previewURL
    private var artWork: String? = artWork
    private var indice: Int? = indice


    fun getArtistNameResult(): String? {
        return artistName
    }

    fun setArtistNameResult(artistName: String?) {
        this.artistName = artistName
    }


    fun getTrackNameResult(): String? {
        return trackName
    }

    fun setTrackNameResult(trackName: String?) {
        this.trackName = trackName
    }


    fun getPreviewUrlResult(): String? {
        return previewURL
    }

    fun setPreviewUrlResult(previewURL: String?) {
        this.previewURL = previewURL
    }

    fun getArtWorkResult(): String? {
        return artWork
    }

    fun setArtWorkResult(artWork: String?) {
        this.artWork = artWork
    }

    fun getIndiceResult(): Int? {
        return indice
    }

    fun setIndiceResult(indice: Int?) {
        this.indice = indice
    }
}