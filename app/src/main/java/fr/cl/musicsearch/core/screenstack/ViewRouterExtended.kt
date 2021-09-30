package fr.cl.musicsearch.core.screenstack

import android.view.View
import com.uber.rib.core.InteractorBaseComponent
import com.uber.rib.core.Router
import com.uber.rib.core.ViewRouter
import com.uber.rib.core.screenstack.lifecycle.ScreenStackEvent

open class ViewRouterExtended<V : View, I : com.uber.rib.core.Interactor<*, *>, C : InteractorBaseComponent<*>>(
    view: V,
    interactor: I,
    component: C
) :
    ViewRouter<V, I, C>(view, interactor, component) {

    fun handleScreenEvents(router: Router<*, *>?, event: ScreenStackEvent) {
        when (event) {
            ScreenStackEvent.APPEARED -> if (router != null) {
                attachChild(router)
            }
            ScreenStackEvent.HIDDEN, ScreenStackEvent.REMOVED -> if (router != null) {
                detachChild(router)
            }
            else ->{}
        }
    }
}