package co.id.billyon.di.component

import co.id.billyon.BillyonApp
import co.id.billyon.di.module.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class,
    NetworkModule::class,
    DatabaseModule::class,
    AndroidInjectionModule::class,
    ActivityBuilder::class,
    FragmentModule::class,
    ViewModelModule::class])

interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: BillyonApp): Builder

        fun build(): AppComponent
    }

    fun inject(app: BillyonApp)
}
