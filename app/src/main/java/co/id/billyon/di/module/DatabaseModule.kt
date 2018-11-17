package co.id.billyon.di.module

import co.id.billyon.api.ApiService
import co.id.billyon.db.BillyonDatabase
import co.id.billyon.db.dao.CategoryDao
import co.id.billyon.db.dao.ProductsDao
import co.id.billyon.di.NetManager
import co.id.billyon.repository.ProductRepository
import dagger.Module
import dagger.Provides
import java.util.concurrent.ExecutorService
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Provides
    @Singleton
    fun provideProductDao(db : BillyonDatabase) = db.productDao()

    @Provides
    @Singleton
    fun provideCategoryDao(db : BillyonDatabase) = db.categoryDao()

    @Provides
    @Singleton
    fun provideProductRepository(apiService: ApiService, productsDao: ProductsDao, categoryDao : CategoryDao, netManager: NetManager) = ProductRepository(apiService,productsDao,categoryDao, netManager)
}