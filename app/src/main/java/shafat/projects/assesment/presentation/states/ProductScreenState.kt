package shafat.projects.assesment.presentation.states

import shafat.projects.assesment.datasource.beans.response.ProductResponseBean


sealed class ProductScreenState(
    val productID: Int? = 0,
    val productsList: List<ProductResponseBean>? = emptyList(),
    val productDetailObj: ProductResponseBean? = null,
    val instruction: String? = ""
) {
    object Idle : ProductScreenState()

    // -------------------- GET ALL PRODUCTS
    object GetAllProductsRequestSend : ProductScreenState()

    object GetAllProductsInProgress : ProductScreenState()

    class GetAllProductsInSuccessful(productsList: List<ProductResponseBean>) :
        ProductScreenState(productsList = productsList)

    class GetAllProductsInsFailed(instruction: String) :
        ProductScreenState(instruction = instruction)

    // -------------------- GET PRODUCT DETAILS
    class GetProductDetailsInRequestSend(productID: Int) :
        ProductScreenState(productID = productID)

    object GetProductDetailsInProgress : ProductScreenState()

    class GetProductDetailsInSuccessful(productDetailObj: ProductResponseBean) :
        ProductScreenState(productDetailObj = productDetailObj)

    class GetProductDetailsInFailed(instruction: String) :
        ProductScreenState(instruction = instruction)
}