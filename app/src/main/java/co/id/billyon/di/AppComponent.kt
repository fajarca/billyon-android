package co.id.billyon.di

import co.id.billyon.di.module.AppModule
import co.id.billyon.di.module.DatabaseModule
import co.id.billyon.di.module.NetworkModule
import co.id.billyon.ui.CashierDashboardFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class, DatabaseModule::class, NetworkModule::class))
interface AppComponent {
    fun inject(fragment: CashierDashboardFragment)
    //fun inject(viewModel: CashierDashboardViewModel)
    //fun inject(app : Application)
}