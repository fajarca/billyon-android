package co.id.billyon.db.entity.join

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Relation
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