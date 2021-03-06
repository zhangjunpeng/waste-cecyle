package com.zjp.medicalwasterecycle.roomscan

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.SPUtils
import com.nextmar.requestdata.NameSpace
import com.nextmar.requestdata.RequestResult
import com.nextmar.requestdata.datasource.DataSource
import com.nextmar.requestdata.model.*
import com.zjp.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class RoomScanViewModel(val dataSource: DataSource) : BaseViewModel() {


    private var isWhiteBag: Boolean = false
    val roomShowDataResult = MutableLiveData<RoomShowData?>()
    val errorResult = MutableLiveData<ResultModel<Nothing>>()
    val roomBagListResult = MutableLiveData<RoomBagListData?>()
    val bagInfoResult = MutableLiveData<BagShowData?>()

    val addBagResult = MutableLiveData<AddBagData?>()

    val widgetData = MutableLiveData<Double>()

    var bagId = ""
    var bagCode = ""
    var roomId=""

    var oldWidget:Double=0.0


    val whiteBagParams = HashMap<String, String>().apply {
        this["project_id"] = SPUtils.getInstance().getString(NameSpace.ProjectID)
        this["member_id"] = SPUtils.getInstance().getString(NameSpace.MemberID)
        this["category"] = "2"
        this["daysp_type"] = "bottle"
        this["weight"] = "20.1"
        this["is_print"] = "1"
    }


    val timerTask= object :TimerTask() {
        override fun run() {
            if (bagId.isNullOrEmpty()){
                return
            }
            if (isWhiteBag){
                addBag(bagCode)
            }else{
                addWeiget()
            }
        }

    }

    var timer:Timer?=null




    fun getRoomInfo(code: String) {
        viewModelScope.launch {
            return@launch withContext(Dispatchers.IO) {
                val result = dataSource.scanRoomInfo(
                    SPUtils.getInstance().getString(NameSpace.TokenName), code
                )
                if (result is RequestResult.Success) {
                    roomShowDataResult.postValue(result.data)
                } else if (result is RequestResult.Error) {
                    errorResult.postValue(result.error)
                }
            }

        }
    }

    fun getRoomBagList(code: String) {
        viewModelScope.launch {
            return@launch withContext(Dispatchers.IO) {
                getRoomList(code)
            }
        }
    }

    private fun getRoomList(code: String) {
        val result = dataSource.roomBagList(
            SPUtils.getInstance().getString(NameSpace.TokenName), code
        )
        if (result is RequestResult.Success) {
            roomBagListResult.postValue(result.data)
        } else if (result is RequestResult.Error) {
            errorResult.postValue(result.error)
        }
    }

    fun showBagInfo(code: String) {
        viewModelScope.launch {
            return@launch withContext(Dispatchers.IO) {
                val result = dataSource.bagShowInfo(
                    SPUtils.getInstance().getString(NameSpace.TokenName), code
                )
                if (result is RequestResult.Success) {
                    isWhiteBag = result.data == null
                    bagInfoResult.postValue(result.data)

                } else if (result is RequestResult.Error) {
                    errorResult.postValue(result.error)
                }
            }
        }
    }

    val quaKeyList = ArrayList<String>().apply {
        this.add("unbroken")
        this.add("sterile")
        this.add("tight")
        this.add("classified")
        this.add("commodious")
        this.add("few_medical")
    }


    val bagQuaData = MutableLiveData<HashMap<String, String>>().apply {
        value = HashMap<String, String>().apply {
            this["unbroken"] = "1"
            this["sterile"] = "0"
            this["tight"] = "0"
            this["classified"] = "0"
            this["commodious"] = "0"
            this["few_medical"] = "1"
            this["dialysis"] = "1"
            this["placenta"] = "1"

        }
    }

    val categoryData = MutableLiveData<HashMap<String, String>>().apply {
        value = HashMap<String, String>().apply {
            this["category"] = "2"
            this["dialysis"] = "1"
            this["placenta"] = "1"
        }
    }

    fun getCategoryString(category: String): String {
        return when (category) {
            "0" -> "?????????"
            "1" -> "?????????"
            "2" -> "?????????"
            "3" -> "?????????"
            "4" -> "?????????"
            "5" -> "?????????"
            "6" -> "???????????????"
            else -> ""
        }
    }


    fun changeBagStatus(bagId: String) {
        viewModelScope.launch {
            return@launch withContext(Dispatchers.IO) {
                val result = dataSource.editBagQuality(
                    SPUtils.getInstance().getString(NameSpace.TokenName), bagId, bagQuaData.value!!
                )
                if (result is RequestResult.Success) {

                } else if (result is RequestResult.Error) {
                    errorResult.postValue(result.error)
                }
            }
        }
    }

    fun changeBagCategory(
        bagId: String,
        params: HashMap<String, String> /* = java.util.HashMap<kotlin.String, kotlin.String> */
    ) {
        viewModelScope.launch {
            return@launch withContext(Dispatchers.IO) {
                val result = dataSource.editBagCategory(
                    SPUtils.getInstance().getString(NameSpace.TokenName), bagId, params
                )
                if (result is RequestResult.Success) {
                    categoryData.postValue(params)

                } else if (result is RequestResult.Error) {
                    errorResult.postValue(result.error)
                }
            }
        }
    }

    fun addBag(code: String) {
        viewModelScope.launch {
            return@launch withContext(Dispatchers.IO) {
                val result = dataSource.addBag(
                    SPUtils.getInstance().getString(NameSpace.TokenName), code, whiteBagParams
                )
                if (result is RequestResult.Success) {
                    addBagResult.postValue(result.data)
                } else if (result is RequestResult.Error) {
                    errorResult.postValue(result.error)

                }
            }
        }
    }

    fun addWeiget(){
        viewModelScope.launch {
            return@launch withContext(Dispatchers.IO) {
                val result = dataSource.editBagWeight(
                    SPUtils.getInstance().getString(NameSpace.TokenName), bagId, ""
                )
                if (result is RequestResult.Success) {
                } else if (result is RequestResult.Error) {

                }
            }
        }
    }

    fun roomPrint(){
        viewModelScope.launch {
            return@launch withContext(Dispatchers.IO) {
                val result = dataSource.printRoomBag(
                    SPUtils.getInstance().getString(NameSpace.TokenName), roomId
                )
                if (result is RequestResult.Success) {
                    getRoomList(roomId)
                } else if (result is RequestResult.Error) {

                }
            }
        }
    }


    fun signBag(code: String) {
        viewModelScope.launch {
            return@launch withContext(Dispatchers.IO) {
                dataSource.signRoomBag(
                    SPUtils.getInstance().getString(NameSpace.TokenName),
                    roomId, code
                )
            }
        }
    }

    fun pirntCode() {
        // TODO: ????????????
    }

    fun getWeiget(): Double {
        // TODO: ??????????????????
        return 0.0
    }

}