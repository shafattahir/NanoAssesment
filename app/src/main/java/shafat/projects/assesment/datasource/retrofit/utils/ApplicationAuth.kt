package shafat.projects.assesment.datasource.retrofit.utils

import android.content.Context

import dagger.hilt.android.qualifiers.ApplicationContext

import shafat.projects.assesment.utils.SavedData
import shafat.projects.assesment.utils.sharedpreference.loadSp
import shafat.projects.assesment.utils.sharedpreference.saveSp
import javax.inject.Inject

class ApplicationAuth @Inject constructor(@ApplicationContext val context: Context) {
    var token: String
        get() = context.loadSp(SavedData.AuthKeys.AUTH_TOKEN) ?: ""
        set(value) = context.saveSp(SavedData.AuthKeys.AUTH_TOKEN, value)
}







