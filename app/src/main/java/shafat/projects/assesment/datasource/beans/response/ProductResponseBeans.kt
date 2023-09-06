package shafat.projects.assesment.datasource.beans.response


data class ProductResponseBean(
    val id: Int ?= 0,
    val title: String ?= "",
    val price: Float ?= 0F,
    val description: String ?= "",
    val category: String ?= "",
    val image: String ?= "",
    val rating: RatingResponseBean ?= null
)

data class RatingResponseBean(
    val rate: Float ?= 0F,
    val count: Int ?= 0
)