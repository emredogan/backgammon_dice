package com.emredogan.tavlazari

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.intro_dialog.view.*

object Util {
     fun showIntroductionDialogue(context: Context) {
        // Inflate the dialog with custom view
        val mDialogView = LayoutInflater.from(context).inflate(R.layout.intro_dialog, null)

        val shake = AnimationUtils.loadAnimation(context, R.anim.shake_animation)
        mDialogView.phoneShakeImage.animation = shake
        // AlertDialogBuilder
        val mBuilder = AlertDialog.Builder(context)
            .setView(mDialogView)
            .setTitle(context.resources.getString(R.string.welcome_string))
        // show dialog
        val mAlertDialog = mBuilder.show()
        // login button click of custom layout
        mDialogView.dismiss_button.setOnClickListener {
            // dismiss dialog
            mAlertDialog.dismiss()
            if (mDialogView.dontShowCheckBox.isChecked) {
                prefs.dontShowIntro = true
            }
        }

        if (mAlertDialog.isShowing) {
            isDialogVisible = true
        }

        mAlertDialog.setOnDismissListener {
            isDialogVisible = false
        }
    }
}