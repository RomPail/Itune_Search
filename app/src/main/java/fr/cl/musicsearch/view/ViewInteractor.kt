package fr.cl.musicsearch.view

import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import com.uber.rib.core.Bundle
import com.uber.rib.core.Interactor
import com.uber.rib.core.RibInteractor
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
class ViewInteractor : Interactor<ViewInteractor.ViewPresenter, ViewRouter>() {

    @Inject
    lateinit var presenter: ViewPresenter


    @Inject
    @field:Named("input")
    lateinit var input : Any


    var actionsDisposable = CompositeDisposable()


    override fun didBecomeActive(savedInstanceState: Bundle?) {
        super.didBecomeActive(savedInstanceState)

        presenter.setURL (input.toString())

    }


    override fun willResignActive() {
        super.willResignActive()
        actionsDisposable.dispose()
    }

    interface ViewPresenter{
        fun setURL (url : String)
    }

}
