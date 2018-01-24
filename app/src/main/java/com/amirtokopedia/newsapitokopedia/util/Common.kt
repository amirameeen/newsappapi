package com.amirtokopedia.newsapitokopedia.util

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Handler
import android.support.v7.app.AlertDialog
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.amirtokopedia.newsapitokopedia.App
import com.amirtokopedia.newsapitokopedia.R
import kotlinx.android.synthetic.main.custom_dialog_message.view.*
import java.io.*
import java.util.regex.Pattern

/**
 * Created by Amir Malik on 1/24/18.
 */
object Common {

    /** The loading progress dialog object */
    var progressDialog: ProgressDialog? = null
    var dialog: AlertDialog? = null
    /**
     * Shows a loading progress dialog.
     * @param context the context
     * @param stringRes the dialog message string resource id
     * @param onBackPressListener the back button press listener when loading is shown
     */
    fun showProgressDialog(context: Context, stringRes: Int = -1, onBackPressListener: ProgressDialog.OnBackPressListener? = null) {
        Common.dismissProgressDialog()
        Common.progressDialog = ProgressDialog(context)
        Common.progressDialog!!.setText(stringRes)
        Common.progressDialog!!.backPressListener = onBackPressListener
        if (context is Activity && !context.isFinishing) Common.progressDialog!!.show()
    }

    /** Hides the currently shown loading progress dialog */
    fun dismissProgressDialog() {
        if (Common.progressDialog != null && Common.progressDialog!!.isShowing) {
            Common.progressDialog!!.dismiss()
            Common.progressDialog = null
        }
    }


    /**
     * Sets the progress dialog progress in percent.
     * @param progress The loading progress in percent
     */
    fun setProgressDialogProgress(progress: Int) {
        if (Common.progressDialog != null && Common.progressDialog!!.isShowing) {
            Common.progressDialog!!.setProgress(progress)
            Common.progressDialog!!.setText(progress.toString() + "%")
        }
    }

    /**
     * Sets the progress dialog progress indeterminate state.
     * @param isIndeterminate Determines if progress dialog is indeterminate
     */
    fun setProgressDialogIndeterminate(isIndeterminate: Boolean) {
        if (Common.progressDialog != null && Common.progressDialog!!.isShowing) {
            Common.progressDialog!!.setIndeterminate(isIndeterminate)
        }
    }

    /**
     * Sets the progress dialog message.
     * @param message The dialog message string
     */
    fun setProgressDialogText(message: String) {
        if (Common.progressDialog != null && Common.progressDialog!!.isShowing) {
            Common.progressDialog!!.setText(message)
        }
    }

    /**
     * Display a simple [Toast].
     * @param stringRes The message string resource id
     */
    fun showToast(stringRes: Int) {
        Common.showToast(App.context!!.getString(stringRes))
    }

    /**
     * Display a simple [Toast].
     * @param message The message string
     */
    fun showToast(message: String) {
        Toast.makeText(App.context, message, Toast.LENGTH_SHORT).show()
    }

    fun showMessageDialog(context: Context,
                          message: String?) {
        Common.showMessageDialog(context, "", message, null)
    }

    fun showMessageDialog(context: Context,
                          message: String?,
                          listener: DialogInterface.OnDismissListener,
                          isAutoDismiss: Boolean) {
        Common.showMessageDialog(context, "", message, listener, isAutoDismiss)
    }

    fun showMessageDialog(context: Context,
                          message: String?,
                          dismissListener: DialogInterface.OnDismissListener) {
        Common.showMessageDialog(context, "", message, dismissListener)
    }

    fun showMessageDialog(
            context: Context,
            title: String?,
            message: String?,
            dismissListener: DialogInterface.OnDismissListener? = null){
        Common.showMessageDialog(context, title, message, dismissListener, false)
    }

    /**
     * Display a simple [AlertDialog] with a simple OK button.
     * If the dismiss listener is specified, the dialog becomes uncancellable
     * @param context The context
     * @param title The title string
     * @param message The message string
     * @param dismissListener The dismiss listener
     */
    fun showMessageDialog(
            context: Context,
            title: String?,
            message: String?,
            dismissListener: DialogInterface.OnDismissListener? = null, isAutoDismiss: Boolean) {
        val view = LayoutInflater.from(context).inflate(R.layout.custom_dialog_message, null)
        view.txt_title_dialog.text = title
        if (title!!.isEmpty()) {
            view.txt_title_dialog.visibility = View.GONE
            view.separator_dialog.visibility = View.GONE
        } else {
            view.txt_title_dialog.visibility = View.VISIBLE
            view.separator_dialog.visibility = View.VISIBLE
        }
        view.btn_positive_dialog.text = context.resources.getString(android.R.string.ok)
        view.txt_message_dialog.text = message
        val builder = AlertDialog.Builder(context, R.style.Theme_AppCompat_Light_Dialog_Alert)
        builder.setView(view)

        Common.dialog = builder.create()
        view.btn_positive_dialog.setOnClickListener({ dialog!!.dismiss() })
        dialog!!.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        if (dismissListener != null) {
            dialog!!.setOnDismissListener(dismissListener)
            dialog!!.setCancelable(false)
            dialog!!.setCanceledOnTouchOutside(false)
        }
        dialog!!.show()

        if(isAutoDismiss){
            val handler = Handler()
            handler.postDelayed({
                dialog!!.dismiss()
            }, 1000)
        }
    }

    fun dismissMessageDialog() {
        if (Common.dialog != null && Common.dialog!!.isShowing) {
            Common.dialog!!.dismiss()
            Common.dialog = null
        }
    }


}