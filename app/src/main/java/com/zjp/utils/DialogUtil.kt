package com.zjp.utils

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.text.TextUtils
import android.view.Gravity
import android.view.ViewGroup
import android.view.Window
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import com.blankj.utilcode.util.ToastUtils
import com.zjp.medicalwasterecycle.R

class DialogUtil {

    companion object {

        val instance: DialogUtil by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            DialogUtil()
        }
    }


    var progressDialog: Dialog? = null
    fun showProgressDialog(context: Context) {
        progressDialog = createDialog(context)

        if (!progressDialog!!.isShowing) {
            progressDialog!!.show()
        }
    }

    fun dismissProgressDialog() {
        if (progressDialog != null && progressDialog!!.isShowing) {
            progressDialog!!.dismiss()
        }
    }


    private fun createDialog(context: Context): Dialog {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_progress)
        dialog.setCanceledOnTouchOutside(false)
        return dialog
    }

    fun showUpDateDialog(context: Context, clickListener: DialogInterface.OnClickListener) {
        val builder = androidx.appcompat.app.AlertDialog.Builder(context)
        builder.setMessage("发现新版本！是否升级？")
            .setPositiveButton("确定", clickListener).setCancelable(false)
            .create().show()
    }


}