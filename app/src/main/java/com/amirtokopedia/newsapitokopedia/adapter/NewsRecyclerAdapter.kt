package com.amirtokopedia.newsapitokopedia.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.amirtokopedia.newsapitokopedia.R
import com.amirtokopedia.newsapitokopedia.model.remote.ArticlesResponse

/**
 * Created by Amir Malik on 1/23/18.
 */
class NewsRecyclerAdapter(val context: Context, val data : ArticlesResponse?) : RecyclerView.Adapter<NewsRecyclerAdapter.MyViewHolder>() {
    var parentContext : Context = context

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bindView(position : Int) {
            var datas = data?.articles?.get(position)

//            itemView.valueTemp.text = datas?.main?.temp.toString()
//            itemView.valuePressure.text = datas?.main?.pressure.toString()
//            itemView.valueHumidity.text = datas?.main?.humidity.toString()
//            itemView.valueType.text = datas?.weather?.get(0)?.main.toString()
//            itemView.valueDescription.text = datas?.weather?.get(0)?.description.toString()
//            itemView.valueCloud.text = datas?.clouds?.all.toString()
//            itemView.valueSpeed.text = datas?.wind?.speed.toString()
//            itemView.valueDeg.text = datas?.wind?.deg.toString()
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

}