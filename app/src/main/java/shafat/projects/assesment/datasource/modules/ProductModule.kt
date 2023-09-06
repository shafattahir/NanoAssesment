package shafat.projects.assesment.datasource.modules

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import shafat.projects.assesment.datasource.endpoints.ProductsEndPoints
import shafat.projects.assesment.datasource.retrofit.RetrofitDi
import javax.inject.Singleton

@Module(includes = [RetrofitDi::class])
@InstallIn(SingletonComponent::class)
class ProductModule {
    @Singleton
    @Provides
    fun provideProductApi(retrofit: Retrofit): ProductsEndPoints {
        return retrofit.create(ProductsEndPoints::class.java)
    }
}