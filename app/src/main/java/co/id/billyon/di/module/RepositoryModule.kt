package co.id.billyon.di.module

import android.content.SharedPreferences
import co.id.billyon.api.ApiService
import co.id.billyon.db.BillyonDatabase
import co.id.billyon.db.dao.CartProductsDao
import co.id.billyon.db.dao.CartsDao
import co.id.billyon.db.dao.CategoryDao
import co.id.billyon.db.dao.ProductsDao
import co.id.billyon.db.dao.join.CartsAndCartProductsDao
import co.id.billyon.di.NetManager
import co.id.billyon.repository.cashier.carts.CartsLocalDataSource
import co.id.billyon.repository.cashier.carts.CartsRemoteDataSource
import co.id.billyon.repository.cashier.carts.CartsRepository
import co.id.billyon.repository.cashier.dashboard.CategoryLocalDataSource
import co.id.billyon.repository.cashier.dashboard.CategoryRemoteDataSource
import co.id.billyon.repository.cashier.dashboard.CategoryRepository
import co.id.billyon.repository.cashier.login.LoginLocalDataSource
import co.id.billyon.repository.cashier.login.LoginRemoteDataSource
import co.id.billyon.repository.cashier.login.LoginRepository
import co.id.billyon.repository.cashier.product.ProductLocalDataSource
import co.id.billyon.repository.cashier.product.ProductRemoteDataSource
import co.id.billyon.repository.cashier.product.ProductRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    //Data access object
    @Provides
    @Singleton
    fun provideProductDao(db: BillyonDatabase) = db.productDao()

    @Provides
    @Singleton
    fun provideCategoryDao(db: BillyonDatabase) = db.categoryDao()

    @Provides
    @Singleton
    fun provideCartsDao(db: BillyonDatabase) = db.cartsDao()

    @Provides
    @Singleton
    fun provideCartProductsDao(db: BillyonDatabase) = db.cartProductDao()

    @Provides
    @Singleton
    fun providesCartsAndProductsDao (db:BillyonDatabase) = db.cartsAndProductDao()


    //Local and remote data source
    @Provides
    @Singleton
    fun providesLoginLocalDataSource(editor: SharedPreferences.Editor, preferences: SharedPreferences) = LoginLocalDataSource(editor, preferences)

    @Provides
    @Singleton
    fun providesLoginRemoteDataSource(apiService: ApiService) = LoginRemoteDataSource(apiService)

    @Provides
    @Singleton
    fun providesDashboardLocalDataSource(categoryDao: CategoryDao, cartProductsDao: CartProductsDao) = CategoryLocalDataSource(categoryDao, cartProductsDao)

    @Provides
    @Singleton
    fun providesDashboardRemoteDataSource(apiService: ApiService) = CategoryRemoteDataSource(apiService)

    @Provides
    @Singleton
    fun providesProductLocalDataSource(productsDao: ProductsDao) = ProductLocalDataSource(productsDao)

    @Provides
    @Singleton
    fun providesProductRemoteDataSource(apiService: ApiService) = ProductRemoteDataSource(apiService)

    @Provides
    @Singleton
    fun providesCartsLocalDataSource(cartDao: CartsDao, cartProductsDao: CartProductsDao, cartsAndCartProductsDao: CartsAndCartProductsDao) = CartsLocalDataSource(cartDao, cartProductsDao, cartsAndCartProductsDao)

    @Provides
    @Singleton
    fun providesCartsRemoteDataSource(apiService: ApiService) = CartsRemoteDataSource(apiService)


    //Repository
    @Provides
    @Singleton
    fun provideProductRepository(localDataSource : ProductLocalDataSource, remoteDataSource: ProductRemoteDataSource, netManager: NetManager) = ProductRepository(localDataSource, remoteDataSource, netManager)

    @Provides
    @Singleton
    fun provideCategoryRepository(localDataSource: CategoryLocalDataSource, remoteDataSource: CategoryRemoteDataSource, netManager: NetManager) = CategoryRepository(localDataSource, remoteDataSource, netManager)

    @Provides
    @Singleton
    fun provideLoginRepository(localDataSource : LoginLocalDataSource, remoteDataSource : LoginRemoteDataSource, netManager: NetManager) = LoginRepository(localDataSource, remoteDataSource, netManager)


    @Provides
    @Singleton
    fun provideCartsRepository(localDataSource : CartsLocalDataSource, remoteDataSource: CartsRemoteDataSource, netManager: NetManager) = CartsRepository(localDataSource, remoteDataSource, netManager)
}