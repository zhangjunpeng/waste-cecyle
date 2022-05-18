package com.zjp.medicalwasterecycle.roomscan

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nextmar.requestdata.model.RoomBagListData
import com.zjp.base.BaseRecyleAdapter
import com.zjp.medicalwasterecycle.databinding.ItemBagInfoBinding

class RoomAdapter(val context: Context, val data: RoomBagListData) : BaseRecyleAdapter() {


    lateinit var binding: ItemBagInfoBinding

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseRecyleAdapter.ViewHolder {
        binding = ItemBagInfoBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: BaseRecyleAdapter.ViewHolder, position: Int) {
        binding.roomItemBagInfo.visibility = View.GONE
        binding.batchNumItemBagInfo.visibility = View.GONE
        if (position == 0) {

        } else {
            data.list!![position - 1].apply {
                binding.isprintBagInfo.text = when (isPrint!!) {
                    "1" -> "未打印"
                    "0" -> "已打印"
                    else -> ""
                }
                binding.cateItemBagInfo.text = getCate(category!!)
                binding.nurseItemBagInfo.text = when (nurseName.isNullOrBlank()) {
                    true -> "未签名"
                    false -> nurseName
                }
                if (nurseName.isNullOrBlank()) {
                    binding.nurseItemBagInfo.setTextColor(Color.RED)
                } else {
                    binding.nurseItemBagInfo.setTextColor(Color.parseColor("#525252"))
                }

                binding.timeItemBagInfo.text = scanTime
                binding.weightItemBagInfo.text = weight.toString()
            }
        }
    }


    override fun getItemId(p0: Int): Long {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return data.list!!.size + 1

    }


}