package shafat.projects.assesment.datasource.endpoints

import retrofit2.Response
import retrofit2.http.GET
import shafat.projects.assesment.datasource.beans.response.ProductResponseBean


interface ProductsEndPoints {

    @GET("products")
    suspend fun getAllProducts(): Response<List<ProductResponseBean>>

    @GET("products/1")
    suspend fun getProductInfo(): Response<ProductResponseBean>

}