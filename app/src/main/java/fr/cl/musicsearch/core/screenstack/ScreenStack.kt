package fr.cl.musicsearch.core.screenstack

import android.view.ViewGroup
import androidx.annotation.UiThread
import com.uber.rib.core.screenstack.ScreenStackBase
import com.uber.rib.core.screenstack.ViewProvider
import java.util.*


@UiThread
 class ScreenStack(private val parentViewGroup: ViewGroup): ScreenStackBase {
    val backStack = ArrayDeque<ViewProvider>()

    fun printStackContent(){

        println(" ////////////////// STACK ///////////////////////")

        for (element in backStack){

            if(element!=null ){
                println( " element::class.java.name : "+element::class.java.name)
                println( " adress : "+element.hashCode())

            }



        }

        println(" /----------------- END STACK --------------------")


    }

    private val currentViewProvider: ViewProvider?
    get() = if (backStack.isEmpty()) {
        null
    } else backStack.peek()

    override fun pushScreen(viewProvider:ViewProvider){
        return pushScreen(viewProvider, false)
    }

    override fun pushScreen(viewProvider: ViewProvider, shouldAnimate:Boolean){
        removeCurrentView()
        onCurrentViewHidden()
        backStack.push(viewProvider)
         // order matters here
        addCurrentView()
       onCurrentViewAppeared()

        //TODO REMOVE
      //  printStackContent()
    }

    override fun popScreen() {
        popScreen(false)
    }

    override fun popScreen(shouldAnimate:Boolean) {
        if (backStack.isEmpty()){
            return
        }

        removeCurrentView()
        onCurrentViewRemoved()
        backStack.pop()
        addCurrentView()
        onCurrentViewAppeared()

        //TODO REMOVE
      //  printStackContent()
    }

    override fun popBackTo(index:Int, shouldAnimate:Boolean) {
        for (size in backStack.size - 1 downTo index + 1){
            popScreen()
        }
    }


    override fun handleBackPress():Boolean {
        return handleBackPress(false)
    }

    override fun handleBackPress(shouldAnimate:Boolean):Boolean {

        if (backStack.size == 1){

            //TODO REMOVE
           // printStackContent()
            return false
        }

        popScreen()

        //TODO REMOVE
      //  printStackContent()
            return true
        }

        override fun size():Int {
            return backStack.size
        }

    /**
     * Returns the index of the last item in the stack.
     * @return -1 is return when the backstack is empty.
     */
        fun indexOfLastItem():Int {
            return size() - 1
        }

        private fun onCurrentViewHidden() {

            val vp = currentViewProvider

            if (vp != null) {
                vp.onViewHidden()
            }
        }

        private fun onCurrentViewAppeared() {

            val vp = currentViewProvider
            if (vp != null){
                vp.onViewAppeared()
            }
        }

        private fun onCurrentViewRemoved() {
            val vp = currentViewProvider
            if (vp != null) {
                vp.onViewRemoved()
            }
        }

        private fun addCurrentView() {
            val vp = currentViewProvider
            if (vp != null) {
                parentViewGroup.addView(vp.buildView(parentViewGroup))
            }
        }

        private fun removeCurrentView() {
            if (parentViewGroup.childCount > 0) {
                parentViewGroup.removeViewAt(parentViewGroup.childCount - 1)
            }
        }
}