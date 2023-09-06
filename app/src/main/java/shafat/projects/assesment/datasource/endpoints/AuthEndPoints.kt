package shafat.projects.assesment.datasource.endpoints

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import shafat.projects.assesment.datasource.beans.request.LoginRequestBean
import shafat.projects.assesment.datasource.beans.response.LoginResponseBean


interface AuthEndPoints {

    @POST("auth/login")
    suspend fun login(@Body loginRequestBean: LoginRequestBean): Response<LoginResponseBean>

}