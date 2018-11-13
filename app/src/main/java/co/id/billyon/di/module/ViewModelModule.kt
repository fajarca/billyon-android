package co.id.billyon.di.module

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import co.id.billyon.di.ViewModelFactory
import co.id.billyon.di.ViewModelKey
import co.id.billyon.viewmodel.CashierDashboardViewModel
import co.id.billyon.viewmodel.LoginViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(CashierDashboardViewModel::class)
    internal abstract fun postListViewModel(viewModel: CashierDashboardViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    internal abstract fun providesLoginViewModel(viewModel: LoginViewModel): ViewModel


    //Add more ViewModels here
}