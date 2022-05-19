package com.zjp.medicalwasterecycle.roomscan

import android.app.Dialog
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import com.nextmar.requestdata.NameSpace
import com.zjp.base.BaseActivity
import com.zjp.medicalwasterecycle.databinding.ActivityRoomScanBinding
import com.zjp.medicalwasterecycle.databinding.DialogStatusBagBinding
import com.zjp.utils.DialogUtil
import com.zjp.viewmodel.MyViewModelFactory

class RoomScanActivity : BaseActivity() {

    lateinit var binding: ActivityRoomScanBinding
    lateinit var roomScanViewModel: RoomScanViewModel
    lateinit var dialogBinding:DialogStatusBagBinding


    val preNum="包裹编号："
    val preRoom1="科室："
    val preRoom2="所属科室："
    val preNurse="交接护士："
    val preCate="医废类型："



    val whiteBagParams=HashMap<String,String>().apply {
        this["project_id"]=SPUtils.getInstance().getString(NameSpace.ProjectID)
        this["member_id"]=SPUtils.getInstance().getString(NameSpace.MemberID)
        this["category"]="2"
        this["daysp_type"]="bottle"
        this["weight"]="20.1"
        this["is_print"]="1"
    }


    var bagId=""

    override fun setView() {
        binding=ActivityRoomScanBinding.inflate(layoutInflater)
        dialogBinding=DialogStatusBagBinding.inflate(layoutInflater,binding.root,true)
        setContentView(binding.root)
        setTitleContent(binding.title)
        binding.recylerRoom.layoutManager=LinearLayoutManager(this)
        binding.changeStatus.setOnClickListener {
            val dialog=Dialog(this).apply {
                setContentView(dialogBinding.root)
                dialogBinding.gridview.layoutManager =
                    GridLayoutManager(this@RoomScanActivity, 4,RecyclerView.HORIZONTAL,false)
                dialogBinding.gridview.adapter = RoomBagStatusAdapter(this@RoomScanActivity)
                show()
            }

        }

    }

    override fun initViewModel() {
        roomScanViewModel= ViewModelProvider(this, MyViewModelFactory.instance)[RoomScanViewModel::class.java]
        roomScanViewModel.roomShowDataResult.observe(this){
            DialogUtil.instance.dismissProgressDialog()
            binding.roomName.text=preRoom1+it!!.name
            binding.belongRoom.text=preRoom2+it!!.name
            whiteBagParams["room_id"] = it.id!!

            roomScanViewModel.getRoomBagList(it.id!!)


        }
        roomScanViewModel.errorResult.observe(this){
            DialogUtil.instance.dismissProgressDialog()
            ToastUtils.showLong(it.msg)

        }
        roomScanViewModel.roomBagListResult.observe(this){
            DialogUtil.instance.dismissProgressDialog()
            binding.bagInfoRoom.totoalWeight.text=it?.weight.toString()+"KG"
            binding.bagInfoRoom.totalNum.text=it?.signNum.toString()+"包"
            binding.bagInfoRoom.unselectNum.text=it?.notSignNum.toString()+"包"
            binding.recylerRoom.adapter=RoomAdapter(this,it!!)
        }
        roomScanViewModel.bagInfoResult.observe(this){
            DialogUtil.instance.dismissProgressDialog()

            if (it == null) {
                addDefaultInfo()
            } else {
                binding.nurse.text = preNurse + it.nrname
                binding.cateWaste.text = preCate + it.category
                binding.changeCate.visibility = View.VISIBLE
            }
        }

        roomScanViewModel.addBagResult.observe(this) {
            if (it == null) {
                return@observe
            }
            binding.bagInfoRoom.totoalWeight.text = it?.weight.toString() + "KG"
            binding.bagInfoRoom.totalNum.text = it?.signNum.toString() + "包"
            binding.bagInfoRoom.unselectNum.text = it?.notSignNum.toString() + "包"

            roomScanViewModel.getRoomBagList(whiteBagParams["room_id"].toString())
        }
    }

    private fun addDefaultInfo() {
        whiteBagParams.putAll(roomScanViewModel.bagQuaData.value!!)
        roomScanViewModel.addBag(bagId,whiteBagParams)
    }

    override fun onRevicerScan(keyStr: String) {
        binding.bagNum.text=keyStr
        bagId=keyStr
        roomScanViewModel.showBagInfo(keyStr)

    }

    override fun initData() {
        val code=intent.getStringExtra("code")
        if (code!=null){
            DialogUtil.instance.showProgressDialog(this)
            roomScanViewModel.getRoomInfo(code)
         //test
            bagId = "BG10000111202903"
            roomScanViewModel.showBagInfo(bagId)
        }
    }
}