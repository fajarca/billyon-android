package co.id.billyon.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import co.id.billyon.db.dao.ProductDao
import co.id.billyon.db.entity.Product

@Database(entities = arrayOf(Product::class), version = 1)
abstract class BillyonDatabase : RoomDatabase() {

    abstract fun ProductDao() : ProductDao

    companion object {
        private var INSTANCE : BillyonDatabase? = null
        private val DATABASE_NAME = "billyon.db"

        fun getInstance(context : Context) : BillyonDatabase? {

            if (INSTANCE == null) {
                synchronized(BillyonDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext, BillyonDatabase::class.java, DATABASE_NAME).build()
                }
            }

            return INSTANCE
        }

        fun destoryInstance() {
            INSTANCE = null
        }
    }

}