package co.id.billyon.db.entity.join

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation
import co.id.billyon.db.entity.CartProducts
import co.id.billyon.db.entity.Carts

@Entity
data class CartsAndCartProducts(
        @Embedded
        var carts: Carts,
        @Relation(parentColumn = "id", entityColumn = "carts_id", entity = CartProducts::class)
        var products: List<CartProducts>

) {
    constructor() : this(
            Carts(0, false, false, "", ""),
            emptyList()
    )
}