package fr.cl.musicsearch.view

import fr.cl.musicsearch.core.screenstack.ScreenStack
import fr.cl.musicsearch.core.screenstack.ViewRouterExtended


/**
 * Adds and removes children of {@link NewCustomRibBuilder.NewCustomRibScope}.
 *
 * TODO describe the possible child configurations of this scope.
 */
class ViewRouter(
    view: ViewView,
    interactor: ViewInteractor,
    component: ViewBuilder.Component,
    val screenStack: ScreenStack
)
    : ViewRouterExtended<ViewView, ViewInteractor, ViewBuilder.Component>(view, interactor, component){


    override fun willDetach() {

    }

    override fun willAttach() {
    }

}
