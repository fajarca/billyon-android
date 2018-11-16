package co.id.billyon.di.module

import android.app.Application
import co.id.billyon.api.ApiService
import co.id.billyon.util.BASE_URL
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {
    @Provides
    @Singleton
    fun provideHttpCache(application: Application) : Cache {
        val cacheSize : Long = 10 * 10 * 1024
        return Cache(application.cacheDir,cacheSize)
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor() : HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor,cache: Cache) : OkHttpClient {
        val client = OkHttpClient.Builder()
        client.cache(cache)
        client.addInterceptor(loggingInterceptor)
        return client.build()
    }

    @Provides
    @Singleton
    fun provideBaseUrl() = BASE_URL

    @Provides
    @Singleton
    fun provideRetrofit(baseUrl : String, okHttpClient: OkHttpClient) : Retrofit {
        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build()
    }
}