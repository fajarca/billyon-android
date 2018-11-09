package co.id.billyon.di.module

import co.id.billyon.di.Info
import dagger.Module
import dagger.Provides

@Module
class AppModule {
    @Provides
    open fun providesInfo() : Info {
        return Info("Love Dagger2")
    }
}