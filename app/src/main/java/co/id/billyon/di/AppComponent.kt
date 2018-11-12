package co.id.billyon.di

import co.id.billyon.BillyonApp
import co.id.billyon.di.module.ActivityBuilder
import co.id.billyon.di.module.AppModule
import co.id.billyon.di.module.DatabaseModule
import co.id.billyon.di.module.NetworkModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        AppModule::class,
        NetworkModule::class,
        DatabaseModule::class,
        AndroidInjectionModule::class,
        ActivityBuilder::class))

interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: BillyonApp): Builder

        fun build(): AppComponent
    }

    fun inject(app: BillyonApp)
}
