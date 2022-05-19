package com.zjp.base

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zjp.medicalwasterecycle.R
import com.zjp.medicalwasterecycle.databinding.ItemBagInfoBinding

abstract class BaseRecyleAdapter(val context:Context): RecyclerView.Adapter<BaseRecyleAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    lateinit var binding: ItemBagInfoBinding

    val bgList=ArrayList<Drawable?>().apply {
        add(context.getDrawable(R.drawable.red_radius))
        add(context.getDrawable(R.drawable.yellow_radius))
        add(context.getDrawable(R.drawable.cyan_radius))
        add(context.getDrawable(R.drawable.blue_radius))
        add(context.getDrawable(R.drawable.orange_radius))
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseRecyleAdapter.ViewHolder {
        binding = ItemBagInfoBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding.root)
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