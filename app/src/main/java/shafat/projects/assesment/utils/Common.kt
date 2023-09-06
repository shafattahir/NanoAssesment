package shafat.projects.assesment.utils

import android.content.*
import android.view.View
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import shafat.projects.assesment.R


fun showSnackBar(view : View, message : String){
    val snack = Snackbar.make(view,message,Snackbar.LENGTH_LONG)
    snack.show()
}

fun <T> Flow<T>.handleErrors(context:Context): Flow<T> =
    catch { Toast.makeText(context,(context.getString(R.string.something_went_wrong))
        ,Toast.LENGTH_SHORT).show() }

fun MaterialButton.enableButton(){
    this.alpha = 1F
    this.isEnabled = true
}

fun MaterialButton.disableButton(){
    this.alpha = 0.5F
    this.isEnabled = false
}

fun isValidInput(s: String): Boolean {
    return s.isEmpty().not()
}

