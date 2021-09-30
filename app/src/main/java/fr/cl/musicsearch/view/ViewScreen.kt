package fr.cl.musicsearch.view

import android.view.View
import android.view.ViewGroup
import com.uber.rib.core.screenstack.ViewProvider

class ViewScreen(private val builder: ViewBuilder) : ViewProvider() {

    var router: ViewRouter? = null
        private set

    lateinit var input : Any


    fun instantiateElement(input: Any){
        this.input = input
    }

    override fun buildView(parentView: ViewGroup): View {
        router = builder.build(parentView, input)
        return router!!.getView()
    }

    override fun doOnViewRemoved() {
        router = null
    }
}
