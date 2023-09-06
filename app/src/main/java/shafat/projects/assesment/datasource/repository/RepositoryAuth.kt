package shafat.projects.assesment.datasource.repository

import kotlinx.coroutines.flow.flow
import shafat.projects.assesment.datasource.beans.request.LoginRequestBean
import shafat.projects.assesment.datasource.constants.DataState
import shafat.projects.assesment.datasource.endpoints.AuthEndPoints
import javax.inject.Inject

class RepositoryAuth @Inject constructor(private val authEndPoints: AuthEndPoints) {

    fun loginUser(userName: String,password: String) = flow {
        emit(DataState.Loading())
        try {
            val networkUser = authEndPoints
                .login(LoginRequestBean(userName, password))
            if (networkUser.isSuccessful) {
                emit(DataState.Success(networkUser.body()?.token))
            } else {
                emit(DataState.Error(networkUser.message()))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}