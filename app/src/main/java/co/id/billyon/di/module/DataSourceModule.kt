package co.id.billyon.di.module

import co.id.billyon.api.ApiService
import co.id.billyon.db.dao.CartsDao
import co.id.billyon.db.dao.CategoryAndProductsDao
import co.id.billyon.db.dao.CategoryDao
import co.id.billyon.db.dao.ProductsDao
import co.id.billyon.repository.cashier.carts.CartsLocalDataSource
import co.id.billyon.repository.cashier.carts.CartsRemoteDataSource
import co.id.billyon.repository.cashier.dashboard.DashboardLocalDataSource
import co.id.billyon.repository.cashier.dashboard.DashboardRemoteDataSource
import co.id.billyon.repository.cashier.product.ProductLocalDataSource
import co.id.billyon.repository.cashier.product.ProductRemoteDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataSourceModule {

    @Provides
    @Singleton
    fun providesDashboardLocalDataSource(categoryDao: CategoryDao, categoryProductDao : CategoryAndProductsDao) = DashboardLocalDataSource(categoryDao,categoryProductDao)

    @Provides
    @Singleton
    fun providesDashboardRemoteDataSource(apiService: ApiService) = DashboardRemoteDataSource(apiService)

    @Provides
    @Singleton
    fun providesProductLocalDataSource(productsDao: ProductsDao) = ProductLocalDataSource(productsDao)

    @Provides
    @Singleton
    fun providesProductRemoteDataSource(apiService: ApiService) = ProductRemoteDataSource(apiService)

    @Provides
    @Singleton
    fun providesCartsLocalDataSource(cartDao: CartsDao) = CartsLocalDataSource(cartDao)

    @Provides
    @Singleton
    fun providesCartsRemoteDataSource(apiService: ApiService) = CartsRemoteDataSource(apiService)

}