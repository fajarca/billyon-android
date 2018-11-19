package co.id.billyon.di.module

import co.id.billyon.ui.cashier.addproduct.AddProductFragment
import co.id.billyon.ui.cashier.dashboard.DashboardFragment
import co.id.billyon.ui.cashier.product.ProductListFragment
import co.id.billyon.ui.other.login.LoginFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract fun contributesLoginFragment() : LoginFragment
    @ContributesAndroidInjector
    abstract fun contributesCashierDashboardFragment() : DashboardFragment
    @ContributesAndroidInjector
    abstract fun contributesAddProductFragment() : AddProductFragment
    @ContributesAndroidInjector
    abstract fun contributesProductListFragment() : ProductListFragment
}