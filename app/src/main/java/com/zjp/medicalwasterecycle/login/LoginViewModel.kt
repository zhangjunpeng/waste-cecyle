package com.zjp.medicalwasterecycle.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nextmar.requestdata.RequestResult
import com.nextmar.requestdata.datasource.DataSource
import com.zjp.base.BaseViewModel
import kotlinx.coroutines.launch

class LoginViewModel(val dataSource: DataSource) : BaseViewModel() {

    val appVersionModelLiveData = MutableLiveData<Nothing>()


    fun login(account:String,password:String){
        viewModelScope.launch {
            val result=dataSource.numberLogin(account,password)
            if (result is RequestResult.Success){

            }
        }
    }

}