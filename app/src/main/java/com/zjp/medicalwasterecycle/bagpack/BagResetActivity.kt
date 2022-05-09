package com.zjp.medicalwasterecycle.bagpack

import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.children
import com.zjp.base.BaseActivity
import com.zjp.medicalwasterecycle.R
import com.zjp.medicalwasterecycle.databinding.ActivityBagResetBinding

class BagResetActivity : BaseActivity(), View.OnClickListener {
    lateinit var binding: ActivityBagResetBinding

    val titleList = ArrayList<View>()
    override fun setView() {
        binding = ActivityBagResetBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bagInfoItem.iconItemBagInfo.visibility = View.VISIBLE
        binding.bagInfoItem.batchNumItemBagInfo.setTypeface(null, Typeface.NORMAL)
        binding.bagInfoItem.cateItemBagInfo.setTypeface(null, Typeface.NORMAL)
        binding.bagInfoItem.roomItemBagInfo.setTypeface(null, Typeface.NORMAL)
        binding.bagInfoItem.nurseItemBagInfo.setTypeface(null, Typeface.NORMAL)
        binding.bagInfoItem.weightItemBagInfo.setTypeface(null, Typeface.NORMAL)
        binding.bagInfoItem.timeItemBagInfo.setTypeface(null, Typeface.NORMAL)

        binding.titleBagReset.children.forEach {
            it.setOnClickListener(this)
        }
        binding.backIndex.setOnClickListener {
            finish()
        }


    }

    override fun onClick(view: View?) {

        if (view in binding.titleBagReset.children) {
            binding.titleBagReset.children.forEach {
                it.background=getDrawable(R.drawable.gray_radius)
            }
            view!!.background = getDrawable(R.drawable.orange_3radius)
        }
    }
}