package co.id.billyon.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import co.id.billyon.db.dao.*
import co.id.billyon.db.dao.join.CartsAndCartProductsDao
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



}