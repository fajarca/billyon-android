package co.id.billyon.base

import android.database.Observable
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {

    private var isLoading = ObservableBoolean()
    private var mCompositeDisposable = CompositeDisposable()

    fun getCompositeDisposable() = mCompositeDisposable


    fun setIsLoading(setAsLoading: Boolean) = isLoading.set(setAsLoading)
    fun getIsLoading() = isLoading

    override fun onCleared() {
        mCompositeDisposable.dispose()
        super.onCleared()
    }

}