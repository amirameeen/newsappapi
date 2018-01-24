package com.amirtokopedia.newsapitokopedia.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.amirtokopedia.newsapitokopedia.R
import com.amirtokopedia.newsapitokopedia.model.local.CategoryModel
import com.amirtokopedia.newsapitokopedia.model.local.CountryModel
import kotlinx.android.synthetic.main.card_menu.view.*
import java.util.*

/**
 * Created by Amir Malik on 1/23/18.
 */
class CategoryAdapter(val context: Context, val data : CategoryModel?) : RecyclerView.Adapter<CategoryAdapter.MyViewHolder>() {
    var parentContext : Context = context
    var itemListener : onItemClick? = null

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        override fun onClick(v: View?) {
            if(v == itemView.menu_card){
                itemListener?.onItemSelected(data?.categories?.get(adapterPosition)!!, itemView.iv_check)
            }
        }

        init {
            itemView.menu_card.setOnClickListener(this)
        }

        fun bindView(position : Int) {
            var source = data?.categories?.get(position)
            itemView.tv_name.text = source?.name
            if(source?.name?.equals("All", true)!!)
            {
                itemView.iv_check.visibility = View.VISIBLE
                itemListener?.setCurrentCategory(itemView.iv_check)
            }

        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        parentContext = parent.context
        return MyViewHolder(inflater.inflate(R.layout.card_menu, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindView(position)
    }

    override fun getItemCount(): Int {
        return data?.categories?.size!!
    }

    /** The listener class for source list items  */
    interface onItemClick {

        fun setCurrentCategory(view : View)
        /** Called when a my request item is selected  */
        fun onItemSelected(item: CategoryModel.dataCategory, tempView : View)

    }
}