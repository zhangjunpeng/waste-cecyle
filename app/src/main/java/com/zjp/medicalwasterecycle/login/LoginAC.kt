package com.zjp.medicalwasterecycle.login

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import com.nextmar.requestdata.NameSpace
import com.zjp.base.BaseActivity
import com.zjp.medicalwasterecycle.databinding.ActivityLoginBinding
import com.zjp.medicalwasterecycle.main.MainActivity
import com.zjp.utils.DialogUtil
import com.zjp.viewmodel.MyViewModelFactory

class LoginAC : BaseActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel

    override fun setView() {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.login.setOnClickListener {
            val account=binding.editAcc.editableText.toString()
            val passwd=binding.editPw.editableText.toString()
            DialogUtil.instance.showProgressDialog(this)
            loginViewModel.login(account,passwd)
        }
    }

    override fun initViewModel() {
        loginViewModel =
            ViewModelProvider(this, MyViewModelFactory.instance)[LoginViewModel::class.java]
        loginViewModel.loginResult.observe(this){
            DialogUtil.instance.dismissProgressDialog()
            LogUtils.i(it!!.token)
            SPUtils.getInstance().put(NameSpace.TokenName,it.token)
            SPUtils.getInstance().put(NameSpace.ProjectID,it.projectID)
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
        loginViewModel.errorResult.observe(this){
            DialogUtil.instance.dismissProgressDialog()
            LogUtils.i(it.msg.toString())
            ToastUtils.showLong(it.msg.toString())
        }
    }


}