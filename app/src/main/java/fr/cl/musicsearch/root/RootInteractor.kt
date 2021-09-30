package fr.cl.musicsearch.root

import android.content.Intent
import android.net.Uri
import com.uber.rib.core.Bundle
import com.uber.rib.core.Interactor
import com.uber.rib.core.RibInteractor
import fr.cl.musicsearch.search.SearchInteractor
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject
import javax.inject.Named

/**
 * Coordinates Business Logic for [NewCustomRibScope].
 *
 * TODO describe the logic of this scope.
 */
@RibInteractor
class RootInteractor : Interactor<RootInteractor.RootPresenter, RootRouter>() {

    @Inject
    lateinit var presenter: RootPresenter
    var intentLink: Intent? = null


    var actionsDisposable = CompositeDisposable()


    override fun didBecomeActive(savedInstanceState: Bundle?) {
        super.didBecomeActive(savedInstanceState)

       //Enter in search RIB
        router.initVar()
        router.attachSearch()

    }


    fun setDeepLink(intent: Intent) {
        intentLink = intent
    }

    internal inner class SearchListener : SearchInteractor.ListenerSearch {

        /**
         * This function is called to switch from the search view to the visualization view
         * **/
        override fun goToViewRib(previewUrl: String) {
            router.detachSearch()
            router.attachView(previewUrl)
        }
    }


    override fun willResignActive() {
        super.willResignActive()
        actionsDisposable.dispose()
    }

    /**
     * Presenter interface implemented by this RIB's view.
     */
    interface RootPresenter
}
