package co.id.billyon.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import javax.inject.Inject
import javax.inject.Provider

class ViewModelFactory
@Inject constructor(private val viewModels : MutableMap<Class<out ViewModel>,Provider<ViewModel>>) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = viewModels[modelClass]?.get() as T
}