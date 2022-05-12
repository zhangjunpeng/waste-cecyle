package com.zjp.medicalwasterecycle.roomscan

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.SPUtils
import com.nextmar.requestdata.NameSpace
import com.nextmar.requestdata.RequestResult
import com.nextmar.requestdata.datasource.DataSource
import com.nextmar.requestdata.model.MemberShowData
import com.nextmar.requestdata.model.ResultModel
import com.nextmar.requestdata.model.RoomBagListData
import com.nextmar.requestdata.model.RoomShowData
import com.zjp.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RoomScanViewModel(val dataSource: DataSource)  : BaseViewModel() {


    val roomShowDataResult = MutableLiveData<RoomShowData>()
    val errorResult = MutableLiveData<ResultModel<Nothing>>()
    val roomBagListResult = MutableLiveData<RoomBagListData>()


    fun getRoomInfo(code:String){
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
}