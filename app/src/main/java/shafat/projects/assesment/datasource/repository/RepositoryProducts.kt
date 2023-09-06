package shafat.projects.assesment.datasource.repository

import kotlinx.coroutines.flow.flow
import shafat.projects.assesment.datasource.constants.DataState
import shafat.projects.assesment.datasource.endpoints.ProductsEndPoints
import javax.inject.Inject

class RepositoryProducts @Inject constructor(private val productsEndPoints: ProductsEndPoints) {

    fun getAllProducts() = flow {
        emit(DataState.Loading())
        try {
            val networkUser = productsEndPoints.getAllProducts()
            if (networkUser.isSuccessful) {
                emit(DataState.Success(networkUser.body()))
            } else {
                emit(DataState.Error(networkUser.message()))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getProductDetails(productID: Int) = flow {
        emit(DataState.Loading())
        try {
            val networkUser = productsEndPoints.getProductInfo()
            if (networkUser.isSuccessful) {
                emit(DataState.Success(networkUser.body()))
            } else {
                emit(DataState.Error(networkUser.message()))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}