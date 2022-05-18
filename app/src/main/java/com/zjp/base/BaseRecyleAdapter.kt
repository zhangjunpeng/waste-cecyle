package com.zjp.base

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyleAdapter: RecyclerView.Adapter<BaseRecyleAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    fun getCate(cate: String) :String{
        return when (cate) {
            "1" -> "损伤性"
            "2" -> "感染性"
            "3" -> "病理性"
            "4" -> "药物性"
            "5" -> "化学性"

            else -> ""
        }
    }
}