package com.zjp.medicalwasterecycle.roomscan

import android.app.Dialog
import android.os.Build
import android.view.*
import androidx.annotation.RequiresApi
import androidx.core.view.children
import androidx.core.view.get
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.ToastUtils
import com.zjp.base.BaseActivity
import com.zjp.medicalwasterecycle.databinding.ActivityRoomScanBinding
import com.zjp.medicalwasterecycle.databinding.DialogCategoryBagBinding
import com.zjp.medicalwasterecycle.databinding.DialogStatusBagBinding
import com.zjp.utils.DialogUtil
import com.zjp.viewmodel.MyViewModelFactory
import java.util.*

class RoomScanActivity : BaseActivity() {

    var preWidget: Double = 0.0
    var preTime: Long? = null
    lateinit var binding: ActivityRoomScanBinding
    lateinit var roomScanViewModel: RoomScanViewModel
    lateinit var dialogBinding: DialogStatusBagBinding
    lateinit var categoryDialogBinding: DialogCategoryBagBinding


    var tempCategoryMap: HashMap<String, String>? = null


    var statusDialog: Dialog? = null
    var categoryDialog: Dialog? = null

    val preNum = "包裹编号："
    val preRoom1 = "科室："
    val preRoom2 = "所属科室："
    val preNurse = "交接护士："
    val preCate = "医废类型："




