package fr.cl.musicsearch.root

import fr.cl.musicsearch.core.screenstack.ScreenStack
import fr.cl.musicsearch.core.screenstack.ViewRouterExtended
import fr.cl.musicsearch.search.SearchRouter
import fr.cl.musicsearch.search.SearchScreen
import fr.cl.musicsearch.view.ViewBuilder
import fr.cl.musicsearch.view.ViewRouter
import fr.cl.musicsearch.view.ViewScreen
import io.reactivex.disposables.CompositeDisposable


/**
 * Adds and removes children of {@link NewCustomRibBuilder.NewCustomRibScope}.
 *
 * TODO describe the possible child configurations of this scope.
 */
class RootRouter(
    view: RootView,
    interactor: RootInteractor,
    component: RootBuilder.Component,
    val searchScreen: SearchScreen,
    val viewScreen: ViewScreen,
    val viewBuilder: ViewBuilder,
    val screenStack: ScreenStack
)
    : ViewRouterExtended<RootView, RootInteractor, RootBuilder.Component>(view, interactor, component){
    var searchRouter: SearchRouter? = null
    var viewRouter: ViewRouter? = null
    private val disposables = CompositeDisposable()

    fun initVar() {
        searchScreen.buildView(view)
        this.searchRouter = searchScreen.router
    }


    override fun willDetach() {
        super.willDetach()
        disposables.clear()
    }

    override fun willAttach() {
        disposables.add(searchScreen
            .lifecycle()
            .subscribe { event ->
                val router = searchScreen.router
                handleScreenEvents(router, event)
            })

        disposables.add(viewScreen
            .lifecycle()
            .subscribe { event ->
                val router = viewScreen.router
                handleScreenEvents(router, event)
            })
    }


    //Attach search parent RIB
    fun attachSearch() {
        screenStack.pushScreen(searchScreen)
    }

    fun detachSearch(){
        screenStack.popScreen()
    }

    //Attach View parent RIB
    fun attachView(previewUrl : String) {
        viewScreen.instantiateElement(previewUrl)
        viewRouter = viewBuilder!!.build(view,previewUrl)
        screenStack.pushScreen(viewScreen)
    }

    fun detachView(){
        screenStack.popScreen()
    }
}
