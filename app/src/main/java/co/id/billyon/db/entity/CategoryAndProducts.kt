package co.id.billyon.db.entity

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Relation

@Entity
data class CategoryAndProducts(
        @Embedded
        var category: Category,
        @Relation(parentColumn = "id", entityColumn = "category_id", entity = Products::class)
        var products: List<Products>

) {
        constructor() : this(Category("","",0,false,false,"",""), emptyList())
}