package fr.cl.musicsearch

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import com.uber.rib.core.RibActivity
import com.uber.rib.core.ViewRouter
import fr.cl.musicsearch.core.Services.Companion.setMainActivity
import fr.cl.musicsearch.core.utils.UtilsDeepLink
import fr.cl.musicsearch.root.RootBuilder
import fr.cl.musicsearch.root.RootInteractor


class MainActivity : RibActivity() {
    private val TAG = "MainActivity"
    lateinit var rootInteractor: RootInteractor

    @SuppressLint("WrongConstant")
    open operator fun get(context: Context): MainActivity {
        return context.getSystemService(TAG) as MainActivity
    }
    public override fun onResume() {
        super.onResume()
    }


    public override fun onDestroy() {
        super.onDestroy()
        // close the database and listeners
    }

    //rib inopen it
    override fun createRouter(parentViewGroup: ViewGroup): ViewRouter<*, *, *> {

        val rootBuilder = RootBuilder(object :
            RootBuilder.ParentComponent {})
        val router = rootBuilder.build(parentViewGroup)
        rootInteractor = router.getInteractor() as RootInteractor
        return router
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // remove title
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setMainActivity(this@MainActivity)

        //To link root RIB
        if (intent.data != null) {
            UtilsDeepLink.setDeepLink(getIntent(), rootInteractor)
        }
    }
}