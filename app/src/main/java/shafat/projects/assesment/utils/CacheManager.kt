package shafat.projects.assesment.utils

import android.content.Context
import shafat.projects.assesment.utils.sharedpreference.load
import shafat.projects.assesment.utils.sharedpreference.saveSp

fun Context.saveAuthToken(token:String) {
    this.saveSp(Keys.AuthKeys.AUTH_TOKEN, token)
}

fun Context.getAuthToken() : String{
   return this.load(Keys.AuthKeys.AUTH_TOKEN) ?:""
}
