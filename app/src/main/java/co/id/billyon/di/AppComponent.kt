package co.id.billyon.di

import co.id.billyon.di.module.AppModule
import co.id.billyon.ui.CashierDashboardFragment
import dagger.Component

@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(fragment : CashierDashboardFragment)
}