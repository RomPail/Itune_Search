package fr.cl.musicsearch.search

import android.R.attr.data
import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.cl.musicsearch.R
import fr.cl.musicsearch.search.manager.SearchGetInfo
import io.reactivex.subjects.PublishSubject


/**
 * Top level view for {@link NewCustomRibBuilder.NewCustomRibScope}.
 */
class SearchView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0)
    : ConstraintLayout(context, attrs, defStyle), SearchInteractor.SearchPresenter,SearchAdapter.ViewHolder.IMyViewHolderClicks{

    val searchPublish = PublishSubject.create<String>()
    val mediaPublish = PublishSubject.create<String>()
    val itemPublish = PublishSubject.create<String>()
    var spinnerMedia:Spinner? = null
    var resultList: ArrayList<SearchGetInfo>? = null
    var recyclerView: RecyclerView? =null
    var adapter: SearchAdapter? = null
    var textEmpty : TextView? = null
    var relativeEmptyText : RelativeLayout? = null
    var relativeLoader : RelativeLayout? = null
    lateinit var loader : ProgressBar

    override fun onFinishInflate() {
        super.onFinishInflate()

        spinnerMedia = findViewById(R.id.spinner_media)
        recyclerView = findViewById(R.id.recyclerview_result)
        loader = findViewById(R.id.progress_circular_search_rib)
        textEmpty = findViewById(R.id.emptyMessage)
        relativeEmptyText = findViewById(R.id.relative_empty_text)
        relativeLoader = findViewById(R.id.relative_loader)

        //Recycler init//
        recyclerView!!.layoutManager = LinearLayoutManager(context)
        resultList = ArrayList()
        adapter = SearchAdapter(context, resultList, this)
        recyclerView!!.adapter = adapter
        /////////////////


        //Search edittext//
        var searchEdit : EditText = findViewById(R.id.search_edit_text)
        searchEdit.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH ||actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_DONE) {

                    //do what you want on the press of 'done'
                    setSearchText(searchEdit.text.toString())

                    return true
                }
                return false
            }
        })
        //////////////////

        //Media spinner//
        val media = resources.getStringArray(R.array.Media)

        if (spinnerMedia != null) {
            val adapter = ArrayAdapter(context,
                android.R.layout.simple_spinner_item, media)
            spinnerMedia!!.adapter = adapter

            spinnerMedia!!.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View, position: Int, id: Long) {
                    Toast.makeText(context,
                        "Selected media:" + " " +
                                "" + media[position], Toast.LENGTH_SHORT).show()

                    setMediaText(media[position])
                }

                override fun onNothingSelected(parent: AdapterView<*>) {

                }
            }
        }
        /////////////////
    }

    /**
     * This function allow to add line in search list
     * **/
    override fun addLineRecycler(getArtistName: String, getTrackName: String, getPreviewURL: String,getArtWork:String, getIndice : Int) {
        val resultToScreenList: ArrayList<SearchGetInfo> = ArrayList()

        resultToScreenList.add(
            SearchGetInfo(
                getArtistName,
                getTrackName,
                getPreviewURL,
                getArtWork,
                getIndice
            )
        )
        val insertIndex = getIndice
        (resultList as ArrayList<SearchGetInfo>).addAll(insertIndex,resultToScreenList)
        adapter!!.notifyItemInserted(insertIndex)
    }




    /**
     * Show loading bar
     * **/
    override fun setLoader(enable : Boolean){
        if(enable){
            loader!!.visibility = View.VISIBLE
            relativeLoader!!.visibility = VISIBLE
            recyclerView!!.visibility = GONE
            textEmpty!!.visibility = GONE
            relativeEmptyText!!.visibility = GONE

        }else{
            loader!!.visibility = View.GONE
            relativeLoader!!.visibility = GONE
            recyclerView!!.visibility = VISIBLE
            textEmpty!!.visibility = GONE
            relativeEmptyText!!.visibility = GONE
        }
    }

    /**
     * Displays the lack of result
     * **/
    override fun setEmptyText(enable : Boolean){
        if(enable){
            textEmpty!!.visibility = VISIBLE
            relativeEmptyText!!.visibility = VISIBLE
            recyclerView!!.visibility = GONE
        }else{
            textEmpty!!.visibility = GONE
            relativeEmptyText!!.visibility = GONE
            recyclerView!!.visibility = VISIBLE
        }
    }

    /**
     * Function to clear the list
     * */
    override fun clearRecycler(){
        // clear old list
        val size: Int = (resultList as ArrayList<SearchGetInfo>).size
        (resultList as ArrayList<SearchGetInfo>).clear()
        adapter!!.notifyItemRangeRemoved(0, size)
    }



    /////Manage several stream/////
    override fun getSearchText(): PublishSubject<String> {
        return searchPublish
    }

    override fun getMediaText(): PublishSubject<String> {
        return mediaPublish
    }

    override fun onItemClick(position: Int) {
        //Call when user push on item
        adapter!!.getItem(position)?.let { itemPublish.onNext(it) }
    }

    override fun getItemId(): PublishSubject<String> {
        return itemPublish
    }

    fun setSearchText(search: String) {

        if( search != null){
            searchPublish.onNext(search)
        }
    }

    fun setMediaText(media: String) {

        if( media != null){
            mediaPublish.onNext(media)
        }
    }



}
