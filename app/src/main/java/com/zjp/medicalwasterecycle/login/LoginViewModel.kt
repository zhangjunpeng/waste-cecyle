package com.zjp.medicalwasterecycle.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nextmar.requestdata.RequestResult
import com.nextmar.requestdata.datasource.DataSource
import com.nextmar.requestdata.model.NumberLoginData
import com.nextmar.requestdata.model.ResultModel
import com.zjp.base.BaseViewModel
import com.zjp.utils.DialogUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(val dataSource: DataSource) : BaseViewModel() {

    val appVersionModelLiveData = MutableLiveData<Nothing>()

    val loginResult = MutableLiveData<NumberLoginData>()
    val errorResult = MutableLiveData<ResultModel<Nothing>>()


    fun login(account:String,password:String){
        viewModelScope.launch {
            return@launch withContext(Dispatchers.IO){
                val result=dataSource.numberLogin(account,password)
                if (result is RequestResult.Success){
                    loginResult.postValue(result.data)
                }else if (result is RequestResult.Error){
                    errorResult.postValue(result.error)
                }
            }

        }
    }

}