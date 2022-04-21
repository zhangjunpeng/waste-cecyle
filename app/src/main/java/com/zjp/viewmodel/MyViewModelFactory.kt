package com.zjp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nextmar.requestdata.datasource.DataSource
import com.zjp.medicalwasterecycle.login.LoginViewModel

class MyViewModelFactory : ViewModelProvider.Factory {

    companion object {
        val instance: MyViewModelFactory by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            MyViewModelFactory()
        }

    }
    private val dataSource = DataSource.instance
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                return LoginViewModel(
                    dataSource
                ) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}