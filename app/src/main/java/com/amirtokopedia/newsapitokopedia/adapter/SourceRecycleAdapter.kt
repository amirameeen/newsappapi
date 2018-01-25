package com.amirtokopedia.newsapitokopedia.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.amirtokopedia.newsapitokopedia.R
import com.amirtokopedia.newsapitokopedia.model.remote.Source
import com.amirtokopedia.newsapitokopedia.model.remote.SourceResponse
import kotlinx.android.synthetic.main.card_news_source.view.*
import java.util.*

/**
 * Created by Amir Malik on 1/23/18.
 */
class SourceRecycleAdapter(val context: Context, val data : SourceResponse?) : RecyclerView.Adapter<SourceRecycleAdapter.MyViewHolder>() {
    var parentContext : Context = context
    var itemListener : onItemClick? = null

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        override fun onClick(v: View?) {
            if(v == itemView.main_card){
                itemListener?.onItemSelected(data?.sources?.get(adapterPosition)!!)
            }
        }

        init {
            itemView.main_card.setOnClickListener(this)
        }

        fun bindView(position : Int) {
            var source = data?.sources?.get(position)

            itemView.source_name.text = source?.name
            var textDescription = source?.description
            if(textDescription?.length!! > 100)
                textDescription = textDescription.substring(0, 100) + "..."
            itemView.source_description.text = textDescription
            itemView.source_url.text = source?.url

            itemView.source_category.text = source?.category

            val language = Locale(source?.language)
            itemView.language.text = language.displayLanguage.toString()

            val country = Locale("", source?.country)
            itemView.country.text = country.displayCountry.toString()

        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        parentContext = parent.context
        return MyViewHolder(inflater.inflate(R.layout.card_news_source, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindView(position)
    }

    override fun getItemCount(): Int {
        return data?.sources?.size!!
    }

    /** The listener class for source list items  */
    interface onItemClick {

        /** Called when a my request item is selected  */
        fun onItemSelected(item: Source)

    }
}