    @RequiresApi(Build.VERSION_CODES.R)
    override fun setView() {
        binding = ActivityRoomScanBinding.inflate(layoutInflater)
        dialogBinding = DialogStatusBagBinding.inflate(layoutInflater)
        categoryDialogBinding = DialogCategoryBagBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTitleContent(binding.title)
        binding.recylerRoom.layoutManager = LinearLayoutManager(this)
        binding.changeStatus.setOnClickListener {
            val adapter = RoomBagStatusAdapter(this@RoomScanActivity, roomScanViewModel)
            dialogBinding.gridview.adapter = adapter
            statusDialog?.show()
        }
        binding.changeCate.setOnClickListener {
            categoryDialog?.show()
            tempCategoryMap = roomScanViewModel.categoryData.value
        }
        binding.roomPrint.setOnClickListener {
            roomScanViewModel.roomPrint()
        }

        initDialog()
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun initDialog() {
        if (statusDialog == null) {
            statusDialog = Dialog(this).apply {
                //自定义的东西
                setContentView(dialogBinding.root)
                dialogBinding.gridview.layoutManager =
                    GridLayoutManager(this@RoomScanActivity, 2, RecyclerView.VERTICAL, false)
                val adapter = RoomBagStatusAdapter(this@RoomScanActivity, roomScanViewModel)
                dialogBinding.gridview.adapter = adapter
                dialogBinding.complete.setOnClickListener {
                    roomScanViewModel.bagQuaData.postValue(adapter.map)
                    dismiss()
                }
//                show()

                //放在show()之后，不然有些属性是没有效果的，比如height和width
                val dialogWindow: Window = window!!
                val d: Display = this@RoomScanActivity.display!! // 获取屏幕宽、高用
                val p: WindowManager.LayoutParams = dialogWindow.attributes // 获取对话框当前的参数值
                // 设置高度和宽度
                p.width = ((d.width * 0.6).toInt()) // 宽度设置为屏幕的0.65
                p.gravity = Gravity.CENTER //设置位置
                dialogWindow.attributes = p

            }
        }

        if (categoryDialog == null) {
            categoryDialog = Dialog(this).apply {
                //自定义的东西
                setContentView(categoryDialogBinding.root)

//                show()
                categoryDialogBinding.categoryLayout.children.forEach { it ->
                    it.isSelected = false
                    val index = categoryDialogBinding.categoryLayout.children.indexOf(it)

                    if (tempCategoryMap == null) {
                        tempCategoryMap = roomScanViewModel.categoryData.value
                    }
                    if (getIndexCategory(index) == tempCategoryMap!!["category"].toString()) {
                        it.isSelected = true
                    }

                    it.setOnClickListener { view ->
                        categoryDialogBinding.categoryLayout.children.forEach { it ->
                            it.isSelected = false
                        }

                        categoryDialogBinding.secondLayout.children.forEach { child ->
                            child.isSelected = false
                        }
                        categoryDialogBinding.shuyeping.isSelected = false

                        tempCategoryMap?.set("category", getIndexCategory(index))
                        view.isSelected = true

                    }
                }
                categoryDialogBinding.touxiLayout.setOnClickListener {
                    categoryDialogBinding.categoryLayout.children.forEach { it ->
                        it.isSelected = false
                    }
                    categoryDialogBinding.shuyeping.isSelected = false
                    categoryDialogBinding.secondLayout.children.forEach { child ->
                        child.isSelected = false
                    }
                    categoryDialogBinding.categoryLayout[0].isSelected = true
                    it.isSelected = true
                }
                categoryDialogBinding.taipanLayout.setOnClickListener {
                    categoryDialogBinding.categoryLayout.children.forEach { it ->
                        it.isSelected = false
                    }
                    categoryDialogBinding.shuyeping.isSelected = false
                    categoryDialogBinding.categoryLayout[2].isSelected = true
                    categoryDialogBinding.secondLayout.children.forEach { child ->
                        child.isSelected = false
                    }
                    it.isSelected = true
                }
                categoryDialogBinding.shuyeping.setOnClickListener {
                    categoryDialogBinding.categoryLayout.children.forEach { it ->
                        it.isSelected = false
                    }
                    categoryDialogBinding.secondLayout.children.forEach { it ->
                        it.isSelected = false
                    }

                    it.isSelected = true
                }
                categoryDialogBinding.canceldefault.setOnClickListener {
                    roomScanViewModel.categoryData.postValue(HashMap<String, String>().apply {
                        this["category"] = "2"
                        this["dialysis"] = "1"
                        this["placenta"] = "1"
                    })
                    dismiss()
                }
                categoryDialogBinding.setdefault.setOnClickListener {
                    roomScanViewModel.categoryData.postValue(tempCategoryMap!!)
                    dismiss()
                }
                //放在show()之后，不然有些属性是没有效果的，比如height和width
                val dialogWindow: Window = window!!
                val d: Display = this@RoomScanActivity.display!! // 获取屏幕宽、高用
                val p: WindowManager.LayoutParams = dialogWindow.attributes // 获取对话框当前的参数值
                // 设置高度和宽度
                p.width = ((d.width * 0.6).toInt()) // 宽度设置为屏幕的0.65
                p.gravity = Gravity.CENTER //设置位置
                dialogWindow.attributes = p

            }
        }

    }

    fun getIndexCategory(index: Int): String {
        return when (index) {
            0 -> "2"
            1 -> "1"
            2 -> "3"
            3 -> "4"
            4 -> "5"
            else -> "-1"
        }
    }

    override fun initViewModel() {
        roomScanViewModel =
            ViewModelProvider(this, MyViewModelFactory.instance)[RoomScanViewModel::class.java]
        roomScanViewModel.roomShowDataResult.observe(this) {
            if (it == null) {
                return@observe
            }
            DialogUtil.instance.dismissProgressDialog()
            binding.roomName.text = preRoom1 + it!!.name
            binding.belongRoom.text = preRoom2 + it!!.name
            roomScanViewModel.whiteBagParams["room_id"] = it.id!!
            roomScanViewModel.roomId = it.id!!


            roomScanViewModel.getRoomBagList(it.id!!)
        }
        roomScanViewModel.errorResult.observe(this) {
            DialogUtil.instance.dismissProgressDialog()
            ToastUtils.showLong(it.msg)

        }
        roomScanViewModel.roomBagListResult.observe(this) {
            DialogUtil.instance.dismissProgressDialog()
            binding.bagInfoRoom.totoalWeight.text = it?.weight.toString() + "KG"
            binding.bagInfoRoom.totalNum.text = it?.signNum.toString() + "包"
            binding.bagInfoRoom.unselectNum.text = it?.notSignNum.toString() + "包"
            binding.recylerRoom.adapter = RoomAdapter(this, it!!)
        }
        roomScanViewModel.bagInfoResult.observe(this) {
            DialogUtil.instance.dismissProgressDialog()

            if (it == null) {
                addDefaultInfo()
            } else {
                binding.nurse.text = preNurse + it.nrname
                binding.cateWaste.text =
                    preCate + roomScanViewModel.getCategoryString(it.category!!)
                binding.changeCate.visibility = View.VISIBLE
                roomScanViewModel.bagId = it.id.toString()

                if (it.quality != null) {
                    roomScanViewModel.bagQuaData.postValue(it.quality!!.toMap())
                }

            }
        }

        roomScanViewModel.addBagResult.observe(this) {
            if (it == null) {
                return@observe
            }
            binding.bagInfoRoom.totoalWeight.text = it?.weight.toString() + "KG"
            binding.bagInfoRoom.totalNum.text = it?.signNum.toString() + "包"
            binding.bagInfoRoom.unselectNum.text = it?.notSignNum.toString() + "包"

            roomScanViewModel.getRoomBagList(roomScanViewModel.whiteBagParams["room_id"].toString())
        }
        roomScanViewModel.bagQuaData.observe(this) {
            roomScanViewModel.changeBagStatus(roomScanViewModel.bagId)
            binding.isbroken.text =
                "包裹是否破损(${getIsString(it[roomScanViewModel.quaKeyList[0]] == "0")})"
            binding.isdisinfect.text =
                "包裹是否消毒(${getIsString(it[roomScanViewModel.quaKeyList[1]] == "0")})"
            binding.istight.text =
                "包裹封口严密(${getIsString(it[roomScanViewModel.quaKeyList[2]] == "0")})"
            binding.isclassify.text =
                "包裹分类收集(${getIsString(it[roomScanViewModel.quaKeyList[3]] == "0")})"
            binding.islittle.text =
                "包裹小于3/4  (${getIsString(it[roomScanViewModel.quaKeyList[4]] == "0")})"
            binding.iscontain.text =
                "含有药物废物(${getIsString(it[roomScanViewModel.quaKeyList[5]] == "0")})"
        }
        roomScanViewModel.categoryData.observe(this) {
            binding.cateWaste.text = preCate + roomScanViewModel.getCategoryString(it["category"]!!)

        }
        roomScanViewModel.widgetData.observe(this) {
            if (it - preWidget < 0.1) {
                if (roomScanViewModel.timer == null) {
                    roomScanViewModel.timer = Timer()
                    roomScanViewModel.timer?.schedule(roomScanViewModel.timerTask, 3 * 1000)
                }
            } else {
                roomScanViewModel.timer?.cancel()
                roomScanViewModel.timer = null
            }

            preWidget = it

        }
    }

    fun getIsString(sta: Boolean): String {
        return if (sta) {
            "是"
        } else {
            "否"
        }
    }

    private fun addDefaultInfo() {
        roomScanViewModel.whiteBagParams.putAll(roomScanViewModel.bagQuaData.value!!)
        roomScanViewModel.addBag(roomScanViewModel.bagCode)
    }

    override fun onRevicerScan(keyStr: String) {
        binding.bagNum.text = preNum + keyStr
        roomScanViewModel.bagCode = keyStr
        roomScanViewModel.showBagInfo(roomScanViewModel.bagCode)

    }

    override fun initData() {
        val code = intent.getStringExtra("code")
        if (code != null) {
            DialogUtil.instance.showProgressDialog(this)
            roomScanViewModel.getRoomInfo(code)
            //test
            roomScanViewModel.bagCode = "BG10000111202902"
            roomScanViewModel.showBagInfo(roomScanViewModel.bagCode)
        }
    }
}