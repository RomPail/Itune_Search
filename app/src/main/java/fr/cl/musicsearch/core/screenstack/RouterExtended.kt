package fr.cl.musicsearch.core.screenstack

import com.uber.rib.core.InteractorBaseComponent
import com.uber.rib.core.Router
import com.uber.rib.core.screenstack.lifecycle.ScreenStackEvent

@Suppress("FINITE_BOUNDS_VIOLATION_IN_JAVA")
open class RouterExtended<I : com.uber.rib.core.Interactor<*, *>, C : InteractorBaseComponent<*>>(
    interactor: I,
    component: C
) :
    Router<I, C>(interactor, component) {

    protected fun handleScreenEvents(router: Router<*, *>?, event: ScreenStackEvent) {
        when (event) {
            ScreenStackEvent.APPEARED -> if (router != null) {
                attachChild(router)
            }
            ScreenStackEvent.HIDDEN, ScreenStackEvent.REMOVED -> if (router != null) {
                detachChild(router)
            }

            else ->{

            }
        }
    }

}