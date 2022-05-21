package com.zjp.medicalwasterecycle.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.SPUtils
import com.nextmar.requestdata.NameSpace
import com.nextmar.requestdata.RequestResult
import com.nextmar.requestdata.datasource.DataSource
import com.nextmar.requestdata.model.CarTotalData
import com.nextmar.requestdata.model.MemberShowData
import com.nextmar.requestdata.model.ResultModel
import com.zjp.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(val dataSource: DataSource) : BaseViewModel() {

    val memberShowInfonResult = MutableLiveData<MemberShowData>()
    val errorResult = MutableLiveData<ResultModel<Nothing>>()
    val carTotalResult = MutableLiveData<CarTotalData>()

    fun memberShowInfo() {
        viewModelScope.launch {
            return@launch withContext(Dispatchers.IO) {
                val result = dataSource.memberShowInfo(
                    SPUtils.getInstance().getString(NameSpace.TokenName),
                    "1"
                )
                if (result is RequestResult.Success) {
                    memberShowInfonResult.postValue(result.data!!)
                } else if (result is RequestResult.Error) {
                    errorResult.postValue(result.error)
                }
            }

        }
    }

    fun getCartTotal() {
        viewModelScope.launch {
            return@launch withContext(Dispatchers.IO) {
                val result = dataSource.carTotal(
                    SPUtils.getInstance().getString(NameSpace.TokenName),
                    SPUtils.getInstance().getString(NameSpace.ProjectID),
                    null
                )
                if (result is RequestResult.Success) {
                    carTotalResult.postValue(result.data!!)
                } else if (result is RequestResult.Error) {
                    errorResult.postValue(result.error)
                }
            }

        }
    }

}