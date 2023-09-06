package shafat.projects.assesment.utils

import android.content.Context
import android.view.View
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

