package co.id.billyon.di.module

import co.id.billyon.di.Info
import co.id.billyon.util.HELLO
import co.id.billyon.util.LOVE
import co.id.billyon.util.annotation.Use
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    @Use(LOVE)
    fun providesInfoLove() = Info("Love Dagger2")


    @Provides
    @Use(HELLO)
    fun providesInfoHello() = Info("Hello Dagger 2")

}