package co.id.billyon.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import co.id.billyon.db.dao.*
import co.id.billyon.db.dao.join.CartsAndCartProductsDao
import co.id.billyon.db.dao.join.CategoryAndProductsDao
import co.id.billyon.db.entity.CartProducts
import co.id.billyon.db.entity.Carts
import co.id.billyon.db.entity.Category
import co.id.billyon.db.entity.Products
import co.id.billyon.util.Converters


@Database(entities = arrayOf(Products::class,Category::class,Carts::class, CartProducts::class), version = 1)
@TypeConverters(Converters::class)
abstract class BillyonDatabase : RoomDatabase() {

    abstract fun productDao() : ProductsDao
    abstract fun categoryDao() : CategoryDao
    abstract fun cartsDao() : CartsDao
    abstract fun cartProductDao() : CartProductsDao

    abstract fun cartsAndProductDao() : CartsAndCartProductsDao
    abstract fun categoryAndProductsDao() : CategoryAndProductsDao


}