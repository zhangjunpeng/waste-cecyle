package com.zjp.medicalwasterecycle.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nextmar.requestdata.model.ListElement
import com.zjp.base.BaseRecyleAdapter
import com.zjp.medicalwasterecycle.databinding.ActivityMainBinding
import com.zjp.medicalwasterecycle.databinding.ItemBagInfoMainBinding

class MainRecylerAdapter(val context: Context,val dataList : List<ListElement>) :
    RecyclerView.Adapter<MainRecylerAdapter.ViewHolder>() {
    lateinit var binding: ItemBagInfoMainBinding

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemBagInfoMainBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position==0){
            binding.iconItemBagInfo.visibility=View.GONE
        }else{

        }
    }

    override fun getItemCount(): Int {
       return dataList.size+1
    }
}