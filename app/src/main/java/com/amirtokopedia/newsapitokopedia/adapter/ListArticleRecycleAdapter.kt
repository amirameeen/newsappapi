package com.amirtokopedia.newsapitokopedia.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.amirtokopedia.newsapitokopedia.R
import com.amirtokopedia.newsapitokopedia.model.remote.ArticlesResponse
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_list_article.view.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Amir Malik on 1/23/18.
 */
class ListArticleRecycleAdapter(val context: Context, val data : ArticlesResponse?) : RecyclerView.Adapter<ListArticleRecycleAdapter.MyViewHolder>() {
    var parentContext : Context = context
    var itemListener : onItemClick? = null

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        override fun onClick(v: View?) {
            if(v == itemView.main_card){
                itemListener?.onItemSelected(data?.articles?.get(adapterPosition)!!)
            }
        }

        init {
            itemView.main_card.setOnClickListener(this)
        }

        fun bindView(position : Int) {
            var source = data?.articles?.get(position)

            Picasso.with(context).load(source?.urlToImage)
                    .error(R.drawable.ic_image_loading)
                    .placeholder(R.drawable.ic_image_loading)
                    .centerCrop().fit().into(itemView.iv_news_image)
            itemView.tv_title.text = source?.title
//            var textDescription = source?.description
//            if(textDescription?.length!! > 100)
//                textDescription = textDescription.substring(0, 100) + "..."
//            itemView.tv_description.text = textDescription
            itemView.tv_author.text = source?.author
            val date = source?.publishedAt
            val simpleFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'",
                    Locale.ENGLISH)
            try {
                val parsedDate = simpleFormat.parse(date)
                val printDate = SimpleDateFormat("dd MMM yyyy - HH:mm")
//                val printTime = SimpleDateFormat("HH:mm")
                itemView.tv_date.text = printDate.format(parsedDate).toString()
//                itemView.tv_time.text = "-" printDate.format(parsedDate).toString()
            } catch (e: ParseException) {
                e.printStackTrace()
            }


        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        parentContext = parent.context
        return MyViewHolder(inflater.inflate(R.layout.card_list_article, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindView(position)
    }

    override fun getItemCount(): Int {
        return data?.articles?.size!!
    }

    /** The listener class for source list items  */
    interface onItemClick {

        /** Called when a my request item is selected  */
        fun onItemSelected(item: ArticlesResponse.dataArticle)

    }
}