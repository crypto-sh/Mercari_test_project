package com.mrecun.model

import androidx.room.PrimaryKey


enum class Status {
    on_sale,
    sold_out
}

class SaleState(status: Status) {
    companion object {
        val OnSale  = SaleState(Status.on_sale)
        val SoldOut = SaleState(Status.sold_out)
        fun convert(status: String) : Status = when(status){
            "on_sale" -> Status.on_sale
            else -> Status.sold_out
        }
    }
}

data class Product(
    @PrimaryKey
    val id      : String,
    val name    : String,
    val status  : String,
    val num_likes : Long,
    val num_comments : Long,
    val price   : Long,
    val photo   : String
)



