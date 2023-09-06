package shafat.projects.assesment.utils

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.ViewGroup
import android.view.Window
import shafat.projects.assesment.R

class LoadingScreen(private val context: Context) {
    private var dialog: Dialog? = null

    fun displayLoading(cancelable: Boolean) {
        dialog = Dialog(context)
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.setContentView(R.layout.layout_loading_screen)
        dialog!!.window!!.setBackgroundDrawable(
            ColorDrawable(
                context.resources.getColor(R.color.transparentBlack)
            )
        )
        dialog!!.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        dialog!!.setCancelable(cancelable)

        try {
            if (dialog != null) {
                dialog!!.show()
            }
        } catch (_: Exception) {
        }
    }

    fun hideLoading() {
        try {
            if (dialog != null) {
                dialog!!.dismiss()
            }
        } catch (_: Exception) {
        }
    }
}