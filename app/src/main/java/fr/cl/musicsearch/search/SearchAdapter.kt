package fr.cl.musicsearch.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.cl.musicsearch.R
import fr.cl.musicsearch.search.manager.SearchGetInfo
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject


class SearchAdapter internal constructor(// inflates the row layout from xml when needed
    var context: Context?, infoResult: ArrayList<SearchGetInfo>?, mListener: ViewHolder.IMyViewHolderClicks) : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {


    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private val onClickSubject = PublishSubject.create<SearchGetInfo>()
    private val mClickListener: ViewHolder.IMyViewHolderClicks
    private var infoResult: ArrayList<SearchGetInfo> = ArrayList()


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = mInflater.inflate(R.layout.recycler_search, parent, false)
        return ViewHolder(view, mClickListener)
    }

    // binds the data to the TextView in each row
    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val scanInf: SearchGetInfo = infoResult[position]
        holder.artistName.text = scanInf.getArtistNameResult()
        holder.trackName.text = scanInf.getTrackNameResult()
        Picasso.get().load(scanInf.getArtWorkResult()).into(holder.artWork);

    }

    val positionClicks: Observable<SearchGetInfo>
        get() = onClickSubject.hide()


    private fun updateData() {

        notifyDataSetChanged()
    }

    /* total number of rows */
    override fun getItemCount(): Int {
        return infoResult.size ?: 0
    }

    // stores and recycles views as they are scrolled off screen
    class ViewHolder(
        itemView: View,
        var mListener: IMyViewHolderClicks
    ) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {

        var artistName: TextView = itemView.findViewById(R.id.artist_name_text)
        var trackName: TextView = itemView.findViewById<TextView>(R.id.track_name_text)
        var artWork : ImageView = itemView.findViewById(R.id.image_view_result)


        override fun onClick(view: View) {

            mListener.onItemClick(adapterPosition)
        }

        interface IMyViewHolderClicks {
            fun onItemClick(position: Int)
        }

        init {
            artistName.setOnClickListener(this)
            trackName.setOnClickListener(this)
            artWork.setOnClickListener(this)
            itemView.setOnClickListener(this)
        }

    }


    init {
        this.infoResult = infoResult!!
        mClickListener = mListener
    }

    fun getItem(position: Int): String? {
        val resultIndice: SearchGetInfo = infoResult[position]

        updateData()
        return resultIndice.getIndiceResult().toString()
    }
}