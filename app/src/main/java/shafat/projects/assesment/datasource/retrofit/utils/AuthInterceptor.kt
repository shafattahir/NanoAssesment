package shafat.projects.assesment.datasource.retrofit.utils


import android.content.Context
import androidx.annotation.NonNull
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    @ApplicationContext val context: Context,
    private val auth: ApplicationAuth,
) : Interceptor {
    @Throws(Exception::class)
    override fun intercept(@NonNull chain: Interceptor.Chain): Response {

        var request = chain.request()
        val builder = request.newBuilder()
        val sid: String = auth.token
        if (sid.isNotEmpty()) {
            builder.addHeader("Authorization", sid)
        }

        request = builder.build()
        val response = chain.proceed(request)
        val bodyString = response.body!!.string()
        return makeResponse(response, bodyString)
    }

    private fun makeResponse(response: Response, responseString: String): Response {
        return response.newBuilder()
            .code(200)
            .body(responseString.toResponseBody(response.body!!.contentType()))
            .build()
    }
}