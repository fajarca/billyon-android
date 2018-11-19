package co.id.billyon.di.module

import co.id.billyon.api.ApiService
import co.id.billyon.db.BillyonDatabase
import co.id.billyon.db.dao.CategoryDao
import co.id.billyon.db.dao.ProductsDao
import co.id.billyon.di.NetManager
import co.id.billyon.repository.cashier.dashboard.DashboardRepository
import co.id.billyon.repository.cashier.product.ProductRepository
import dagger.Module
import dagger.Provides
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
    fun provideProductRepository(apiService: ApiService, productsDao: ProductsDao, netManager: NetManager) = ProductRepository(apiService, productsDao, netManager)

    @Provides
    @Singleton
    fun provideDashboardRepository(apiService: ApiService, categoryDao : CategoryDao, netManager: NetManager) = DashboardRepository(apiService, categoryDao, netManager)
}