package com.example.groceryappprojectcharles.model.remote.response

import com.example.groceryappprojectcharles.model.remote.data.ProductsBySubID

data class ProductsBySubIDResponse(
    val count: Int,
    val `data`: List<ProductsBySubID>,
    val error: Boolean
)