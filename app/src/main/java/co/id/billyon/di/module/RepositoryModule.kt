package co.id.billyon.di.module

import android.content.SharedPreferences
import co.id.billyon.db.BillyonDatabase
import co.id.billyon.di.NetManager
import co.id.billyon.repository.cashier.dashboard.DashboardLocalDataSource
import co.id.billyon.repository.cashier.dashboard.DashboardRemoteDataSource
import co.id.billyon.repository.cashier.dashboard.DashboardRepository
import co.id.billyon.repository.cashier.login.LoginRepository
import co.id.billyon.repository.cashier.product.ProductLocalDataSource
import co.id.billyon.repository.cashier.product.ProductRemoteDataSource
import co.id.billyon.repository.cashier.product.ProductRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun provideProductDao(db: BillyonDatabase) = db.productDao()

    @Provides
    @Singleton
    fun provideCategoryDao(db: BillyonDatabase) = db.categoryDao()

    @Provides
    @Singleton
    fun providesCategoryAndProductsDao (db:BillyonDatabase) = db.categoryAndProductsDao()

    @Provides
    @Singleton
    fun provideProductRepository(localDataSource : ProductLocalDataSource, remoteDataSource: ProductRemoteDataSource, netManager: NetManager) = ProductRepository(localDataSource, remoteDataSource, netManager)

    @Provides
    @Singleton
    fun provideDashboardRepository(localDataSource: DashboardLocalDataSource, remoteDataSource: DashboardRemoteDataSource, netManager: NetManager) = DashboardRepository(localDataSource, remoteDataSource, netManager)

    @Provides
    @Singleton
    fun provideLoginRepository(editor: SharedPreferences.Editor, preferences: SharedPreferences) = LoginRepository(editor, preferences)
}