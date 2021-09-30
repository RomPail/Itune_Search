package fr.cl.musicsearch.root

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.drawerlayout.widget.DrawerLayout
import fr.cl.musicsearch.R


/**
 * Top level view for {@link NewCustomRibBuilder.NewCustomRibScope}.
 */
class RootView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0)
    : DrawerLayout(context, attrs, defStyle), RootInteractor.RootPresenter{

    internal lateinit var container: ViewGroup

    override fun onFinishInflate() {
        super.onFinishInflate()
        var containerLayout = findViewById<ConstraintLayout>(R.id.main_content)
        container = containerLayout
    }


    fun viewContainer(): ViewGroup {
        return container
    }
}
