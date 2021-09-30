package fr.cl.musicsearch.search

import android.view.View
import android.view.ViewGroup
import com.uber.rib.core.screenstack.ViewProvider

class SearchScreen(private val builder: SearchBuilder) : ViewProvider() {

    var router: SearchRouter? = null
        private set


    override fun buildView(parentView: ViewGroup): View {
        router = builder.build(parentView)
        return router!!.getView()
    }

    override fun doOnViewRemoved() {
        router = null
    }
}
