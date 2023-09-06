package shafat.projects.assesment.datasource.modules

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import shafat.projects.assesment.datasource.endpoints.AuthEndPoints
import shafat.projects.assesment.datasource.retrofit.RetrofitDi
import javax.inject.Singleton

@Module(includes = [RetrofitDi::class])
@InstallIn(SingletonComponent::class)
class AuthModule {
    @Singleton
    @Provides
    fun provideAuthApi(retrofit: Retrofit): AuthEndPoints {
        return retrofit.create(AuthEndPoints::class.java)
    }
}