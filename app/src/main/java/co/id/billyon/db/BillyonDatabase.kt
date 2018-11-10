package co.id.billyon.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import co.id.billyon.db.dao.ProductsDao
import co.id.billyon.db.entity.Products
import co.id.billyon.util.DATABASE_NAME


@Database(entities = arrayOf(Products::class), version = 1)
abstract class BillyonDatabase : RoomDatabase() {

    abstract fun productDao() : ProductsDao

    companion object {
        @Volatile
        private var INSTANCE : BillyonDatabase? = null

        fun getDatabase(context : Context) : BillyonDatabase {
            val tempInstance = INSTANCE

            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext, BillyonDatabase::class.java, DATABASE_NAME).build()
                INSTANCE = instance
                return instance
            }
        }

        fun destoryInstance() {
            INSTANCE = null
        }
    }

}