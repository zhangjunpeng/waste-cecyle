package com.zjp.medicalwasterecycle.roomscan

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zjp.base.BaseRecyleAdapter
import com.zjp.medicalwasterecycle.databinding.ItemBagInfoBinding
import com.zjp.medicalwasterecycle.databinding.ItemStatusBagDialogBinding

class RoomBagStatusAdapter(val context: Context): RecyclerView.Adapter<RoomBagStatusAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    lateinit var binding: ItemStatusBagDialogBinding

    val nameList=ArrayList<String>().apply {
        add("包裹是否破损")
        add("包裹是否消毒")
        add("包裹封口严密")
        add("包裹分类收集")
        add("包裹小于3/4 ")
        add("含有药物废物")

    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RoomBagStatusAdapter.ViewHolder {
        binding = ItemStatusBagDialogBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: RoomBagStatusAdapter.ViewHolder, position: Int) {
        binding.name.text=nameList[position]
    }

    override fun getItemCount(): Int {
        return 6
    }
}