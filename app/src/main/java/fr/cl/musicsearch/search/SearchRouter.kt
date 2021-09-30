package fr.cl.musicsearch.search

import fr.cl.musicsearch.core.screenstack.ScreenStack
import fr.cl.musicsearch.core.screenstack.ViewRouterExtended


/**
 * Adds and removes children of {@link NewCustomRibBuilder.NewCustomRibScope}.
 *
 * TODO describe the possible child configurations of this scope.
 */
class SearchRouter(
    view: SearchView,
    interactor: SearchInteractor,
    component: SearchBuilder.Component,
    val screenStack: ScreenStack
)
    : ViewRouterExtended<SearchView, SearchInteractor, SearchBuilder.Component>(view, interactor, component){


    override fun willDetach() {

    }

    override fun willAttach() {
    }

}
