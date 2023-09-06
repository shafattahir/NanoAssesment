package shafat.projects.assesment.utils

import android.content.Context
import shafat.projects.assesment.utils.SavedData.ProjectKeys.AUTH_HEADER
import shafat.projects.assesment.utils.sharedpreference.load
import shafat.projects.assesment.utils.sharedpreference.saveSp

fun Context.saveAuthToken(token:String) {
    if (token.isEmpty()) {
        this.saveSp(SavedData.AuthKeys.AUTH_TOKEN, "")
    } else {
        this.saveSp(SavedData.AuthKeys.AUTH_TOKEN, AUTH_HEADER + token)
    }
}

fun Context.getAuthToken() : String{
   return this.load(SavedData.AuthKeys.AUTH_TOKEN) ?:""
}
