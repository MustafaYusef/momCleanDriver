package com.croczi.mamaclean.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface



fun showMessageOK(activity: Activity,title: String, message: String, okListener: DialogInterface.OnClickListener) {
    AlertDialog.Builder(activity)
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton("تأكيد", okListener)
        .create()
        .show()
}

fun showMessageOKCancel(activity: Activity,
    title: String,
    message: String,
    okListener: DialogInterface.OnClickListener,
    cancelListener: DialogInterface.OnClickListener
) {
    AlertDialog.Builder(activity)
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton("تأكيد", okListener)
        .setNeutralButton("خروج", cancelListener)
        .create()
        .show()
}