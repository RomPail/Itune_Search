package fr.cl.musicsearch.search

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.startActivity
import com.uber.rib.core.Bundle
import com.uber.rib.core.Interactor
import com.uber.rib.core.RibInteractor
import fr.cl.musicsearch.core.networking.WebserviceCallbackListener
import fr.cl.musicsearch.core.networking.response.GetSearch
import fr.cl.musicsearch.core.networking.response.InformationList
import fr.cl.musicsearch.search.manager.SearchWebServiceManager
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject


/**
 * Coordinates Business Logic for [NewCustomRibScope].
 *
 * TODO describe the logic of this scope.
 */
@RibInteractor
class SearchInteractor : Interactor<SearchInteractor.SearchPresenter, SearchRouter>() {

    @Inject
    lateinit var presenter: SearchPresenter

    @Inject
    lateinit var listenerSearch: SearchInteractor.ListenerSearch

    var actionsDisposable = CompositeDisposable()
    var mediaText = "music"
    var resultList : MutableList<InformationList> = mutableListOf<InformationList>()


    override fun didBecomeActive(savedInstanceState: Bundle?) {
        super.didBecomeActive(savedInstanceState)
        presenter.setLoader(false)
        presenter.setEmptyText(true)

        actionsDisposable.addAll(
            presenter.getSearchText().subscribe {

                //Clear list
                presenter.clearRecycler()

                //Close keyboard and show loader
                closeKeyboard()
                presenter.setLoader(true)

                //call WS with search text
                fetchDataClearInDB(router.view.context,it, mediaText)

            })

        //Subscribe my spinner observer to my observable//
        actionsDisposable.addAll(
            presenter.getMediaText().subscribe {

                mediaText = it
            })

        //Subscribe my edit text observer to my observable//
        actionsDisposable.addAll(
            presenter.getItemId().subscribe {
                var previewURL = resultList[it.toInt()].previewUrl

                //Call URL in browser
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(previewURL))
                startActivity(router.view.context,browserIntent,null)

                //If call Url in RIB
                //previewURL?.let { it1 -> listenerSearch.goToViewRib(it1) }
            })

       
    }

    override fun willResignActive() {
        super.willResignActive()
        actionsDisposable.dispose()
    }

    /**
     * This function call itune WebService
     * **/
    fun fetchDataClearInDB(context: Context, searchText : String, media : String){
        val webserviceCallbackListener = object : WebserviceCallbackListener {

            override fun onFailure(call: Call<*>, t: Throwable) {
                println(" on Failure : " +call.request())

                //Close loader and show warning message
                presenter.setLoader(false)
                presenter.setEmptyText(true)

            }

            override fun onResponse(call: Call<*>, response: Response<*>) {
                if (response.isSuccessful && response.body() != null) {

                    val res = response.body() as GetSearch

                    var countInResult = 0
                    if(res.results != null){
                        if(res.results!!.isNotEmpty()){
                            resultList = res.results!!

                            //Add result in recycler list
                            while(countInResult < res.results!!.size){
                                presenter.addLineRecycler(
                                    res.results!![countInResult].artistName.toString(),
                                    res.results!![countInResult].trackName.toString(),
                                    res.results!![countInResult].previewUrl.toString(),
                                    res.results!![countInResult].artworkUrl100.toString(),
                                    countInResult)
                                countInResult ++
                            }

                            //close loader
                            presenter.setLoader(false)

                            //Check if result is empty or not to adapt the view
                            if(res.results!!.isEmpty()){
                                presenter.setEmptyText(true)
                            }else{
                                presenter.setEmptyText(false)
                            }
                        }else{
                            //Close loader and show warning message
                            presenter.setLoader(false)
                            presenter.setEmptyText(true)
                        }
                    }else{
                        //Close loader and show warning message
                        presenter.setLoader(false)
                        presenter.setEmptyText(true)
                    }
                 }
            }
        }

        SearchWebServiceManager.fetchMusicInformation(webserviceCallbackListener, context , searchText, media)

    }

    /**
     * This function allow to close the keyboard
     * **/
    private fun closeKeyboard() {
        val imm = router.view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(router.view.windowToken, 0)
    }


    /**
     * Presenter interface implemented by this RIB's view.
     */
    interface SearchPresenter{
        fun getSearchText(): PublishSubject<String>
        fun getMediaText(): PublishSubject<String>
        fun addLineRecycler(getArtistName: String, getTrackName: String, getPreviewURL: String,getArtWork:String, getIndice : Int)
        fun getItemId(): PublishSubject<String>
        fun clearRecycler()
        fun setLoader(enable : Boolean)
        fun setEmptyText(enable : Boolean)
    }

    interface ListenerSearch{
        fun goToViewRib(previewUrl : String)
    }

}
