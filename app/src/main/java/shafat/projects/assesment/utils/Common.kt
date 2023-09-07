package shafat.projects.assesment.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import shafat.projects.assesment.R
import shafat.projects.assesment.utils.SavedData.ProjectKeys.AED


fun showSnackBar(view: View, message: String) {
    val snack = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
    snack.show()
}

fun <T> Flow<T>.handleErrors(context: Context): Flow<T> =
    catch {
        Toast.makeText(
            context, (context.getString(R.string.something_went_wrong)), Toast.LENGTH_SHORT
        ).show()
    }

fun MaterialButton.enableButton() {
    this.alpha = 1F
    this.isEnabled = true
}

fun Activity.setFullScreenUI() {
    setWindowFlag(
        WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                or WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, false
    )
    window.statusBarColor = Color.TRANSPARENT
    window.navigationBarColor = Color.TRANSPARENT
}

private fun Activity.setWindowFlag(bits: Int, on: Boolean) {
    val win = window
    val winParams = win.attributes
    if (on) {
        winParams.flags = winParams.flags or bits
    } else {
        winParams.flags = winParams.flags and bits.inv()
    }
    win.attributes = winParams
}

fun MaterialButton.disableButton() {
    this.alpha = 0.5F
    this.isEnabled = false
}

fun isValidInput(s: String): Boolean {
    return s.isEmpty().not()
}

fun TextView.setPrice(value: String) {
    val finalValue = valueCheck(value) + AED
    this.text = finalValue
}

fun valueCheck(str: String): String {
    return str.ifEmpty { return "0.0" }
}

fun shareWithFriends(productName: String?,context: Context){
    try {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.app_name))
        val shareMessage = "\nHi! I would like to invite you $productName come join Nano Health app with me\n\n"

        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
        context.startActivity(Intent.createChooser(shareIntent, "Choose one"))
    } catch (_: Exception) { }
}

