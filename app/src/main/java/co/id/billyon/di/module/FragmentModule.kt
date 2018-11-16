package co.id.billyon.di.module

import co.id.billyon.ui.addproduct.AddProductFragment
import co.id.billyon.ui.cashierdashboard.CashierDashboardFragment
import co.id.billyon.ui.login.LoginFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract fun contributesLoginFragment() : LoginFragment
    @ContributesAndroidInjector
    abstract fun contributesCashierDashboardFragment() : CashierDashboardFragment
    @ContributesAndroidInjector
    abstract fun contributesAddProductFragment() : AddProductFragment
}