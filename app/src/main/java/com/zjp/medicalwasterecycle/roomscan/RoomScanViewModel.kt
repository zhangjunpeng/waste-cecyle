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

class RoomScanViewModel(val dataSource: DataSource)  : BaseViewModel() {


    private var isWhiteBag: Boolean = false
    val roomShowDataResult = MutableLiveData<RoomShowData?>()
    val errorResult = MutableLiveData<ResultModel<Nothing>>()
    val roomBagListResult = MutableLiveData<RoomBagListData?>()
    val bagInfoResult = MutableLiveData<BagShowData?>()

    val addBagResult = MutableLiveData<AddBagData?>()



    fun getRoomInfo(code: String) {
        viewModelScope.launch {
            return@launch withContext(Dispatchers.IO) {
                val result = dataSource.scanRoomInfo(
                    SPUtils.getInstance().getString(NameSpace.TokenName),
                    code
                )
                if (result is RequestResult.Success) {
                    roomShowDataResult.postValue(result.data)
                } else if (result is RequestResult.Error) {
                    errorResult.postValue(result.error)
                }
            }

        }
    }

    fun getRoomBagList(code:String){
        viewModelScope.launch {
            return@launch withContext(Dispatchers.IO) {
                val result = dataSource.roomBagList(
                    SPUtils.getInstance().getString(NameSpace.TokenName),
                    code
                )
                if (result is RequestResult.Success) {
                    roomBagListResult.postValue(result.data)
                } else if (result is RequestResult.Error) {
                    errorResult.postValue(result.error)
                }
            }
        }
    }

    fun showBagInfo(code: String) {
        viewModelScope.launch {
            return@launch withContext(Dispatchers.IO) {
                val result = dataSource.bagShowInfo(
                    SPUtils.getInstance().getString(NameSpace.TokenName),
                    code
                )
                if (result is RequestResult.Success) {

                    bagInfoResult.postValue(result.data)

                } else if (result is RequestResult.Error) {
                    errorResult.postValue(result.error)
                }
            }
        }
    }


    val bagQuaData = MutableLiveData<HashMap<String, String>>().apply {
        value = HashMap<String, String>().apply {
            this["unbroken"] = "1"
            this["sterile"] = "0"
            this["tight"] = "0"
            this["classified"] = "0"
            this["commodious"] = "0"
            this["few_medical"] = "1"
            this["dialysis"]="1"
            this["placenta"]="1"

        }
    }


    fun changeBagStatus(bagId: String) {
        viewModelScope.launch {
            return@launch withContext(Dispatchers.IO) {
                val result = dataSource.editBagQuality(
                    SPUtils.getInstance().getString(NameSpace.TokenName),
                    bagId,
                    bagQuaData.value!!
                )
                if (result is RequestResult.Success) {

                    bagInfoResult.postValue(result.data)

                } else if (result is RequestResult.Error) {
                    errorResult.postValue(result.error)
                }
            }
        }
    }

    fun addBag( code: String,params:HashMap<String,String>){
        viewModelScope.launch {
            return@launch withContext(Dispatchers.IO) {
                val result = dataSource.addBag(
                    SPUtils.getInstance().getString(NameSpace.TokenName),
                    code,
                    params
                )
                if (result is RequestResult.Success){
                    addBagResult.postValue(result.data)
                }else if (result is RequestResult.Error){
                    errorResult.postValue(result.error)

                }
            }
        }
    }

}