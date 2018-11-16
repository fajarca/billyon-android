package co.id.billyon.di.module

import android.app.Application
import android.arch.persistence.room.Room
import android.content.Context
import co.id.billyon.BillyonApp
import co.id.billyon.api.ApiService
import co.id.billyon.db.BillyonDatabase
import co.id.billyon.di.Info
import co.id.billyon.util.DATABASE_NAME
import co.id.billyon.util.HELLO
import co.id.billyon.util.LOVE
import co.id.billyon.util.annotation.Use
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import java.util.concurrent.Executors
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Use(LOVE)
    fun providesInfoLove() = Info("Love Dagger2")


    @Provides
    @Use(HELLO)
    fun providesInfoHello() = Info("Hello Dagger 2")


    @Provides
    @Singleton
    fun provideContext(app: BillyonApp) : Context = app

    @Provides
    @Singleton
    fun provideApplications(app : BillyonApp) : Application = app

    @Provides
    @Singleton
    fun provideDatabase(context: Context) = Room.databaseBuilder(context, BillyonDatabase::class.java, DATABASE_NAME).build()
    
    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) = retrofit.create(ApiService::class.java)
}