package co.id.billyon.di.module

import co.id.billyon.db.BillyonDatabase
import co.id.billyon.db.dao.ProductsDao
import co.id.billyon.repository.ProductRepository
import dagger.Module

@Module
class DatabaseModule {

    fun provideProductDao(db : BillyonDatabase) = db.productDao()
    fun provideProductRepository(productDao : ProductsDao) = ProductRepository(productDao)
}