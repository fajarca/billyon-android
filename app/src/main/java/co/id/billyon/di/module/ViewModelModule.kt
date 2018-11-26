package co.id.billyon.di.module

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import co.id.billyon.di.ViewModelFactory
import co.id.billyon.di.ViewModelKey
import co.id.billyon.ui.cashier.addcategory.AddCategoryViewModel
import co.id.billyon.ui.cashier.addproduct.AddProductViewModel
import co.id.billyon.ui.cashier.dashboard.DashboardViewModel
import co.id.billyon.ui.cashier.product.ProductListViewModel
import co.id.billyon.ui.other.login.LoginViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(DashboardViewModel::class)
    internal abstract fun postListViewModel(viewModel: DashboardViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    internal abstract fun providesLoginViewModel(viewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddProductViewModel::class)
    internal abstract fun providesAddProductViewModel(viewModel: AddProductViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProductListViewModel::class)
    internal abstract fun providesProductListViewModel(viewModel: ProductListViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddCategoryViewModel::class)
    internal abstract fun providesAddCategoryViewModel(viewModel: AddCategoryViewModel) : ViewModel
    //Add more ViewModels here
}