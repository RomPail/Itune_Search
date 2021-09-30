package fr.cl.musicsearch.view

import android.content.Context
import android.util.AttributeSet
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.constraintlayout.widget.ConstraintLayout
import fr.cl.musicsearch.R
import io.reactivex.subjects.PublishSubject


/**
 * Top level view for {@link NewCustomRibBuilder.NewCustomRibScope}.
 */
class ViewView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0)
    : ConstraintLayout(context, attrs, defStyle), ViewInteractor.ViewPresenter{

    private lateinit var webView: WebView
    var getUrl:String = ""
    override fun onFinishInflate() {
        super.onFinishInflate()


        webView = findViewById(R.id.webview)
        webView.settings.setJavaScriptEnabled(true)
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url.toString())
                return true
            }
        }
        webView.loadUrl("https://www.google.fr/")

    }

    override fun setURL (url : String){
        getUrl = url
        webView.loadUrl(getUrl)
    }

}
